package com.topview.school.service.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.topview.school.dao.user.ShowFeaturesMapper;
import com.topview.school.po.ShowFeatures;
import com.topview.school.service.base.BaseServiceImpl;


@Service
public class ShowFeaturesServiceImpl extends BaseServiceImpl implements ShowFeaturesService{

	@Resource
	private ShowFeaturesMapper showFeaturesMapper;
	
	
	@Override
	public ShowFeatures getUserReadStatus(String userId) {
		return this.showFeaturesMapper.getUserReadStatus(userId);
	}

	@Override
	public int udpateUserReadStatus(ShowFeatures showFeatures) {
		return this.showFeaturesMapper.udpateUserReadStatus(showFeatures);
	}

	@Override
	public int insertNewUserToFeature(ShowFeatures showFeatures) {
	     return this.showFeaturesMapper.insert(showFeatures);
	}
		
}