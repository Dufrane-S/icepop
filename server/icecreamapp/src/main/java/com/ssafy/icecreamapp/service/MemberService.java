package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.request.Token;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.request.InitMember;

public interface MemberService {
    public int join(InitMember initMember);

    public Member login(String id, String pass);

    public boolean isUsedEmail(String id);

    public MemberInfo infoByEmail(String email);

    public MemberInfo infoById(int id);

    public int updateTokenByEmail(Token token);
}
