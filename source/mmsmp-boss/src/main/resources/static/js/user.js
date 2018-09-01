'use strict';
var App = angular.module('myModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize' ]);
App.config([ '$translateProvider', translateProvider ]);
App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, UserService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(apiUrl + '/user').withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('userId').withTitle(
					$translate('user.userId')).notSortable(),
			DTColumnBuilder.newColumn('userName').withTitle('userName')
					.notSortable(),
			DTColumnBuilder.newColumn('password').withTitle('password')
					.notSortable(),
			DTColumnBuilder.newColumn(null).withTitle('Actions').notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;

	function actionsHtml(data, type, full, meta) {
		vm.beans[data.userId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" ng-click="ctrl.edit(ctrl.beans['
				+ data.userId
				+ '])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" data-toggle="modal" data-target="#myModal" ng-click="ctrl.delete(ctrl.beans['
				+ data.userId
				+ '])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}

	function addInit() {
		vm.modelTitle = $translate.instant('user.useradd');
		vm.readonlyID = false;
		vm.bean = {};
	}
	function edit(bean) {
		vm.modelTitle = $translate.instant('user.useredit');
		vm.readonlyID = true;
		vm.bean = bean;
	}

	function submit() {
		//if (vm.bean.userId == null) {
		if(!vm.readonlyID){
			UserService.createUser(vm.bean).then(reloadData,
					function(errResponse) {
						console.error('Error while creating User.');
					});
		} else {
			UserService.updateUser(vm.bean, vm.bean.userId).then(reloadData,
					function(errResponse) {
						console.error('Error while updating User.');
					});
		}
		vm.reset();
	}

	function reloadData() {
		var userId = $("#userIdSer").val();
		vm.dtInstance.changeData(apiUrl + '/user?id=' + userId);
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
		//console.log(json);
	}
	function createdRow(row, data, dataIndex) {
		// Recompiling so we can bind Angular directive to the DT
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}