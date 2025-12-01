package com.example.app.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//このフィルターは 「セッションに authMember があるか」 を見て、
//なければ / にリダイレクト（未ログイン扱い）
//あれば chain.doFilter(...) で通常処理へ通す
//という「ログイン必須チェック」をしているクラスです。


//認証チェック用のフィルタークラス。
//「Filter」を実装することで、コントローラに届く前のリクエストを横取りして検査できる。
public class AuthFilter implements Filter {

	
    // すべてのリクエストで呼び出されるメソッド。
    // request : クライアントから送られたリクエスト情報
    // response: サーバからのレスポンス情報
    // chain   : 次のフィルターや最終的な処理（コントローラ）につなぐためのオブジェクト

	@Override // (@Override は必須ではないが、付けるのがおすすめ このクラスのチェックが利用されていない時、エラーで知らせてもらえるから)
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
        // 汎用の ServletRequest を、HTTP 用の HttpServletRequest 型にキャストしている。
        // これで「URL」「HTTPメソッド」「セッション」など HTTP 特有の機能が使えるようになる。
		HttpServletRequest req = (HttpServletRequest) request;
		
        // 同様に、ServletResponse を HttpServletResponse 型にキャストする。
        // これで「リダイレクト」「ステータスコード設定」などが使える。
		HttpServletResponse res = (HttpServletResponse) response;
		
        // 現在のリクエストに対応する HttpSession を取得する。
        // セッションが無ければ新しく作られ、あれば既存のセッションが返される。
		HttpSession session = req.getSession();
		
        // ▼ 認証済みかどうかのチェック
        // セッションから "authMember" という名前の属性を取り出し、
        // それが null かどうかを見る。
        // ・null の場合   → ログインしていない とみなす
        // ・null でない → ログイン済み とみなす
		if(session.getAttribute("authMember") == null) {
			
            // ログインしていない場合の処理。
            // "/" へリダイレクトする（トップページやログインページを想定）。
            // ブラウザ側に「別のURLへ行ってください」という指示を返す。
			res.sendRedirect("/");
			return;// ここでメソッドを終了し、これ以降の処理（コントローラなど）には進ませない。
		}
		
        // ▼ 認証済みの場合の処理
        // 何もせず、そのまま次のフィルター、または本来の処理（コントローラ）に処理を渡す。
        // これを呼ばないと、リクエストが先に進まず、画面も表示されない。
		chain.doFilter(request, response);
		
	}

}
