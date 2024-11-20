package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.MemberInfo;

public interface MemberService {
    public int join(Member member);

    public Member login(String id, String pass);

    public boolean isUsedId(String id);

    public MemberInfo info(String email);
}
