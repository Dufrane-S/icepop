package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dto.request.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.Icecream;

import java.util.List;

public interface IcecreamService {
    Icecream getIcecreamById(int id);
    List<Icecream> getIcecreamsWithCon(IceSelectCon iceSelectCon);
}
