'use strict';
var App = angular.module('spStatisticsMonthlyReportModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(function() {
	$("#orderTimeStart").datetimepicker({
		language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        startView: 3,
        minView: 3,
        format: monthFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#orderTimeEnd").datetimepicker({
		language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        startView: 3,
        minView: 3,
        format: monthFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SpStatisticsDailyReportService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    vm.provinceData=[];
    //列的状态end
    var j=1;
	var arr=[];
	var date = new Date();
	date.setMonth(date.getMonth());
    var todayTime = getBeforeDayFormatDate(date);
    var weekdate = new Date();
    weekdate.setMonth(weekdate.getMonth()-5);
    var weekTime = getBeforeDayFormatDate(weekdate);
	vm.beanSer.orderTimeStart=monthFormatOld(weekTime);
	vm.beanSer.orderTimeEnd=monthFormatOld(todayTime);
    vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/spStatisticsDailyReport?orderType=2'+"&orderTimeStart="+ getValueForSelect(weekTime)+"&orderTimeEnd="+getValueForSelect(todayTime))).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('orderTimeStart').withTitle($translate('userOrderHis.YearMonth')).notSortable(),
		DTColumnBuilder.newColumn('spInfoId').withTitle($translate('spInfo.spInfoId')).notSortable(),
		DTColumnBuilder.newColumn('companyCode').withTitle($translate('spInfo.companyCode')).notSortable(),
		DTColumnBuilder.newColumn('onDemandTimes').withTitle($translate('mmsmp.productInfo.onDemandTimes')).notSortable(),
		DTColumnBuilder.newColumn('onDemandFee').withTitle($translate('mmsmp.productInfo.onDemandFee')).notSortable(),
		DTColumnBuilder.newColumn('countOnDemandFee').withTitle($translate('mmsmp.productInfo.countOnDemandFee')).notSortable(),
		DTColumnBuilder.newColumn('newOrderNumber').withTitle($translate('mmsmp.productInfo.newOrderNumber')).notSortable(),
		DTColumnBuilder.newColumn('newOrderNumberFee').withTitle($translate('mmsmp.productInfo.newOrderNumberFee')).notSortable(),
		DTColumnBuilder.newColumn('countFee').withTitle($translate('mmsmp.productInfo.countFee')).notSortable(),
		DTColumnBuilder.newColumn('countMMSDownlink').withTitle($translate('mmsmp.productInfo.countMMSDownlink')).notSortable(),
		DTColumnBuilder.newColumn('countTerminalArrival').withTitle($translate('mmsmp.productInfo.countTerminalArrival')).notSortable()
		];

	//表头start
	tableHandle();
	vm.selData = selData;
	selData();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	function selData() {
		SpStatisticsDailyReportService.fetchProvince().then(function(d) {
			vm.provinceData = d.body;
		}, function(errResponse) {
			console.error('Error while fetching fetchAllCoupons');
		});
		SpStatisticsDailyReportService.selSpInfo().then(function(d) {
	        vm.spInfoData = d.body;
       }, function(errResponse) {
			console.error('Error while fetching fetchAllCoupons');
		});
	}
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#province").select2();
		$("#spInfo").select2();
		$("#myModal").attr("tabindex","");
		//解决selec2在火狐模太框中输入框不能输入start
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end
	}
	$("#queryBtn").click(function(){
		selectDevice();
	})
	function onSubmitSuccess(data){
		vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SpInfoService.deleteAlarmInfo(bean.spInfoId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmInfo.');
					});
					dialogItself.close();
				}

			}, {
				label : $translate.instant('common.cancel'),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			} ]
		});
		
	}
	//查询一周前时间start
	function getBeforeDayFormatDate(date) {
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var h = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (h >= 0 && h <= 9) {
    	h = "0" + h;
    }
    if (m >= 0 && m <= 9) {
    	m = "0" + m;
    }
    if (s >= 0 && s <= 9) {
    	s = "0" + s;
    }
    var currentdate = date.getFullYear() + seperator1 + month;

    return currentdate;
}
//查询一周前时间end
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	  //解决查询后保持列的显示start
	$('#table_id').on( 'init.dt', function ( e, settings, column, state ) {
		vm.columnStatusData = settings.aoColumns;
	} );
	//解决查询后保持列的显示end
	//start
    $('#table_id').on('draw.dt',function() {
    	setTimeout(function(){
    		$("#loadDiv").hide();
    		$("#bth-searchDate").attr("disabled",false);
    	},500);
    });
    
    $("#bth-searchDate").click(function(){
    	$("#loadDiv").show();
    	
    })
    //end
	
	function reloadData() {
    	$("#bth-searchDate").attr("disabled",true);
		 //解决查询后保持列的显示start
		var columuFinalStatus = vm.columnStatusData;
		if(columuFinalStatus.length>0){
		  for(var i = 0; i < columuFinalStatus.length; i++){
			  vm.dtColumns[i].bVisible = columuFinalStatus[i].bVisible;
			  }
		}
		//解决查询后保持列的显示end
		var spInfoId = vm.beanSer.spInfoId;
		var companyCode = vm.beanSer.companyCode;
		var orderTimeStart = monthFormatNew(vm.beanSer.orderTimeStart);
		var orderTimeEnd = monthFormatNew(vm.beanSer.orderTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl + '/spStatisticsDailyReport?spInfoId=' + getValueForSelect(spInfoId)+
				"&companyCode="+getValueForSelect(companyCode) + "&orderTimeStart="+ getValueForSelect(orderTimeStart) + "&orderTimeEnd="+ getValueForSelect(orderTimeEnd)+  getValueForSelect(2,"&orderType=")));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}