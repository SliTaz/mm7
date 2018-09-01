'use strict';
var App = angular.module('myModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize' , 'LocalStorageModule', 'ui.tree']);

configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, MenuService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/menu')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('menuId').withTitle(
					$translate('menu.menuId')).notSortable(),
					DTColumnBuilder.newColumn('userId').withTitle($translate('menu.userId')).notSortable(),
					DTColumnBuilder.newColumn('menuKeyWord').withTitle($translate('menu.menuKeyWord')).notSortable(),
					DTColumnBuilder.newColumn('menuSortno').withTitle($translate('menu.menuSortno')).notSortable(),
			DTColumnBuilder.newColumn('menuName').withTitle($translate('menu.menuName')).notSortable(),
			DTColumnBuilder.newColumn('menuPic').withTitle($translate('menu.menuPic')).notSortable(),
			DTColumnBuilder.newColumn('menuType').withTitle($translate('menu.menuType')).notSortable(),
			DTColumnBuilder.newColumn('preMenuId').withTitle($translate('menu.preMenuId')).notSortable(),
			DTColumnBuilder.newColumn('menuProces').withTitle($translate('menu.menuProces')).notSortable(),
			DTColumnBuilder.newColumn('remark').withTitle($translate('menu.remark')).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width','10%').notSortable()
			.renderWith(actionsHtml) ];
	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;

	initltCommon(vm,localStorageService,topleftService);
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
	}
	function edit(bean) {
		vm.modelTitle = $translate.instant('menu.menuedit');
		vm.readonlyID = true;
		vm.bean = bean;
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
			title : "删除",
			message : "确定要删除该记录吗",
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
		var menuId = vm.beanSer.menuId;
		var userId = vm.beanSer.userId;
		var menuKeyWord = vm.beanSer.menuKeyWord;
		var menuSortno = vm.beanSer.menuSortno;
		var menuName = vm.beanSer.menuName;
		var menuPic = vm.beanSer.menuPic;
		var menuType = vm.beanSer.menuType;
		var preMenuId = vm.beanSer.preMenuId;
		var menuProces = vm.beanSer.menuProces;
		var remark = vm.beanSer.remark;
		
		var rurl = "";
		if(menuId!=null&&menuId!=""&&menuId!=undefined){
			rurl = rurl + "id="+menuId+"&"
		}
		if(userId!=null&&userId!=""&&userId!=undefined){
			rurl = rurl + "userId="+userId+"&"
		}
		if(menuKeyWord!=null&&menuKeyWord!=""&&menuKeyWord!=undefined){
			rurl = rurl + "menuKeyWord="+menuKeyWord+"&"
		}
		if(menuSortno!=null&&menuSortno!=""&&menuSortno!=undefined){
			rurl = rurl + "menuSortno="+menuSortno+"&"
		}
		if(menuName!=null&&menuName!=""&&menuName!=undefined){
			rurl = rurl + "menuName="+menuName+"&"
		}
		if(menuPic!=null&&menuPic!=""&&menuPic!=undefined){
			rurl = rurl + "menuPic="+menuPic+"&"
		}
		if(menuType!=null&&menuType!=""&&menuType!=undefined){
			rurl = rurl + "menuType="+menuType+"&"
		}
		if(preMenuId!=null&&preMenuId!=""&&preMenuId!=undefined){
			rurl = rurl + "preMenuId="+preMenuId+"&"
		}
		if(menuProces!=null&&menuProces!=""&&menuProces!=undefined){
			rurl = rurl + "menuProces="+menuProces+"&"
		}
		if(remark!=null&&remark!=""&&remark!=undefined){
			rurl = rurl + "remark="+remark+"&"
		}
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/menu?' +rurl));
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
