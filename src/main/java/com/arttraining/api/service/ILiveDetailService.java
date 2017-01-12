package com.arttraining.api.service;

public interface ILiveDetailService {
    //coffee 0110 获取直播观看人次 live/stop接口调用 
    int getLiveNumberByRoomId(Integer room_id);
}
