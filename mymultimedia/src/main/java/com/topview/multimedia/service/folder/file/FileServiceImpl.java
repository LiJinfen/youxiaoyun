package com.topview.multimedia.service.folder.file;

import java.util.List;

import javax.annotation.Resource;

import com.topview.multimedia.dao.MultimediaFileMapper;
import com.topview.multimedia.vo.FileInfo;
import com.topview.multimedia.vo.result.FileInfoResult;

/** * @author  Rachel 
@date 创建时间：2016年7月29日 下午1:43:06 * 
@version 1.0 * 
@parameter  *
 @since  * 
@return  */
public class FileServiceImpl implements FileService {
	
	List<FileProcess> fileSaveProcesses;
	List<FileProcess> fileDeleteProcesses;
	List<FileProcess> fileFindAllProcesses;
	List<FileProcess> fileFindProcesses;
	List<FileProcess> fileJudgeOwnerProcesses;
	
	@Resource
	private MultimediaFileMapper multimediaFileMapper;
	
	public FileInfoResult fileSave(FileInfo file) {
		return doProcess(file, fileSaveProcesses);
	}
	
	public FileInfoResult fileDelete(FileInfo file){
		return doProcess(file , fileDeleteProcesses);
	}
	
	public FileInfoResult fileFindAll(FileInfo file){
		return doProcess(file,fileFindAllProcesses);
	}
	
	public FileInfoResult fileFind(FileInfo file){
		return doProcess(file,fileFindProcesses);
	}
	
	public FileInfoResult fileJudgeOwner(FileInfo file){
		return doProcess(file,fileJudgeOwnerProcesses);
	}
	
	public List<FileProcess> getFileSaveProcesses() {
		return fileSaveProcesses;
	}



	public void setFileSaveProcesses(List<FileProcess> fileSaveProcesses) {
		this.fileSaveProcesses = fileSaveProcesses;
	}



	public List<FileProcess> getFileDeleteProcesses() {
		return fileDeleteProcesses;
	}



	public void setFileDeleteProcesses(List<FileProcess> fileDeleteProcesses) {
		this.fileDeleteProcesses = fileDeleteProcesses;
	}



	public List<FileProcess> getFileFindAllProcesses() {
		return fileFindAllProcesses;
	}



	public void setFileFindAllProcesses(List<FileProcess> fileFindAllProcesses) {
		this.fileFindAllProcesses = fileFindAllProcesses;
	}



	public List<FileProcess> getFileFindProcesses() {
		return fileFindProcesses;
	}



	public void setFileFindProcesses(List<FileProcess> fileFindProcesses) {
		this.fileFindProcesses = fileFindProcesses;
	}



	public MultimediaFileMapper getMultimediaFileMapper() {
		return multimediaFileMapper;
	}



	public void setMultimediaFileMapper(MultimediaFileMapper multimediaFileMapper) {
		this.multimediaFileMapper = multimediaFileMapper;
	}



	public List<FileProcess> getFileJudgeOwnerProcesses() {
		return fileJudgeOwnerProcesses;
	}

	public void setFileJudgeOwnerProcesses(List<FileProcess> fileJudgeOwnerProcesses) {
		this.fileJudgeOwnerProcesses = fileJudgeOwnerProcesses;
	}

	public FileInfoResult doProcess(FileInfo file,
			List<FileProcess> processes) {
		FileProcessContext context = new FileProcessContext();
		FileInfoResult result = new FileInfoResult();
		context.setResult(result);
		context.setInfo(file);
		for (FileProcess process : processes) {
			if (!process.doProcess(context)) {
				break;
			}
		}
		return context.getResult();
	}

	public int selectCount(String folderId) {
		return multimediaFileMapper.selectCount(folderId);
	}


}
