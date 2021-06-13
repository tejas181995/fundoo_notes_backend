package com.backend.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.fundoo.dto.NoteDto;
import com.backend.fundoo.dto.NoteUpdation;
import com.backend.fundoo.model.NoteInfo;
import com.backend.fundoo.response.Response;
import com.backend.fundoo.service.INoteService;

@CrossOrigin(origins = "*")
@RestControllerAdvice
@RequestMapping("note")
public class NoteController {
	
	@Autowired
	private INoteService noteService;
	
	@CrossOrigin(origins = "*")
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto note, @RequestHeader String token) {
		boolean result = noteService.createNote(note, token);
		return (result) ? ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200, note))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Error.....please check your noteId", 404, note));
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("getAllNotes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token) {
		List<NoteInfo> list = noteService.getAllNotes(token);
		if (!list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("All notes are ", 200, list));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Empty list ", 404));
	}

	@CrossOrigin(origins = "*")
	@PostMapping("archive/{noteId}")
	public ResponseEntity<Response> archiveNote(@PathVariable("noteId") long noteId, @RequestHeader String token) {
		boolean result = noteService.archiveNote(noteId, token);
		return (result) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note archived", 200))
				: ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
						.body(new Response("Error, check your noteId", 208));
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("getAllNotes/archived")
	public ResponseEntity<Response> getAllArchivedNotes(@RequestHeader("token") String token) {
		List<NoteInfo> list = noteService.getAllArchivedNotes(token);
		if (!list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("All archived notes are ", 200, list));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Empty list", 404));
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("trash/{noteId}")
	public ResponseEntity<Response> trashNote(@PathVariable("noteId") long noteId, @RequestHeader String token) {
		boolean result = noteService.trashNote(noteId, token);
		return (result) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note trashed", 200))
				: ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("Error, check your noteId", 502));
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("getAllNotes/trashed")
	public ResponseEntity<Response> getAllTrashedNotes(@RequestHeader("token") String token) {
		List<NoteInfo> list = noteService.getAllTrashedNotes(token);
		if (!list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("All trashed notes are", 200, list));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Empty list", 404));
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping("updateColour/{noteId}")
	public ResponseEntity<Response> updateColour(@PathVariable("noteId") long noteId, @RequestHeader("token") String token,
			@RequestParam String colour) {
		boolean result = noteService.updateColour(noteId, token, colour);
		return (result) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Colour updated", 200))
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Colour not updated", 304));
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping("updateNote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteUpdation update, @RequestHeader("token") String token) {
		boolean result = noteService.updateNote(update, token);
		return (result) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note updated", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Error....check your noteId", 400));
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping("delete/{noteId}")
	public ResponseEntity<Response> deleteNotePermanently(@PathVariable("noteId") long noteId,
			@RequestHeader("token") String token) {
		boolean result = noteService.deleteNote(noteId, token);
		return (result) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Error....please check your noteId!", 404));
	}


}
