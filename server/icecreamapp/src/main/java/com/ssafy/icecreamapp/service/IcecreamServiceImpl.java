package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.model.dao.IcecreamDao;
import com.ssafy.icecreamapp.model.dto.request.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.Icecream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class IcecreamServiceImpl implements IcecreamService {

    private final IcecreamDao icecreamDao;

    @Override
    public Icecream getIcecreamById(int id) {
        Icecream icecream = icecreamDao.selectIcecreamById(id);
        if (icecream == null) {
            log.error("없는 아이디 검색");
            throw new NoSuchElementException("없는 제품 아이디 : " + id);

        }
        return icecream;
    }

    @Override
    public List<Icecream> getIcecreamsWithCon(IceSelectCon iceSelectCon) {
        return icecreamDao.selectIcecreamsByCon(iceSelectCon);
    }
}
