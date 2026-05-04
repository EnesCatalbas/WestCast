WestCast - Backend & CI/CD Geliştirme
Bu proje, Java ve Spring Boot ekosistemi kullanılarak geliştirilmiş, kurumsal yazılım geliştirme standartlarını ve modern DevOps süreçlerini (CI/CD) temel alan bir backend uygulamasıdır.  
Not: Bu proje şu anda aktif geliştirme aşamasındadır. Şu an için sadece Backend ve API mimarisi kısmen tamamlanmış olup, Frontend geliştirme süreci devam etmektedir.

Teknolojiler ve Araçlar
Backend: Java, Spring Boot, Jakarta EE.  

Veritabanı & Katmanlar: JPA / Hibernate.  

DevOps: Jenkins (CI/CD Pipeline), Maven.  

Test: JUnit, Selenium (Uçtan uca testler).  

Öne Çıkan Özellikler
CI/CD Entegrasyonu: Proje, Jenkins üzerinde yapılandırılmış bir boru hattına (pipeline) sahiptir. Her kod gönderiminde (push) otomatik olarak derleme, test ve raporlama süreçleri çalışmaktadır.  

Yazılım Doğrulama: UserControllerIT gibi entegrasyon testleri ile API uç noktalarının güvenilirliği ve veri tutarlılığı her aşamada kontrol edilmektedir.  

Katmanlı Mimari: Proje, sürdürülebilirlik ve test edilebilirlik için Controller-Service-Repository katmanlı mimarisi üzerine inşa edilmiştir.  

Kurulum ve Çalıştırma
Projeyi klonlayın:

Bash
git clone https://github.com/EnesCatalbas/WestCast.git

2. Maven bağımlılıklarını yükleyin:
   ```bash
   mvn install
Uygulamayı başlatın:

Bash
mvn spring-boot:run
