'use strict';
var App = angular.module('myModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SysRoleUserService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/sysRoleUser')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('roleId').withTitle(
					'roleId').notSortable(),
			DTColumnBuilder.newColumn('userId').withTitle('userId').notSortable(),
			
			DTColumnBuilder.newColumn(null).withTitle('Actions').notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;

	initltCommon(vm,localStorageService,topleftService);
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.roleId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans['
				+ data.roleId
				+ '])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans['
				+ data.roleId
				+ '])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}

	function addInit() {
		vm.modelTitle = 'sysRoleUseradd';
		vm.readonlyID = false;
		vm.bean = {};
	}
	function edit(bean) {
		vm.modelTitle = 'sysRoleUseredit';
		vm.readonlyID = true;
		vm.bean = bean;
	}

	function submit() {
		// if (vm.bean.roleId == null) {
		if (!vm.readonlyID) {
			SysRoleUserService.createSysRoleUser(vm.bean).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating SysRoleUser.');
					});
			vm.reset();
		} else {
			SysRoleUserService.updateSysRoleUser(vm.bean, vm.bean.roleId,vm.bean.userId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating SysRoleUser.');
					});
		}
	}

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('print'),
			message : "确定要删除该记录吗",
			buttons : [ {
				label : 'yes',
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SysRoleUserService.deleteSysRoleUser(bean.roleId,bean.userId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating SysRoleUser.');
					});
					dialogItself.close();
				}

			}, {
				label : 'cancel',
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			} ]
		});
		
	}
	function reloadData() {
		var roleId = vm.beanSer.roleId;
		var userId = vm.beanSer.userId;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/sysRoleUser?id=' + roleId+'&userId=' +userId));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
		//console.log(json);
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}
