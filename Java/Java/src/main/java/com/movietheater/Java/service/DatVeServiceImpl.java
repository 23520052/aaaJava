package com.movietheater.Java.service;

import com.movietheater.Java.dto.*;
import com.movietheater.Java.entity.*;
import com.movietheater.Java.exception.BookingException;
import com.movietheater.Java.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DatVeServiceImpl implements DatVeService {
    @Autowired private DatChoRepository datChoRepo;
    @Autowired private ChiTietDatChoRepository chiTietRepo;
    @Autowired private ThanhToanRepository thanhToanRepo;
    @Autowired private GheRepository gheRepo;
    @Autowired private GiaGheRepository giaGheRepo;
    @Autowired private KhachHangRepository khachHangRepo;
    @Autowired private SuatChieuRepository suatChieuRepo;

    private static final Logger logger = Logger.getLogger(DatVeServiceImpl.class.getName());

    // Cache giá ghế theo loại
    private final Map<String, Double> giaGheCache = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public String datVe(BookingRequestDTO dto) {
        // Tạo mã đặt
        String maDat = "DAT" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Tìm khách hàng và suất chiếu
        KhachHang kh = khachHangRepo.findById(dto.getMaKhachHang())
                .orElseThrow(() -> new BookingException("Không tìm thấy khách hàng"));

        SuatChieu suat = suatChieuRepo.findById(dto.getMaSuatChieu())
                .orElseThrow(() -> new BookingException("Không tìm thấy suất chiếu"));

        // Tạo đối tượng đặt chỗ
        DatCho datCho = new DatCho();
        datCho.setMaDat(maDat);
        datCho.setKhachHang(kh);
        datCho.setSuatChieu(suat);
        datCho.setNgayDat(LocalDateTime.now());

        datChoRepo.save(datCho);

        for (String maGhe : dto.getMaGheList()) {
            Ghe ghe = gheRepo.findById(maGhe)
                    .orElseThrow(() -> new BookingException("Không tìm thấy ghế: " + maGhe));

            if (!"Trống".equalsIgnoreCase(ghe.getTrangThai())) {
                throw new BookingException("Ghế không khả dụng: " + maGhe);
            }

            int updated = gheRepo.updateTrangThaiIfAvailable(maGhe, "Đang giữ chỗ", "Trống");
            if (updated == 0) {
                throw new BookingException("Ghế đã bị người khác giữ chỗ: " + maGhe);
            }

            double gia = giaGheCache.computeIfAbsent(
                    ghe.getGiaGhe().getLoaiGhe(),
                    loai -> giaGheRepo.findById(loai)
                            .orElseThrow(() -> new BookingException("Không tìm thấy loại ghế: " + loai))
                            .getGia()
            );

            ChiTietDatCho chiTiet = new ChiTietDatCho();
            chiTiet.setDatCho(datCho); // datCho đã được save ở trên
            chiTiet.setGhe(ghe);
            chiTiet.setGia(gia);

            chiTietRepo.save(chiTiet);
        }

        logger.info("Đã đặt giữ chỗ thành công với mã: " + maDat);
        return maDat;
    }


    @Override
    @Transactional
    public void huyVe(CancelBookingDTO dto) {
        DatCho datCho = datChoRepo.findById(dto.getMaDat())
                .orElseThrow(() -> new BookingException("Không tìm thấy mã đặt chỗ: " + dto.getMaDat()));

        if (thanhToanRepo.existsByDatCho_MaDat(dto.getMaDat())) {
            throw new BookingException("Vé đã được thanh toán, không thể hủy");
        }

        List<ChiTietDatCho> chiTiets = chiTietRepo.findByDatCho(datCho);
        for (ChiTietDatCho ct : chiTiets) {
            Ghe ghe = ct.getGhe();
            ghe.setTrangThai("Trống");
            gheRepo.save(ghe);
        }

        chiTietRepo.deleteAll(chiTiets);
        datChoRepo.delete(datCho);
        logger.info("Đã hủy đặt chỗ: " + dto.getMaDat());
    }

    @Override
    @Transactional
    public PaymentResultDTO thanhToan(PaymentDTO dto) {
        if (thanhToanRepo.existsByDatCho_MaDat(dto.getMaDat())) {
            throw new BookingException("Vé đã được thanh toán trước đó");
        }

        DatCho datCho = datChoRepo.findById(dto.getMaDat())
                .orElseThrow(() -> new BookingException("Không tìm thấy đặt chỗ với mã: " + dto.getMaDat()));

        List<ChiTietDatCho> chiTiets = chiTietRepo.findByDatCho(datCho);
        double tongTienTinhToan = chiTiets.stream().mapToDouble(ChiTietDatCho::getGia).sum();

        if (Math.abs(dto.getTongTien().doubleValue() - tongTienTinhToan) > 0.001) {
            throw new BookingException("Tổng tiền không khớp với hệ thống");
        }

        for (ChiTietDatCho ct : chiTiets) {
            Ghe ghe = ct.getGhe();
            ghe.setTrangThai("Đã đặt");
            gheRepo.save(ghe);
        }

        ThanhToan thanhToan = new ThanhToan();
        thanhToan.setMaThanhToan("PAY" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        thanhToan.setDatCho(datCho);
        thanhToan.setPhuongThuc(dto.getPhuongThuc());
        thanhToan.setTongTien(tongTienTinhToan);
        thanhToan.setNgayThanhToan(LocalDate.now());

        thanhToanRepo.save(thanhToan);

        List<String> gheIds = chiTiets.stream()
                .map(ct -> ct.getGhe().getMaGhe())
                .collect(Collectors.toList());

        logger.info("Đã thanh toán thành công mã đặt chỗ: " + dto.getMaDat());

        return new PaymentResultDTO(
                thanhToan.getMaThanhToan(),
                thanhToan.getNgayThanhToan(),
                thanhToan.getTongTien(),
                thanhToan.getPhuongThuc(),
                gheIds
        );
    }


    @Override
    public BookingInfoDTO layThongTinDatCho(String maDat) {
        DatCho datCho = datChoRepo.findById(maDat)
                .orElseThrow(() -> new BookingException("Không tìm thấy đặt chỗ"));

        List<String> gheList = chiTietRepo.findByDatCho(datCho).stream()
                .map(ct -> ct.getGhe().getMaGhe())
                .collect(Collectors.toList());

        String trangThai = thanhToanRepo.existsByDatCho_MaDat(maDat) ? "Đã thanh toán" : "Chưa thanh toán";

        BookingInfoDTO info = new BookingInfoDTO();
        info.setMaDat(maDat);
        info.setNgayDat(datCho.getNgayDat());
        info.setSuatChieuId(datCho.getSuatChieu().getMaSuat());
        info.setKhachHangId(datCho.getKhachHang().getMaKh());
        info.setTrangThaiThanhToan(trangThai);
        info.setDanhSachGhe(gheList);

        return info;
    }


    @Scheduled(fixedRate = 300000) // 5 phút
    @Transactional
    public void huyChoTuDong() {
        LocalDateTime thoiGian = LocalDateTime.now().minusMinutes(5);
        List<DatCho> danhSachHetHan = datChoRepo.findByThanhToanIsNullAndNgayDatBefore(thoiGian);

        for (DatCho datCho : danhSachHetHan) {
            List<ChiTietDatCho> chiTiets = chiTietRepo.findByDatCho(datCho);
            for (ChiTietDatCho ct : chiTiets) {
                Ghe ghe = ct.getGhe();
                ghe.setTrangThai("Trống");
                gheRepo.save(ghe);
            }
            chiTietRepo.deleteAll(chiTiets);
            datChoRepo.delete(datCho);
            logger.info("Đã hủy giữ chỗ quá hạn: " + datCho.getMaDat());
        }
    }
}
