package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.request.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDao {

    int insert(Member Member);

    Member selectByEmail(String email);

    int updateSum(@Param("email")String email, @Param("price")int price);

    Member selectById(int id);

    int updateTokenByEmail(Token token);

    Member searchByToken(Token token);
}
