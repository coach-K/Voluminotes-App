package com.andela.notelib.note;

import com.andela.notelib.database.Config;
import com.andela.notelib.database.DatabaseConnector;
import com.andela.notelib.database.DatabaseManager;
import com.andela.notelib.mapper.NoteMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoteDBWriter<T extends Note> {
    private DatabaseManager databaseManager;
    private String tableName;
    private String id;
    private String folderid;
    private String trash;
    private String noteblock;
    private ArrayList<String> fields;

    public NoteDBWriter(String tableName) throws Exception {
        this.tableName = tableName;

        getColumns();

        initializeDatabaseConnection();
    }

    private void getColumns(){
        this.id = Folders.ID.getColumns();
        this.folderid = Folders.FOLDERID.getColumns();
        this.trash = Folders.TRASH.getColumns();
        this.noteblock = Folders.NOTEBLOCK.getColumns();
    }

    private void initializeDatabaseConnection() throws Exception {
        DatabaseConnector databaseConnector = new DatabaseConnector(Config.URL.toString());
        this.databaseManager = new DatabaseManager(databaseConnector.connect(), this.tableName);

        this.createTable();
        this.createFields();
    }

    public void insertNote(T note) {
        this.databaseManager.insert(getFields(), getValues(note))
                .executeUpdate();
    }

    public NoteList<Note> selectAllNote(int trashId) {
        NoteList<Note> notes = new NoteList<>();
        ResultSet resultSet = this.databaseManager.select()
                .where(this.trash, String.valueOf(trashId))
                .executeQuery();

        try {
            while (resultSet.next())
                notes.addNote(this.getDataFromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public NoteList<Note> selectByFolderId(int id, int trashId) {
        NoteList<Note> notes = new NoteList<>();
        ResultSet resultSet = this.databaseManager.select()
                .where(this.folderid, String.valueOf(id))
                .and(this.trash, String.valueOf(trashId))
                .executeQuery();

        try {
            while (resultSet.next())
                notes.addNote(this.getDataFromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public Note selectById(int id) {
        ResultSet resultSet = this.databaseManager.select()
                .where(this.id, String.valueOf(id))
                .executeQuery();

        try {
            if (resultSet.next())
                return this.getDataFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateNote(T note) {
        this.databaseManager.update(getFields(), getValues(note))
                .where(this.id, String.valueOf(note.getId()))
                .executeUpdate();
    }

    public void deleteNote(int id) {
        this.databaseManager.delete()
                .where(this.id, String.valueOf(id))
                .executeUpdate();
    }

    public void deleteAlNote(int id) {
        this.databaseManager.delete()
                .where(this.trash, String.valueOf(id))
                .executeUpdate();
    }

    public void deleteFolderNotes(int id) {
        this.databaseManager.delete()
                .where(this.folderid, String.valueOf(id))
                .executeUpdate();
    }

    private Note getDataFromResultSet(ResultSet resultSet) throws SQLException {
        DefaultNote defaultNote = new DefaultNote();
        defaultNote.setId(resultSet.getInt(this.id));
        defaultNote.setFolderId(resultSet.getInt(this.folderid));
        defaultNote.setTrash(resultSet.getInt(this.trash));
        defaultNote.setDefaultNote(resultSet.getString(this.noteblock));
        return deSerializeNote(defaultNote);
    }

    private Note deSerializeNote(DefaultNote defaultNote) {
        Note note = null;
        switch (defaultNote.getFolderId()) {
            case Folders.STATIC_PAPER_NOTE:
                note = new NoteMapper<PaperNote>().writeNote(defaultNote.getDefaultNote(), PaperNote.class);
                break;
            case Folders.STATIC_TODO_NOTE:
                note = new NoteMapper<TodoNote>().writeNote(defaultNote.getDefaultNote(), TodoNote.class);
                break;
            case Folders.STATIC_AUDIO_NOTE:
                note = new NoteMapper<FileNote>().writeNote(defaultNote.getDefaultNote(), FileNote.class);
                break;
            case Folders.STATIC_DRAW_NOTE:
                note = new NoteMapper<FileNote>().writeNote(defaultNote.getDefaultNote(), FileNote.class);
                break;
        }
        return note;
    }

    private ArrayList<String> getValues(T note) {
        ArrayList<String> values = new ArrayList<>();
        values.add(String.valueOf(note.getId()));
        values.add(String.valueOf(note.getFolderId()));
        values.add(String.valueOf(note.getTrash()));
        values.add(new NoteMapper<T>().readNote(note));
        return values;
    }

    private ArrayList<String> getFields() {
        return fields;
    }

    private void createFields(){
        fields = new ArrayList<>();
        fields.add(this.id);
        fields.add(this.folderid);
        fields.add(this.trash);
        fields.add(this.noteblock);
    }

    private void createTable() {
        this.databaseManager.createNewTable(this.tableName)
                .addColumn(this.id)
                .addColumn(this.folderid)
                .addColumn(this.trash)
                .addColumn(this.noteblock, DatabaseManager.TEXT)
                .execute();
    }
}
