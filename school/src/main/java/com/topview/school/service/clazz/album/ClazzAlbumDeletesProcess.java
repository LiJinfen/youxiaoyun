package com.topview.school.service.clazz.album;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topview.multimedia.service.album.AlbumService;
import com.topview.multimedia.vo.AlbumInfo;
import com.topview.multimedia.vo.result.AlbumInfoResult;

@Service
public class ClazzAlbumDeletesProcess implements ClazzAlbumProcess {
	@Autowired
	private AlbumService albumService;
	
	private static final Logger logger = Logger.getLogger(ClazzAlbumDeletesProcess.class);

	@Override
	public boolean doProcess(ClazzAlbumProcessContext context) {
		try {
			AlbumInfo info = context.getInfo();
			AlbumInfoResult result = albumService.albumDeletes(info);
			context.setResult(result);
			context.getResult().setSuccess(true);
			return true;
		} catch (Exception e) {
			logger.error("clazz delete album fail");
			context.getResult().setSuccess(false);
			return false;

		}
	}

}