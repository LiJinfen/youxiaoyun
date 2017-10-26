package com.topview.school.service.user.teacher;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topview.school.dao.appraise.AppraiseMapper;
import com.topview.school.dao.curricula.CurriculaMapper;
import com.topview.school.dao.curricula.CurriculaVariableMapper;
import com.topview.school.dao.leave.LeaveMapper;
import com.topview.school.dao.school.ClazzMapper;
import com.topview.school.dao.school.DepartmentsMapper;
import com.topview.school.dao.school.SubjectMapper;
import com.topview.school.dao.user.TeacherMapper;
import com.topview.school.dao.user.TeacherPositionMapper;
import com.topview.school.po.CurriculaVariable;
import com.topview.school.po.Departments;
import com.topview.school.po.School;
import com.topview.school.po.Subject;
import com.topview.school.po.Teacher;
import com.topview.school.po.TeacherPosition;
import com.topview.school.po.UserRoleKey;
import com.topview.school.service.school.SchoolService;
import com.topview.school.service.system.authc.RoleType;
import com.topview.school.service.system.authc.UserRoleService;
import com.topview.school.util.ExcelUtil;
import com.topview.school.util.MyBatisMapUtil;
import com.topview.school.util.UUIDUtil;
import com.topview.school.vo.User.TeacherExcelInfo;
import com.topview.school.vo.User.TeacherVo;
import com.topview.school.vo.User.UserInfo;
import com.topview.school.vo.User.result.TeacherInfoResult;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Resource
	private TeacherMapper teacherMapper;
	@Resource
	private SubjectMapper subjectMapper;
	@Autowired
	private UserRoleService userRoleService;
	@Resource
	private ClazzMapper clazzMapper;			
	@Resource
	private TeacherPositionMapper teacherPositionMapper;	
	@Resource
	private CurriculaVariableMapper curriculaVariableMapper; 
	@Resource
	private CurriculaMapper curriculaMapper;
	@Resource
	private DepartmentsMapper departmentsMapper;		
	@Resource
	private LeaveMapper leaveMapper;
	@Resource
	private AppraiseMapper appraiseMapper;
	@Autowired
	private SchoolService schoolService;
	/**
	 * 根据老师id查询教师(保持返回json格式不变)
	 */
	public TeacherInfoResult teacherFindById(String teacherId) {
		TeacherInfoResult result = new TeacherInfoResult();
		Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
		TeacherVo vo = TeacherVo.changeToVo(teacher);
		Subject subject = subjectMapper.getTeacherSubject(teacherId);
		if (subject != null) {
			vo.setSubject(subject.getName());
		}
		List<TeacherVo> teacherVos = new ArrayList<TeacherVo>();
		teacherVos.add(vo);
		result.setSuccess(true);
		result.setResult(teacherVos);
		return result;
	}

	/**
	 * 更新教师信息
	 */
	@Override
	public boolean updateTeacherInfo(Teacher teacher) {
		Teacher t = teacherMapper.selectByPrimaryKey(teacher.getId());
		if (null != teacher.getPhone()  && !"".equals(teacher.getPhone())
				&& !t.getPhone().equals(teacher.getPhone())) { // 如果是修改账号
			if (teacherMapper.findByPhone(teacher.getPhone()) != null) {
				return false;
			}
		}
		return teacherMapper.updateByPrimaryKeySelective(teacher) > 0 ? true
				: false;
	}

	/**
	 * 查找班主任
	 */
	@Override
	public Teacher findHeadTeacher(String classId) {
		return teacherMapper.findHeadTeacher(classId);
	}

	/**
	 * 创建空的教师excel表格
	 */
	@Override
	public boolean createNullExcel(String path) {

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");
		CellStyle style = wb.createCellStyle();
		style.setAlignment((short) 1);
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("教师工号");
		row.createCell(1).setCellValue("教师姓名");
		row.createCell(2).setCellValue("性别");
		row.createCell(3).setCellValue("email");
		row.createCell(4).setCellValue("手机号码");
		row.createCell(5).setCellValue("教师学历");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			wb.write(fos);
			return true;
		} catch (Exception e) {
			System.out.println("---创建空白教师信息Excel表出错--");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 上传教师信息表
	 */
	@Transactional
	@Override
	public boolean uploadTeacherInfo(String fileName, String realPath,
			UserInfo userInfo, List<Teacher> teacherlists) {

		List<String> headList = new ArrayList<String>(); // excel表头
		Map<String, String> map = new HashMap<String, String>();
		ExcelUtil poi = new ExcelUtil();
		List<Teacher> teachers = new ArrayList<Teacher>();

		headList.add("教师工号");
		headList.add("教师姓名");
		headList.add("性别");
		headList.add("email");
		headList.add("手机号码");
		headList.add("教师学历");

		map.put("教师工号", "idcard");
		map.put("教师姓名", "name");
		map.put("性别", "sex");
		map.put("email", "email");
		map.put("手机号码", "phone");
		map.put("教师学历", "education");

		try {
			teachers = poi.importExcel("sheet1", realPath + "\\" + fileName,
					map, headList, Teacher.class);
			String schoolId = userInfo.getSchool_id();
			for (Teacher t : teachers) {
				t.setLastUpdate(new Date());
				if ("男".equals(t.getSex())) {

					t.setPicUrl("/schoolUpload/userPic/老师boy.png");
				} else {

					t.setPicUrl("/schoolUpload/userPic/老师girl.png");
				}
				if (teacherMapper.findByPhone(t.getPhone()) == null) {
					t.setId(UUIDUtil.getUUID());
					t.setCreateTime(new Date());
					t.setPassword("123456");
					t.settScSchoolId(schoolId);
					
					if (teacherMapper.insert(t) <= 0) {

						throw new Exception();
					} else {
						if (t.getId() != null
								&& !userRoleService.hasRole(t.getId(),
										RoleType.TEACHER.value())) {

							UserRoleKey key = new UserRoleKey(t.getId(),
									RoleType.TEACHER.value());
							userRoleService.insert(key);// 添加教师角色
						}
					}
				} else {
					Teacher teach = teacherMapper.findByPhone(t.getPhone());
					if(teach.gettScSchoolId().equals(schoolId)) {
						t.setId(teach.getId());
						teacherMapper.updateByPrimaryKeySelective(t);
					}else {
						School school = schoolService.selectByPrimaryKey(teach.gettScSchoolId());
						t.setSchoolName(school.getName());
						teacherlists.add(t);
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("上传教师信息表失败"); // 回滚事务
		}
	}

	@Override
	public List<Teacher> getTeacher(Map<String, Object> map) {

		return teacherMapper.getTeacher(map);
	}

	@Override
	public List<Teacher> selectTeacherByDepartmentId(String departmentId) {

		return teacherMapper.selectTeacherByDepartmentId(departmentId);
	}

	@Override
	public int selectCount(String schoolId) {

		return teacherMapper.selectCount(schoolId);
	}

	/**
	 * 取消老师班主任或级长的身份
	 */
	@Override
	public boolean cancelPosition(Map<String, Object> map) {

		return teacherMapper.cancelPosition(map) > 0 ? true : false;
	}

	@Override
	public Teacher selectTeacherById(String id) {

		return teacherMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据手机号查询教师
	 */
	@Override
	public Teacher findByPhone(String phone) {

		return teacherMapper.findByPhone(phone);
	}

	@Override
	public boolean updatePassword(Teacher teacher) {

		return teacherMapper.updateByPrimaryKeySelective(teacher) > 0 ? true
				: false;
	}

	@Override
	public boolean addPosition(String positionId, String teacherId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("positionId", positionId);
		params.put("teacherId", teacherId);
		return teacherMapper.addPosition(params) > 0 ? true : false;
	}

	@Override
	public boolean deletePosition(String positionId, String teacherId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("positionId", positionId);
		params.put("teacherId", teacherId);
		return teacherMapper.deletePosition(params) > 0 ? true : false;
	}

	@Override
	public List<Teacher> selectTeacherByPositionId(String positionId,
			Integer start, Integer limit) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("positionId", positionId);
		map.put("offset", start);
		map.put("limit", limit);
		return teacherMapper.selectTeacherByPositionId(map);
	}

	@Override
	public int getCountByPositionId(String positionId) {

		return teacherMapper.getCountByPositionId(positionId);
	}

	@Override
	public String getMonitorId(String gradeId) {

		Map<String, Object> map = new HashMap<>();
		map.put("t_sc_grade_id", gradeId);
		List<Teacher> ts = teacherMapper.selectByParameters(MyBatisMapUtil
				.warp(map));
		String tId = null;
		if (ts.size() != 0 && ts.get(0) != null) {
			tId = ts.get(0).getId();
		}
		return tId;
	}

	public List<TeacherVo> findLike(Map<String, Object> param) {

		List<Teacher> teachers = teacherMapper.findLike(param);
		return TeacherVo.changeToVo(teachers);
	}

	@Override
	@Transactional
	public void delete(String teacherId) {
		try {

			userRoleService.deleteByUserId(teacherId);// 删除教师角色绑定
			teacherMapper.deleteByPrimaryKey(teacherId); // 若无其他关联则删除成功
		} catch (Exception e) { // 否则将账号和密码置为空

			Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
			teacher.setPhone(" ");
			teacher.setPassword(null);
			teacherMapper.updateByPrimaryKey(teacher);
		}
	}

	@Override
	public boolean addTeacher(TeacherVo vo) {
		Teacher t = TeacherVo.changeToPo(vo);
		if(t.getId() == null) {
			t.setId(UUIDUtil.getUUID());
		}
		t.setPassword("123456");
		t.setCreateTime(new Date());
		if ("男".equals(t.getSex())) {
			t.setPicUrl("/schoolUpload/userPic/老师boy.png");
		} else {
			t.setPicUrl("/schoolUpload/userPic/老师girl.png");
		}
		if (teacherMapper.findByPhone(t.getPhone()) == null) { // 当前教师手机号不存在数据库中时才能添加新教师
			if (teacherMapper.insert(t) > 0) { // 如果插入成功则分配角色
				if ("是".equals(vo.getIsAuthc())) { //分配管理员角色
					UserRoleKey key = new UserRoleKey(t.getId(),
							RoleType.SCHOOL_MANAGER.value());
					userRoleService.insert(key);
				} else { //分配普通任课老师角色
					UserRoleKey key = new UserRoleKey(t.getId(),
							RoleType.TEACHER.value());
					userRoleService.insert(key);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public List<Teacher> getAllTeacher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Teacher> getTeacherBySchoolId(String schoolId) {
		return teacherMapper.getTeacherBySchoolId(schoolId);
	}

	private List<String> createHeadList() {
		List<String> headList = new ArrayList<String>();
		headList.add("教师工号");
		headList.add("教师姓名");
		headList.add("性别");
		headList.add("email");
		headList.add("手机号码");
		headList.add("教师学历");
		return headList;
	}
	
	private Map<String, String> createHeadListMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("教师工号", "idcard");
		map.put("教师姓名", "name");
		map.put("性别", "sex");
		map.put("email", "email");
		map.put("手机号码", "phone");
		map.put("教师学历", "education");
		return map;
	}
	
	@Override
	public boolean createTeacherExcel(String filePath, List<TeacherExcelInfo> sapis) {
		List<String> headList = createHeadList();
		Map<String, String> map = createHeadListMap();
		ExcelUtil poi = new ExcelUtil();
		try {
			poi.exportExcel("Sheet1", filePath, map, headList, sapis, 1,
					TeacherExcelInfo.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String newDeleteTeacher(String teacherId) {
		List<TeacherPosition> teacherPosition;
		Subject subject;
		List<CurriculaVariable> curriculaVariable;
		List<Departments> departments;
		Teacher teacher;
		teacher = teacherMapper.selectByPrimaryKey(teacherId);
		subject = subjectMapper.getTeacherSubject(teacherId);
		teacherPosition = teacherPositionMapper.getPositions(teacherId);
		//userRole = userRoleService.selectByParameters(MapUtil.warp("userId", teacherId));
		curriculaVariable = curriculaVariableMapper.selectByTeacherId(teacherId);
		departments = departmentsMapper.selectDepartmentsByTeacherId(teacherId);
		//leave = leaveMapper.selectByParameters(MapUtil.warp("tScTeacherId", teacherId));
		//appraise = appraiseMapper.selectByParameters(MapUtil.warp("teacherId", teacherId));
		if(teacherId == null || "".equals(teacherId)) {
			return "id"; //id为空
		}
		if(subject != null) {
			return "该教师是"+ subject.getName() +"课程的负责人" + ",请先处理后再删除"; //该老师是课程负责人
		}
		if(teacherPosition != null && !teacherPosition.isEmpty()) {
			return "该教师担任着该学校的"+ teacherPosition.get(0).getName() +"职务" + ",请先处理后再删除"; //该老师担任该学校的职位
		}
		if(curriculaVariable != null && !curriculaVariable.isEmpty()) {
			return "该教师有任课id为" + curriculaVariable.get(0).gettScCurriculaId() + "的记录" + ",请先处理后再删除"; //该教师有任课记录
		}
		if(departments != null && !departments.isEmpty()) {
			return "该教师是属于" + departments.get(0).getName() + "部门" + ",请先处理后再删除"; //该教师属于某个组别
		}
		if(teacher != null && teacher.gettScClassId() != null) {
			return "该教师是id为" + teacher.gettScClassId() + "的班主任" + ",请先处理后再删除";
		}
//		if(leave != null && !leave.isEmpty()) {
//			return "该教师有签过请假条"+"，请假条的id为" + leave.get(0).getId() + ",请先处理后再删除";
//		}
//		if(appraise != null && !appraise.isEmpty()) {
//			return "该教师评价过学生id为"+ appraise.get(0).getStudentId()+"的学生" + ",请先处理后再删除";
//		}
//		for(int i=0;i<userRole.size();i++) {
//			userRoleService.deleteByPrimaryKey(userRole.get(0));
//		}
		userRoleService.deleteByUserId(teacherId);
		if(teacherMapper.deleteByPrimaryKey(teacherId) == 1) {
			return null;
		}
		return "删除失败，请与管理员联系";
	}

	@Override
	public List<Teacher> selectTeacherByschoolIdAndLikeName(String schoolId, String name) {
		return teacherMapper.selectTeacherByschoolIdAndLikeName(schoolId, name);
	}

	@Override
	public Teacher judgeTeacher(String phone) {
		Teacher teacher = teacherMapper.findByPhone(phone);
		if(null != teacher) {
			School school = schoolService.selectByPrimaryKey(teacher.gettScSchoolId());
			teacher.setSchoolName(school.getName());
			return teacher;
		}
		return null;
	}

	@Override
	public List<Teacher> findLikeByName(String keyword, String schoolId, Integer start, Integer limit) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("schoolId", schoolId);
		map.put("offset",start);
		map.put("limit", limit);
		List<Teacher> list = teacherMapper.findLikeByName(map);
		if(list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}

	@Override
	public int countFindLike(String keyword, String schoolId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("schoolId", schoolId);
		int count = teacherMapper.countFindLike(map);
		return count;
	}
}
