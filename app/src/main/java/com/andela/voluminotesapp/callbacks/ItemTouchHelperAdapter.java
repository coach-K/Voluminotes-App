package com.andela.voluminotesapp.callbacks;


import android.support.v7.widget.RecyclerView;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(RecyclerView.ViewHolder viewHolder, int fromPosition);

    void onItemDismiss(int position);
}
