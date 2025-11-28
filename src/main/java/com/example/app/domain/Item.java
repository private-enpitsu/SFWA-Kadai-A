package com.example.app.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;


//items テーブルと連携する際にDTO の役割を果たすクラス。
//ID 番号、備品名、数量、保管場所、備考、登録⽇時、情報更新⽇時を保持する
//ためのフィールドを有する。



@Data
public class Item {
	
//	itemsテーブルのカラム対応フィールド
	private Integer id;					// ★ 備品ID（主キー）
	
	
	// ▼ 追加：バリデーション用アノテーション
    // ▼ 品名：未入力＆50文字以内チェック
    //   @NotBlank : null/空文字/空白だけ を禁止
    //   @Size(max=50) : 最大50文字まで
    //   message 属性に課題指定のエラーメッセージを直接記述
    //   （メッセージファイルに分ける場合は別途対応）
	@NotBlank(message = "備品名が未入力です")
	@Size(max = 50, message = "備品名は 50 文字以内で入力してください")
	private String name;				// ★ 品名
	
	
	// ▼ 追加：バリデーション用アノテーション
    // ▼ 数量：未入力・0以上チェック
    //   @NotNull : null禁止（＝未入力チェック）
    //   @Min(0)  : 0以上
    //   整数チェックは、フィールド型を Integer にすることで
    //   「整数以外は型変換エラー」として扱われます
	@NotNull(message = "数量が未入力です")
	@Min(value = 0, message ="数量は 0 以上の値を入力してください")
	private Integer amount;				// ★ 数量
	
	private Location location;			// ★ 場所情報
	
	private String note;				// ★ 備考
	
	private LocalDateTime registered;	// ★ 登録日時
	
	private LocalDateTime updated;		// ★ 更新日時

}




//アノテーション無し版

//
////Lombok の @Data を使わず、
////フィールド + コンストラクタ + getter/setter を直書きしたバージョン
//public class Item {
//
//private Integer id;					// ★ 備品ID（主キー）
//
//private String name;				// ★ 品名
//
//private Integer amount;				// ★ 数量
//
//private Location location;			// ★ 場所情報
//
//private String note;				// ★ 備考
//
//private LocalDateTime registered;	// ★ 登録日時
//
//private LocalDateTime updated;		// ★ 更新日時
//
// // ----------------------------------------------------
// // コンストラクタ
// // ----------------------------------------------------
//
// /**
//クラス内に final や @NonNull のフィールドが 1つもない → 引数なしコンストラクタ（デフォルトコンストラクタ相当）が生成される
//final や @NonNull がある → それらを引数に持つコンストラクタが生成され、純粋な「引数なし」は作られない
//  */
// public Item() {
// }
//
// // ----------------------------------------------------
// // getter / setter
// // ----------------------------------------------------
//
// // id
// public Integer getId() {
//     return id;
// }
//
// public void setId(Integer id) {
//     this.id = id;
// }
//
// // name
// public String getName() {
//     return name;
// }
//
// public void setName(String name) {
//     this.name = name;
// }
//
// // amount
// public Integer getAmount() {
//     return amount;
// }
//
// public void setAmount(Integer amount) {
//     this.amount = amount;
// }
//
// // Location
// public Location getLocation() {
//     return Location;
// }
//
// public void setLocation(Location location) {
//     this.Location = location;
// }
//
// // note
// public String getNote() {
//     return note;
// }
//
// public void setNote(String note) {
//     this.note = note;
// }
//
// // registered
// public LocalDateTime getRegistered() {
//     return registered;
// }
//
// public void setRegistered(LocalDateTime registered) {
//     this.registered = registered;
// }
//
// // updated
// public LocalDateTime getUpdated() {
//     return updated;
// }
//
// public void setUpdated(LocalDateTime updated) {
//     this.updated = updated;
// }
//}
