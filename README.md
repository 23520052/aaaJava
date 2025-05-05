# aaaJava

# 🎬 Cinema Backend – Spring Boot (Oracle DB)

## ✅ Yêu cầu cài đặt
1. Clone project 
2. jdk24 (này là tui dùng, mn có thể tự cài lại theo jdk trên máy mn nếu muốn) 
3. Maven (cài nếu chưa có) 
4. Oracle database
   - Cấu hình csdl
   - Trong src/resources/application.properties: đổi lại phần url, username, password 
5. IDE: IntelliJ IDEA
   - Có cài Lombok plugin (bắt buộc nếu dùng @Data, @NoArgsConstructor) : tự động sinh ra getter/setter, toString(), equals() và hashCode(), ...
       + Vào Settings → Plugins → tìm Lombok và cài đặt.
       + Sau đó bật: Enable annotation processing ở Settings > Build > Compiler > Annotation Processors.
   - Nếu ko cài Lombok, mn có thể tự thêm các get/set ở các class trong mục entity 
