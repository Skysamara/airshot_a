package ru.panorobot.airshot

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.random.Random

class Shots {
    private var arrayOfShots: Array<Shot> = arrayOf(
        Shot(50, 70),
        Shot(200, 100),
        Shot(50, 50),
        Shot(70, 300),
        Shot(10, 20),
    )

    private fun randomizeShots(screenWidth: Int, screenHeight: Int) {
        for (shot in arrayOfShots) {
            shot.x = Random.nextInt(0, screenWidth)
            shot.y = Random.nextInt(0, screenHeight)
        }
    }

    fun draw(canvas: Canvas?, screenWidth: Int, screenHeight: Int) {
        val paintWite: Paint = Paint()
        paintWite.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }

        val paintBlack: Paint = Paint()
        paintBlack.apply {
            style = Paint.Style.FILL
            color = Color.BLACK
        }

        randomizeShots(screenWidth, screenHeight)
        for (shot in arrayOfShots) {
            val x = shot.x
            val y = shot.y

            var radius: Int = (screenWidth + screenHeight) / 90
            canvas!!.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paintWite)

            radius = (screenWidth + screenHeight) / 120
            canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paintBlack)
        }
    }
}