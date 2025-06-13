
package com.phim.model;
import java.time.LocalDate;
public class NHAN_VIEN {
    private int MaNV; // MaNV
    private String FULLNAME; // FullName
    private String ROLE; // Role
    private String PHONE; // Phone
    private String EMAIL; // Email
    private LocalDate HIREDATE; // HireDate
    private String PASSWORD; // Password

    public NHAN_VIEN(int maNv, String fullName, String role, String phone, String email, LocalDate hireDate, String password) {
        this.MaNV = maNv;
        this.FULLNAME = fullName;
        this.ROLE = role;
        this.PHONE = phone;
        this.EMAIL = email;
        this.HIREDATE = hireDate;
        this.PASSWORD = password;
    }

    public int getMaNv() { return MaNV; }
    public void setMaNv(int maNv) { this.MaNV = maNv; }

    public String getFullName() { return FULLNAME; }
    public void setFullName(String fullName) { this.FULLNAME = fullName; }

    public String getRole() { return ROLE; }
    public void setRole(String role) { this.ROLE = role; }

    public String getPhone() { return PHONE; }
    public void setPhone(String phone) { this.PHONE = phone; }

    public String getEmail() { return EMAIL; }
    public void setEmail(String email) { this.EMAIL = email; }

    public LocalDate getHireDate() { return HIREDATE; }
    public void setHireDate(LocalDate hireDate) { this.HIREDATE = hireDate; }

    public String getPassword() { return PASSWORD; }
    public void setPassword(String password) { this.PASSWORD = password; }

    @Override
    public String toString() {
        return "NHAN_VIEN{" +
                "maNV=" + MaNV +
                ", fullName='" + FULLNAME + '\'' +
                ", role='" + ROLE + '\'' +
                ", phone='" + PHONE + '\'' +
                ", email='" + EMAIL + '\'' +
                ", hireDate=" + HIREDATE +
                '}';
    }
    
}
