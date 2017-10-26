package com.topview.school.controller.msg_push;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.topview.message.po.BaiduPush;
import com.topview.message.service.BaiduPushService;
import com.topview.message.service.JPushService;
import com.topview.message.service.PushMsgService;
import com.topview.message.vo.HistoryMessageVo;
import com.topview.message.vo.OfflineMessageVo;
import com.topview.message.vo.result.OfflineMessageVoResult;
import com.topview.push.service.PushService;
import com.topview.school.po.Student;
import com.topview.school.po.Teacher;
import com.topview.school.service.user.parent.ParentService;
import com.topview.school.service.user.student.StudentService;
import com.topview.school.service.user.teacher.TeacherService;
import com.topview.school.util.DateFormatUtil;
import com.topview.school.util.FileUploadUtil;
import com.topview.school.util.JSONUtil;
import com.topview.school.util.NotEmptyString;
import com.topview.school.util.PushThreadUtil;
import com.topview.school.util.SimpleAudioChange;
import com.topview.school.util.UUIDUtil;
import com.topview.school.vo.FileUploadVo;
import com.topview.school.vo.User.UserInfo;

/**
 * @Description 消息推送controller
 * @Author <wuyiliang 757210697@qq.com>
 * @Date 2015年8月16日 下午10:38:24
 * @CopyRight 2015 TopView Inc
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/push/*", produces = "text/html;charset=UTF-8")
public class MessagePushController {

	// 注意：登陆后的补发接口写在controller/user/UserController.java中(getOfflineMessage)
	@Resource
	private PushMsgService pushMsgService;
	@Resource
	private ParentService parentService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private PushService pushService;
	@Resource
	private StudentService studentService;
	@Resource
	private BaiduPushService baiduPushService;
	@Resource
	private JPushService jPushService;
	/**
	 * 我的短信群发接口一
	 * 
	 * @param senderId
	 *            发送者id
	 * @param studentsId
	 *            学生id，接收者若为教师则对应的学生id为空字符串，用逗号分隔
	 * @param receiversId
	 *            接收者id（用逗号分隔多个接收者）
	 * @param content
	 *            短信内容
	 * @return
	 */
	@RequestMapping("saveShortMessage")
	@ResponseBody
	public String saveShortMessage(String senderId, String studentsId,
			String receiversId, String kindId, String content) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sendTime = DateFormatUtil.formatDateToSeconds(new Date());
		OfflineMessageVo vo = new OfflineMessageVo();
		vo.setContent(content);
		vo.setReceiverId(receiversId);
		vo.setSenderId(senderId);
		vo.setSendTime(sendTime);
		vo.setSms(1); // 我的短信特有标志位
		vo.setStatue("1"); // 未接收状态
		vo.setStudentId(studentsId);//单发给学生时保存学生的Id 
		vo.setMessageType("1"); // 消息类型为文字
		vo.setType("2"); // 所属模块为我的短信,硬编码限制了该接口只能发送我的短信
		vo.setKindId(kindId);//设置校园通知消息的种类
		
		OfflineMessageVoResult result = pushMsgService.saveMassPush(vo);

		if (result.getCode() == -1) {
			resultMap.put("success", false);
			resultMap.put("msg", "学生id与接收者id数量不匹配");
			return JSONUtil.toObject(resultMap).toString();
		}

		if (result.isSuccess()) {
			resultMap.put("success", "true");
			resultMap.put("sendTime", sendTime);
			new Thread(new PushThreadUtil(pushMsgService, result.getResult()))
			.start();// 推送
		}
		if(receiversId.contains(",")) {
			String[] recIds = receiversId.split(",");
			for(int i = 0; i < recIds.length; i++) {
				if(!"".equals(recIds[i])) {
//					if(!pushMsgService.judgeOnline(recIds[i]).isSuccess()) {
						baiduPush(recIds[i]);
//					}
				}
			}
		}else {
//			if(!pushMsgService.judgeOnline(receiversId).isSuccess()) {
				baiduPush(receiversId);
//			}
		}
		return JSONUtil.toObject(resultMap).toString();
	}
	
	//百度云推送
	private void baiduPush(String recId) {
		List<BaiduPush> list = baiduPushService.queryPush(recId);
		if(null != list) {
			if(0 != list.size()) {
				BaiduPush baiduPush = list.get(0);
				if("iOS".equals(baiduPush.getDevicetype())) {
					baiduPushService.pushMessageToIOSOne("您有新的未读信息", baiduPush.getChannelid());
				}else {
					baiduPushService.pushMessageToAndroidOne("您有新的未读信息", baiduPush.getChannelid());
				}
			}
		}
	}

	/**
	 * 家园桥离线消息、我的短信保存接口 消息类型messageType：1代表文字，2代表图片， 3代表语音， 4代表视频， 5代表文件 --必须传
	 * 消息所属模块type ： 约定1为家园桥，2为我的短信，3为请假申请，4为成绩推送，5为系统通知 --必须传
	 * 
	 * @param files
	 * @param vo
	 *            （其中必须传的是content、receiverId、senderId）
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveOfflineMessage", method = RequestMethod.POST)
	public @ResponseBody String saveOfflineMessage(OfflineMessageVo vo,
			HttpSession session, HttpServletRequest request) throws IOException {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] filter = { "files" };

		vo.setEnvelopeId(UUIDUtil.getUUID());
		vo.setStatue("1"); // 设置状态为未发送
		vo.setSendTime(DateFormatUtil.formatDateToSeconds(new Date())); // 设置发送时间
		/**
		 * 处理"我的短信"（消息类型为2）：1.保存进数据库; 2.将消息类型和所属模块改为文字; 3.推送
		 */
		if ("2".equals(vo.getType())) {
			vo.setSms(1); // sms标志为1以便之后使用短信机发送
			pushMsgService.saveOfflineMessage(vo);
			vo.setMessageType("文字");
			vo.setType("我的短信");
		}
		/**
		 * 处理家园桥消息：1.判断有无文件：若有文件则保存文件，设置推送消息内容为文件的路径并保存到数据库; 若无文件则直接保存到数据库
		 * 2.用文字表示消息类型和所属模块，然后推送
		 */
		else {
			MultipartFile[] files = vo.getFiles();
			if (files != null && files.length > 0 && vo != null) {
				if (files[0].getSize() > 0) { // 家园桥聊天一次请求最多只能有一个多媒体文件

					FileUploadVo fileUploadVo = FileUploadUtil.uploadFile(
							files[0],
							"chat/" + vo.getSenderId() + "/"
									+ vo.getReceiverId(), request, true);

					vo.setContent(fileUploadVo.getRelativePath()); // 消息内容为图片url

					if ("3".equals(vo.getMessageType())
							&& fileUploadVo.getFileName().contains(".amr")) { // 如果是语音并且是amr格式则转成mp3
						File mp3 = SimpleAudioChange.changeToMp3WithFfmpeg(
								new File(fileUploadVo.getRealPath() + "/"
										+ fileUploadVo.getFileName()),
								fileUploadVo.getRealPath());
						if (mp3 != null) {
							String relativePath = fileUploadVo
									.getRelativePath().replaceAll(
											fileUploadVo.getFileName(),
											mp3.getName());
							vo.setContent(relativePath);
						}
					}

				}
			}
			pushMsgService.saveOfflineMessage(vo);
			switch (vo.getMessageType()) {
			case "1":
				vo.setMessageType("文字");
				break;
			case "2":
				vo.setMessageType("图片");
				break;
			case "3":
				vo.setMessageType("语音");
				break;
			case "4":
				vo.setMessageType("视频");
				break;
			case "5":
				vo.setMessageType("文件");
				break;
			}
			vo.setType("家园桥");
		}
		pushMsgService.pushMessage(vo); // TODO 阻塞问题，考虑使用Spring异步标签
		resultMap.put("sendTime", vo.getSendTime());
		resultMap.put("success", true);
		resultMap.put("messageModel", vo);
		
		//百度云推送家园桥离线消息
