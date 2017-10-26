// /**
// * @class School.view.school.InsertStudent
// * @author ccDeng
// * @contact 173634610@qq.com
// * @description: 插入新增学生的Ext.Window
// *这个可以和addStudent复用 小玉
// */

// Ext.define("School.view.school.InsertStudent", {
// 	extend: "Ext.window.Window",
// 	closeAction: "destroy",
// 	alias: "widget.insertstudent",
// 	closable: true,
// 	modal: true,
// 	layout: {
// 		type: "fit",
// 		align: 'stretch'
// 	},
// 	width: 350,
// 	title: "新增学生",
// 	autoShow: true,
// 	items: [
// 		{
// 			xtype: "form",
// 			border: false,
// 			style: {
// 				padding: "10px"
// 			},
// 			layout: {
// 				type: "anchor"
// 			},
// 			defaults: {
// 				xtype: "textfield",
// 				anchor: "100%",
// 				labelWidth: 90,
// 				afterLabelTextTpl : '<span style="color:red">*</span>', 
// 				labelSeparator: ":",
// 				allowBlank: false,
// 				style: {
// 					marginTop: "20px"
// 				}
				
// 			},
// 			items: [
// 				{
// 					name: "studentIDCard",
// 					afterLabelTextTpl : '<span style="color:red">*</span>',
// 					fieldLabel: "学生学号",
// 					// allowBlank: true
// 				},
			
// 				{
// 					name: "studentName",
// 					fieldLabel: "学生姓名"
// 				},
// 				{
// 					xtype: "radiogroup",
// 					allowBlank: true,
// 					itemId: "student_sex",
// 					name: "studentGender",
// 					fieldLabel: "学生性别",
// 					columns: 2,
// 					labelAlign: "left",
// 					items: [
// 						{
// 							boxLabel: "男",
// 							name: "studentGender",
// 							inputValue: "男"
// 						},
// 						{
// 							boxLabel: "女",
// 							name: "studentGender",
// 							inputValue: "女"
// 						}
						
// 					]
// 				},
// 				{
// 					name: "studentAddress",
// 					afterLabelTextTpl : ' ',
// 					allowBlank: true,
// 					fieldLabel: "学生住址"
// 				},
// 				{
// 					fieldLabel: "出生日期",
// 					xtype: 'datefield',
// 					allowBlank: true,
// 					itemId: 'student-Birthday',
// 					format: 'Y-m-d',
// 					afterLabelTextTpl : ' ',
// 					name: "studentBirthday"
// 				},
// 				{
// 					name: "studentPhone",
// 					afterLabelTextTpl : ' ',
// 					allowBlank: true,
// 					fieldLabel: "固定电话"
// 				},
// 				{
// 					name: "parentName",
// 					afterLabelTextTpl : ' ',
// 					allowBlank: true,
// 					fieldLabel: "监护人姓名"
// 				},				
// 				{
// 					name: "parentPhone",
// 					fieldLabel: "监护人手机",
// 					afterLabelTextTpl : '<span style="color:red">*</span>',
// 				},
// 				{
// 					name: "emergencyPhone",
// 					afterLabelTextTpl : ' ',
// 					allowBlank: true,
// 					fieldLabel: "紧急电话"
// 				}
// 			],
// 			//底部的按钮栏
// 			buttons : [
// 					{
// 						text : '保存',
// 						itemId: "save"
// 					},
					
// 					{
// 						text : '取消',
// 						itemId: "cancel"
// 					}
					
// 			]
// 		}
// 	]
	
// });