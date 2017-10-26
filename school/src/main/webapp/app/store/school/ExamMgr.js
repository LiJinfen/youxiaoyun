Ext.define("School.store.school.ExamMgr", {
	extend: "Ext.data.Store",
	requires: [
		"School.model.school.ExamMgr"
	],
	model: "School.model.school.ExamMgr",
	//因为每次请求传送的参数可能不一样，
	//所以把代理放到beforeload监听事件里面
	//可以动态设置proxy
	listeners: {
		beforeload: {
			fn: function(store) {
				var params = {
					semesterId: Ext.ComponentQuery.query('exammgr')[0].down('toolbar').getComponent('termcombo').getValue(),
					teacherId: zy_teacherId,
					gradeId: Ext.ComponentQuery.query('exammgr')[0].down('toolbar').getComponent('gradecombo').getValue(),
					subjectId: Ext.ComponentQuery.query('exammgr')[0].down('toolbar').getComponent('subjectcombo').getValue()
			};
				store.setProxy(School.util.Util.setProxy('exam/selectExamRecord.action', params , 'exams' ));
			}
		}
	},
	sorters: {
		property: 'date',
		direction: 'DESC'
	},
});