package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Item;
import com.example.app.domain.Location;
import com.example.app.mapper.ItemMapper;
import com.example.app.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;

//ItemService インターフェースを実装するクラス。
//ItemMapper やLocationMapper を使用し、備品情報を操作する。

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	
	
	private final ItemMapper itemMapper;
	private final LocationMapper locationMapper;

	@Override
	public List<Item> getAllItems() {

		return itemMapper.selectAll();
	}

	@Override
	public Item getItemById(Integer id) {

		return itemMapper.selectById(id);
	}

	@Override
	public void addItem(Item item) {
		
//		DBに登録のため、ItemMapperのinsert関数を呼ぶ
		itemMapper.insert(item);
		
	}

	@Override
	public void editItem(Item item) {
		itemMapper.update(item);
		
	}

	@Override
	public void deleteItem(Integer id) {
		
		if(id == null) {
			return;
		}
		
		itemMapper.delete(id);
		
	}

	@Override
	public List<Location> getItemLocations() {
		return locationMapper.selectAll();
	}

}
