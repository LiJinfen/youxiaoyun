//ScoreMgr.js
Ext.define("School.store.school.ScoreMgr", {
	extend: "Ext.data.Store",
	requires: [
		"School.model.school.ScoreMgr"
	],
	model: "School.model.school.ScoreMgr",
	
	//因为每次请求传送的参数可能不一样，
	//所以把代理放到beforeload监听事件里面
	//可以动态设置proxy
	listeners: {
		beforeload: {
			fn: function(store) {
				var params = {examId: zy_examId};
				store.setProxy(School.util.Util.setProxy('score/getScore.action', params , 'result' ));
			}
		}
	}
	//autoLoad: true//自动加载数据
	// proxy: {
	// 	type: "ajax",
	// 	url: zyHost + 'subject/getAllSubject.action',
	// 	extraParams: {
	// 		schoolId: zy_schoolId
	// 	},
	// 	reader: {
	// 		type: 'json',
	// 		root: 'subjects'
	// 	}
	// },
	

});