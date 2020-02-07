package jp.iodata.overlaycalculator

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

class DraggableLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attrs, defStyle) {

//    var currentX: Float = 0f   //Viewの左辺座標：X軸
//    var currentY: Float = 0f   //Viewの上辺座標：Y軸
//    var offsetX: Float = 0f    //画面タッチ位置の座標：X軸
//    var offsetY: Float = 0f    //画面タッチ位置の座標：Y軸
//
//    init {
//
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        val x = event?.rawX ?: 0f
//        val y = event?.rawY ?: 0f
//
//        when (event?.action) {
//            MotionEvent.ACTION_DOWN -> {
//                //x,yセット
//                currentX = getLeft().toFloat()
//                currentY = getTop().toFloat()
//                offsetX = x
//                offsetY = y
//            }
//            MotionEvent.ACTION_MOVE -> {
//                var diffX = offsetX - x
//                var diffY = offsetY - y
//
//                currentX -= diffX
//                currentY -= diffY
//                //画像の移動
//                layout(
//                    currentX.toInt(),
//                    currentY.toInt(),
//                    currentX.toInt() + width,
//                    currentY.toInt() + height
//                )
//                offsetX = x
//                offsetY = y
//            }
//        }
//        return true
//    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}