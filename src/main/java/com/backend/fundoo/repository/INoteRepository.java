package com.backend.fundoo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.fundoo.model.NoteInfo;

@Repository
public interface INoteRepository {

	public NoteInfo save(NoteInfo note);
	public NoteInfo findById(long noteId);
	public boolean deleteNote(long noteId);
	public List<NoteInfo> getAllNotes(long userId);
	public List<NoteInfo> getAllTrashedNotes(long userId);
	public List<NoteInfo> getAllArchivedNotes(long userId);
	
}
