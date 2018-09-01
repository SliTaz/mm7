'use strict';
var App = angular.module('menuModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize' , 'LocalStorageModule', 'ui.tree']);
configAppModule(App);
App.controller('ClientSideCtrl', ClientSideCtrl);


function ClientSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, MenuService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/menu')).withOption('createdRow', createdRow);

	setDtOptionsClientSide(vm);

	vm.dtOptions.withColumnFilter({
		aoColumns : [ {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		} ]
	});
	vm.dtColumns = [
			DTColumnBuilder.newColumn('menuId').withTitle(
					$translate('menu.menuId')),
					DTColumnBuilder.newColumn('menuKeyWord').withTitle($translate('menu.menuKeyWord')),
					DTColumnBuilder.newColumn('menuSortno').withTitle($translate('menu.menuSortno')),
			DTColumnBuilder.newColumn('menuName').withTitle($translate('menu.menuName')),
			DTColumnBuilder.newColumn('menuPic').withTitle($translate('menu.menuPic')),
			DTColumnBuilder.newColumn('menuType').withTitle($translate('menu.menuType')),
			DTColumnBuilder.newColumn('preMenuId').withTitle($translate('menu.preMenuId')),
			DTColumnBuilder.newColumn('menuProces').withTitle($translate('menu.menuProces')),
			DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('menu.remark')),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width','10%').notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	
	initltCommon(vm,localStorageService,topleftService);
	
	
	vm.statusData = [
		{value:0,text:'ͣ��'},
		{value:1,text:'����'}];
	
	function status(data, type, full, meta){
		if(data=='0'){
			return $translate.instant('common.inactivate');
		}else if(data=='1'){
			return $translate.instant('common.activate');
		}
	}

	function actionsHtml(data, type, full, meta) {
		vm.beans[data.menuId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans['
				+ data.menuId
				+ '])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans['
				+ data.menuId
				+ '])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}

	function addInit() {
		vm.modelTitle = $translate.instant('menu.menuadd');
		vm.readonlyID = false;
		vm.bean = {};
	vm.statusMessage="";
	}
	function edit(bean) {
		vm.modelTitle = $translate.instant('menu.menuedit');
		vm.readonlyID = true;
		vm.bean = bean;
	vm.statusMessage="";
	}

	function submit() {
		// if (vm.bean.menuId == null) {
		if (!vm.readonlyID) {
			MenuService.createMenu(vm.bean).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Menu.');
					});
			vm.reset();
		} else {
			MenuService.updateMenu(vm.bean, vm.bean.menuId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Menu.');
					});
		}
	}

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('print'),
			message : "are you sure you want to delete this record",
			buttons : [ {
				label : 'yes',
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					MenuService.deleteMenu(bean.menuId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Menu.');
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
