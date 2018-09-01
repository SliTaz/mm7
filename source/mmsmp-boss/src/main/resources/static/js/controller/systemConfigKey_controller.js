'use strict';
var App = angular.module('systemConfigKeyModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize' , 'LocalStorageModule', 'ui.tree']);
configAppModule(App);
App.controller('ClientSideCtrl', ClientSideCtrl);


function ClientSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SystemConfigKeyService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/systemConfigKey')).withOption('createdRow', createdRow);

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
		}]
	});
	vm.dtColumns = [
		    DTColumnBuilder.newColumn('code').withTitle($translate('common.code')),
		    DTColumnBuilder.newColumn('defalut').withTitle($translate('common.defalut')),
		    DTColumnBuilder.newColumn('nameOne').withTitle($translate('common.nameOne')),
		    DTColumnBuilder.newColumn('nameTwo').withTitle($translate('common.nameTwo')).notVisible(),
		    DTColumnBuilder.newColumn('nameThree').withTitle($translate('common.nameThree')).notVisible()];
	vm.test =test;
	test();
	
	initltCommon(vm,localStorageService,topleftService);
	
	function test(){
		$(window).resizeEnd({
		    delay: 300
		}, function(){
			//水平滚动条带列名称滚动
			$(".dataTables_scroll").attr("style","overflow:auto");
			$(".dataTables_scrollHead").css("overflow", "");
			$(".dataTables_scrollBody").css("overflow", "");
			$(".dataTables_scrollBody").attr("style","border:0px");
			$("#table_id").attr("style","border-bottom:1px solid black");
			//水平滚动条带列名称滚动
		    //处理代码
			$("#table_id").dataTable().fnAdjustColumnSizing(false);//重新计算列宽
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
