Ext.define("School.store.multimedia.PhotoMgr", {
	extend: "Ext.data.Store",
	requires: [
		"School.model.multimedia.PhotoMgr"
	],
	model: "School.model.multimedia.PhotoMgr",
	storeId: "photos",
	autoLoad: false,
	// sorters: [{ 
	// 	property: 'uploadTime',
	// 	direction: 'DESC'
	// }],
	
	// 因为每次请求传送的参数可能不一样，
	// 所以把代理放到beforeload监听事件里面
	// 可以动态设置proxy
	// listeners: {
	// 	beforeload: {
	// 		fn: function(store) {

	// 			console.log(this.params);
	// 			store.setProxy(School.util.Util.setProxy("photo/getPassPhoto.action", "" , "photos", "totalCount" ));
	// 		}
	// 	}
	// }
});

