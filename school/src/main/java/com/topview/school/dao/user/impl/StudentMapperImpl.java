package com.topview.school.dao.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.topview.school.dao.base.impl.BaseDaoImpl;
import com.topview.school.dao.user.StudentMapper;
import com.topview.school.po.Student;

/**
 * 
 * 项目名称：school <br>
 * 类名称：StudentMapperImpl <br>
 * 类描述： <br>
 * 创建人：luozhangjie <br>
 * 创建时间：2015年3月26日 下午8:48:37 <br>
 * 修改人：luozhangjie<br>
 * 修改时间：2015年3月26日 下午8:48:37 <br>
 * 修改备注： <br>
 * 
 * @version 1.0 <br>
 *
 */
@Repository
public class StudentMapperImpl extends BaseDaoImpl<Student> implements
		StudentMapper {

	@Override
	public List<Student> selectByClazzId(String clazzId) {
		return template.selectList(getStatement("selectByClazzId"), clazzId);
	}

	/**
	 * 
	 * @Title: findByParentId
	 * @Description: 根据父母Id查找学生
	 * @param @param parent_id
	 * @param @return
	 * @return Student
	 * @throws
	 */
	@Override
	public List<Student> findByParentId(String parent_id) {

		return template.selectList(getStatement("findByParentId"), parent_id);
	}

	@Override
	public List<Student> findByMulti(Map<String, Object> params) {
		return template.selectList(getStatement("findByMulti"), params);
	}

	@Override
	public Student findByNameAndIdCard(String name, String idCard) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("idCard", idCard);
		return template.selectOne(getStatement("findByNameAndIdCard"), map);
	}

	@Override
	public List<Student> getAllStudentBySchool(String schoolId) {
		return template.selectList(getStatement("getAllStudentBySchool"), schoolId);
	}

	@Override
	public List<Student> getAllStudentByGradeId(String gradeId) {
		return template.selectList(getStatement("getAllStudentByGradeId"), gradeId);
	}

	@Override
	public List<Student> findByParentIdAndName(Map<String, Object> map) {
		return template.selectList(getStatement("findByParentIdAndName"), map);
	}

	@Override
	public String findSchoolNameByStudent(Map<String, Object> map) {
		return template.selectOne(getStatement("findSchoolNameByStudent"), map);
	}

	@Override
	public int countByClazzId(String clazzId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Student queryById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
