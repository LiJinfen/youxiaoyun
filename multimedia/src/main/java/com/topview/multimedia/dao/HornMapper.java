package com.topview.multimedia.dao;

import java.util.List;

import com.topview.multimedia.dao.base.BaseDao;
import com.topview.multimedia.po.Horn;

public interface HornMapper extends BaseDao<Horn> {
	
	/**
	 * @dateTime 2016年10月13日下午7:56:40
	 * @author zjd
	 * @description 由用户id获取小喇叭 
	 */
	public List<Horn> getHornByUserId(String userId);
	
	/**
	 * @dateTime 2016年11月28日下午7:06:38
	 * @author zjd
	 * @description 根据发送者id获取到小喇叭
	 */
	public List<Horn> getHornBySenderId(String senderId);
}