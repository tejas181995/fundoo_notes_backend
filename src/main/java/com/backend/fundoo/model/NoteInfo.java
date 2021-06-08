package com.backend.fundoo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Table(name = "note")
@Data
@Entity
public class NoteInfo  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private long noteId;
	
	private String title;
	private String description;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isArchived;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isTrashed;
	
	private String color;
	
	
	
}
