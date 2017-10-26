/*
 path: view.auth.role.ArrangeRoleWin.js
 author: Drake
 description: 分配按钮权限窗口
 */
Ext.define('School.view.auth.module.ArrangeBtnPermissionWin', {
  extend: 'Ext.window.Window',
  alias: 'widget.arrangebtnpermissionwin',

  requires: [
    'Ext.grid.*',
    'Ext.layout.container.HBox',
    'School.store.auth.BtnPermission'
  ],

  width: 550,
  height: 350,
  modal: true,

  layout: {
    type: 'hbox',
    align: 'stretch',
    padding: 5
  },


  initComponent: function(){
    var group1 = this.id + 'group1',
      group2 = this.id  + 'group2',

      columns = [{
        hidden: true,
        dataIndex: 'id'
      }, {
        text: '按钮名称',
        dataIndex: 'permission'
      }, {
        text: '按钮描述',
        flex: 1,
        dataIndex: 'description'
      }];

    this.items = [{
      itemId: 'unarrangedGrid',
      flex: 1,
      xtype: 'grid',
      //multiSelect: true,
      viewConfig: {
        plugins: {
          ptype: 'gridviewdragdrop',
          dragGroup: group1,
          dropGroup: group2
        }
        //listeners: {
        //	drop: function(node, data, dropRec, dropPosition) {
        //		//Ext.ux.Tips.msg();
        //	}
        //}
      },

      store: Ext.create('School.store.auth.BtnPermission'),

      columns: columns,
      stripeRows: true,
      title: '未分配的按钮',
      margins: '0 5 0 0'
      //tools: [{
      //	type: 'refresh',
      //	tooltip: 'Reset both grids',
      //	scope: this,
      //	handler: this.onResetClick
      //}],
    }, {
      itemId: 'arrangedGrid',
      flex: 1,
      xtype: 'grid',
      viewConfig: {
        plugins: {
          ptype: 'gridviewdragdrop',
          dragGroup: group2,
          dropGroup: group1
        }
      },
      store: Ext.create('School.store.auth.BtnPermission'),
      columns: columns,
      stripeRows: true,
      title: '已分配的按钮'
    }];

    this.buttons =  [{
      text: '取消',
      itemId: 'cancel'
    }, {
      text: '保存',
      itemId: 'save'
    }];

    this.callParent();
  }
});