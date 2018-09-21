'use strict';
var App = angular.module('provincialAnnualIncomeModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize',
		'LocalStorageModule', 'ui.tree' ]);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(window).on('load', function() {
	$('#userIds').selectpicker({
		'selectedText' : 'cat'
	});
});

$(function() {
	$("#orderTimeStart").datetimepicker({
		format: 'yyyy', 
		 startView : "decade", 
	   	 minView: 'decade',
	   	 language : 'zh-CN',
	   	 autoclose: true//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#orderTimeEnd").datetimepicker({
		 format: 'yyyy', 
		 startView : "decade", 
	   	 minView: 'decade',
	   	 language : 'zh-CN',
	   	 autoclose: true//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});
function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile,provincialAnnualIncomeService, localStorageService,
		topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
	// 列的状态start
	vm.columnStatusData = [];
	// 列的状态end
	var pwdFirst;
	vm.reloadData = reloadData;
	vm.appData = [];
	var date = new Date();
	vm.selData=selData;
	selData();
	date.setDate(date.getDate());
    var todayTime = getBeforeDayFormatDate(date);
    var weekDate = new Date();
        weekDate.setFullYear(weekDate.getFullYear()-1);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.beanSer.orderTimeStart=dayFormatOld(weekTime);
		vm.beanSer.orderTimeEnd=dayFormatOld(todayTime);
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userOrderHis/provincialIncomeAnalysis?orderTimeStart=' + getValueForSelect(weekTime)+"&orderTimeEnd="+getValueForSelect(todayTime))).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		    DTColumnBuilder.newColumn('province').withTitle($translate('userOrderHis.province')).notSortable(),
			DTColumnBuilder.newColumn('newOrderSum').withTitle($translate('userOrderHis.newOrderSum')).notSortable(),
			DTColumnBuilder.newColumn('newBuySum').withTitle($translate('userOrderHis.newBuySum')).notSortable(),
			DTColumnBuilder.newColumn('newTotal').withTitle($translate('userOrderHis.newTotal')).notSortable()];


	// 表头start
	tableHandle();
	// 表头end
	vm.VAR_MAX_FILE_SIZE_M=1;//1M
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();   

	function getBeforeDayFormatDate(date) {
	    var currentdate = date.getFullYear() ;
	
	    return currentdate;
	}
	function getformatmonth(data){
		var naLan = navigator.language;
		if (naLan == undefined || naLan == "") {
			naLan = navigator.browserLanguage;
		}	
		if(naLan=='zh'){
		return  data; 
		}else if(naLan.indexOf("zh") >= 0){
			return  data; 
		}else{
			var monthData=[];
			for(var i=0;i<data.length;i++){
				if(data[i].length==8){
					monthData[i]="0"+data[i].substring(7,8)+"/0"+data[i].substring(5,6)+"/"+data[i].substring(0,4)
				}if(data[i].length==9){
					if(data[i].substring(6,7)=="-"){
						monthData[i]=data[i].substring(7,9)+"/0"+data[i].substring(5,6)+"/"+data[i].substring(0,4);
					}else{
					    monthData[i]="0"+data[i].substring(8,9)+"/"+data[i].substring(5,7)+"/"+data[i].substring(0,4);
					}
				}
				if(data[i].length==10){
						monthData[i]=data[i].substring(8,10)+"/"+data[i].substring(5,7)+"/"+data[i].substring(0,4); 
					}
				}
			return monthData;
		 }
		}
	// 超长备注处理start
	$("#example")
			.on(
					"click",
					".spanFun",
					function() {
						var remarkDetail = $(this).text();
						BootstrapDialog
								.show({
									title : $translate.instant('user.remark'),
									message : function(dialog) {
										var $message = $('<span id="content_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'
												+ '</span>');
										return $message;
									},
									onshown : function(dialogRef) {// 打开完成
										$("#content_detail").text(remarkDetail);
									},
									buttons : [ {
										label : $translate
												.instant('common.yes'),
										cssClass : 'btn-primary',
										action : function(dialogItself) {
											dialogItself.close();
										}
									} ]
								});
					});
	// 超长备注处理end
	// 解决查询后保持列的显示start
	$('#table_id').on('init.dt', function(e, settings, column, state) {
		var flag = $(".dataTables_scrollBody").css("overflow");
    	if(flag='visible'){
		}
    	if(flag='auto'){
			$(".dataTables_scroll").attr("style","overflow:auto");
			$(".dataTables_scrollHead").css("overflow", "");
			$(".dataTables_scrollBody").css("overflow", "");
			$(".dataTables_scrollBody").attr("style","border:0px");
			$("#table_id").attr("style","border-bottom:1px solid black");
		}
		vm.columnStatusData = settings.aoColumns;
	});
	// 解决查询后保持列的显示end
	// start
	$('#table_id').on('draw.dt', function() {
		setTimeout(function() {
			$("#loadDiv").hide();
			$("#bth-searchDate").attr("disabled", false);
		}, 500);
	});

	$("#bth-searchDate").click(function() {
		$("#loadDiv").show();
		

	});
	// end
	function selData(){
		
	
		
		provincialAnnualIncomeService.searchAllProvinces().then(function(d){
			vm.ProvinceCityData = d.body;
			
		},
				function(errResponse) {
						handleAjaxError(errResponse);
					console.error('Error while updating Menu.');
				});
		
		
		
	}

	function reloadData() {
		$("#bth-searchDate").attr("disabled", true);
		// 解决查询后保持列的显示start
		var columuFinalStatus = vm.columnStatusData;
		if (columuFinalStatus.length > 0) {
			for (var i = 0; i < columuFinalStatus.length; i++) {
				vm.dtColumns[i].bVisible = columuFinalStatus[i].bVisible;
			}
		}
		var  provinceCityId=vm.beanSer.provinceCityId;
		var orderTimeStart = timeFormatNew(vm.beanSer.orderTimeStart);
		var orderTimeEnd = timeFormatNew(vm.beanSer.orderTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/userOrderHis/provincialIncomeAnalysis?orderTimeStart=' + getValueForSelect(orderTimeStart)+ "&provinceCityId=" + getValueForSelect(provinceCityId)
				+ "&orderTimeEnd=" + getValueForSelect(orderTimeEnd)));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
		// console.log(json);
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}