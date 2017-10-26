/**
 * 
 * Author: ccDeng
 * Contact: 173634610@qq.com
 * Description: 超级管理员查看选择的学校的登陆情况view
 * 
 */
Ext.define("School.view.area.SchoolLogin", {
	extend: "Ext.grid.Panel",
	requires:[
		"School.store.area.SchoolLogin",
	],
	alias:"widget.schoollogin",
	itemId: "schoollogin",
	forceFit: true, 
	initComponent: function() {
		this.store = Ext.create("School.store.area.SchoolLogin", {
			pageSize: 100
		});
		this.columns = [{
			text: "id",
			hidden: true,
			dataIndex: "id",
		}, {
			text: "班级",
			dataIndex: "name",
			flex: 3,
		}, {
			text: "班主任",
			dataIndex: "headTeacher",
			flex: 3
		}, {
			xtype: "actioncolumn",
			flex: 1,
			header: "查看",
			align: "center",
			items: [{
				iconCls: "key_go",
				handler: function(grid, rowIndex, colIndex) {
					this.fireEvent("itemclick", grid, rowIndex, colIndex);
				}
			}]
		}];

		//固定菜单栏
		this.dockedItems = [{
			xtype: "toolbar",
			flex: 1,
			dock: "top",
			layout: 'vbox',
			items: [{
				xtype: 'form',
				layout: 'hbox',
				items: [{
					fieldLabel: "年级选择",
					width: 200,
					labelWidth: 60,
					itemId: "gradeselect",
					xtype: "combobox",
					triggerAction: "all",
					emptyText: "请选择...",
					editable: false,
					displayField: "name",
					valueField: "id",
					//获取数据集
					store: Ext.create('School.store.clazz.SelectSchoolGrade'),
				}, {
					xtype: "label",
					margin: "2 0 0 50",
					text: "温馨提示：请选择年级",
					style: {
						color: "red",
						fontWeight: "800"
					}
				}]
			},{
				xtype: 'form',
				layout: 'hbox',
				items: [{
					xtype: "label",
					margin: "10 10 10 10",
					text: "",
					itemId: 'schoolCountOfTeacher',
					style: {
						color: "red",
						fontWeight: "800"
					}
				}, {
					xtype: "label",
					margin: "10 10 10 10",
					text: "",
					itemId: 'loginCountOfTeacher',
					style: {
						color: "red",
						fontWeight: "800"
					}
				}, {
					xtype: "label",
					margin: "10 10 10 10",
					text: "",
					itemId: 'NotLoginCountOfTeacher',
					style: {
						color: "red",
						fontWeight: "800"
					}
				}, {
					xtype: "label",
					margin: "10 10 10 10",
					text: "",
					itemId: 'schoolCountOfParent',
					style: {
						color: "red",
						fontWeight: "800"
					}
				}, {
					xtype: "label",
					margin: "10 10 10 10",
					text: "",
					itemId: 'loginCountOfParent',
					style: {
						color: "red",
						fontWeight: "800"
					}
				}, {
					xtype: "label",
					margin: "10 10 10 10",
					text: "",
					itemId: 'NotLoginCountOfParent',
					style: {
						color: "red",
						fontWeight: "800"
					}					
				}]
			}]
		}];

		this.callParent(arguments);
	}

});