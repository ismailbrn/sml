@echo off
setlocal
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle komutu bulunamadi.
  echo Bu klasoru Android Studio ile acin ve Build ^> Build APK(s) secin.
  pause
  exit /b 1
)
gradle assembleDebug
if errorlevel 1 (
  echo Derleme basarisiz. Android Studio Build penceresindeki hata ayrintilarini kontrol edin.
  pause
  exit /b 1
)
echo APK: app\build\outputs\apk\debug\app-debug.apk
pause
