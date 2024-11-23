package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.request.InitMember;
import com.ssafy.icecreamapp.model.dto.request.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    int insert(Member Member);

    Member selectByEmail(String email);

    int updateSum(String email, int price);

    Member selectById(int id);

    int updateToken(Token token);
}
