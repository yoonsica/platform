package com.ceit.vic.platform.dao;

import java.util.List;

import com.ceit.vic.platform.models.Dictionary;

public interface DictionaryDao {

	Dictionary findById(int id);
	
	List<Dictionary> findAll();
	
	void add(Dictionary dictionary);
	
	void update(Dictionary dictionary);

	void delete(int id);

	void up(int currentId, int prevId);

	void down(int currentId, int nextId);

}
