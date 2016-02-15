package com.andela.voluminotesapp.adapters.views;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andela.voluminotesapp.R;

/**
 * Created by andela on 2/15/16.
 */
public class WelcomeItem extends RecyclerView.ViewHolder {
    public TextView textView;
    public CardView welcomeCard;

    public WelcomeItem(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        welcomeCard = (CardView) itemView.findViewById(R.id.welcomeCard);
    }
}
