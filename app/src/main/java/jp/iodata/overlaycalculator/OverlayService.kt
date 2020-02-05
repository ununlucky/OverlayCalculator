package jp.iodata.overlaycalculator

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import timber.log.Timber


class OverlayService : Service() {

    companion object {
        val START_DRAW = "START_DRAW"
    }

    private val wm: WindowManager by lazy { applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val view: FrameLayout by lazy { LayoutInflater.from(this).inflate(R.layout.view_layout_overlay, null) as FrameLayout }

    override fun onBind(p0: Intent?): IBinder {
        Timber.d("onBind")
        return LocalBinder()
    }
    inner class LocalBinder : Binder() {
        internal val service: OverlayService
            get() = this@OverlayService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")

        val overlayMode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            overlayMode,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
            .also {
                it.gravity = Gravity.BOTTOM or Gravity.START
            }

        view.findViewById<Button>(R.id.btn_num0).setOnClickListener { onClickNum(0) }
        view.findViewById<Button>(R.id.btn_num1).setOnClickListener { onClickNum(1) }
        view.findViewById<Button>(R.id.btn_num2).setOnClickListener { onClickNum(2) }
        view.findViewById<Button>(R.id.btn_num3).setOnClickListener { onClickNum(3) }
        view.findViewById<Button>(R.id.btn_num4).setOnClickListener { onClickNum(4) }
        view.findViewById<Button>(R.id.btn_num5).setOnClickListener { onClickNum(5) }
        view.findViewById<Button>(R.id.btn_num6).setOnClickListener { onClickNum(6) }
        view.findViewById<Button>(R.id.btn_num7).setOnClickListener { onClickNum(7) }
        view.findViewById<Button>(R.id.btn_num8).setOnClickListener { onClickNum(8) }
        view.findViewById<Button>(R.id.btn_num9).setOnClickListener { onClickNum(9) }

        try {
            wm.addView(view, params)
        } catch (e: Exception) {
            Timber.w(e)
        }

        return START_STICKY
    }

    private fun onClickNum(num: Int) {
        Timber.d("onClick: $num")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy !!!!!!")
        wm.removeView(view)
    }
}