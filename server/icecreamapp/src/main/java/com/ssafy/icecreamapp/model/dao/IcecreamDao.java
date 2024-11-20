package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.Icecream;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IcecreamDao {

    Icecream selectIcecreamById(int id);

    List<Icecream> selectIcecreamsByCon(IceSelectCon iceSelectCon);

    void updateIcecreamById(int id, int count);
}
