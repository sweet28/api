package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.pojo.ActivityVote;

public interface IVoteService {
	 //用于获取投票列表信息 vote/act/list接口调用 
    List<ActivityVote> getVoteListByActId(Map<String, Object> map);

}
