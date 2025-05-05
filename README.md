# 🎬 aaaJava - Quy Tắc Quản Lý Git Branch Cho Dự Án Rạp Chiếu Phim

## 1. Nhánh `main`
- **Mô tả**: Đây là nhánh chính và chỉ chứa mã nguồn đã hoàn thiện, kiểm tra kỹ lưỡng và sẵn sàng để triển khai lên môi trường production.

- **Quy tắc**:
  - Không bao giờ commit trực tiếp vào nhánh main.❌
  - Mỗi lần hoàn thành một tính năng hoặc sửa lỗi, cần phải merge vào nhánh develop và sau đó mới merge vào nhánh main khi đã kiểm tra kỹ lưỡng.

## 2. Nhánh `develop`
- **Mô tả**: Đây là nhánh phát triển chính, nơi tất cả các tính năng mới sẽ được phát triển trước khi được kiểm tra và merge vào main. Nó chứa mã nguồn hiện tại đang trong quá trình phát triển và có thể chưa ổn định.

- **Quy tắc**:
  - Là nhánh nơi phát triển và thử nghiệm các tính năng mới (feature branches) và sửa lỗi (bugfix branches).
  - Cập nhật thường xuyên từ main để đảm bảo tính tương thích và tránh xung đột khi phát triển các tính năng mới.
  - Merge tất cả các nhánh tính năng và nhánh sửa lỗi vào develop trước khi chuyển lên main.
- **Quy trình**:
  - Mỗi khi hoàn thành một tính năng hoặc sửa lỗi, nhánh đó sẽ được merge vào develop.
  - Đảm bảo tất cả các tính năng đã qua kiểm tra cơ bản trước khi merge vào develop.

## 3. Nhánh `feature/tên-chức-năng`
- **Mô tả**: Đây là các nhánh dùng để phát triển các tính năng mới của hệ thống. Mỗi tính năng hoặc module sẽ có một nhánh riêng biệt.
- **Quy tắc**:
Mỗi chức năng được phát triển trên một nhánh riêng biệt, tách biệt với các tính năng khác.
Nhánh này sẽ được tạo từ develop và sau khi hoàn thành, sẽ được merge trở lại develop.

- **Quy trình**:
  - Tạo nhánh từ develop cho mỗi tính năng mới.
  - Khi hoàn thành tính năng, tạo một Pull Request (PR) vào nhánh develop để kiểm tra và merge.
 
## 4. Nhánh `bugfix/[ten-bug]`

**Mô tả:**  
Dùng để sửa lỗi phát sinh trong quá trình phát triển (không phải lỗi production).

**Quy tắc:**
- Tạo từ `develop`.
- Sau khi sửa lỗi, merge về lại `develop`.
- Nếu lỗi ảnh hưởng production, chuyển sang dùng `hotfix`.

**Ví dụ đặt tên:**  
`bugfix/sai-thoi-gian-suatchieu`, `bugfix/loi-tinh-gia`

---

## 5. Nhánh `hotfix/[ten-hotfix]`

**Mô tả:**  
Dành cho lỗi nghiêm trọng xảy ra trên production cần xử lý **ngay lập tức**.

**Quy tắc:**
- Tạo từ `main`.
- Sau khi sửa, phải merge vào cả `main` và `develop` để đồng bộ mã nguồn.

**Quy trình:**
1. Phát hiện lỗi production.
2. Tạo nhánh `hotfix/loi-xxx` từ `main`.
3. Sửa và kiểm thử kỹ lưỡng.
4. Merge vào `main` và `develop`.

---

## ✅ Tóm Tắt Quy Trình Làm Việc

| Nhánh         | Tạo từ   | Merge vào         | Mục đích                                |
|---------------|----------|-------------------|------------------------------------------|
| `main`        | —        | —                 | Mã ổn định, sẵn sàng triển khai         |
| `develop`     | `main`   | `main`            | Tích hợp các tính năng và sửa lỗi       |
| `feature/*`   | `develop`| `develop`         | Phát triển tính năng                    |
| `bugfix/*`    | `develop`| `develop`         | Sửa lỗi phát sinh trong quá trình dev   |
| `hotfix/*`    | `main`   | `main`, `develop` | Sửa lỗi khẩn cấp trên môi trường thật   |

---

## 📌 Ghi chú khi làm việc nhóm:

- Mỗi người làm **1 nhánh feature/chức năng hoặc bugfix riêng biệt**.
- Luôn tạo **Pull Request (PR)** khi muốn merge.
- Viết commit rõ ràng: `thêm chức năng quản lý suất chiếu`, `fix: lỗi không chọn được ghế`.
- Kiểm tra mã người khác trước khi merge (`code review`).
- Thường xuyên `pull` từ `develop` để tránh xung đột.
