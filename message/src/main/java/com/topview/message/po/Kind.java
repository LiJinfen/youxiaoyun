package com.topview.message.po;

public class Kind {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_kind.id
     *
     * @mbggenerated Wed May 11 17:50:32 CST 2016
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_push_kind.type
     *
     * @mbggenerated Wed May 11 17:50:32 CST 2016
     */
    private String type;//消息的种类：1作业  2公告  3通知  4其他

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_kind.id
     *
     * @return the value of t_push_kind.id
     *
     * @mbggenerated Wed May 11 17:50:32 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_kind.id
     *
     * @param id the value for t_push_kind.id
     *
     * @mbggenerated Wed May 11 17:50:32 CST 2016
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_push_kind.type
     *
     * @return the value of t_push_kind.type
     *
     * @mbggenerated Wed May 11 17:50:32 CST 2016
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_push_kind.type
     *
     * @param type the value for t_push_kind.type
     *
     * @mbggenerated Wed May 11 17:50:32 CST 2016
     */
    public void setType(String type) {
        this.type = type;
    }
}