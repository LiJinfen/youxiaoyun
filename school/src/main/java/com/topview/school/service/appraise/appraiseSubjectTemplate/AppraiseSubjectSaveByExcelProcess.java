/**
 * @Description: 
 * @author chenzufeng
 * @date  2015年9月13日 上午10:18:09 
 * @version V1.0
 */
package com.topview.school.service.appraise.appraiseSubjectTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topview.school.dao.appraise.AppraiseSubjectTemplateMapper;
import com.topview.school.po.AppraiseSubjectTemplate;
import com.topview.school.service.base.Context;
import com.topview.school.service.base.Process;
import com.topview.school.util.ExcelUtil;
import com.topview.school.util.UUIDUtil;
import com.topview.school.vo.appraise.AppraiseSubjectInfo;

/**
 * @ClassName: AppraiseSubjectSaveByExcelProcess
 * @Description: TODO
 * @author Chenzufeng 1023284613@qq.com
 * @date 2015年9月13日 上午10:18:09
 * 
 */
@Service
public class AppraiseSubjectSaveByExcelProcess extends Process {

	@Autowired
	private AppraiseSubjectTemplateMapper appraiseSubjectMapper;
	private static final Logger logger = Logger
			.getLogger(AppraiseSubjectSaveByExcelProcess.class);

	@Override
	public boolean doProcess(Context context) {
		AppraiseSubjectRequest asrequest = (AppraiseSubjectRequest) context
				.getRequest();
		try {
			List<String> headList = new ArrayList<String>();
			Map<String, String> map = new HashMap<String, String>();
			ExcelUtil poi = new ExcelUtil();
			File file = null;
			List<AppraiseSubjectInfo> result;
			headList.add("评价内容");
			headList.add("星数等级");

			map.put("评价内容", "word");
			map.put("星数等级", "star");
			try {
				result = poi.importExcel("Sheet1", asrequest.getPath(), map,
						headList, AppraiseSubjectInfo.class); // 解析Excel内容
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			for (AppraiseSubjectInfo info : result) {
				AppraiseSubjectTemplate appraiseSubjectTemplate = new AppraiseSubjectTemplate();
				appraiseSubjectTemplate.setId(UUIDUtil.getUUID());
				appraiseSubjectTemplate.setWord(info.getWord());
				appraiseSubjectTemplate.setStar(info.getStar());
				// appraiseSubjectTemplate.setSchoolId(asrequest.getSchoolId());
				appraiseSubjectTemplate.setSubject(asrequest.getSubject());
//				System.out.println(asrequest.getSign()+appraiseSubjectTemplate.getSubject());
				appraiseSubjectTemplate.setSign(asrequest.getSign());
				// appraiseSubjectTemplate.setType(asrequest.getType());
				// appraiseSubjectTemplate.setSubjectId(asrequest.getSubjectId());
				try {
					appraiseSubjectMapper.insert(appraiseSubjectTemplate);

				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(); // 事务回滚
				}
			}
			file = new File(asrequest.getPath());
			file.delete();// 数据读取完后删除掉文件
			asrequest.setSuccess(true);
			return true;
		} catch (Exception e) {
			logger.error("save appraiseSubjectTemplate fail");
			asrequest.setSuccess(false);
			return false;
		}

	}

}
