package com.backend.fundoo.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NoteUpdation {

	@NotBlank
	private long noteId;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	
	
}
