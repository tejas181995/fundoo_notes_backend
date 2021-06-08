package com.backend.fundoo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.fundoo.dto.NoteDto;
import com.backend.fundoo.dto.NoteUpdation;
import com.backend.fundoo.exception.NoteNotFoundException;
import com.backend.fundoo.exception.UserNotFoundException;
import com.backend.fundoo.exception.UserNotVerifiedException;
import com.backend.fundoo.model.NoteInfo;
import com.backend.fundoo.model.UserEntity;
import com.backend.fundoo.repository.INoteRepository;
import com.backend.fundoo.repository.IUserRepository;
import com.backend.fundoo.util.JwtGenerator;

@Component
public class NoteService implements INoteService {

	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private JwtGenerator generate;
	
	static final String USER_STATUS = "User not found";
	static final String NOTE_STATUS = "Note not found";
	
	@Override
	public boolean createNote(NoteDto note,  String token) {
		long userId = generate.parseJWT(token);
		System.out.println(userId);
		UserEntity user = userRepository.getUser(userId) ;
		NoteInfo noteInfo = new NoteInfo();
		if (user != null) {
			BeanUtils.copyProperties(note, noteInfo);
			noteInfo.setArchived(false);
			noteInfo.setTrashed(false);
			noteInfo.setColor("white");
			user.getNote().add(noteInfo);
			noteRepository.save(noteInfo);
			return true;
		}
		throw new UserNotFoundException("User does not exist");
	}
	
	@Transactional
	@Override
	public boolean updateNote(NoteUpdation updateNote) {
		UserEntity user = null;
		user = userRepository.getUser(user.getUserId());
		NoteInfo note = noteRepository.findById(updateNote.getNoteId());
		if (user != null) {
			if (note != null) {
				BeanUtils.copyProperties(updateNote, user);
				note.setNoteId(updateNote.getNoteId());
				note.setTitle(updateNote.getTitle());
				note.setDescription(updateNote.getDescription());
				note.setArchived(updateNote.isArchived());
				note.setTrashed(updateNote.isTrashed());
				noteRepository.save(note);
				return true;
			}
			throw new NoteNotFoundException(NOTE_STATUS);
		}
		throw new UserNotVerifiedException("Please verify");
	}
	
	@Transactional
	@Override
	public boolean deleteNote(long noteId, String token) {
		UserEntity userId = userRepository.getUser(generate.parseJWT(token));
		if (userId != null) {
			NoteInfo note = noteRepository.findById(noteId);
			if (note != null) {
				noteRepository.deleteNote(noteId);
				return true;
			}
			throw new NoteNotFoundException(NOTE_STATUS);
		}
		throw new UserNotFoundException(USER_STATUS);
	}
	
	@Transactional
	@Override
	public boolean archiveNote(long noteId) {
		UserEntity user =null;
		user =  userRepository.getUser(user.getUserId());
		if (user != null) {
			NoteInfo note = noteRepository.findById(noteId);
			if (note != null) {
				if (!note.isArchived()) {
					note.setArchived(true);
					noteRepository.save(note);
					return true;
				}
				throw new NoteNotFoundException("note already archived");
			}
			throw new NoteNotFoundException(NOTE_STATUS);
		}
		throw new UserNotFoundException(USER_STATUS);
	}

	@Transactional
	@Override
	public boolean trashNote(long noteId) {
		UserEntity user = null;
		user = userRepository.getUser(user.getUserId());
		if (user != null) {
			NoteInfo note = noteRepository.findById(noteId);
			if (note != null) {
				if (!note.isTrashed()) {
					note.setTrashed(true);
					noteRepository.save(note);
					return true;
				}
				return false;
			}
			throw new NoteNotFoundException(NOTE_STATUS);
		}
		throw new UserNotFoundException(USER_STATUS);

	}

	@Override
	public List<NoteInfo> getAllNotes(String token) {
		long userId = generate.parseJWT(token);
		UserEntity user = userRepository.getUser(userId);
		if (user != null) {
			List<NoteInfo> fetchedNotes = noteRepository.getAllNotes(userId);
			if (!fetchedNotes.isEmpty()) {
				return fetchedNotes;
			}
			return fetchedNotes;
		}
		throw new UserNotFoundException(USER_STATUS);
	}

	@Override
	public List<NoteInfo> getAllTrashedNotes(){
		UserEntity user = null;
		user = userRepository.getUser(user.getUserId());
		if (user != null) {
			List<NoteInfo> fetchedNotes = noteRepository.getAllTrashedNotes(user.getUserId());
			if (!fetchedNotes.isEmpty()) {
				return fetchedNotes;
			}
			return fetchedNotes;
		}
		throw new UserNotFoundException(USER_STATUS);
	}

	@Override
	public List<NoteInfo> getAllArchivedNotes() {
		UserEntity user = null;
		user = userRepository.getUser(user.getUserId());
		if (user != null) {
			List<NoteInfo> fetchedNotes = noteRepository.getAllArchivedNotes(user.getUserId());
			if (!fetchedNotes.isEmpty()) {
				return fetchedNotes;
			}
			return fetchedNotes;
		}
		throw new UserNotFoundException(USER_STATUS);
	}

	@Override
	public boolean updateColour(long noteId, String colour) {
		UserEntity user = null;
		user = userRepository.getUser(user.getUserId());
		if (user != null) {
			NoteInfo note = noteRepository.findById(noteId);
			if (note != null) {
				note.setColor(colour);
				noteRepository.save(note);
				return true;
			}
			throw new NoteNotFoundException(NOTE_STATUS);
		}
		throw new UserNotFoundException(USER_STATUS);
	}

}