package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    int insert(Member member);

    Member selectByEmail(String email);

    int updateSum(String email, int price);
}
