package com.backend.fundoo.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class NoteDto {
	
	private String title;
	private String description;
	
}
