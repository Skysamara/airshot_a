//https://developer.alexanderklimov.ru/android/simplepaint.php

package ru.panorobot.airshot

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Target(context: Context?) : View(context) {

    private val paint: Paint = Paint()
    private val shots:Shots = Shots()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val screenWidth: Int = getWidth()
        val screenHeight: Int = getHeight()

        drawTarget(canvas, screenWidth, screenHeight)
        shots.draw(canvas,screenWidth, screenHeight)
    }


    private fun drawTarget(canvas: Canvas?, screenWidth: Int, screenHeight: Int) {
        paint.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas?.drawPaint(paint)

        paint.apply {
            isAntiAlias = true
            color = Color.BLACK
        }

        val centerX = width / 2
        val centerY = height / 2

        var radiusApple: Int = (screenWidth + screenHeight) / 30
        canvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), radiusApple.toFloat(), paint)

        paint.apply {
            style = Paint.Style.STROKE
        }
        var radius = radiusApple * 2
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)

        radius = radiusApple * 3
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)

        radius = radiusApple * 4
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)

        radius = radiusApple * 5
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)
    }


}