package com.topview.school.po;

public class Permission {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.id
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    private String permission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.description
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.available
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    private Boolean available;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.id
     *
     * @return the value of permission.id
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.id
     *
     * @param id the value for permission.id
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission
     *
     * @return the value of permission.permission
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public String getPermission() {
        return permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission
     *
     * @param permission the value for permission.permission
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.description
     *
     * @return the value of permission.description
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.description
     *
     * @param description the value for permission.description
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.available
     *
     * @return the value of permission.available
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.available
     *
     * @param available the value for permission.available
     *
     * @mbggenerated Tue Sep 29 19:49:42 CST 2015
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}