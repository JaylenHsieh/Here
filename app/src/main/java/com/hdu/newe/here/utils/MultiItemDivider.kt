package com.hdu.newe.here.utils

/**
 * Created by Jaylen Hsieh on 2018/03/09.
 */

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import java.util.ArrayList

class MultiItemDivider : RecyclerView.ItemDecoration {
    private var mDivider: Drawable? = null
    private var mOrientation: Int = 0

    /**
     * 获取分割线模式
     * @return [.INSIDE],[.END]
     */
    /**
     * 设置分割线模式
     * @param dividerMode [.INSIDE] or [.END]
     */
    var dividerMode = INSIDE
    private var clipChild: Boolean = false

    private var exceptList: MutableList<Int>? = null

    private var cleanOffset = true

    constructor(context: Context, orientation: Int) {
        val a = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        init(orientation, divider)
        a.recycle()
    }

    constructor(context: Context, orientation: Int, drawableId: Int) {
        var divider: Drawable? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            divider = context.getDrawable(drawableId)
        } else {
            divider = context.resources.getDrawable(drawableId)
        }
        init(orientation, divider)
    }

    constructor(context: Context, orientation: Int, divider: Drawable) {
        init(orientation, divider)
    }

    private fun init(orientation: Int, divider: Drawable?) {
        mDivider = divider
        exceptList = ArrayList()
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (mOrientation == HORIZONTAL_LIST) {//以recyclerView的布局设置为标准
            drawHorizontal(c, parent)
        } else {
            drawVertical(c, parent)
        }
    }

    /**
     * 是否卷进padding中
     * @param clipChild
     */
    fun clipToChildPadding(clipChild: Boolean) {
        this.clipChild = clipChild
    }

    /**
     * 垂直方向分割线（横线）收卷到子view的计算
     * @param child
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private fun clipChildVertical(child: View, left: Int, top: Int, right: Int, bottom: Int) {
        //同上
        if (clipChild) {
            val l = left + child.paddingLeft
            val r = right - child.paddingRight
            val t = top - child.paddingTop
            val b = bottom - child.paddingBottom
            //L.d(">>>", l + " -- " + t + " -- " + r + " -- " + b);
            mDivider!!.setBounds(l, t, r, b)
        } else {
            mDivider!!.setBounds(left, top, right, bottom)
        }
    }

    /**
     * 绘制垂直方向分割线（横线）
     * @param c
     * @param parent
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount

        val layout = parent.layoutManager
        val lin = layout as? LinearLayoutManager

        //下边画线
        if (lin != null && lin.orientation == LinearLayoutManager.HORIZONTAL && dividerMode == END) {
            val child = parent.getChildAt(0)
            if (child == null || exceptList!!.contains(0)) {

                return
            }
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + getDividerOffH(parent, child)
            clipChildVertical(child, left, top, right, bottom)
            mDivider!!.draw(c)
            return
        }


        for (i in 0 until childCount) {

            val child = parent.getChildAt(i)


            val position = parent.getChildAdapterPosition(child)
            if (child == null || exceptList!!.contains(position)) {
                //L.d(">>>", "00000======" + position);
                continue
            }

            val params = child.layoutParams as RecyclerView.LayoutParams

            //            if (dividerMode == START) {
            //                final int bottom = child.getTop() + params.bottomMargin;
            //                final int top = bottom - mDivider.getIntrinsicHeight();
            //                ;
            //
            //                mDivider.setBounds(left, top, right, bottom);
            //                mDivider.draw(c);


            val top = child.bottom + params.bottomMargin
            val bottom = top + getDividerOffH(parent, child)
            if (dividerMode == END) {
                clipChildVertical(child, left, top, right, bottom)
                mDivider!!.draw(c)
            } else {//默认绘制 INSIDE
                if (i < childCount - 1) {
                    clipChildVertical(child, left, top, right, bottom)
                    mDivider!!.draw(c)
                }
            }


        }
    }

    /**
     * 添加排除绘制
     * @param index 排除绘制的分割线的索引下标
     */
    fun addExcept(index: Int) {
        if (exceptList == null) {
            exceptList = ArrayList()
        }
        exceptList!!.add(index)
    }

    /**
     * 添加排除绘制
     * @param indexList 排除绘制的分割线的索引下标数组
     */
    fun addExcepts(vararg indexList: Int) {
        if (indexList == null) {
            return
        }
        if (exceptList == null) {
            exceptList = ArrayList()
        }
        for (i in indexList.indices) {
            exceptList!!.add(indexList[i])
        }
    }

    /**
     * 水平方向分割线（竖线）收卷到子view的计算
     * @param child
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private fun clipHorizontal(child: View, left: Int, top: Int, right: Int, bottom: Int) {

        if (clipChild) {
            val l = left - child.paddingRight
            val r = right - child.paddingRight
            val t = top + child.paddingTop
            val b = bottom - child.paddingBottom
            //L.d(">>>", l + " -- " + t + " -- " + r + " -- " + b);
            mDivider!!.setBounds(l, t, r, b)
        } else {
            mDivider!!.setBounds(left, top, right, bottom)
        }
    }

    /**
     * 绘制水平方向分割线（竖线）
     * @param c      画布
     * @param parent RecyclerView
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount

        val layout = parent.layoutManager
        val lin = layout as? LinearLayoutManager

        //右边画线
        if (lin != null && lin.orientation == LinearLayoutManager.VERTICAL && dividerMode == END) {
            val child = parent.getChildAt(0)
            if (child == null || exceptList!!.contains(0)) {
                return
            }
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + getDividerOffW(parent, child)
            clipHorizontal(child, left, top, right, bottom)
            mDivider!!.draw(c)
            return
        }

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (child == null || exceptList!!.contains(position)) {
                continue
            }

            val params = child.layoutParams as RecyclerView.LayoutParams
            //            if (dividerMode == START) {
            //                final int right = child.getRight() + params.rightMargin;
            //                final int left = right - mDivider.getIntrinsicHeight();
            //                mDivider.setBounds(left, top, right, bottom);
            //                mDivider.draw(c);

            val left = child.right + params.rightMargin
            val right = left + getDividerOffW(parent, child)

            if (dividerMode == END) {
                clipHorizontal(child, left, top, right, bottom)
                mDivider!!.draw(c)

            } else {//(dividerMode == INSIDE) 和其它值
                if (i < childCount - 1) {
                    clipHorizontal(child, left, top, right, bottom)
                    mDivider!!.draw(c)
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if (mOrientation == HORIZONTAL_LIST) {
            outRect.set(0, 0, getDividerOffW(parent, view), 0)
        } else {
            outRect.set(0, 0, 0, getDividerOffH(parent, view))
        }
    }

    private fun getDividerOffH(parent: RecyclerView, child: View): Int {
        var h = mDivider!!.intrinsicHeight
        if (h == -1) {
            h = 0
        }
        if (cleanOffset) {
            val position = parent.getChildLayoutPosition(child)
            if (exceptList!!.contains(position)) {
                h = 0
            }
        }
        return h
    }

    private fun getDividerOffW(parent: RecyclerView, child: View): Int {
        var w = mDivider!!.intrinsicWidth
        if (w == -1) {
            w = 0
        }
        if (cleanOffset) {
            val position = parent.getChildLayoutPosition(child)
            if (exceptList!!.contains(position)) {
                w = 0
            }
        }
        return w
    }

    /**
     * 清理未使用的item 装饰，默认清理。offset 位移，装饰view
     * @param c
     */
    fun cleanBlankOffset(c: Boolean) {
        this.cleanOffset = c
    }

    /**
     * 清理不绘制列表
     */
    fun clearExpects() {
        if (exceptList != null) {
            exceptList!!.clear()
        }
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)

        val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        val VERTICAL_LIST = LinearLayoutManager.VERTICAL


        //    public final static int START = 0x02;//第一个item顶部无偏移，没有实现第一个上方分割线
        val INSIDE = 0x00
        val END = 0x01
    }
}