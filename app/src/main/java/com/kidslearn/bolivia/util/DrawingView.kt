package com.kidslearn.bolivia.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val drawPaint = Paint().apply {
        color = Color.parseColor("#D81B60")
        style = Paint.Style.STROKE
        strokeWidth = 28f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }
    private var path = Path()
    private val paths = mutableListOf<Path>()
    var drawCount = 0
        private set

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (p in paths) canvas.drawPath(p, drawPaint)
        canvas.drawPath(path, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path = Path()
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
            MotionEvent.ACTION_UP -> {
                paths.add(path)
                path = Path()
                drawCount++
            }
        }
        invalidate()
        return true
    }

    fun clear() {
        paths.clear()
        path = Path()
        drawCount = 0
        invalidate()
    }

    fun setColor(color: Int) {
        drawPaint.color = color
    }
}
