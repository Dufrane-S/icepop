package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.request.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.Icecream;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IcecreamDao {

    Icecream selectIcecreamById(int id);

    List<Icecream> selectIcecreamsByCon(IceSelectCon iceSelectCon);

    int updateIcecreamById(@Param("id") int id, @Param("quantity")int quantity, @Param("age")int age, @Param("gender")int gender);

    List<Icecream> selectIcecreamsByIds(List<Integer>idList);
}
