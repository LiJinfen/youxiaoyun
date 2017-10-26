/**
* @class School.view.school.MyClass
* @author ChessZhang
* @contact k3note2@gmial.com
* @description: 班级管理的主面板
*/

Ext.define("School.view.school.MyClass", {
	extend: "Ext.grid.Panel",
	requires:[
		"School.store.school.MyClass",
	],
	alias:"widget.myclass",
	itemId: "myclass",
	forceFit: true, 
	initComponent: function() {
		this.store = Ext.create("School.store.school.MyClass", {
			pageSize: 100
		});
		this.columns = [
			//"emergencyPhone", , , 
			{
				text: "学生id",
				hidden: true,
				dataIndex: "studentId"
			},
			{
				text: "家长id",
				hidden: true,
				dataIndex: "parentId"
			},
			{
				text: "出生日期",
				hidden: true,
				xtype: 'datefield',
				format: 'Y-m-d',
				dataIndex: "studentBirthday"
			},
			{
				text: "学号",
				flex: 3,
				dataIndex: "studentIDCard"
			},
			{
				text: "姓名",
				flex: 3,
				dataIndex: "studentName"
			},
			{
				text: "性别",
				flex: 3,
				dataIndex: "studentGender"
			},
			{
				text: "住址",
				flex: 3,
				dataIndex: "studentAddress"
			},
			{
				text: "固定电话",
				flex: 3,
				dataIndex: "studentPhone"
			},
			{
				text: "监护人",
				flex: 3,
				dataIndex: "parentName"
			},

			{
				text: "监护人手机",
				readOnly: true,
				flex: 3,
				dataIndex: "parentPhone"
			},
			{
				text: "紧急电话",
				readOnly: true,
				flex: 3,
				dataIndex: "emergencyPhone"
			},
			{
				xtype: "actioncolumn",
				flex: 1,
				header: "删除",
				permissionId: 'deleteStudent',
				align: "center",
				items: [
					{
						iconCls: "delete",
						handler: function(grid, rowIndex, colIndex) {
							this.fireEvent("itemclick", grid, rowIndex, colIndex);
						}
					}
				]
			}
		];

		//固定菜单栏
		this.dockedItems = [
			{
				xtype: "toolbar",
				flex: 1,
				dock: "top",
				items: [
					// {
					// 	fieldLabel: "科目选择",
					// 	labelWidth:60,
					// 	itemId: "curriculacombo",
					// 	xtype: "combo",
					// 	triggerAction: "all",
					// 	width: 150,
					// 	displayField: "name",
					// 	emptyText: "请选择...",
					// 	editable: false,
					// 	valueField: "id",
					// 	//获取数据集
					// 	store: Ext.create("School.store.school.CourseMgr"),
					// 	queryMode: "remote"
					// },
					{
						fieldLabel: "我的班级",
						width: 200,
						labelWidth: 60,
						itemId: "myclasses",
						xtype: "combobox",
						triggerAction: "all",
						emptyText: "请选择...",
						editable: false,
						displayField: "name",
						valueField: "id",
						//获取数据集
						store: zy_classes,
						queryMode: "local"
					},
					{
						xtype: "button",
						text: "导入学生",
						itemId: "upload",
						permissionId: 'uploadStudent',
						iconCls: "upload"
					},
					
					{
						xtype:  "button",
						text:　"导出学生",
						itemId: "download",
						permissionId: 'downloadStudent',
						iconCls: "download"
					},
					{
						xtype: "button",
						text: "编辑",
						itemId: "edit",
						permissionId: 'editStudent',
						iconCls: "edit"
					},
					{
						xtype: "button",
						text: "新增",
						itemId: "addstudent",
						permissionId: 'insertStudent',
						iconCls: "add"						
					},
					{
						xtype:  "button",
						text:　"查看课表",
						itemId: "viewSyllabus",
						permissionId: 'viewSyllabus',
						iconCls: "key_go"
					},
					{
						xtype:  "button",
						text:　"查看帖子",
						itemId: "viewPost",
						permissionId: 'viewPost',
						iconCls: "key_go"
					}, 
					{
						xtype: "label",
						margin: "0 0 0 50",
						text: "当前班级："
					},
					{
						xtype: "label",
						itemId: "className",
						style: {
							color: "red",
							fontWeight: "800"
						}
					}
					// {
					// 	xtype:  "button",
					// 	text:　"导出学生信息",
					// 	itemId: "download",
					// 	iconCls: "download"
					// }
				]
			},
			{  
		        xtype: 'pagingtoolbar',  
		        store: this.getStore(),   // same store GridPanel is using  
		        dock: 'bottom',  
		        displayInfo: true  
		    }  
		];

		this.callParent(arguments);
	}

});