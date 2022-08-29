//https://developer.alexanderklimov.ru/android/simplepaint.php

package ru.panorobot.airshot

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import kotlin.random.Random

class Draw2D(context: Context?) : View(context) {
    private val paint: Paint = Paint()
    private val shots:Shots = Shots()

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val screenWidth: Int = getWidth()
        val screenHeight: Int = getHeight()

        drawTarget(canvas)
        shots.draw(canvas,screenWidth, screenHeight)

    }


    private fun drawTarget(canvas: Canvas?) {
        paint.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas?.drawPaint(paint)

        paint.apply {
            isAntiAlias = true
            color = Color.BLACK
        }

        val screenWidth: Int = getWidth()
        val screenHeight: Int = getHeight()
        val centerX = width / 2
        val centerY = height / 2

        var radiusSun: Int = (screenWidth + screenHeight) / 30
        canvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), radiusSun.toFloat(), paint)

        paint.apply {
            style = Paint.Style.STROKE
        }
        var radius = radiusSun * 2
        canvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)

        radius = radiusSun * 3
        canvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)

        radius = radiusSun * 4
        canvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)

        radius = radiusSun * 5
        canvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)
    }


}