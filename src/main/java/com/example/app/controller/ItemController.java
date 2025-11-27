package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Item;
import com.example.app.service.ItemService;

import lombok.RequiredArgsConstructor;

//localhost:8080/items 以下のURL に対応するコントローラークラス。
//リクエストに応じて、備品リスト表示、備品詳細表示、備品登録、備品編集、
//備品削除の機能を提供する。この時、ItemService を利用する。




@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	
	
	private final ItemService itemService;

	
	
	
	
//	@GetMapping("/items")
	@GetMapping
	public String showItems(Model model) {
		
		List<Item> itemList = itemService.getAllItems();
		
		model.addAttribute("itemList", itemList);
		
		return "list";
	}
	
	public String showItemDetail() {
		return null;
	}
	
	public String shoAddForm() {
		return null;
	}
	
	public String addItem() {
		return null;
	}
	
	public String showEditForm() {
		return null;
	}
	
	public String editItem() {
		return null;
	}
	
	public String deleteItem() {
		return null;
	}

}
