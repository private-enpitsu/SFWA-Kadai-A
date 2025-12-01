package com.example.app.service;

import com.example.app.domain.Member;

public interface MemberService {
	
	Member getAuthenticatedMember(String loginId, String loginPass);

}
