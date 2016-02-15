package com.andela.voluminotesapp.adapters;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.notelib.note.Folders;
import com.andela.notelib.note.Note;
import com.andela.notelib.note.NoteList;
import com.andela.notelib.note.PaperNote;
import com.andela.notelib.note.TodoNote;
import com.andela.notelib.util.DateFormatter;
import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.adapters.views.MasterViewHolder;
import com.andela.voluminotesapp.callbacks.FragmentRecyclerListener;
import com.andela.voluminotesapp.callbacks.ItemTouchHelperAdapter;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<MasterViewHolder>
        implements ItemTouchHelperAdapter {

    private NoteList<Note> mItems;
    private FragmentRecyclerListener fragmentRecyclerListener;
    private int layout;
    private int type;
    private boolean restore;
    private Context context;

    public NotesRecyclerAdapter(Context context, NoteList<Note> mItems, int layout, int type) {
        this.context = context;
        this.layout = layout;
        this.mItems = mItems;
        this.type = type;
    }

    public void setNoteList(NoteList<Note> mItems) {
        this.mItems = mItems;
    }

    @Override
    public MasterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MasterViewHolder(LayoutInflater.from(parent.getContext()).inflate(this.layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MasterViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fragmentRecyclerListener.onNoteTouch(type, position);
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentRecyclerListener.onNoteClick(type, position);
            }
        });

        bindNote(holder, mItems.get(position));
    }

    private void bindNote(MasterViewHolder holder, Note note) {
        switch (note.getFolderId()) {
            case Folders.STATIC_PAPER_NOTE:
                setPaperNote(holder, (PaperNote) note);
                break;
            case Folders.STATIC_TODO_NOTE:
                setTodoNote(holder, (TodoNote) note);
                break;
            case Folders.STATIC_AUDIO_NOTE:
                //setFileNote(holder, (FileNote) note);
                break;
            case Folders.STATIC_DRAW_NOTE:
                //setFileNote(holder, (FileNote) note);
                break;
            default:
                break;
        }
    }

    public void setPaperNote(MasterViewHolder holder, PaperNote paperNote) {
        holder.text.setText(paperNote.getTitle());
        holder.date.setText(DateFormatter.getTimeAgo(paperNote.getDate()));
        setColor(holder, paperNote.getBackground());
    }

    public void setTodoNote(MasterViewHolder holder, TodoNote todoNote) {
        holder.text.setText(todoNote.getTodos().get(0).getTitle());
        holder.date.setText(DateFormatter.getTimeAgo(todoNote.getDate()));
    }

    private void setColor(MasterViewHolder holder, int color) {
        switch (color) {
            case R.drawable.note_white:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.note_white));
                break;
            case R.drawable.note_blue:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.note_blue));
                break;
            case R.drawable.note_green:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.note_green));
                break;
            case R.drawable.note_pink:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.note_pink));
                break;
            case R.drawable.note_yellow:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.note_yellow));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public boolean onItemMove(RecyclerView.ViewHolder viewHolder, final int fromPosition) {
        fragmentRecyclerListener.onViewDrag(viewHolder, fromPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        fragmentRecyclerListener.onNoteDelete(type, isRestore(), mItems.get(position));
        notifyDataSetChanged();
        this.restore = false;
    }

    public void setFragmentRecyclerListener(FragmentRecyclerListener fragmentRecyclerListener) {
        this.fragmentRecyclerListener = fragmentRecyclerListener;
    }

    public boolean isRestore() {
        return restore;
    }

    public void setRestore(boolean restore) {
        this.restore = restore;
    }

    public void deleteAllNotes() {
        this.restore = false;
        for (int i = this.mItems.size()-1; i >= 0 ; i--) {
            onItemDismiss(i);
        }
    }
}
