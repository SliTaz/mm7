'use strict';
var App = angular.module('SysRoleMenuModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize' ]);
App.config([ '$translateProvider', translateProvider ]);
App.controller('ClientSideCtrl', ClientSideCtrl);


function ClientSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SysRoleMenuService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(apiUrl + '/role').withOption(
			'createdRow', createdRow);

	setDtOptionsClientSide(vm);

	vm.dtOptions.withColumnFilter({
		aoColumns : [ {
			type : 'text'
		}, {
			type : 'text'
		} ]
	});
	vm.dtColumns = [
			DTColumnBuilder.newColumn('roleId').withTitle(
					$translate('role.roleId')).notSortable(),
					DTColumnBuilder.newColumn('menuId').withTitle($translate('role.menuId')).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;

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
		vm.modelTitle = $translate.instant('role.roleadd');
		vm.readonlyID = false;
		vm.bean = {};
	}
	function edit(bean) {
		vm.modelTitle = $translate.instant('role.roleedit');
		vm.readonlyID = true;
		vm.bean = bean;
	}

	function submit() {
		// if (vm.bean.roleId == null) {
		if (!vm.readonlyID) {
			RoleService.createRole(vm.bean).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Role.');
					});
			vm.reset();
		} else {
			RoleService.updateRole(vm.bean, vm.bean.roleId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Role.');
					});
		}
	
	}

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('print'),
			message : $translate.instant('are you sure you want to delete this record'),
			buttons : [ {
				label : 'yes',
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					RoleService.deleteRole(bean.roleId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Role.');
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