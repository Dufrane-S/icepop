package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.exception.NoSuchElementsException;
import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.request.InitMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public int join(InitMember initMember) {
        try {
            return memberDao.insert(new Member(initMember));
        } catch (DataAccessException e) {
            throw e;
            /*if (e instanceof ) {
                log.error("이미 사용 중인 이메일");
                return 0;
            }*/
        }
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
            log.error("없는 아이디 검색");
            throw new NoSuchElementsException("입력된 email : " + email);
        }
        return new MemberInfo(selected);
    }

    @Override
    public MemberInfo infoById(int id) {
        return new MemberInfo(memberDao.selectById(id));
    }
}
