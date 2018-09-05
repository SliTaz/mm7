'use strict';
var App = angular.module('userOrderModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(function() {
	$("#orderTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#notDisturbTime").datetimepicker({
		language: "zh-CN",
		autoclose: true,             
		clearBtn: true,//清除按钮
		todayBtn: 'linked',
//        minView: 'month',
		format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#effTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, UserOrderService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    vm.spInfoData = [];
    vm.phoneNumberData = [];
    vm.selData = selData;
	selData();
    //列的状态end
    vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userOrder')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('phoneNumber').withTitle($translate('userInfo.phoneNumber')).notSortable(),
		DTColumnBuilder.newColumn('area').withTitle($translate('userInfo.area')).notSortable(),
		DTColumnBuilder.newColumn('spInfoId').withTitle($translate('spInfo.spInfoId')).notSortable(),
		DTColumnBuilder.newColumn('productInfoId').withTitle($translate('userInfo.productInfoId')).notSortable(),
		DTColumnBuilder.newColumn('chargePhoneNumber').withTitle($translate('userInfo.chargePhoneNumber')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('fee').withTitle($translate('userInfo.fee')).notSortable(),
		DTColumnBuilder.newColumn('status').withTitle($translate('user.status')).renderWith(statusType).notSortable(),
		DTColumnBuilder.newColumn('orderTime').withTitle($translate('userInfo.orderTime')).notSortable().renderWith(timeRender),
		DTColumnBuilder.newColumn('priority').withTitle($translate('userInfo.priority')).notSortable(),
		DTColumnBuilder.newColumn('orderRoute').withTitle($translate('userInfo.orderRoute')).notSortable().renderWith(orderRoute).notVisible(),
		DTColumnBuilder.newColumn('orderType').withTitle($translate('userInfo.orderType')).notSortable().renderWith(orderType).notVisible(),
		DTColumnBuilder.newColumn('featureStr').withTitle($translate('userInfo.featureStr')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('version').withTitle($translate('userInfo.version')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('notDisturbTime').withTitle($translate('userInfo.notDisturbTime')).notSortable().notVisible().renderWith(timeRender),
		DTColumnBuilder.newColumn('transactionId').withTitle($translate('userInfo.transactionId')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('effTime').withTitle($translate('spInfo.effTime')).renderWith(timeRender).notSortable().notVisible(),
		DTColumnBuilder.newColumn('lastBatchId').withTitle($translate('userInfo.lastBatchId')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('isPackage').withTitle($translate('userInfo.isPackage')).notSortable().renderWith(isPackage).notVisible(),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
				.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.statusType = statusType;
	vm.orderRoute = orderRoute;
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	function moneyFormat(data, type, full, meta){
		//angular js 金额格式过滤器
		return outputmoney(data);
	}
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.phoneNumber] = data;
		return  '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.phoneNumber
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.phoneNumber
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	} 
	
	function selData() {
		UserOrderService.fetchSpInfo().then(function(d) {
			vm.spInfoData = d.body;
		}, function(errResponse) {
			console.error('Error while fetching fetchAllCoupons');
		});
		UserOrderService.fetchUserInfo().then(function(d) {
			vm.phoneNumberData = d.body;
		}, function(errResponse) {
			console.error('Error while fetching fetchAllCoupons');
		});
	}
	function timeRender(data, type, full, meta) {
		if(data==null||data==''){
			return '';
		}else{
			var naLan = navigator.language;
			if (naLan == undefined || naLan == "") {
				naLan = navigator.browserLanguage;
			}	
			if(naLan=='zh'){
			return  moment(data).format("YYYY-MM-DD HH:mm:ss"); 
			}else if(naLan.indexOf("zh") >= 0){
				return  moment(data).format("YYYY-MM-DD HH:mm:ss"); 
			}else{
				return  moment(data).format("DD/MM/YYYY HH:mm:ss"); 
			}

		}
		
	}
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#spInfoId").select2();
		$("#spInfoIds").select2();
		$("#phoneNumber").select2();
		$("#phoneNumbers").select2();
		$("#myModal").attr("tabindex","");
		//解决selec2在火狐模太框中输入框不能输入start
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end
	}
	
	$("#queryBtn").click(function(){
		selectDevice();
	})
	
	function statusType(data, type, full, meta){
		if(data=='0'){
			return $translate.instant('common.order');
		}else if(data=='1'){
			return $translate.instant('common.freeze');
		}else{
			return '';
		}
	}
	
	function orderType(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.monthly');
		}else if(data=='2'){
			return $translate.instant('common.onDemand');
		}else if(data=='3'){
			return $translate.instant('common.free');
		}else{
			return '';
		}
	}
	function isPackage(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.package');
		}else if(data=='0'){
			return $translate.instant('common.unpackage');
		}else{
			return '';
		}
	}
	function orderRoute(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.sms');
		}else if(data=='2'){
			return $translate.instant('common.mms');
		}else if(data=='3'){
			return $translate.instant('common.wap');
		}else if(data=='4'){
			return $translate.instant('common.web');
		}else if(data=='5'){
			return $translate.instant('common.customerService');
		}else{
			return '';
		}
	}
	
	function addInit() {
		selectDevice();
		$("#spInfoId").val("").select2();
		$("#spInfoIds").val("").select2();
		$("#phoneNumber").val("").select2();
		$("#phoneNumbers").val("").select2();
		vm.modelTitle = $translate.instant('userOrder.add');
		vm.readonlyID = false;
		vm.disabledID = false;
		vm.bean = {};
		vm.bean.phoneNumber=null;
		vm.bean.spInfoId=null;
		vm.bean.productInfoId=null;
		vm.bean.orderTime=null;
		vm.bean.chargePhoneNumber=null;
		vm.bean.orderRoute=2;
		vm.bean.orderType=1;
		vm.bean.fee=null;
		vm.bean.featureStr=null;
		vm.bean.status=0;
		vm.bean.version=null;
		vm.bean.notDisturbTime=null;
		vm.bean.transactionId=null;
		vm.bean.effTime=null;
		vm.bean.area=null;
		vm.bean.priority=null;
		vm.bean.lastBatchId=null;
		vm.bean.isPackage=1;
		vm.statusData = [
			{value:0,text:$translate.instant('common.order')},
			{value:1,text:$translate.instant('common.freeze')}];
		vm.orderTypeData = [
			{value:1,text:$translate.instant('common.monthly')},
			{value:2,text:$translate.instant('common.onDemand')},
			{value:3,text:$translate.instant('common.free')}];
		vm.isPackageData = [
			{value:1,text:$translate.instant('common.package')},
			{value:0,text:$translate.instant('common.unpackage')}];
		vm.orderRouteData = [
			{value:1,text:$translate.instant('common.sms')},
			{value:2,text:$translate.instant('common.mms')},
			{value:3,text:$translate.instant('common.wap')},
			{value:4,text:$translate.instant('common.web')},
			{value:5,text:$translate.instant('common.customerService')}];
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		selectDevice();
		//bean.fee = outputmoney(bean.fee);
		$("#spInfoId").val(bean.spInfoId).select2();
		$("#phoneNumber").val(bean.phoneNumber).select2();
		reloadData();
		vm.modelTitle = $translate.instant('userOrder.edit');
		vm.readonlyID = true;
		vm.disabledID = true;
		vm.bean = bean;
		vm.statusData = [
			{value:0,text:$translate.instant('common.order')},
			{value:1,text:$translate.instant('common.freeze')}];
		vm.orderRouteData = [
			{value:1,text:$translate.instant('common.sms')},
			{value:2,text:$translate.instant('common.mms')},
			{value:3,text:$translate.instant('common.wap')},
			{value:4,text:$translate.instant('common.web')},
			{value:5,text:$translate.instant('common.customerService')}];
		vm.orderTypeData = [
			{value:1,text:$translate.instant('common.monthly')},
			{value:2,text:$translate.instant('common.onDemand')},
			{value:3,text:$translate.instant('common.free')}];
		vm.isPackageData = [
			{value:1,text:$translate.instant('common.package')},
			{value:0,text:$translate.instant('common.unpackage')}];
		vm.bean.orderTime = timeFormatOld(vm.bean.orderTime);
		vm.bean.notDisturbTime = timeFormatOld(vm.bean.notDisturbTime);
		vm.bean.effTime = timeFormatOld(vm.bean.effTime);
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.orderTime = timeFormatNew(vm.bean.orderTime);
			vm.bean.notDisturbTime = timeFormatNew(vm.bean.notDisturbTime);
			vm.bean.effTime = timeFormatNew(vm.bean.effTime);
			/*if(vm.bean.fee){
				var fee=vm.bean.fee.replace(/\./g,"").replace(/,/g,".");
				vm.bean.fee = fee;//转换为数据库double
			}*/
			UserOrderService.createAlarmInfo(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmLevel.');
					});
			vm.reset();
		} else {
			vm.bean.orderTime = timeFormatNew(vm.bean.orderTime);
			vm.bean.notDisturbTime = timeFormatNew(vm.bean.notDisturbTime);
			vm.bean.effTime = timeFormatNew(vm.bean.effTime);
			/*if(vm.bean.fee){
				var fee=vm.bean.fee.replace(/\./g,"").replace(/,/g,".");
				vm.bean.fee = fee;//转换为数据库double
			}*/
			UserOrderService.updateAlarmInfo(vm.bean, vm.bean.phoneNumber).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmLevel.');
					});
		}
	}

	function onSubmitSuccess(data){
		/*if(data.body){
			vm.bean.fee = outputmoney(data.body.fee);
		}*/
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
					UserOrderService.deleteAlarmInfo(bean.phoneNumber).then(reloadData,
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
	
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	$("#example").on("click", ".spanFun", function(){
		var remarkDetail = $(this).text();
		BootstrapDialog.show({
			title: $translate.instant('user.remark'),
			message: function(dialog) {
				var $message=$(
						'<span id="content_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'+
					    '</span>'
				);
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            	$("#content_detail").text(remarkDetail);
            },
	        buttons: [
	        {
	            label: $translate.instant('common.yes'),
	            cssClass: 'btn-primary',
	            action: function(dialogItself){
	            	dialogItself.close();
	            }
	        }]
	    });
	});
	//超长备注处理end
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
		var phoneNumber = vm.beanSer.phoneNumber;
		var spInfoId = vm.beanSer.spInfoId;
		var status = vm.beanSer.status;
		var productInfoId = vm.beanSer.productInfoId;
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/userOrder?id=' + getValueForSelect(phoneNumber)+
				"&spInfoId="+getValueForSelect(spInfoId)+"&productInfoId="+getValueForSelect(productInfoId)+getValueForSelect(status,"&status=")));
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