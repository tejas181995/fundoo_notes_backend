package com.backend.fundoo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId ;
	
	@Column (nullable = false, length = 50)
	private String firstName;
	
	@Column (nullable = false, length = 50)
	private String lastName;
	
	@Column (nullable = false, length = 120, unique = true)
	private String email;
	
	@Column (nullable = false)
	private String password;
	
	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean isVerifed;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	private List<NoteInfo> note;


	public UserEntity(long userId, String firstName, String lastName, String email, String password, boolean isVerifed,
			List<NoteInfo> note) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isVerifed = isVerifed;
		this.note = note;
	}


	public UserEntity() {
		super();
	}

	
	
}









