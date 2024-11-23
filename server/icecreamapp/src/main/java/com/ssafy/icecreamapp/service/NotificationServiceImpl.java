package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dao.NotificationDao;
import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.Notification;
import com.ssafy.icecreamapp.model.dto.respond.NotificationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl {
    private final NotificationDao notificationDao;
    private final MemberDao memberDao;

    public List<NotificationInfo> getNotiByEmail(String email) {
        Member member = memberDao.selectByEmail(email);
        List<Notification> list = notificationDao.selectNotisByMemberId(member.getId());
        List<NotificationInfo> result = new ArrayList<>();
        for (Notification notification : list) {
            result.add(new NotificationInfo(notification));
        }
        return result;
    }
}
