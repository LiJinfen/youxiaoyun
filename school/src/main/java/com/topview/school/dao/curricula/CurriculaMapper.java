package com.topview.school.dao.curricula;

import java.util.List;
import java.util.Map;

import com.topview.school.dao.base.BaseDao;
import com.topview.school.po.Curricula;
import com.topview.school.vo.curricula.CurriculaInfoVo;
import com.topview.school.vo.curricula.CurriculaVo;

public interface CurriculaMapper extends BaseDao<Curricula> {

	/**
	 * 根据课程名称和学校id查询课程
	 * 
	 * @return
	 */
	public Curricula selectByNameAndSchoolId(String curriculaName,
			String schoolId);
	
	/**
	 * 根据学科id获取课程
	 * @param subjectId
	 * @return
	 */
	public List<CurriculaVo> getCurriculaBySubjectId(String subjectId);
	
	/**
	 * 根据学科id、适应学期、适应年级查询课程
	 * @param map
	 * @return
	 */
	public List<Curricula> getCurriculas(Map<String, Object> map);
	
	
	/**
	 * 根据学期Id和班级id 获取对应学科的任课课程
	 */
	public List<CurriculaInfoVo> getCurriculaInfo(String semesterId,String classId);

	

	/**
	 * 根据考试Id获取对应的任课课程
	 */
	public Curricula getCurriculaByExamId(String examId);
}