//		if(!pushMsgService.judgeOnline(vo.getReceiverId()).isSuccess()) {
			baiduPush(vo.getReceiverId());
//		}
		
		return JSONUtil.toObject(resultMap, filter).toString();
	}

	/**
	 * 根据信封id修改信息状态为已发送
	 * 
	 * @param session
	 * @param envelopeId
	 * @return
	 */
	@RequestMapping("/updateMessage")
	@ResponseBody
	public String updateMessage(HttpSession session, String envelopeId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		pushMsgService.updateMessage(envelopeId);
	//	pushService.updateMessage(envelopeId);
		System.out.println(envelopeId + "确认收到");
		resultMap.put("success", true);
		return JSONUtil.toObject(resultMap).toString();
	}

	/**
	 * web端查看我的发送过的短信记录
	 * 存在bug:当前仍然没有查看个人收到的短信的接口。
	 * 该部分recieverId 要求保存为接受者id 
	 * 当调用群发接口时 该字段将保存组id
	 * 此处需要修正逻辑
	 * @param session
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("getHistoryMessage")
	@ResponseBody
	public String getHistoryMessage(HttpSession session, String beginTime,
			String endTime, int page, int start, int limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] filter = { "receiversId" };
		UserInfo userInfo = (UserInfo) session.getAttribute("currentUser");
		String senderId = userInfo.getUser_id();
		// 先查询出同一条短信的所有接收者id，然后再分别去查询接收者姓名 //TODO 这种写法是不好的，容易造成NullPointExection
		List<HistoryMessageVo> historyMessageVos = pushMsgService
				.getHistoryMessage(senderId, beginTime, endTime,limit,page)
				.getHistoryMessageVos();
		for (int i = 0; i < historyMessageVos.size(); i++) {
			List<String> receiversId = historyMessageVos.get(i)
					.getReceiversId();
			List<String> receiversName = new ArrayList<String>();
			for (int j = 0; j < receiversId.size(); j++) {
				String recId = receiversId.get(j);
				Teacher t = teacherService.selectTeacherById(recId);
				if (t != null) {
					receiversName.add(t.getName());
				} else {
					try{
										Student student=this.studentService.findByParentId(recId).get(0);
										if (student != null) {
											receiversName.add(student.getName());
										} 
					}
					catch(Exception e){
					}
				}
			}
			historyMessageVos.get(i).setReceiversName(receiversName);
		}
		resultMap.put("success", true);
		resultMap.put("historyMessages", historyMessageVos);
		resultMap.put("total", pushMsgService.getHisoryMessageCounut(senderId, beginTime, endTime));
		return JSONUtil.toObject(resultMap, filter).toString();
	}
	
	
	/**
	 * web端对所有学生发送校园通知
	 * @param senderId
	 *            发送者id
	 * @param content
	 *            短信内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendMessagesToAllStudent")
	public String sendMessageToSchoolStudent(String senderId,String content,String schoolId, String kindId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		List<Student> students=this.studentService.getAllStudentBySchool(schoolId);
		List<OfflineMessageVo> messageResult = new ArrayList<OfflineMessageVo>();
		int totalCount=students.size();
		int sendCount=0;
		for(int i=0;i<totalCount;i++){
			OfflineMessageVo vo = new OfflineMessageVo();
			String sendTime = DateFormatUtil.formatDateToSeconds(new Date());
			vo.setContent(content);
			vo.setReceiverId(this.parentService.selectMainParent(students.get(i).getId()).getId());
			vo.setSenderId(senderId);
			vo.setSendTime(sendTime);
			vo.setSms(1); // 我的短信特有标志位
			vo.setStatue("1"); // 未接收状态
			vo.setStudentId(students.get(i).getId());
			vo.setMessageType("1"); // 消息类型为文字
			vo.setType("2"); // 所属模块为我的短信,硬编码限制了该接口只能发送我的短信
			vo.setKindId(kindId);//消息种类为：通知
			messageResult.addAll(pushMsgService.saveMassPush(vo).getResult());
			sendCount++;
		}
		if(sendCount==totalCount){
			System.out.println(sendCount + "--------" + totalCount);
			new Thread(new PushThreadUtil(pushMsgService, messageResult))
			.start();// 推送
			resultMap.put("success", true);
		}
		return JSONUtil.toObject(resultMap).toString();
	}
	
	/**
	 * @dateTime 2016年7月20日上午10:52:28
	 * @author zjd
	 * @description web端对部分年级发送通知
	 */
	@ResponseBody
	@RequestMapping("/sendMessagesToOneGrade")
	public String sendMessagesToOneGrade(String senderId,String content,String schoolId, String kindId, String gradeIds) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<OfflineMessageVo> messageResult = new ArrayList<OfflineMessageVo>();
		List<Student> studentList = this.studentService.getAllStudentByGradeId(gradeIds);
		int totalCount=studentList.size();
		int sendCount=0;
		for(int i = 0; i < totalCount; i++) {
			OfflineMessageVo vo = new OfflineMessageVo();
			String sendTime = DateFormatUtil.formatDateToSeconds(new Date());
			vo.setContent(content);
			vo.setReceiverId(this.parentService.selectMainParent(studentList.get(i).getId()).getId());
			vo.setSenderId(senderId);
			vo.setSendTime(sendTime);
			vo.setSms(1); // 我的短信特有标志位
			vo.setStatue("1"); // 未接收状态
			vo.setStudentId(studentList.get(i).getId());
			vo.setMessageType("1"); // 消息类型为文字
			vo.setType("2"); // 所属模块为我的短信,硬编码限制了该接口只能发送我的短信
			vo.setKindId(kindId);//消息种类为：通知
			messageResult.addAll(pushMsgService.saveMassPush(vo).getResult());
			sendCount++;
		}
		if(sendCount == totalCount){
			System.out.println(sendCount + "--------" + totalCount);
			new Thread(new PushThreadUtil(pushMsgService, messageResult))
			.start();// 推送
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
		}
		return JSONUtil.toObject(resultMap).toString();
	}
	
	/**通过极光推送来群发消息到某个特定分组中。用于群发消息 
	 * @param senderId
	 * @param content
	 * @param schoolId
	 * @param kindId
	 * @param gradeIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendJPushMessagesToGroup")
	public String sendMessageToGrade(HttpSession session, 
			String senderId,   //发送者id
			String content,   //发送内容
			String groupId,  //所发送的组别 可以是班级id 年级id 学校id 
			String idTag, //该字段用于判断传过来的groupId是什么类型 班级id 则为 class ,年级id 则为 grade , 学校ID 则为 school
			String kindId){	 //发送的通知的类型。
		//用于作校园通知的区别 消息种类：1.作业  2.公告  3.通知  4.其他（群发可用,群发时将保存为“通知”
		if(session.getAttribute("currentUser")==null){
			throw new RuntimeException();
		}
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		OfflineMessageVo vo = new OfflineMessageVo();
		
		if(NotEmptyString.isNotEmpty(new String[] {senderId, content, groupId})){
			String[] groupIds = groupId.split(",");
			for(String gId: groupIds) {
				String sendTime = DateFormatUtil.formatDateToSeconds(new Date());
				vo.setContent(content);
				vo.setReceiverId(gId);
				vo.setSenderId(senderId);
				vo.setSendTime(sendTime);
				vo.setMessageType("1");// 表示发送消息的类型为文字
				vo.setStudentId((new StringBuilder(gId).append(idTag)).toString());//群发通知时将学生id 设置为接受者所在的组的id
				
				vo.setSms(1); // 我的短信特有标志位
				vo.setStatue("2"); // TODO 群发接口不考虑是否全部收到,直接设置为2 
				vo.setKindId(kindId);//设置校园通知消息的种类
				vo.setType("7"); // 所属模块为我的短信,硬编码限制了该接口只能发送我的短信
			
				if(this.pushMsgService.saveMultiMessage(vo).isSuccess()){
					
					
					//推送自定义消息之前先推送通知给ios
					
					
					resultMap.put("success",
							//2代表ios平台
							this.jPushService.sendNoticeToTag(gId, "您有一条新的消息待查看", "2")&&
							this.jPushService.sendMultiMessageToTag(vo.toJsonString(), gId, null));
					resultMap.put("sendTime", DateFormatUtil.formatDateToSeconds(new Date()));
				}
			}
		}
		else{
			resultMap.put("success", false);
		}
		return JSONUtil.toObject(resultMap).toString();
	}
	
	@ResponseBody
	@RequestMapping("/getMessageById") 
	public String getMessageById(HttpSession session,String messageId) {
		if(session.getAttribute("currentUser") == null) {
			throw new RuntimeException();
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		OfflineMessageVo vo = new OfflineMessageVo();
		if(!"".equals(messageId)) {
			vo.setMessageId(messageId);
			OfflineMessageVoResult result = this.pushMsgService.getMessageById(vo);
			if(result.isSuccess()) {
				resultMap.put("vo", result.getResult().get(0));
				resultMap.put("success", true);
			}
			else {
				resultMap.put("success", false);
			}
		}
		String[] filter = { "envelopeId", "examId","examStudentId","kindId","senderKidId","sms","statue","studentId"};
		return JSONUtil.toObject(resultMap,filter).toString();
		
	}
	
	
}
