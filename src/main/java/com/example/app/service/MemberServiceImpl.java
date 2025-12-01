package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.app.domain.Member;
import com.example.app.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

	// final が付いたフィールド。
	// @RequiredArgsConstructor によって、この mapper を受け取るコンストラクタが
	// 自動生成される。
	// この mapper 経由で DB（members テーブル）にアクセスする。
	private final MemberMapper memberMapper;

	// MemberService インターフェースで宣言されているメソッドを実装していることを示す。
	@Override
	public Member getAuthenticatedMember(String loginId, String loginPass) {
		
		// 認証に成功したときに返す Member を一時的に入れておく変数。
		// ここでは、最初は null（未認証）としておく。
		Member authenticatedMember = null;
		
		// ログインIDをもとに、DB から Member 情報を1件取得する。
		// 見つからなければ member は null、見つかれば Member オブジェクトが入る。
		Member member = memberMapper.selectByLoginId(loginId);
		
		
		// ▼ 認証チェック
		// 1) member が null でない（= ログインIDが存在する）
		// 2) BCrypt.checkpw(...) でパスワードが一致している
		//    - loginPass: ユーザがフォームに入力した平文パスワード
		//    - member.getLoginPass(): DB に保存されているハッシュ化済みパスワード
		//    → これが true のとき、認証成功とみなす。
		if(member != null && BCrypt.checkpw(loginPass, member.getLoginPass())) {
			
			// 認証成功なので、戻り値用の authenticatedMember に DB から取得した member をセットする。
			authenticatedMember = member;
		}
		
		// 認証成功なら Member オブジェクト、失敗なら null を返す。
		// 呼び出し側（コントローラ）は、null かどうかで「成功／失敗」を判断する。
		return authenticatedMember;
	} 
}
