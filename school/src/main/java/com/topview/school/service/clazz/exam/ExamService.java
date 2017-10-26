package com.topview.school.service.clazz.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.topview.school.po.Exam;
import com.topview.school.po.ExamTemplet;
import com.topview.school.vo.exam.ExamInfo;
import com.topview.school.vo.exam.ExamInfoToTeacherResult;

public interface ExamService{

	//教师端获取考试列表
	public ExamInfoToTeacherResult examFind(HttpSession session);
	
	/**
	 * 创建一个考试
	 * @param info
	 * @return
	 */
	public boolean createExam(ExamInfo info);
	
	/**
	 * 删除一个考试
	 * @param examId
	 * @return
	 */
	public boolean deleteExam(String examId);
	
	/**
	 * 修改考试信息
	 * @param info
	 * @return
	 */
	public boolean updateExam(ExamInfo info);
	
	/**
	 * 单条件或多条件查询考试记录
	 * @param subjectId
	 * @param clazzId
	 * @param semesterId
	 * @param gradeId
	 * @param curriculaId
	 * @return
	 */
	public List<ExamInfo> selectExamRecord(Map<String, Object> map);
	
	public List<ExamInfo> selectAllTeacherExam(Map<String, Object> map);
	
	/**
	 * 查询考试记录条数
	 * @param map
	 * @return
	 */
	public int selectExamRecordCount(Map<String, Object> map);
	
	public int selectAllTeacherRecordCount(Map<String, Object> map);
	
	/**
	 * 根据选课id查询考试
	 * @param curriculaVariableId
	 * @return
	 */
	public List<Exam> selectByCurriculaVariableId(String curriculaVariableId);
	
	
	public boolean updateExamStatus(String userId, int status,String examId);
	

	/**
	 * @dateTime 2016年8月19日上午12:04:20
	 * @author zjd
	 * @description 根据课程id或学期id获取到所有的考试
	 */
	public List<Exam> getExamByCurriculaIdAndTermId(String curriculaId, String termId, String clazzId);

	public Exam selectById(String examId);

	public String getSubjectByExamId(String examId);
	
	/**
	 * 查看 
	 * @return
	 */
	public List<ExamTemplet> getAllExamTemplet();
	
	/**
	 * 增加考试类型
	 * @param examTemplet
	 * @return
	 */
	public boolean addExamTemplet(ExamTemplet examTemplet);
}