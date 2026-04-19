
# 👥 UserApp

UserApp, JSONPlaceholder API üzerinden kullanıcı verilerini çekip modern bir Android arayüzünde listeleyen ve detaylarını gösteren bir uygulamadır.  

Bu proje; **MVVM mimarisi**, **Jetpack Compose**, **Hilt**, **Retrofit** ve **StateFlow** kullanılarak geliştirilmiştir.


---
## 🚀 Özellikler

### ✅ Temel Özellikler
- API’den kullanıcı verilerini çekme
- Kullanıcıları listeleme (LazyColumn)
- Loading / Error / Success state yönetimi
- Modern Material 3 tasarım

### ⭐ Bonus Özellikler
- 🔍 **Arama (Search):** İsim ve email’e göre filtreleme  
- 📄 **Detay Ekranı:** Kullanıcıya tıklayınca detay sayfasına geçiş  
- 🔄 **Pull-to-Refresh:** Listeyi aşağı çekerek yenileme  
- 🧩 **Hilt (Dependency Injection):** Temiz ve sürdürülebilir mimari  
- 🌙 **Dark Mode:** Açık/Koyu tema desteği  
---
## 🛠️ Kullanılan Teknolojiler ve Mimari

* **Programlama Dili:** [![Kotlin](https://img.shields.io/badge/Kotlin-B125EA?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
* **Kullanıcı Arayüzü (UI):** [![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=flat-square&logo=jetpack-compose&logoColor=white)](https://developer.android.com/jetpack/compose) [![Material Design 3](https://img.shields.io/badge/Material_Design_3-EADDFF?style=flat-square&logo=material-design&logoColor=black)](https://m3.material.io/)
* **Mimari Desen:** [![MVVM](https://img.shields.io/badge/MVVM-3DDC84?style=flat-square&logo=android&logoColor=white)](#)
* **Dependency Injection:** [![Dagger Hilt](https://img.shields.io/badge/Dagger_Hilt-171E2E?style=flat-square&logo=android&logoColor=white)](https://dagger.dev/hilt/)
* **Ağ İstekleri:** [![Retrofit2](https://img.shields.io/badge/Retrofit2-2C3E50?style=flat-square)](#) [![Gson](https://img.shields.io/badge/Gson-F89820?style=flat-square)](#)
* **Asenkron İşlemler:** [![Coroutines & Flow](https://img.shields.io/badge/Coroutines_%26_Flow-B125EA?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/docs/coroutines-overview.html)
* **Navigasyon:** [![Navigation Compose](https://img.shields.io/badge/Navigation_Compose-4285F4?style=flat-square&logo=jetpack-compose&logoColor=white)](https://developer.android.com/jetpack/compose/navigation)
---
## 📦 Proje Yapısı

```text
com.mustafaderinoz.userapp
├── data                      # Veri katmanı (API, model, repository)
│   ├── model                 # Veri modelleri (data class'lar)
│   │   └── User.kt           # Kullanıcı veri modeli
│   ├── remote                # API işlemleri
│   │   ├── ApiService.kt     # Retrofit API endpoint tanımları
│   │   └── RetrofitInstance.kt # Retrofit yapılandırması (base URL vb.)
│   └── repository            # Veri erişim katmanı (abstraction)
│       └── UserRepository.kt # API'den veri çekip ViewModel'e sağlar
│
├── di                        # Dependency Injection (Hilt)
│   └── AppModule.kt          # Retrofit ve ApiService provider'ları
│
├── ui                        # UI katmanı (Jetpack Compose)
│   ├── components            # Tekrar kullanılabilir UI bileşenleri
│   │   └── UserItem.kt       # Kullanıcı liste item tasarımı
│   ├── screen                # Ekranlar
│   │   ├── UserListScreen.kt # Kullanıcı liste ekranı
│   │   └── UserDetailScreen.kt # Kullanıcı detay ekranı
│   └── theme                 # Tema ve stil ayarları
│       ├── Color.kt          # Renk tanımları
│       ├── Theme.kt          # Light/Dark tema ayarları
│       └── Type.kt           # Typography ayarları
│
├── utils                     # Yardımcı fonksiyonlar
│   └── Exceptions.kt         # Hataları kullanıcı dostu mesaja çevirir
│
├── viewmodel                 # UI state yönetimi
│   └── UserViewModel.kt      # UI state, veri çekme ve iş mantığı
│
├── MainActivity.kt           # Uygulama giriş noktası ve navigation yönetimi
└── UserApp.kt                # Hilt Application class
```
---
## 📱 Ekran Görüntüleri

|⏳ Loading |✅ Success  |❌ Error |
|----------------------|------------------|----------------|
| ![Login](screenshots/loading.jpeg) | ![Feed](screenshots/success.jpeg) | ![Upload](screenshots/error.jpeg) |

|🌙 Liste |🌑 Detay  |
|----------------------|------------------|
| ![Login](screenshots/dark1.jpeg) | ![Feed](screenshots/dark2.jpeg) | 
---

## ⚙️ Kurulum ve Çalıştırma

Projeyi yerel ortamında çalıştırmak için aşağıdaki adımları izleyebilirsin:


**1. Depoyu klonla**
```bash
git clone https://github.com/mustafaderinoz/Turkcell-GYGY-KullaniciListesi.git
```
**2. Android Studio ile aç**
- File > Open seçeneği ile projeyi içeri aktar
  
**3. Gradle senkronizasyonu**
- Gerekli bağımlılıkların yüklenmesini bekle
  
**4. Uygulamayı çalıştır**
- Bir emülatör veya gerçek cihaz bağla ve projeyi başlat

---

## 👨‍💻 Geliştirici

**Mustafa Derinöz**

