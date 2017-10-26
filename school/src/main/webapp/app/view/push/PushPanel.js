	/*
 path: view.push.PushPanel
 author: Drake
 description: 消息推送
 */

Ext.define('School.view.push.PushPanel', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.pushpanel',

	requires: [
		'School.view.push.ContactTree'
	],

	layout: 'border',

	initComponent: function () {
		var me = this;

		Ext.apply(me, {
			items: [{
				region: 'east',
				xtype: 'contacttree',
				width: 300
			}, {
				region: 'center',
				xtype: 'form',
				layout: 'border',
				bodyPadding: 10,
				bodyStyle: {
					background: '#fff'
				},
				items: [{
					layout: 'anchor',
					defaults: {
						labelWidth: 70,
						anchor: '100%',
						margin: '10 0'
					},
					items: [{
						xtype: 'textfield',
						itemId: 'receiverName',
						name: 'name',
						fieldLabel: '收信人',
						readOnly: true,
						emptyText: '请在右边侧栏选择收信人'
					}, {
						xtype: 'combobox',
						itemId: 'pushType',
						name: 'kindId',
						fieldLabel: '通知类型',
						store: Ext.create('Ext.data.Store', {
					    fields: ['kind', 'name'],
					    data : [
				        {"kind":"1", "name":"作业"},				        
				        {"kind":"2", "name":"通知"},
				        {"kind":"3", "name":"公告"},
				        {"kind":"4", "name":"其他"}
					    ]
						}),
						displayField: 'name',
    				valueField: 'kind',
    				editable: false,
    				value: '1',				
					}],
					region: 'north',
					height: 69,
				}, {
					region: 'center',
					xtype: 'textarea',
					itemId: 'content',
					name: 'content',
					rows: 10,
					grow: true,
					fieldLabel: '正文',
					allowBlank: false,
					emptyText: '信息的主要内容...',
					labelWidth: 70,
					anchor: '100%',
					margin: '10 0'
				}],

				buttons: [{
					text: '清空正文',
					itemId: 'resetContent'
				}, {
					text: '发送',
					itemId: 'doSend',
					permissionId: 'doPush',
					formBind: true
				}]
			}]
		});

		me.callParent();
	}
});