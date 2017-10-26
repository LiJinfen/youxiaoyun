package com.topview.school.dao.exam;

import java.util.List;
import java.util.Map;

import com.topview.school.dao.base.BaseDao;
import com.topview.school.po.Exam;

public interface ExamMapper extends BaseDao<Exam>{
	
	public List<Exam> findByMulti(Map<String, Object> params);
	
	/**
	 * 
	* @Title: selectMyExam 
	* @Description: 安卓端成绩列表
	* @param @param map
	* @param @return   
	* @return List<Exam>   
	* @throws
	 */
	public List<Exam> selectMyExam(Map<String, Object> map);
	
	/**
	 * 
	* @Title: getSubjectExamHistory 
	* @Description: 某科目考试历史成绩
	* @param @param map
	* @param @return   
	* @return List<Exam>   
	* @throws
	 */
	public List<Exam> getSubjectExamHistory(Map<String, Object> map);
	
	//根据科目名称、班级id获取考试列表
	public List<Exam> getExamList(Map<String, Object> map);
	//根据班级id和科任老师id获取考试列表
	public List<Exam> getExams(Map<String, Object> map);
	
	/**
	 * 单条件或多条件查询考试记录：clazzId、semesterId、gradeId、curriculaId
	 * @param map
	 * @return
	 */
	public List<Exam> selectExamRecord(Map<String, Object> map);
	
	public List<Exam> selectAllTeacherExam(Map<String, Object> map);
	
	

	/**
	 * 查询记录条数
	 * @param map
	 * @return
	 */
	public int selectExamRecordCount(Map<String, Object> map);
	
	
	public int selectAllTeacherRecordCount(Map<String, Object> map);
	/**
	 * 根据多个课程id查询考试记录（按时间正序排序）
	 * @return
	 */
	public List<Exam> selectExamsByCurriculaVariableIds(List<String> curriculaVarriableIds);

	/**
	 * 根据选课id查询考试
	 * @param curriculaVariableId
	 * @return
	 */
	public List<Exam> selectByCurriculaVariableId(String curriculaVariableId);
	
	/**
	 * 设置已经开设的考试的状态
	 */
	public int updateExamStatus(Map<String,Object> map);
	
	public List<Exam> getExamByCurriculaIdAndTermId(String curriculaId, String termId, String clazzId);

	public String getSubjectByExamId(String examId);
}