package com.topview.message.po;

/**
 * @Description 推送情况
 * @Author <wuyiliang 757210697@qq.com>
 * @Date 2016年1月9日 上午11:10:54
 * @CopyRight 2016 TopView Inc
 * @version V1.0
 */
public class Envelope {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_situation.id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_situation.receiver_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    private String receiverId; //接收人id
    
    /**用于极光推送的目标id
     * @author  Dr
     * @date Create Time：2016年8月18日 下午8:12:34
     *当推送目标为单发时，若用户为教师 为用户的Id ； 若用户为家长，则为对应的孩子的id
     * 当推送目标为群发时，为群发所对应的tag的id 。通常为学校Id 或 班级Id 或年级Id
     */
    private String jPushTarget; 

    public String getjPushTarget() {
		return jPushTarget;
	}

	public void setjPushTarget(String jPushTarget) {
		this.jPushTarget = jPushTarget;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_situation.student_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */ 
    private String studentId; //学生id，非必须

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_situation.t_push_statue_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    private String pushStatus; //接收状态,1为未接收，2为已接收

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_situation.t_push_message_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    private String tPushMessageId; //所属消息

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_situation.id
     *
     * @return the value of t_push_situation.id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_situation.id
     *
     * @param id the value for t_push_situation.id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_situation.receiver_id
     *
     * @return the value of t_push_situation.receiver_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_situation.receiver_id
     *
     * @param receiverId the value for t_push_situation.receiver_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_situation.student_id
     *
     * @return the value of t_push_situation.student_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_situation.student_id
     *
     * @param studentId the value for t_push_situation.student_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_situation.push_statue
     *
     * @return the value of t_push_situation.push_statue
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public String getPushStatus() {
        return pushStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_situation.push_statue
     *
     * @param tPushStatueId the value for t_push_situation.push_statue
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_situation.t_push_message_id
     *
     * @return the value of t_push_situation.t_push_message_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public String gettPushMessageId() {
        return tPushMessageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_situation.t_push_message_id
     *
     * @param tPushMessageId the value for t_push_situation.t_push_message_id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public void settPushMessageId(String tPushMessageId) {
        this.tPushMessageId = tPushMessageId;
    }

	@Override
	public String toString() {
		return "Envelope [id=" + id + ", receiverId=" + receiverId
				+ ", studentId=" + studentId + ", pushStatus=" + pushStatus
				+ ", tPushMessageId=" + tPushMessageId + "]";
	}
    
}