package com.example.app.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Item;
import com.example.app.domain.Location;
import com.example.app.service.ItemService;

import lombok.RequiredArgsConstructor;

//localhost:8080/items 以下のURL に対応するコントローラークラス。
//リクエストに応じて、備品リスト表示、備品詳細表示、備品登録、備品編集、
//備品削除の機能を提供する。この時、ItemService を利用する。




@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	
	
	// ★ Service を使って、Item や Location を取得・登録する
	private final ItemService itemService; //マッパークラスを定数で保持

	
	// 一覧表示  GET /items
	@GetMapping
	public String showItems(Model model) {
		
		List<Item> itemList = itemService.getAllItems();
		
		model.addAttribute("itemList", itemList);
		return "list";
	}
	
	
	// 詳細表示  GET /items/detail/{id}
	@GetMapping("/detail/{id}")
	public String showItemDetail(@PathVariable Integer id, Model model) {
		
		model.addAttribute("item", itemService.getItemById(id));
		return "detail";
	}

	
    // ================================
    // A-01-2: 備品登録フォーム表示
    // ================================
    // GET /items/add で save.html を表示する
	@GetMapping("/add")
	public String shoAddForm(Model model) {
		
		// ▼ 1. 空の Item を Model に登録（フォームの th:object 用）
		model.addAttribute("item", new Item());
		
		// ▼ 2. 場所の一覧表示のために、一覧を取得して Model に登録（セレクトボックス用）
		List<Location> locations = itemService.getItemLocations();
		model.addAttribute("locations", locations);
		
		return "save"; //  3. 備品登録ページ(save.html) を表示
	}
	
	
    // ================================
    // A-01-2: 備品登録処理
    // ================================
    // POST /items/add
	@PostMapping("/add")
	public String addItem(
			
			// ▼ @ModelAttribute("item") の名前は save.html の th:object と対応
			// ▼ バリデーション対象のフォームオブジェクト
			//@ModelAttribute("item") @Valid Item item,
			// ▼ バリデーション結果（エラー情報を保持）Errors errors を継承してるので同じ機能
//			BindingResult bindingResult,
//			// ▼ 画面（ビュー）に値を渡すための Model
			
			@Valid Item item,
			Errors errors,
			Model model,
			RedirectAttributes ra
			) {
		
		// ▼ 入力チェックでエラーがあれば、登録は行わずにフォームを再表示
		if(errors.hasErrors()) {
			// エラー時も場所のリストが必要なので、再度取得してセット
			List<Location> locations = itemService.getItemLocations();
			model.addAttribute("locations", locations);
			
			// save.html をそのまま表示（Errors/BindingResult の内容でエラー表示）
			return"save";
		}
		
		// ▼ 入力に問題がなければ DB に登録
		itemService.addItem(item);
		ra.addFlashAttribute("status", "備品を追加しました");
	    // ▼ 今回は教材に「メッセージ表示」の要件がないため、
	    //    RedirectAttributes は使わず、そのまま一覧へリダイレクト
		return "redirect:/items";
	}
		
    // 「/items/edit/{id}」というURLへの GET リクエストを
    // このメソッドで受け取る、という意味。
	@GetMapping("/edit/{id}")
	public String showEditForm(
			// URL の {id} 部分を Integer 型の id という変数として受け取る。
			@PathVariable Integer id,
			
			// 画面（ビュー）に値を渡すための入れ物。
            // フォームに表示する Item や、場所一覧を詰めてテンプレートに渡す。
			Model model
			) {
		
        // id を使って、編集対象の備品データを1件取得し、
        // "item" という名前で Model に入れる。
        // edit.html 側では th:object="${item}" などで参照する想定。
		model.addAttribute("item", itemService.getItemById(id));

        // セレクトボックス用の場所一覧を取得して、
        // "locationList" という名前で Model に入れる。
        // edit.html で th:each を使って選択肢を表示するためのリスト。
		model.addAttribute("locationList", itemService.getItemLocations());

        // "edit" という名前のビュー（通常は edit.html）を表示する。
        // ここまでに Model に入れた "item" と "locationList" を使って
        // 編集フォームの初期表示を行う。
		return "edit";
	}
	
	
    // ================================
    // A-01-5: 備品情報の更新機能
    // ================================
    // 「/items/edit/{id}」というURLに対する POST リクエストを
    // このメソッドで受け取る、という意味。
    // 例: /items/edit/5 にフォームをPOSTすると、このメソッドが呼ばれる。
	@PostMapping("/edit/{id}")
	public String editItem(
			
			// URL パスの {id} 部分を Integer 型の変数 id として受け取る。
			@PathVariable Integer id,
			
            // 画面のフォーム入力値を Item オブジェクトに詰める。
            // 同時に @Valid により、Item クラスに付いているバリデーション
            // アノテーション（@NotBlank など）を使って入力チェックを行う。
			@Valid Item item,
			
            // 上の @Valid の結果（エラー情報）が入ってくるオブジェクト。
            // エラーがあるかどうかは errors.hasErrors() で判定できる。
			Errors errors,
			
            // 画面（ビュー）に値を渡すための入れ物。
            // 再表示時に、セレクトボックス用のリストなどを入れるのに使う。
			Model model,
			
            // リダイレクト先の画面に「1回だけ表示するメッセージ」
            // （フラッシュメッセージ）を渡すためのオブジェクト。
			RedirectAttributes ra) {
		
		// バリデーションエラー（必須項目未入力・桁数オーバーなど）が1件でもあれば true。
		if(errors.hasErrors()) {
			
            // エラーがある場合、再表示する edit.html で場所のプルダウンを
            // 正しく表示するために、場所一覧を取得して Model に入れている。
            // ※ service.getItemLocations() は「場所一覧を返す」メソッド想定。
			model.addAttribute("locationList", itemService.getItemLocations());
			
            // 入力エラーがあるので更新処理は行わず、
            // 編集画面（edit.html）をそのまま再表示する。
			return "edit";
		}
		
		// ItemService を使って、Item の内容でDBの備品情報を更新する。
		itemService.editItem(item);
		
        // リダイレクト先の詳細画面で 1 回だけ使えるメッセージを登録する。
        // ここでは "status" という名前で「備品情報を更新しました」という文字列を渡す。
        // detail.html 側で [[${status}]] などとして表示できる。
		ra.addFlashAttribute("status", "備品情報を更新しました");

		// 更新が完了したら、その備品の詳細ページにリダイレクトする。
		return "redirect:/items/detail/" + id;
	}
	
    // ================================
    // A-01-4: 備品の削除機能
    // ================================
	// 「/items/delete/{id}」というURLへのGETリクエストを、このメソッドに割り当てる。
	// 例: /items/delete/5 にアクセスすると、このメソッドが呼ばれる。
	@GetMapping("/delete/{id}")
	public String deleteItem(
	        // URL パスの {id} の部分を Integer 型の変数 id として受け取る。
	        // 例: /items/delete/5 → id には 5 が入る。
			@PathVariable Integer id,
			RedirectAttributes ra) {// リダイレクト後の画面（/items）に、1回だけ表示するメッセージを渡すためのオブジェクト。


	    // Service を使って、指定された id の備品データを削除する。
		itemService.deleteItem(id);
		
	    // リダイレクト先（/items）で 1 回だけ使える属性 "status" を追加する。
	    // list.html 側で ${status} を表示することで、「備品を削除しました」というメッセージを出せる。
		ra.addFlashAttribute("status", "備品を削除しました");
		
	    // 削除処理が終わったら、備品リストのページ /items にリダイレクトする。
	    // 画面を直接返すのではなく、URL を /items に振り向ける動きになる。
		return "redirect:/items";
	}

}
