package com.shahriyar.notes;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotesDao {
    @Query("SELECT*FROM notes ORDER BY dayOfWeek DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT*FROM notes WHERE id== :noteId")
    Note getNoteById(int noteId);

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
