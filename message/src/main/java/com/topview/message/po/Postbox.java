package com.topview.message.po;

/**
 * @Description 信息类型：type字段文字类型为1,图片类型为2,语音类型为3，视频类型为4,文件类型为5
 * @Author <wuyiliang 757210697@qq.com>
 * @Date 2016年1月9日 上午10:53:37
 * @CopyRight 2016 TopView Inc
 * @version V1.0
 */
public class Postbox {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_postbox.id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_postbox.type
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    private String type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_postbox.id
     *
     * @return the value of t_push_postbox.id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_postbox.id
     *
     * @param id the value for t_push_postbox.id
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_postbox.type
     *
     * @return the value of t_push_postbox.type
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_postbox.type
     *
     * @param type the value for t_push_postbox.type
     *
     * @mbggenerated Tue Dec 01 22:48:03 CST 2015
     */
    public void setType(String type) {
        this.type = type;
    }
}