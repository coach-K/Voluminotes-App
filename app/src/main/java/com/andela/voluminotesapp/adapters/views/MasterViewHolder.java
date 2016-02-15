package com.andela.voluminotesapp.adapters.views;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andela.voluminotesapp.R;


public class MasterViewHolder extends RecyclerView.ViewHolder {
    public TextView text, date;
    public CardView cardView;

    public MasterViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
        date = (TextView) itemView.findViewById(R.id.date);
        cardView = (CardView) itemView.findViewById(R.id.item);
    }
}
