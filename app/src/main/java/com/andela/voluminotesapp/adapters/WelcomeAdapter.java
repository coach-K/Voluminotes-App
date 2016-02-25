package com.andela.voluminotesapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.activities.MyApplication;
import com.andela.voluminotesapp.adapters.views.WelcomeItem;
import com.andela.voluminotesapp.callbacks.WelcomeListener;
import com.andela.voluminotesapp.utilities.Pages;
import com.andela.voluminotesapp.model.Welcome;

import java.util.ArrayList;

/**
 * Created by andela on 2/15/16.
 */
public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeItem> {
    private ArrayList<Welcome> mItems;
    private Context context;
    private WelcomeListener welcomeListener;

    public WelcomeAdapter(Context context) {
        this.context = context;
        populateItem();
    }

    @Override
    public WelcomeItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.welcome_item, parent, false);
        return new WelcomeItem(view);
    }

    @Override
    public void onBindViewHolder(WelcomeItem holder, final int position) {
        Welcome welcome = mItems.get(position);

        holder.textView.setText(welcome.getTitle());
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(context, welcome.getDrawable()), null, null);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeListener.onClick(position);
            }
        });
        holder.welcomeCard.setCardBackgroundColor(
                ContextCompat.getColor(context, welcome.getColor()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void populateItem() {
        mItems = new ArrayList<>();
        mItems.add(new Welcome(Pages.TAKE_A_NOTE, R.mipmap.pencil, R.color.note_green,
                context.getString(R.string.write)));

        mItems.add(new Welcome(Pages.VIEW_ALL_NOTES, R.mipmap.book, R.color.note_yellow,
                String.format("%s (%s)", context.getString(R.string.all_notes),
                        MyApplication.getNoteManager(context).getNotesSize())));

        mItems.add(new Welcome(Pages.VIEW_TRASH, R.mipmap.trash, R.color.note_blue,
                String.format("%s (%s)", context.getString(R.string.trash),
                        MyApplication.getNoteManager(context).getTrashNotesSize())));

        mItems.add(new Welcome(Pages.VIEW_SETTINGS, R.mipmap.ic_settings, R.color.note_pink,
                context.getString(R.string.action_settings)));
    }

    public void setOnWelcomeListener(WelcomeListener welcomeListener) {
        this.welcomeListener = welcomeListener;
    }
}
