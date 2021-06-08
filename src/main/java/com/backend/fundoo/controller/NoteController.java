package com.backend.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.fundoo.dto.NoteDto;
import com.backend.fundoo.model.NoteInfo;
import com.backend.fundoo.response.Response;
import com.backend.fundoo.service.INoteService;

@RestControllerAdvice
@RequestMapping("note")
public class NoteController {
	
	@Autowired
	private INoteService noteService;
	
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto note, @RequestHeader String token) {
		boolean result = noteService.createNote(note, token);
		return (result) ? ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200, note))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Error.....please check your noteId", 404, note));
	}
	@GetMapping("getAllNotes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token) {
		List<NoteInfo> list = noteService.getAllNotes(token);
		if (!list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("All notes are ", 200, list));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Empty list ", 404));
	}

}
