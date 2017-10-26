package com.topview.multimedia.service.section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topview.multimedia.dao.MultimediaZoneMapper;
import com.topview.multimedia.po.MultimediaZone;

@Service
public class SectionCheckProcess implements SectionProcess{
	@Autowired
	private MultimediaZoneMapper multimediaZoneMapper;

	public boolean doProcess(SectionProcessContext context) {
		try {
			MultimediaZone zone = multimediaZoneMapper.findById(context
					.getInfo().gettMId());
			if (zone != null) {
				context.getResult().setSuccess(true);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			context.getResult().setSuccess(false);
			return false;
		}
	}
}
