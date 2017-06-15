package com.tongzhichao.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by tongzhichao on 17-6-15.
 */

public class DefaultItemTouchHelper extends ItemTouchHelper {
    private static final String TAG = "DefaultItemTouchHelper";

    public DefaultItemTouchHelper(ItemListener itemListener) {
        super(new DefaultItemTouchHelpCallback(itemListener));
    }

    public static class DefaultItemTouchHelpCallback extends ItemTouchHelper.Callback {
        private ItemListener itemListener;

        public DefaultItemTouchHelpCallback(ItemListener itemListener) {
            this.itemListener = itemListener;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            Log.d(TAG, "getMovementFlags");
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {// GridLayoutManager
                // flag如果值是0，相当于这个功能被关闭
                int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlag = 0;
                // create make
                return makeMovementFlags(dragFlag, swipeFlag);
            } else if (layoutManager instanceof LinearLayoutManager) {// linearLayoutManager
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int orientation = linearLayoutManager.getOrientation();

                int dragFlag = 0;
                int swipeFlag = 0;

                // 为了方便理解，相当于分为横着的ListView和竖着的ListView
                if (orientation == LinearLayoutManager.HORIZONTAL) {// 如果是横向的布局
                    swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else if (orientation == LinearLayoutManager.VERTICAL) {// 如果是竖向的布局，相当于ListView
                    dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlag, swipeFlag);
            }
            return 0;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Log.d(TAG, "onMove");
            itemListener.call("onMove", viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Log.d(TAG, "onSwiped");
            itemListener.onSwiped(viewHolder.getAdapterPosition(), direction == 8);
        }


    }

    public interface ItemListener {
        void call(String type, int a, int b);

        void onSwiped(int position, boolean toright);
    }
}
