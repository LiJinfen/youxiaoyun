/**
 * 
 * Author: ccDeng
 * Contact: 173634610@qq.com
 * Description: 根据school获取年级，年级的store
 * 
 */
Ext.define("School.store.clazz.GradeStore", {
	extend: "Ext.data.Store",
	requires: [
		"School.model.clazz.GradeStore"
	],
	model: "School.model.clazz.GradeStore",

	listeners: {
		beforeload: {
			fn: function(store) {
				var params = {schoolId: zy_schoolId};
				store.setProxy(School.util.Util.setProxy('grade/getAllGrade.action', params , 'grades', 100 ));
			}
		}
	},
	// sorters: [{
 	// 	property: 'IDCard',
 	// 	direction: 'ASC'
 	// }],
 	remoteSort: true //服务器端排序 
});