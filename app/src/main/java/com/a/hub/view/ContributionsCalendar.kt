package com.a.hub.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min
import kotlin.random.Random


//TODO fake - calendar without data
class ContributionsCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private val rectF: RectF = RectF()
    private val paint: Paint = Paint()

    private val rows = 7
    private val cols = 42 //TODO temp - change with set calendar data

    private val rectSize = 30f
    private val rectMargin = 8f
    private val rectRadius = 5f

    private val defaultColor = Color.parseColor("#ebedf0")
    private val colors = listOf(
        Color.parseColor("#9be9a8"),
        Color.parseColor("#40c463"),
        Color.parseColor("#30a14e"),
        Color.parseColor("#216e39")
    )

    init {
        paint.style = Paint.Style.FILL
        paint.color = defaultColor

        //TODO generate data List<RectF> and draw on cashedBitmap

        /*if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs, R.styleable.ColorCircles, 0, 0
            )
        }*/
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(measureDesiredWidth(), widthSize)
            else -> measureDesiredWidth()

        }
        val height: Int = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(measureDesiredHeight(), heightSize)
            else -> measureDesiredHeight()
        }

        setMeasuredDimension(width, height)

    }

    private fun measureDesiredWidth(): Int {
        val p = paddingStart + paddingEnd
        val s = cols * (rectSize + rectMargin * 2)
        return (p + s).toInt()
    }

    private fun measureDesiredHeight(): Int {
        val p = paddingTop + paddingBottom
        val s = rows * (rectSize + rectMargin/* * 2*/)
        return (p + s + rectMargin).toInt()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        //TODO canvas.drawBitmap(cashedBitmap)

        val left = paddingStart.toFloat()
        val top = paddingTop.toFloat()

        //initial
        rectF.left = left + rectMargin
        rectF.top = top + rectMargin
        rectF.right = rectF.left + rectSize
        rectF.bottom = rectF.top + rectSize

        for (i in 1..cols) {
            for (j in 1..rows) {
                paint.color = randomFakeDataColor()
                canvas?.drawRoundRect(rectF, rectRadius, rectRadius, paint)
                rectF.top += rectSize + rectMargin
                rectF.bottom = rectF.top + rectSize
            }
            rectF.left += rectSize + rectMargin * 2
            rectF.right = rectF.left + rectSize
            rectF.top = top + rectMargin
            rectF.bottom = rectF.top + rectSize
        }
    }

    private fun randomFakeDataColor(): Int {
        if(Random.nextBoolean()) return colors[Random.nextInt(4)]
        return defaultColor
    }
}