package com.example.app.domain;

import lombok.Data;

//locations テーブルと連携する際にDTO の役割を果たすクラス。
//ID 番号、場所名を保持するためのフィールドを有する。

@Data
public class Location {
	
	private Integer id;		//ID 番号を保持するためのフィールド
	
	private String name;	//場所名を保持するためのフィールド

}
