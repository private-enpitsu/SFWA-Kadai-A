package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;


//items テーブルと連携する際にDTO の役割を果たすクラス。
//ID 番号、備品名、数量、保管場所、備考、登録⽇時、情報更新⽇時を保持する
//ためのフィールドを有する。



@Data
public class Item {
	
	
//	itemsテーブルのカラム対応フィールド
	private Integer id;
	
	private String name;
	
	private Integer amount;
	
	private Location Location;
	
	private String note;
	
	private LocalDateTime registered;
	
	private LocalDateTime updated;

}
