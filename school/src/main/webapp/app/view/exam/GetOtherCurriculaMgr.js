/**
 * 
 * Author: ccDeng
 * Contact: 173634610@qq.com
 * Description: 查看本校所有老师选课情况的view
 * 
 */
Ext.define("School.view.exam.GetOtherCurriculaMgr", {
	extend: "Ext.grid.Panel",
	requires:[
		"School.store.exam.GetOtherCurriculaMgr",
	],
	alias:"widget.getothercurriculamgr",
	itemId: "getothercurriculamgr",
	forceFit: true, 
	initComponent: function() {
		this.store = Ext.create("School.store.exam.GetOtherCurriculaMgr", {
			pageSize: 100
		});
		this.columns = [{
			text: "id",
			dataIndex: "id",
			hidden: true,
			flex: 3,
		}, {
			text: "teacherId",
			dataIndex: "tScTeacherId",
			hidden: true,
			flex: 3,
		}, {
			text: "curriculaId",
			dataIndex: "curriculaId",
			hidden: true,
			flex: 3,
		}, {
			text: "班级",
			dataIndex: "clazzName",
			flex: 3,
		}, {
			text: "科目",
			dataIndex: "subjectName",
			flex: 3
		}, {
			text: "任课老师",
			dataIndex: "teacherName",
			flex: 3
		}, {
			xtype: "actioncolumn", 
			flex: 3,
			header: "修改任课老师",
			itemId: "editTeacher",
			align: "center",
			items: [{
				iconCls: "edit",
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
				store: Ext.create('School.store.clazz.GradeStore'),
			}, {
				fieldLabel: "班级选择",
				width: 200,
				labelWidth: 60,
				itemId: "classselect",
				xtype: "combobox",
				triggerAction: "all",
				emptyText: "请先选择年级...",
				editable: false,
				displayField: "name",
				valueField: "id",
				//获取数据集
				store: Ext.create('School.store.school.GetClassByGradeId'),
			}, {
				fieldLabel: "学期选择",
				labelWidth: 60,
				itemId: "termcombo",
				xtype: "combobox",
				triggerAction: "all",
				width: 200,
				emptyText: "请选择...",
				editable: false,
				displayField: "name",
				valueField: "id",
				//获取数据集
				store: Ext.create("School.store.school.Semester"),
				queryMode: "remote"
			}, {
				xtype: "button",
				text: "查询",
				margin: "0 0 0 10",
				itemId: "search",
				width: 80,
				iconCls: "key_go", 
			}]
		}];

		this.callParent(arguments);
	}

});