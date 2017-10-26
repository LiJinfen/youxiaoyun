package com.topview.school.controller.user;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.topview.school.po.Parent;
import com.topview.school.po.Student;
import com.topview.school.service.user.parent.ParentService;
import com.topview.school.service.user.student.StudentService;
import com.topview.school.util.ClassLoaderUtil;
import com.topview.school.util.DateFormatUtil;
import com.topview.school.util.DownloadAndUploadUtil;
import com.topview.school.util.FileUploadUtil;
import com.topview.school.util.JSONUtil;
import com.topview.school.vo.FileUploadVo;
import com.topview.school.vo.User.StudentAndParentInfo;
import com.topview.school.vo.User.UserInfo;

/**
 * @Description 学生controller
 * @Author <wuyiliang 757210697@qq.com>
 * @Date 2015年8月16日 下午11:04:30
 * @CopyRight 2015 TopView Inc
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/student", produces = "text/html;charset=UTF-8")
public class StudentController {

	@Resource
	private StudentService studentService;
	@Resource
	private ParentService parentService;

	/**
	 * 
	 * @Title: downloadStudentAndParent
	 * @Description: 下载学生和家长信息Excel表
	 * @param @param request
	 * @param @param respone
	 * @param @return
	 * @param @throws Exception
	 * @return ResponseEntity<byte[]>
	 * @throws
	 */
	@RequestMapping(value = "/downloadStudentAndParentExcel", method = RequestMethod.POST)
	public ResponseEntity<byte[]> downloadStudentAndParent(
			HttpServletRequest request, HttpServletResponse respone,
			String clazzId) throws Exception {

		List<StudentAndParentInfo> sapis = new ArrayList<StudentAndParentInfo>();
		if (clazzId != null && !"".equals(clazzId)) {
			// 1.查询班级学生
			List<Student> students = studentService.selectByClazzId(clazzId);
			// 2.查询每个学生对应的家长
			for (Student s : students) {
				List<Parent> parents = parentService.selectByStudentId(s
						.getId());
				StudentAndParentInfo sapi = new StudentAndParentInfo();
				sapi.setStudentName(s.getName()); // 学生姓名
				sapi.setStudentGender(s.getSex()); // 学生性别
				sapi.setStudentIDCard(s.getIdcard()); // 学号
				if (s.getBirthday() != null && !"".equals(s.getBirthday())) {
					sapi.setFeteday(DateFormatUtil.formatDateToDay(s
							.getBirthday())); // 出生年月
				}
				sapi.setStudentAddress(s.getAddress()); // 家庭住址
				sapi.setEmergencyPhone(s.getEmergencyPhone()); // 紧急电话
				sapi.setStudentPhone(s.getPhone()); // 家庭固定电话
				sapi.setParentName(parents.get(0).getName()); // 家长1姓名
				sapi.setParentGender(parents.get(0).getSex()); // 家长1性别
				sapi.setParentPhone(parents.get(0).getMobile()); // 家长1电话
				if (parents.size() == 2) {
					sapi.setParent2Name(parents.get(1).getName()); // 家长2姓名
					sapi.setParent2Gender(parents.get(1).getSex()); // 家长2性别
					sapi.setParentPhone(parents.get(1).getMobile()); // 家长2手机号码
				}
				sapis.add(sapi);
			}
		}
		// 3.处理目标文件路径
		String fileName = "学生与家长信息";
		String relativePath = ClassLoaderUtil.getExtendResource(
				"../schoolUpload/studentAndParent", "school").toString();
		String realPath = relativePath.replace("/", "\\");
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String filePath = realPath + "\\" + fileName;
		// 4.生成excel文件
		studentService.createStudentAndParentExcel(filePath, sapis);
		File targetFile = new File(filePath);
		return DownloadAndUploadUtil.download(request, targetFile, fileName);
	}

	/**
	 * 
	 * @Title: saveStudentAndParent
	 * @Description: 上传学生家长excel
	 * @param @param file
	 * @param @param request
	 * @param @param session
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	@Transactional
	@RequestMapping(value = "/saveStudentAndParent", method = RequestMethod.POST)
	@ResponseBody
	public String saveStudentAndParent(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, String clazzId, HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) session.getAttribute("currentUser");
		// 1.保存文件到服务器
		FileUploadVo vo = FileUploadUtil.uploadFile(file, userInfo.getSchool_id() + "/studentInfo",
				request, true);
		// 2.解析excel并保存到数据库
		boolean flag = studentService.uploadStudentAndParentInfo(
				vo.getFileName(), vo.getRealPath() + "\\" + vo.getFileName(),
				clazzId);
		resultMap.put("success", flag);
		return JSONUtil.toObject(resultMap).toString();
	}

	/**
	 * 根据班级id查询学生和主家长信息
	 * 
	 * @param clazzId
	 * @return
	 */
	@RequestMapping("/getStudentsByClazzId")
	@ResponseBody
	public String getStudentsByClazzId(String clazzId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] filter = { "birthday", "studentPassword", "feteday",
				"parentGender", "parentPassword", "parent2Name",
				"parent2Gender", "parent2Phone", "parent2Password" };
		List<StudentAndParentInfo> sapis = new ArrayList<StudentAndParentInfo>(); // 存放学生和家长信息
		List<Student> students = studentService.selectByClazzId(clazzId);
		for (Student s : students) {
			StudentAndParentInfo info = new StudentAndParentInfo();
			info.setStudentId(s.getId());
			info.setStudentIDCard(s.getIdcard());
			info.setStudentName(s.getName());
			info.setStudentAddress(s.getAddress());
			info.setStudentGender(s.getSex());
			info.setStudentPhone(s.getPhone());
			info.setEmergencyPhone(s.getEmergencyPhone());
			Parent p = parentService.selectMainParent(s.getId());
			info.setParentId(p.getId());
			info.setParentName(p.getName());
			info.setParentPhone(p.getMobile());
			sapis.add(info);
		}
		resultMap.put("success", true);
		resultMap.put("studentInfo", sapis);
		return JSONUtil.toObject(resultMap, filter).toString();
	}

	/**
	 * web端修改学生信息
	 * @param studentAndParentInfo
	 * @return
	 */
	@RequestMapping("updateStudentInfo")
	@ResponseBody
	public String updateStudentInfo(StudentAndParentInfo studentAndParentInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 目前web端只显示部分字段，所以只提供修改部分字段
		String parentPhone = studentAndParentInfo.getParentPhone();
		if (parentPhone == null || "".equals(parentPhone)) {
			resultMap.put("success", false);
			resultMap.put("msg", "监护人联系方式不能为空");
			return JSONUtil.toObject(resultMap).toString();
		}
		Parent p = parentService.selectByPrimaryKey(studentAndParentInfo
				.getParentId());
		p.setName(studentAndParentInfo.getParentName());
		p.setMobile(parentPhone);
		Student s = studentService.selectByPrimaryKey(studentAndParentInfo
				.getStudentId());
		s.setIdcard(studentAndParentInfo.getStudentIDCard());
		s.setName(studentAndParentInfo.getStudentName());
		s.setSex(studentAndParentInfo.getStudentGender());
		s.setAddress(studentAndParentInfo.getStudentAddress());
		s.setPhone(studentAndParentInfo.getStudentPhone());
		resultMap.put("success", parentService.updateParent(p)
				&& studentService.updateStudent(s));
		return JSONUtil.toObject(resultMap).toString();
	}
	
	/**
	 * 删除学生家长
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteStudent")
	@ResponseBody
	public String deleteStudent(String studentId, String parentId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = studentService.deleteStudentAndParent(studentId, parentId);
		resultMap.put("success", flag);
		if(!flag) {
			resultMap.put("msg", "该账号关联其他重要数据，如需删除请联系系统管理员!");
		}
		return JSONUtil.toObject(resultMap).toString();
	}
}
