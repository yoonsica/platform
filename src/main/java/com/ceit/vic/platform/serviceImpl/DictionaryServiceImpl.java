package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.DictionaryDao;
import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.models.Dictionary;
import com.ceit.vic.platform.models.ZTreeNode;
import com.ceit.vic.platform.service.DictionaryService;

@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	DictionaryDao dictionaryDao;
	
	@Autowired
	IDPROVIDERDao idproviderDao;
	
	@Override
	public List<ZTreeNode> generateDictionaryTree() {
		List<ZTreeNode> dictionaryTree = new ArrayList<ZTreeNode>();
		List<Dictionary> dictionaryList = dictionaryDao.findAll();
		for (Dictionary dictionary : dictionaryList) {
			ZTreeNode node = new ZTreeNode();
			node.setId(dictionary.getId());
			node.setName(dictionary.getName() + "(" + dictionary.getValue() + ")");
			node.setpId(dictionary.getParentId());
			node.setIsParent(String.valueOf(!dictionary.isLeaf()));
			
			dictionaryTree.add(node);
		}
		return dictionaryTree;
	}

	@Override
	public void addDictionaryElement(int parentId, boolean isParent, String name, String value) {
		Dictionary dictionary = new Dictionary();
		int id = idproviderDao.generateId(Dictionary.class);
		dictionary.setId(id);
		dictionary.setDispIndex(id);
		dictionary.setName(name);
		dictionary.setValue(value);
		dictionary.setParentId(parentId);
		dictionary.setIsLeaf(isParent ? 0 : 1);
		
		dictionaryDao.add(dictionary);
	}

	@Override
	public void updateDictionaryElement(int id, String name, String value) {
		Dictionary dictionary = dictionaryDao.findById(id);
		dictionary.setName(name);
		dictionary.setValue(value);
		
		dictionaryDao.update(dictionary);
	}

	@Override
	public void deleteDictionaryElement(int id) {
		dictionaryDao.delete(id);
	}

	@Override
	public void upDictionaryElement(int currentId, int prevId) {
		dictionaryDao.up(currentId, prevId);
	}
	
	@Override
	public void downDictionaryElement(int currentId, int downId) {
		dictionaryDao.down(currentId, downId);
	}

	@Override
	public List<Dictionary> getByName(String name) {
		return dictionaryDao.findByName(name);
	}

}
