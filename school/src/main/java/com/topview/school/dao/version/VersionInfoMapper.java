package com.topview.school.dao.version;

import java.util.List;

import com.topview.school.po.VersionInfo;

public interface VersionInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sc_version_record
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sc_version_record
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    int insert(VersionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sc_version_record
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    int insertSelective(VersionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sc_version_record
     *
     * @mbggenerated Tue Jan 19 15:15:42 CST 2016
     */
    
    /**
     * @description 根据用户的手机号获取用户在数据库中的版本记录
     * @param id
     * @return
     */
    List<VersionInfo> selectByUserMobile(String userMobile);
}