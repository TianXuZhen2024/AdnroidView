package com.xuzhen.library_base.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.xuzhen.library_base.R
import com.xuzhen.library_base.viewholder.EmptyVH

/**
 * 基类的BaseAdapter
 * @author: TXZ
 * @version: 1.0
 * @date: created by 2024/9/26 23:38
 */
abstract class BaseAdapter<T>(private var items: List<T>) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        private const val EMPTY_VIEW = 100
        internal const val EMPTY_PAYLOAD = 0
    }

    private var mLastPosition = -1

    /**
     * items 转化为 MutableList
     */
    private val mutableItems: MutableList<T>
        get() {
            return when (items) {
                is java.util.ArrayList -> {
                    items as java.util.ArrayList
                }

                is MutableList -> {
                    items as MutableList
                }

                else -> {
                    items.toMutableList().apply { items = this }
                }
            }
        }

    protected fun getItemData(position: Int): T {
        return mutableItems[position]
    }

    protected abstract fun onCreateVHolder(parent: ViewGroup, viewType: Int): ViewHolder

    protected abstract fun onBindVHolder(holder: ViewHolder, position: Int)


    override fun getItemCount(): Int {
        return if (displayEmptyView()) {
            1
        } else{
            items.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (displayEmptyView()) return EMPTY_VIEW
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == EMPTY_VIEW) {
            return EmptyVH(parent, stateView)
        }
        return onCreateVHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is EmptyVH) {
            holder.changeStateView(stateView)
        }
        onBindVHolder(holder, position)
    }


    /**
     * Set state view layout
     * 状态视图的布局id
     *
     * @param layoutResId
     */
    fun setStateViewLayout(context: Context, @LayoutRes layoutResId: Int) {
        stateView = LayoutInflater.from(context).inflate(layoutResId, FrameLayout(context), false)
    }

    /**
     * State view. Attention please: take effect when [items] is empty array.
     * 状态视图，注意：[items]为空数组才会生效
     */
    var stateView: View? = null
        set(value) {
            //之前是否存在空图形
            val oldDisplayEmptyLayout = displayEmptyView()
            field = value
            //现在是否存在空图形
            val newDisplayEmptyLayout = displayEmptyView()
            if (oldDisplayEmptyLayout && !newDisplayEmptyLayout) {
                //之前存在，现在不允许了，那就移除空图形
                notifyItemRemoved(0)
            } else if (newDisplayEmptyLayout && !oldDisplayEmptyLayout) {
                //现在存在，但是之前不存在，就插入空图形
                notifyItemInserted(0)
            } else if (oldDisplayEmptyLayout && newDisplayEmptyLayout) {
                notifyItemChanged(0, EMPTY_PAYLOAD)
            }
        }


    var isStateViewEnable = false
        set(value) {
            val oldDisplayEmptyLayout = displayEmptyView()

            field = value

            val newDisplayEmptyLayout = displayEmptyView()

            if (oldDisplayEmptyLayout && !newDisplayEmptyLayout) {
                notifyItemRemoved(0)
            } else if (newDisplayEmptyLayout && !oldDisplayEmptyLayout) {
                notifyItemInserted(0)
            } else if (oldDisplayEmptyLayout && newDisplayEmptyLayout) {
                notifyItemChanged(0, EMPTY_PAYLOAD)
            }
        }


    /**
     * 判断是否能显示“空状态”布局
     */
    private fun displayEmptyView(list: List<T> = items): Boolean {
//        判断此时如何不存在空图形，同时也允许空图形
        if (stateView == null || !isStateViewEnable) return false
        return list.isEmpty()
    }


    /**
     * setting up a new instance to data;
     *
     * 设置新的数据集合,需要考虑之前界面为空的情况
     *
     * @param list 新数据集
     */
    @SuppressLint("NotifyDataSetChanged")
    open fun submitList(list: List<T>?) {
        val newList = list ?: emptyList()

        mLastPosition = -1

        val oldDisplayEmptyLayout = displayEmptyView()
        val newDisplayEmptyLayout = displayEmptyView(newList)

        if (oldDisplayEmptyLayout && !newDisplayEmptyLayout) {
            items = newList
            notifyItemRemoved(0)
            notifyItemRangeInserted(0, newList.size)
        } else if (newDisplayEmptyLayout && !oldDisplayEmptyLayout) {
            notifyItemRangeRemoved(0, items.size)
            items = newList
            notifyItemInserted(0)
        } else if (oldDisplayEmptyLayout && newDisplayEmptyLayout) {
            items = newList
            notifyItemChanged(0, EMPTY_PAYLOAD)
        } else {
            items = newList
            notifyDataSetChanged()
        }
    }

    /**
     * add one new data，not null.
     * 添加一条新数据，不可为 null。
     */
    open fun add(data: T) {
        if (displayEmptyView()) {
            // 如果之前在显示空布局，需要先移除
            notifyItemRemoved(0)
        }
        if (mutableItems.add(data)) {
            notifyItemInserted(items.size - 1)
        }
    }

    /**
     * 添加一组数据，不可为 null。
     */
    open fun addAll(collection: Collection<T>) {
        if (collection.isEmpty()) return

        if (displayEmptyView()) {
            // 如果之前在显示空布局，需要先移除
            notifyItemRemoved(0)
        }

        val oldSize = items.size
        if (mutableItems.addAll(collection)) {
            notifyItemRangeInserted(oldSize, collection.size)
        }
    }

}