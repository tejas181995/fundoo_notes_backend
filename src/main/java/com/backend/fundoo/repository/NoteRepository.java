package com.backend.fundoo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backend.fundoo.model.NoteInfo;

@SuppressWarnings({ "rawtypes", "deprecation" })
@Repository
public class NoteRepository implements INoteRepository {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public NoteInfo save(NoteInfo note) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(note);
		return note;
	}

	@Override
	public NoteInfo findById(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query idQuery = session.createQuery("FROM NoteInfo where noteId=:noteId");
		idQuery.setParameter("noteId", noteId);
		return (NoteInfo) idQuery.uniqueResult();
	}

	@Override
	public boolean deleteNote(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query deleteQuery = session.createQuery("delete from NoteInfo where noteId=:noteId");
		deleteQuery.setParameter("noteId", noteId);
		int result = deleteQuery.executeUpdate();
		if (result > 0)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NoteInfo> getAllNotes(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query selectQuery = session
				.createQuery("FROM NoteInfo where user_id=:id and is_trashed=false and is_archived=false");
		selectQuery.setParameter("id", userId);
		return selectQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NoteInfo> getAllTrashedNotes(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query selectQuery = session.createQuery("From NoteInfo where user_id=:userId and is_trashed=true");
		selectQuery.setParameter("userId", userId);
		return selectQuery.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<NoteInfo> getAllArchivedNotes(long userId) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM NoteInfo where user_id=:userId and is_archived=true")
				.setParameter("userId", userId).getResultList();
	}


}
