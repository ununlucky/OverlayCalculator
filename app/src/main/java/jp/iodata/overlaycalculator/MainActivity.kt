package jp.iodata.overlaycalculator

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private val REQUEST_OVERLAY_PERMISSION_1 = 1
        private val REQUEST_OVERLAY_PERMISSION_2 = 2
    }

    private lateinit var btnStart: Button

    private fun Activity.hasOverlayPermission(): Boolean =
        if (Build.VERSION.SDK_INT >= 23) Settings.canDrawOverlays(this) else true

    private fun Activity.requestOverlayPermission(requestCode: Int) {
        if (Build.VERSION.SDK_INT >= 23) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            if (hasOverlayPermission()) {
                startOverlayService()
            } else {
                requestOverlayPermission(REQUEST_OVERLAY_PERMISSION_1)
            }
        }
    }

    private fun startOverlayService() {
        val intent = Intent(this, OverlayService::class.java)
            .setAction(OverlayService.START_DRAW)
        startService(intent)
//        finish()
    }
}
