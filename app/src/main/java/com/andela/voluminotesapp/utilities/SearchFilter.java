package com.andela.voluminotesapp.utilities;

import android.content.Context;
import android.widget.Filter;

import com.andela.notelib.note.Note;
import com.andela.notelib.note.NoteList;
import com.andela.notelib.note.PaperNote;
import com.andela.voluminotesapp.activities.MyApplication;
import com.andela.voluminotesapp.adapters.NotesRecyclerAdapter;

/**
 * Created by andela on 2/14/16.
 */
public class SearchFilter extends Filter {
    private NotesRecyclerAdapter notesRecyclerAdapter;
    private NoteList<Note> notes;

    public SearchFilter(Context context, NotesRecyclerAdapter notesRecyclerAdapter) {
        this.notesRecyclerAdapter = notesRecyclerAdapter;
        this.notes = MyApplication.getNoteManager(context).getAllNotes();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        this.notesRecyclerAdapter.getNotes().clear();

        String query = constraint.toString().trim().toLowerCase();
        if (!query.isEmpty()) {
            for (Note note : this.notes) {
                if (note instanceof PaperNote) {
                    PaperNote paperNote = (PaperNote) note;

                    if (paperNote.getTitle().trim().toLowerCase().contains(query)
                            || paperNote.getNote().trim().toLowerCase().contains(query)) {
                        this.notesRecyclerAdapter.getNotes().addNote(paperNote);
                    }
                }
            }
        } else {
            this.notesRecyclerAdapter.getNotes().clear();
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        this.notesRecyclerAdapter.notifyDataSetChanged();
    }
}
