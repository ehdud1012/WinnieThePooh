package com.kdy.nwtp.note;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Integer>{
	public abstract List<Note> findByCategory(String s);	
}
