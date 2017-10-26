/**
 * @Description: 
 * @author chenzufeng
 * @date  2015年9月4日 下午8:32:30 
 * @version V1.0
 */
package com.topview.school.service.appraise.appraiseSubjectTemplate;

import java.util.List;

import com.topview.school.service.base.BaseServiceImpl;
import com.topview.school.service.base.Process;
import com.topview.school.vo.appraise.AppraiseSubjectInfo;
import com.topview.school.vo.appraise.result.AppraiseSubjectInfoResult;
/** 
 * @ClassName: AppraiseSubjectServiceImpl 
 * @Description: TODO 
 * @author Chenzufeng 1023284613@qq.com
 * @date 2015年9月4日 下午8:32:30 
 *  
 */
public class AppraiseSubjectServiceImpl extends BaseServiceImpl implements AppraiseSubjectService{

	private List<AppraiseSubjectProcess> appraiseSubjectFindProcesses;
    private List<AppraiseSubjectProcess> appraiseSubjectSaveProcesses;
	private List<AppraiseSubjectProcess> appraiseSubjectUpdateProcesses;
	
	private List<Process> appraiseSubjectSaveByExcelProcesses;
    private List<Process> appraiseSubjectDeleteProcesses;
    
	@Override
	public AppraiseSubjectInfoResult getAppraiseSubject(AppraiseSubjectInfo info) {

		return doProcess(info,appraiseSubjectFindProcesses);
	}

	@Override
	public AppraiseSubjectInfoResult saveAppraiseSubject(
			AppraiseSubjectInfo info) {
		
		return doProcess(info,appraiseSubjectSaveProcesses);
	}

	
	@Override
	public AppraiseSubjectInfoResult updateAppraiseSubject(
			AppraiseSubjectInfo info) {
		return doProcess(info,appraiseSubjectUpdateProcesses);
	}
	
	
	@Override
	public boolean appraiseSubjectSaveByExcel(
			AppraiseSubjectRequest appraiseSubjectSavaRequest) {
				return excute(appraiseSubjectSavaRequest,appraiseSubjectSaveByExcelProcesses);
		
	}

	@Override
	public boolean DeleteappraiseSubject(
			AppraiseSubjectRequest appraiseSubjectSavaRequest) {
		
		return excute(appraiseSubjectSavaRequest,appraiseSubjectDeleteProcesses);
	}



	public List<AppraiseSubjectProcess> getAppraiseSubjectFindProcesses() {
		return appraiseSubjectFindProcesses;
	}





	public void setAppraiseSubjectFindProcesses(
			List<AppraiseSubjectProcess> appraiseSubjectFindProcesses) {
		this.appraiseSubjectFindProcesses = appraiseSubjectFindProcesses;
	}





	public List<AppraiseSubjectProcess> getAppraiseSubjectSaveProcesses() {
		return appraiseSubjectSaveProcesses;
	}





	public void setAppraiseSubjectSaveProcesses(
			List<AppraiseSubjectProcess> appraiseSubjectSaveProcesses) {
		this.appraiseSubjectSaveProcesses = appraiseSubjectSaveProcesses;
	}





	public List<Process> getAppraiseSubjectSaveByExcelProcesses() {
		return appraiseSubjectSaveByExcelProcesses;
	}

	public void setAppraiseSubjectSaveByExcelProcesses(
			List<Process> appraiseSubjectSaveByExcelProcesses) {
		this.appraiseSubjectSaveByExcelProcesses = appraiseSubjectSaveByExcelProcesses;
	}

	
	


	public List<Process> getAppraiseSubjectDeleteProcesses() {
		return appraiseSubjectDeleteProcesses;
	}

	public void setAppraiseSubjectDeleteProcesses(
			List<Process> appraiseSubjectDeleteProcesses) {
		this.appraiseSubjectDeleteProcesses = appraiseSubjectDeleteProcesses;
	}

	public List<AppraiseSubjectProcess> getAppraiseSubjectUpdateProcesses() {
		return appraiseSubjectUpdateProcesses;
	}

	public void setAppraiseSubjectUpdateProcesses(
			List<AppraiseSubjectProcess> appraiseSubjectUpdateProcesses) {
		this.appraiseSubjectUpdateProcesses = appraiseSubjectUpdateProcesses;
	}

	
	
	public AppraiseSubjectInfoResult doProcess(AppraiseSubjectInfo info,List<AppraiseSubjectProcess> processes) {
	AppraiseSubjectProcessContext context = new AppraiseSubjectProcessContext();
	AppraiseSubjectInfoResult result = new AppraiseSubjectInfoResult();
	context.setResult(result);
	context.setInfo(info);
	for(AppraiseSubjectProcess process : processes){
		if(!process.doProcess(context))
			break;
	}
		
	return context.getResult();
}



	



	

	
}