package com.edboutique.dashboard

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ScrollView
import android.media.AudioManager
import android.view.KeyEvent

class MainActivity : Activity() {
    private val bg = Color.rgb(8, 12, 18)
    private val panel = Color.rgb(20, 27, 36)
    private val green = Color.rgb(25, 190, 116)
    private val white = Color.rgb(242, 246, 250)
    private val muted = Color.rgb(159, 173, 188)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        buildDashboard()
    }

    private fun buildDashboard() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setBackgroundColor(bg)
            setPadding(dp(12), dp(10), dp(12), dp(10))
        }
        val side = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(5), dp(4), dp(5), dp(4))
        }
        side.addView(text("K240P", 16, white, true), lp(WRAP, dp(38)))
        side.addView(text("⌂", 26, white, true).also { it.setOnClickListener { buildDashboard() } }, lp(WRAP, dp(48)))
        side.addView(text("▦", 24, muted, true).also { it.setOnClickListener { openAppList() } }, lp(WRAP, dp(48)))
        side.addView(text("⚙", 23, muted, true).also { it.setOnClickListener { openSettings() } }, lp(WRAP, dp(48)))
        root.addView(side, LinearLayout.LayoutParams(dp(64), MATCH))

        val mapCard = card().apply { orientation = LinearLayout.VERTICAL; setPadding(dp(18), dp(14), dp(18), dp(14)) }
        val mapTitle = text("●  NAVİGASYON", 14, green, true)
        mapCard.addView(mapTitle, lp(MATCH, dp(28)))
        val mapArea = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            background = rounded(Color.rgb(32, 48, 57), 18)
            setPadding(dp(14), dp(10), dp(14), dp(10))
        }
        mapArea.addView(text("➤", 52, green, true), lp(WRAP, dp(62)))
        mapArea.addView(text("Haritalar ile yola çık", 20, white, true), lp(WRAP, dp(34)))
        mapArea.addView(text("Navigasyonu başlatmak için dokun", 13, muted, false), lp(WRAP, dp(28)))
        mapArea.setOnClickListener { showMapPicker() }
        mapCard.addView(mapArea, LinearLayout.LayoutParams(MATCH, 0, 1f))
        val navButtons = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        navButtons.addView(actionButton("Harita Seç ▾") { showMapPicker() }, LinearLayout.LayoutParams(0, dp(44), 1f))
        navButtons.addView(space(dp(8), 1))
        navButtons.addView(actionButton("Son kullanılan: Google") { launchMap("Google Haritalar") }, LinearLayout.LayoutParams(0, dp(44), 1f))
        mapCard.addView(navButtons, lp(MATCH, dp(52)))
        root.addView(mapCard, LinearLayout.LayoutParams(0, MATCH, 1.35f).apply { setMargins(dp(5), 0, dp(8), 0) })

        val right = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val music = card().apply { orientation = LinearLayout.VERTICAL; setPadding(dp(16), dp(12), dp(16), dp(10)) }
        music.addView(text("♫  ŞİMDİ ÇALIYOR", 14, green, true), lp(MATCH, dp(28)))
        music.addView(text("Müzik uygulamanı aç", 20, white, true), lp(MATCH, dp(34)))
        music.addView(text("Spotify veya YouTube Music üzerinden", 13, muted, false), lp(MATCH, dp(26)))
        val controls = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER }
        controls.addView(actionButton("◀◀") { sendMediaKey(KeyEvent.KEYCODE_MEDIA_PREVIOUS) }, LinearLayout.LayoutParams(0, dp(42), 1f))
        controls.addView(space(dp(8), 1))
        controls.addView(actionButton("▶ / Ⅱ") { sendMediaKey(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) }, LinearLayout.LayoutParams(0, dp(42), 1f))
        controls.addView(space(dp(8), 1))
        controls.addView(actionButton("▶▶") { sendMediaKey(KeyEvent.KEYCODE_MEDIA_NEXT) }, LinearLayout.LayoutParams(0, dp(42), 1f))
        music.addView(controls, lp(MATCH, dp(50)))
        right.addView(music, LinearLayout.LayoutParams(MATCH, 0, 1f))

        val apps = card().apply { orientation = LinearLayout.VERTICAL; setPadding(dp(14), dp(10), dp(14), dp(10)) }
        apps.addView(text("UYGULAMALAR", 13, muted, true), lp(MATCH, dp(25)))
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER }
        row.addView(appButton("Spotify", "♫", green) { launchPackage("com.spotify.music") }, LinearLayout.LayoutParams(0, MATCH, 1f))
        row.addView(appButton("YouTube", "▶", Color.rgb(255, 55, 55)) { launchPackage("com.google.android.youtube") }, LinearLayout.LayoutParams(0, MATCH, 1f))
        row.addView(appButton("Yt Music", "♫", Color.rgb(255, 80, 90)) { launchPackage("com.google.android.apps.youtube.music") }, LinearLayout.LayoutParams(0, MATCH, 1f))
        row.addView(appButton("Harita Seç", "➤", green) { showMapPicker() }, LinearLayout.LayoutParams(0, MATCH, 1f))
        apps.addView(row, LinearLayout.LayoutParams(MATCH, 0, 1f))
        right.addView(space(1, dp(9)))
        right.addView(apps, LinearLayout.LayoutParams(MATCH, 0, 1f))
        root.addView(right, LinearLayout.LayoutParams(0, MATCH, 1f))
        setContentView(root)
    }

    private fun card() = LinearLayout(this).apply { background = rounded(panel, 20) }
    private fun text(value: String, size: Int, color: Int, bold: Boolean) = TextView(this).apply {
        text = value; textSize = size.toFloat(); setTextColor(color); gravity = Gravity.CENTER
        if (bold) setTypeface(typeface, Typeface.BOLD)
        maxLines = 2
    }
    private fun actionButton(label: String, action: () -> Unit) = Button(this).apply {
        text = label; textSize = 12f; isAllCaps = false; setTextColor(white)
        background = rounded(Color.rgb(39, 53, 66), 12); setOnClickListener { action() }
    }
    private fun appButton(label: String, symbol: String, accent: Int, action: () -> Unit) = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER
        addView(TextView(this@MainActivity).apply {
            text = symbol; textSize = 25f; setTextColor(accent); gravity = Gravity.CENTER
            background = rounded(Color.rgb(34, 44, 55), 14)
        }, LinearLayout.LayoutParams(dp(52), dp(48)))
        addView(text(label, 11, white, true), lp(MATCH, dp(28)))
        setOnClickListener { action() }
    }
    private fun rounded(color: Int, radius: Int) = GradientDrawable().apply { setColor(color); cornerRadius = dp(radius).toFloat() }
    private fun space(w: Int, h: Int) = View(this).apply { layoutParams = LinearLayout.LayoutParams(w, h) }
    private fun lp(w: Int, h: Int) = LinearLayout.LayoutParams(w, h)
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun showMapPicker() {
        val choices = arrayOf("Google Haritalar", "Yandex Navigasyon", "Apple Haritalar (web)")
        AlertDialog.Builder(this)
            .setTitle("Navigasyon uygulamasını seç")
            .setItems(choices) { _, which -> launchMap(choices[which]) }
            .setNegativeButton("İptal", null)
            .show()
    }

    private fun launchMap(provider: String) {
        when (provider) {
            "Google Haritalar" -> {
                val navigation = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="))
                    .setPackage("com.google.android.apps.maps")
                try { startActivity(navigation) }
                catch (_: Exception) {
                    if (!launchPackage("com.google.android.apps.maps")) {
                        Toast.makeText(this, "Google Haritalar yüklü değil.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            "Yandex Navigasyon" -> {
                val yandexUris = listOf("yandexnavi://", "yandexmaps://")
                var opened = false
                for (uri in yandexUris) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                        opened = true
                        break
                    } catch (_: Exception) { }
                }
                if (!opened && !launchPackage("ru.yandex.yandexnavi") && !launchPackage("ru.yandex.yandexmaps")) {
                    Toast.makeText(this, "Yandex Navigasyon veya Yandex Haritalar yüklü değil.", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                // Apple Maps has no native Android app; open Apple's Maps website instead.
                try { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.apple.com/"))) }
                catch (_: Exception) { Toast.makeText(this, "Apple Haritalar web sayfası açılamadı.", Toast.LENGTH_LONG).show() }
            }
        }
    }
    private fun launchMusic() {
        val choices = arrayOf("Spotify", "YouTube Music", "YouTube")
        val packages = arrayOf("com.spotify.music", "com.google.android.apps.youtube.music", "com.google.android.youtube")
        AlertDialog.Builder(this)
            .setTitle("Müzik uygulamasını seç")
            .setItems(choices) { _, which -> launchPackage(packages[which]) }
            .setNegativeButton("İptal", null)
            .show()
    }

    private fun sendMediaKey(keyCode: Int) {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        try {
            audioManager.dispatchMediaKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
            audioManager.dispatchMediaKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
        } catch (_: Exception) {
            Toast.makeText(this, "Medya kontrolü desteklenmiyor veya aktif bir müzik uygulaması yok.", Toast.LENGTH_LONG).show()
        }
    }
    private fun launchPackage(pkg: String): Boolean {
        val intent = packageManager.getLaunchIntentForPackage(pkg)
        if (intent == null) { Toast.makeText(this, "$pkg yüklü değil. Önce multimedya cihazına kurun.", Toast.LENGTH_LONG).show(); return false }
        return try { startActivity(intent); true } catch (_: Exception) { false }
    }
    private fun openUrl(uri: String) {
        try { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri))) }
        catch (_: Exception) { Toast.makeText(this, "Bağlantı açılamadı.", Toast.LENGTH_LONG).show() }
    }
    private fun openSettings() { startActivity(Intent(android.provider.Settings.ACTION_SETTINGS)) }
    private fun openAppList() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(12), dp(18), dp(12))
            setBackgroundColor(bg)
        }
        val header = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        header.addView(text("←", 28, white, true).apply { setOnClickListener { buildDashboard() } }, lp(dp(54), dp(52)))
        header.addView(text("Uygulamalar", 24, white, true), LinearLayout.LayoutParams(0, dp(52), 1f))
        root.addView(header, lp(MATCH, dp(56)))

        val scroll = ScrollView(this)
        val grid = android.widget.GridLayout(this).apply { columnCount = 5; useDefaultMargins = true }
        val launcherIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val activities = packageManager.queryIntentActivities(launcherIntent, 0)
            .filter { it.activityInfo.packageName != packageName }
            .sortedBy { it.loadLabel(packageManager).toString().lowercase() }
        if (activities.isEmpty()) {
            root.addView(text("Cihazda başlatılabilir uygulama bulunamadı.", 16, muted, false), lp(MATCH, dp(60)))
        } else {
            activities.forEach { info ->
                val app = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER
                    setPadding(dp(5), dp(8), dp(5), dp(8))
                    background = rounded(Color.rgb(17, 25, 35), 14)
                    setOnClickListener {
                        try { startActivity(packageManager.getLaunchIntentForPackage(info.activityInfo.packageName)) }
                        catch (_: Exception) { Toast.makeText(this@MainActivity, "Uygulama açılamadı", Toast.LENGTH_SHORT).show() }
                    }
                }
                val icon = info.loadIcon(packageManager)
                app.addView(TextView(this).apply {
                    text = ""
                    gravity = Gravity.CENTER
                    setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null)
                }, LinearLayout.LayoutParams(MATCH, dp(56)))
                app.addView(text(info.loadLabel(packageManager).toString(), 11, white, true), lp(MATCH, dp(36)))
                val params = android.widget.GridLayout.LayoutParams().apply {
                    width = 0; height = dp(102); columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNDEFINED, 1f)
                    setMargins(dp(4), dp(4), dp(4), dp(4))
                }
                grid.addView(app, params)
            }
            scroll.addView(grid)
            root.addView(scroll, LinearLayout.LayoutParams(MATCH, 0, 1f))
        }
        setContentView(root)
    }

    companion object { const val MATCH = ViewGroup.LayoutParams.MATCH_PARENT; const val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT }
}
