package com.example.app.service;

import java.util.List;

import com.example.app.domain.Item;
import com.example.app.domain.Location;

//備品情報を操作するためのインターフェース。


public interface ItemService {
	
	List<Item> getAllItems();			//備品リストを取得する
	
	
    /*
     * 備品をIDで1件取得する。
     * @param id 取得したい備品のID
     * @return 該当する Item。存在しなければ null（MyBatis の仕様）
     */
	Item getItemById(Integer id);		//ID 番号に基づき、１件分の備品情報を取得する。
	
	void addItem(Item item);			//備品を登録する。
	
	void editItem(Item item);			//備品情報を編集する。
	
	void deleteItem(Integer id);		//ID 番号に基づき、１件分の備品情報を削除する。
	
	List<Location> getItemLocations();	//備品保管場所のリストを取得する。
	

}
