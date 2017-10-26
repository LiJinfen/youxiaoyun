package com.topview.school.service.clazz.album.photo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topview.multimedia.service.album.photo.PhotoService;
import com.topview.multimedia.vo.result.PhotoInfoResult;

@Service
public class ClazzPhotoFindProcess implements ClazzPhotoProcess {
	private static final Logger logger = Logger
			.getLogger(ClazzPhotoFindProcess.class);
	@Autowired
	private PhotoService photoService;

	@Override
	public boolean doProcess(ClazzPhotoProcessContext context) {
		try {
			PhotoInfoResult result = photoService.photoFind(context.getInfo());
			context.getResult().setInfoResult(result.getInfoResult());
			context.getResult().setSuccess(true);
			return true;
		} catch (Exception e) {
			logger.error("clazz find photo fail");
			context.getResult().setSuccess(false);
			return false;
		}
	}
}
