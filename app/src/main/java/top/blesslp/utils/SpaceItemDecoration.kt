package top.blesslp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

/**
 * @author blesslp
 * Date: 2020/6/20
 * Desc: 自动均分GridLayoutManager条目
 */
class SpaceItemDecoration(val itemWidthDp: Int, val colCount: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {

        val index = parent.getChildAdapterPosition(view) % colCount
        val totalItemWidth = SizeUtils.dp2px(itemWidthDp * colCount.toFloat())
        val parentTotalWidth = parent.measuredWidth - parent.paddingLeft - parent.paddingRight
        val verticalSpace = (parentTotalWidth - totalItemWidth) / colCount
        val horizontalSpace = verticalSpace / (colCount - 1)
        outRect.left = horizontalSpace * index
        outRect.bottom = verticalSpace
    }
}