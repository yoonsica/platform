package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.ZTreeNode;

public interface DictionaryService {
	@Transactional
	List<ZTreeNode> generateDictionaryTree();
	@Transactional
	void addDictionaryElement(int parentId, boolean isParent, String name, String value);
	@Transactional
	void updateDictionaryElement(int id, String name, String value);
	@Transactional
	void deleteDictionaryElement(int id);
	@Transactional
	void upDictionaryElement(int currentId, int prevId);
	@Transactional
	void downDictionaryElement(int currentId, int downId);
}
