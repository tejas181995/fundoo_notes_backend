package com.backend.fundoo.dto;

import lombok.Data;

@Data
public class NoteUpdation {

	private long noteId;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isTrashed;
	
}
