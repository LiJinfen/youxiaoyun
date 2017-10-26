/**
 * 
  * Author: ccDeng
 * Contact: 173634610@qq.com
 * Description: 超级管理员查看管理的该学校班级的登陆情况的model
 * 
 */
Ext.define("School.model.area.ClassLogin", {
	extend: "Ext.data.Model",
	fields: [
	"IDCard",
	"className",
    "lastLogin",
    "parentName",
    "studentName"
  ]
});