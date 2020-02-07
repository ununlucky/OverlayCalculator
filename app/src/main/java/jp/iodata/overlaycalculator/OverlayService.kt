package jp.iodata.overlaycalculator

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.Button
import android.widget.TextView
import timber.log.Timber


class OverlayService : Service() {

    companion object {
        val START_DRAW = "START_DRAW"
    }

    private val wm: WindowManager by lazy { applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val overlayView: DraggableLayout by lazy { LayoutInflater.from(this).inflate(R.layout.view_layout_overlay, null) as DraggableLayout }
    private var currentX: Float = 0f   //Viewの左辺座標：X軸
    private var currentY: Float = 0f   //Viewの上辺座標：Y軸
    private var offsetX: Float = 0f    //画面タッチ位置の座標：X軸
    private var offsetY: Float = 0f    //画面タッチ位置の座標：Y軸

    private lateinit var answer: TextView

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
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            overlayMode,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
            .also {
                it.gravity = Gravity.TOP or Gravity.START
            }

        overlayView.setOnTouchListener { view, motionEvent ->
            return@setOnTouchListener overlayTouchListener(view, motionEvent, params)
        }

        answer = overlayView.findViewById(R.id.tv_answer)
        overlayView.findViewById<Button>(R.id.btn_num0).setOnClickListener { onClickNum(0) }
        overlayView.findViewById<Button>(R.id.btn_num1).setOnClickListener { onClickNum(1) }
        overlayView.findViewById<Button>(R.id.btn_num2).setOnClickListener { onClickNum(2) }
        overlayView.findViewById<Button>(R.id.btn_num3).setOnClickListener { onClickNum(3) }
        overlayView.findViewById<Button>(R.id.btn_num4).setOnClickListener { onClickNum(4) }
        overlayView.findViewById<Button>(R.id.btn_num5).setOnClickListener { onClickNum(5) }
        overlayView.findViewById<Button>(R.id.btn_num6).setOnClickListener { onClickNum(6) }
        overlayView.findViewById<Button>(R.id.btn_num7).setOnClickListener { onClickNum(7) }
        overlayView.findViewById<Button>(R.id.btn_num8).setOnClickListener { onClickNum(8) }
        overlayView.findViewById<Button>(R.id.btn_num9).setOnClickListener { onClickNum(9) }

        try {
            wm.addView(overlayView, params)
        } catch (e: Exception) {
            Timber.w(e)
        }

        return START_STICKY
    }

    private fun onClickNum(num: Int) {
        Timber.d("onClick: $num")
        val current = "${answer.text.toString().replace(",", "")}$num"
        answer.text = "%,d".format(current.toLong())
    }

    private fun overlayTouchListener(view: View, event: MotionEvent, params: WindowManager.LayoutParams): Boolean {
        val x = event.rawX
        val y = event.rawY

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //x,yセット
                (view.layoutParams as WindowManager.LayoutParams).let {
                    currentX = it.x.toFloat()
                    currentY = it.y.toFloat()
                }
                offsetX = x
                offsetY = y
            }
            MotionEvent.ACTION_MOVE -> {
                var diffX = offsetX - x
                var diffY = offsetY - y

                currentX -= diffX
                currentY -= diffY
                offsetX = x
                offsetY = y

                params.x = currentX.toInt()
                params.y = currentY.toInt()
                wm.updateViewLayout(overlayView, params)
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy !!!!!!")
        wm.removeView(overlayView)
    }
}