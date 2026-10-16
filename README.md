# K240P Dashboard — Android 13 / 10 inç yatay ekran

Bu proje, K240P Android multimedya ekranı için düzenlenebilir bir Android Studio kaynak projesidir. **Bu ZIP'in içinde önceden derlenmiş APK yoktur**; APK Windows bilgisayarında Android Studio ile oluşturulur.

## Bu sürümde
- Yatay ekran ve tam ekran koyu temalı gösterge paneli
- Uygulama çekmecesi: cihazda açılabilir uygulamaları listeler
- Google Haritalar ve Yandex Navigasyon açma seçenekleri
- Spotify, YouTube Music ve YouTube açma kısayolları
- Önceki / oynat-duraklat / sonraki medya tuşları (cihazdaki aktif medya oturumu destekliyorsa çalışır)
- Ayarlar kısayolu

## Önemli sınırlar
- Harita paneli canlı haritayı içine gömmez; seçilen navigasyon uygulamasını açar.
- Android'de Apple Haritalar'ın yerel uygulaması yoktur; bu seçenek Apple Haritalar web sayfasını açar.
- Medya tuşları Spotify/YouTube Music uygulamasının sürümüne ve cihazın medya oturumuna bağlıdır. Gerçek araç üzerinde test edilmelidir.
- K240P cihazının üretici yazılımı/launcher kısıtlamalarına göre varsayılan ana ekran olarak seçilemeyebilir.

## Windows'ta APK oluşturma
1. Android Studio'yu resmi sayfadan kurun: https://developer.android.com/studio
2. Android Studio'yu açıp **Open** seçin ve bu ZIP'ten çıkardığınız `K240P_Dashboard` klasörünü seçin.
3. İlk açılışta Android SDK ve Gradle eklentileri indirilebilir; internet bağlantısına izin verin.
4. Proje senkronizasyonu tamamlanınca **Build > Build APK(s)** seçin.
5. Derleme başarılı olursa Android Studio bildirimindeki **locate** bağlantısına basın. APK genellikle `app\build\outputs\apk\debug\app-debug.apk` konumuna yazılır.
6. APK'yı USB bellekle veya güvenilir bir dosya aktarımıyla multimedya cihazına taşıyın. Cihazda dosyayı açıp yükleyin; sorulursa yalnızca bu kurulum için bilinmeyen kaynaklardan yüklemeye izin verin.
7. Önce normal uygulama olarak test edin. Sorunsuz çalıştığından emin olmadan varsayılan launcher yapmayın.

## Derleme ayarları
- Namespace / applicationId: `com.edboutique.dashboard`
- minSdk: 26
- compileSdk: 35
- targetSdk: 35
- Android Gradle Plugin: 8.7.3
- Kotlin Android plugin: 2.0.21

İlk derleme sırasında Android Studio gerekli SDK bileşenlerini indirir. Bu ortamda Android SDK/Gradle bulunmadığı için APK'yı burada derleyip cihazda test ettiğimi söyleyemem.
