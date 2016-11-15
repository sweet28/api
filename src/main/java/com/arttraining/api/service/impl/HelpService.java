package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.HelpListBean;
import com.arttraining.api.bean.HelpShowBean;
import com.arttraining.api.dao.HelpMapper;
import com.arttraining.api.service.IHelpService;

@Service("helpService")
public class HelpService implements IHelpService {
	@Resource
	private HelpMapper helpDao;
	
	@Override
	public List<HelpListBean> getHelpList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.helpDao.selectHelpList(map);
	}

	@Override
	public HelpShowBean getHelpShow(Integer id) {
		// TODO Auto-generated method stub
		return this.helpDao.selectHelpShow(id);
	}

}
