/**
 * Author: 张展宇
 * Contact: k3note2@gmail.com
 * Description: 考试管理控制器
 */
Ext.define("School.controller.exam.ExamMgr", {
	extend: "Ext.app.Controller",
	views: [
		"exam.ExamMgr"
	],
	init: function(application) {

		"use strict";

		this.control({

			//界面渲染后为学期选择当前学期为默认值
			"exammgr": {
				afterrender: function(component) {
					this.setDefaultValue(component);
				}
			},

			//重置考试管理面板下的所有的下拉框
			"exammgr button#resetcombo": {
				click: function(button) {
					var xtype = "exammgr combo";
					this.resetComboboxes(xtype);
				}
			},

			//考试管理的下拉框监听事件
			"exammgr combobox": {
				change: function(combobox, newValue) {
					var xtype = "exammgr";
					this.showSelectedCurricula(combobox, newValue, xtype);
				}
			},

			//为新增考试窗口设置默认学期
			"addexam #termcombo": {
				afterrender: function(component) {
					School.util.Util.setComboboxValue(component,
							"currentSemester", "id", 1);
				} 
			},


			//保存新增的考试
			"addexam form button#save": {
				click: function(button) {
					this.saveExam(button);
				}
			},

			//重置表单
			"addexam button#reset": {
				click: function(button) {
					button.up("form").getForm().reset();
				}
			},

			//关闭上传窗口
			"addexam button#cancel": {
				click: function(button) {
					button.up("addexam").destroy();
				}
			},



			//打开编辑考试窗口
			"exammgr toolbar button#edit": {
				click: function(button) {
					this.editExam(button);					
				}
			},

			//保存修改后的考试
			"editexam form button#save": {
				click: function(button, e, options) {
					this.updateExam(button);
				} 
			},

			//删除学校
			"exammgr toolbar button#delete": {
				click: function(button) {
					this.deleteExam(button);					
				} 
			},

			//查看考试成绩
			"exammgr actioncolumn": {
				itemclick: function(grid, rowIndex, colIndex) {					
					this.viewScore(grid, rowIndex, colIndex);
				}
			}		
		});
	
	},

	setDefaultValue: function(component) {
		"use strict";

		var termCombo = component.down("#termcombo");

		//设置学期选择的默认值为当前学期(currentSemester===1的学期)
		School.util.Util.setComboboxValue(termCombo, "currentSemester",
				"id", 1);
	},

	/**
	* @method resetComboboxes
	* @param void
	* @return void
	* @description: 用来重置所有的comboboxes组件
	*/
	resetComboboxes: function(xtype) {

		"use strict";

		var comboboxes =  Ext.ComponentQuery.query(xtype);
		for(var i = 0, len = comboboxes.length; i < len; i++) {
			comboboxes[i].reset();
		}
	},

	/**
	* @method showSelectedCurricula
	* @param comobo: 当前改变的combobox组件
	* @param newValue: combobox的新值
	* @param xtype: 当前面板的别名
	* @return void
	* @description: 用来重置所有的comboboxes组件
	*/
	showSelectedCurricula: function(combo, newValue, xtype) {

		"use strict";

		var comboboxes = Ext.ComponentQuery.query(xtype + " combo"),
			params = {
				subjectId: comboboxes[0].getValue() || "",
				gradeId: comboboxes[1].getValue() || "",
				semesterId: comboboxes[2].getValue() || "",
				teacherId: comboboxes[3].getValue() || ""
			};

		if(newValue !== null) {

			combo.up("grid").getStore().reload({
				params: params
			});

		} else if(!params.subjectId && !params.gradeId && !params.semesterId && !params.teacherId){
			combo.up("grid").getStore().reload({
				params: params,
			})
		}
	},

	saveExam: function(button) {

		"use strict";

		var  win = button.up("addexam"),
			values = button.up("addexam").down("form").getValues(),
			store = Ext.ComponentQuery.query("showselectedcurricula")[0].getStore(),
			records = win.extraParams.records,
			url = "exam/createExam.action",
			msg = "您可以到\"考试&成绩\"面板里查看考试详情";

		//判断表单是否有效，如果无效则退出
		if(!win.down("form").getForm().isValid()) {
			School.util.Util.showErrorMsg("请正确填写好表单！");
			return ;
		}

		//因为后台每次请求只能保存单个考试，
		//所以需要用到循环来保存多个考试
		for(var i = 0, len = records.length; i < len; i++) {
			values.curriculaVariableId =  records[i].get("id");
			School.util.Update.onSaveButtonClick(win, url, store, values, msg);
		}
	},

	editExam: function(button) {

		"use strict";

		var record = button.up("grid").getSelectionModel().getSelection(), 
			win = {};

		//如果没有选择需要编辑的考试，则提示用户
		if(!record[0]) {
			School.util.Util.showErrorMsg("您没有选择任何考试，无法编辑！");
			return 0;
		}

		win = Ext.create("School.view.exam.EditExam", {
			itemId: "edit",
			title: "编辑考试"
		}).show();

		//用loadRecord把用户选中的记录填入到表单当中
		win.down("form").loadRecord(record[0]); 
	},

	updateExam: function(button) {

		"use strict";

		var win = button.up("window"),
			store = Ext.ComponentQuery.query("exammgr")[0].getStore(),
			url = "exam/updateExam.action",
			formValues = win.down("form").getForm().getValues(); 

		if(!win.down("form").getForm().isValid()) {
			School.util.Util.showErrorMsg("请正确填写好表单");
			return ;
		}

		School.util.Update.onSaveButtonClick(win, url, store, formValues);
	},


	deleteExam: function(button) {

		"use strict";

		var grid = button.up("gridpanel"), 
			records = grid.getSelectionModel().getSelection(),
			store = grid.getStore(),
			url = "exam/deleteExam.action",
			params = {};

		//假如没有选择任何一行，则提示用户，并且退出删除操作
		if(records.length === 0) {
			School.util.Util.showWarningMsg("请选择一个要删除的考试！");
			return;
		}

		//获取学校id
		params.id = records[0].get("id"); 
		
		//提示用户是否确定删除
		School.util.Util.confirm("删除考试", function(buttonId) {
			if(buttonId === "yes") { 
				School.util.Update.onDeleteButtonClick(store, url, params, {}, refresh);	
			}
		});

		//该函数在删除成功后会回调执行
		//作用是刷新考试记录
		function refresh() {

			var comboboxes = ["#termcombo", "#gradecombo", "#subjectcombo"],
				value = "";

			for(var i = 0; i < 3; i++) {
				if(grid.down(comboboxes[i]).getValue()) {
					break;
				}
			}

			value = grid.down(comboboxes[i]).getValue();
			grid.down(comboboxes[i]).reset();
			grid.down(comboboxes[i]).setValue(value);

		}
	},

	viewScore: function(grid, rowIndex, colIndex) {

		"use strict";

		var	mainPanel = Ext.ComponentQuery.query("mainpanel")[0],
			title = '成绩管理',
			xtype = "scoremgr",
			examStore = grid.getStore(),
			scorePanel = {};

		//更新与考试相关的全局变量
		zy_examRec = examStore.getAt(rowIndex);
		zy_examId = examStore.getAt(rowIndex).get("id") ;
		
		//将成绩面板添加到mainPanel
		School.util.AddTab.addTab(mainPanel, title, xtype);

		//假如已经存在scroemgr的实例，则重新加载数据并且改变下拉框的值
		scorePanel = Ext.ComponentQuery.query("scoremgr")[0];
		if(scorePanel) {
			//重新加载班级数据
			scorePanel.getStore().reload({
				params: {
					examId: zy_examId
				},
				callback:function() {
					scorePanel.down("label#examName").setText(examStore.getAt(rowIndex).get("name"));
				}
			});
		}
	}

});