package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.exception.NoSuchMemberException;
import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.request.InitMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public int join(InitMember initMember) {
        return memberDao.insert(new Member(initMember));
    }

    @Override
    public Member login(String id, String pass) {
        Member member = memberDao.selectByEmail(id);
        if (member == null) return null;
        if (member.getPassword().equals(pass)) {
            return member;
        } else {
            return null;
        }
    }

    @Override
    public boolean isUsedEmail(String email) {
        Member member = memberDao.selectByEmail(email);
        if (member == null) return true;
        return false;
    }

    @Override
    public MemberInfo infoByEmail(String email) {
        Member selected = memberDao.selectByEmail(email);
        if (selected == null) {
            throw new NoSuchMemberException("입력된 email : " + email);
        }
        return new MemberInfo(selected);
    }

    @Override
    public MemberInfo infoById(int id) {
        return new MemberInfo(memberDao.selectById(id));
    }
}
