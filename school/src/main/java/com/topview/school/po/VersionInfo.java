package com.topview.school.po;

import java.util.Date;

public class VersionInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sc_version_record.id
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sc_version_record.user_mobile
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    private String userMobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sc_version_record.app_version
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    private String appVersion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sc_version_record.device_name
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    private String deviceName;
    
    
    private Date recordTime;

    public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sc_version_record.id
     *
     * @return the value of t_sc_version_record.id
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sc_version_record.id
     *
     * @param id the value for t_sc_version_record.id
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sc_version_record.user_mobile
     *
     * @return the value of t_sc_version_record.user_mobile
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sc_version_record.user_mobile
     *
     * @param userMobile the value for t_sc_version_record.user_mobile
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sc_version_record.app_version
     *
     * @return the value of t_sc_version_record.app_version
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sc_version_record.app_version
     *
     * @param appVersion the value for t_sc_version_record.app_version
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sc_version_record.device_name
     *
     * @return the value of t_sc_version_record.device_name
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sc_version_record.device_name
     *
     * @param deviceName the value for t_sc_version_record.device_name
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}