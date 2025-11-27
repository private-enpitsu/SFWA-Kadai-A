package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Item;

//items テーブルを操作するためのインターフェース。
//src/main/resoureces/mybatis/ItemMapper.xml と連携する。

@Mapper
public interface ItemMapper {

	
//	items テーブルから全備品のリストを登録日時の新しい順に取得する。
	List<Item> selectAll();
	
	
//	ID 番号に基づき、items テーブルから１件分の備品データを取得する。
	Item selectById(int id);
	
	
//	items テーブルに備品データを登録する。
	void insert(Item item);
	
	
//	items テーブルの備品データを更新する。
	void update(Item item);
	
	
//	ID 番号に基づき、items テーブルから1 件分の備品データを削除する。
	void delete(int id);
	
}
