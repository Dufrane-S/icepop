package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dao.IcecreamDao;
import com.ssafy.icecreamapp.model.dto.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.Icecream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IcecreamServiceImpl implements IcecreamService{

    private final IcecreamDao icecreamDao;

    @Override
    public Icecream getIcecreamById(int id) {
        return icecreamDao.selectIcecreamById(id);
    }

    @Override
    public List<Icecream> getIcecreamsWithCon(IceSelectCon iceSelectCon) {
        return icecreamDao.selectIcecreamsByCon(iceSelectCon);
    }
}
