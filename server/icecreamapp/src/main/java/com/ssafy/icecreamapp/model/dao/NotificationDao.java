package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Notification;
import com.ssafy.icecreamapp.model.dto.respond.NotificationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationDao {
    int insertNotification(Notification notification);

    List<Notification> selectNotisByMemberId(int memberId);
}
