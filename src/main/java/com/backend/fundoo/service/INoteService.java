package com.backend.fundoo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.fundoo.dto.NoteDto;
import com.backend.fundoo.dto.NoteUpdation;
import com.backend.fundoo.model.NoteInfo;

@Service
public interface INoteService {

	
	public boolean createNote(NoteDto note,  String token);
	public boolean updateNote(NoteUpdation updateNote, String token);
	public boolean deleteNote(long noteId, String token);
	public boolean archiveNote(long noteId, String token);
	public boolean trashNote(long noteId, String token);
	public List<NoteInfo> getAllNotes(String token);
	public List<NoteInfo> getAllTrashedNotes(String token);
	public List<NoteInfo> getAllArchivedNotes(String token);
	public boolean updateColour(long noteId, String token, String colour);
	
	
	

}

