package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.request.InitMember;

public interface MemberService {
    public int join(InitMember initMember);

    public Member login(String id, String pass);

    public boolean isUsedId(String id);

    public MemberInfo info(String email);
}
