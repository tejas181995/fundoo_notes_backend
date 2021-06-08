package com.backend.fundoo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.fundoo.dto.NoteDto;
import com.backend.fundoo.dto.NoteUpdation;
import com.backend.fundoo.model.NoteInfo;

@Service
public interface INoteService {

	
	public boolean createNote(NoteDto note,  String token);
	public boolean updateNote(NoteUpdation updateNote);
	public boolean deleteNote(long noteId, String token);
	public boolean archiveNote(long noteId);
	public boolean trashNote(long noteId);
	public List<NoteInfo> getAllNotes(String token);
	public List<NoteInfo> getAllTrashedNotes();
	public List<NoteInfo> getAllArchivedNotes();
	public boolean updateColour(long noteId,  String colour);
	
	
	

}

