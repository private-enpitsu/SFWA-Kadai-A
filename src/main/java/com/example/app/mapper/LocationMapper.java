package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Location;

//locations テーブルを操作するためのインターフェース。
//src/main/resoureces/mybatis/LocationMapper.xml と連携する。

@Mapper
public interface LocationMapper {

	List<Location> selectAll();
	
}
