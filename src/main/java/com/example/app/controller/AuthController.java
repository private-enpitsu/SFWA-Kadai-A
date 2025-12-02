package com.example.app.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Member;
import com.example.app.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {
	
	private final MemberService memberService;
	private final HttpSession session;

//	ログイン画面
    // 「このコントローラのベースURL」に対する GET リクエストを受け取る。
    // 例えばクラスに @RequestMapping("/login") が付いている場合、
    // GET /login にアクセスしたときにこのメソッドが呼ばれる。
	@GetMapping
	public String showLoginForm(Model model) {
		
        // ログインフォームと紐づけるための空の Member オブジェクトを用意して、
        // "member" という名前で Model に格納する。
        // login.html 側では th:object="${member}" などで使う想定。
		model.addAttribute("member", new Member());
		
        // "login" という名前のビュー（通常は login.html）を返す。
        // → ログイン画面を表示する。
		return "login";
	}
	
	
//	ログイン認証
    // このコントローラのベースURL（例: /login）に対する POST リクエストを受け取る。
    // ログインフォームからの送信先が POST /login になっている想定。
	@PostMapping
	public String loginCheck(
			
            // login.html のフォーム入力値を Member オブジェクトに詰める。
            // 同時に @Valid により、Member クラスに付けたバリデーション
            // （@NotBlank など）を使って入力チェックを行う。
			@Valid Member member,
			Errors errors,
			
			// リダイレクト先の画面（トップページなど）に「1回だけ表示する
			RedirectAttributes ra
			) {
		
		// ▼ バリデーションエラーをチェック
		if(errors.hasErrors()) {
			return "login";
		}
		
        // ▼ ID・パスワードを使って認証処理を行う
        // member.getLoginId()    : フォームに入力されたログインID
        // member.getLoginPass()  : フォームに入力された平文パスワード
        // memberService.getAuthenticatedMember(...):
        //   - DB から該当ログインIDの Member を取得
        //   - BCrypt でパスワード比較
        //   - 成功なら Member、失敗なら null を返す想定。			//	memberのID/PWをゲッターで取得
		Member authMember = memberService.getAuthenticatedMember(member.getLoginId(), member.getLoginPass());
		
		// ▼ 認証失敗（IDが存在しない or パスワード不一致）の場合
		if(authMember == null) {
			errors.rejectValue("loginId", "wrong_id_or_pass");
			return "login";
		}
		
        // ▼ 認証成功した場合：セッションにログイン情報を保存
        // "authMember" という名前で、認証済みの Member オブジェクトをセッションに格納。
        // これにより、他の画面から session.getAttribute("authMember") で
        // ログイン中ユーザー情報を参照できる。
		session.setAttribute("authMember", authMember);
		
		// フラッシュメッセージとして「ログインしました」を登録。// リダイレクト先の画面で 1 回だけ ${status} などで表示できる。
		ra.addFlashAttribute("status","ログインしました");
		return "redirect:/items";
	}
	
	
//	ログアウト
	@GetMapping("/logout")
	public String logout(RedirectAttributes ra) {
		session.invalidate();   // ← ログアウト：ログイン情報などを全部破棄
		ra.addFlashAttribute("status", "ログアウトしました");
		return "redirect:/";
	}

}
