'use strict';
var App = angular.module('productServiceModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree'  ]);

configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);
$(function(){
	$("#popularizeEndTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#popularizeStartTime").datetimepicker({
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
	$("#expTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});	
});
function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, productServiceService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.isFreeFlag=true;
	vm.isFree=false;
	vm.beanSer = {};
    vm.columnStatusData=[];
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/productService')).withOption(
			'createdRow', createdRow);
	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('productServiceId').withTitle($translate('mmsmp.productService.productServiceId')).notSortable(),
			DTColumnBuilder.newColumn('productInfoId').withTitle($translate('mmsmp.productInfo.id')).notSortable(),
			DTColumnBuilder.newColumn('productInfoName').withTitle($translate('mmsmp.productInfo.productName')).notSortable(),
			DTColumnBuilder.newColumn('spProductId').withTitle($translate('mmsmp.productService.spProductId')).notSortable(),
			DTColumnBuilder.newColumn('reportMessageUrl').withTitle($translate('mmsmp.productService.reportMessageUrl')).notSortable(),
			
			DTColumnBuilder.newColumn('isConfirm').withTitle($translate('mmsmp.productService.isConfirm')).notVisible().notSortable().renderWith(isConfirmHtml),
			
			DTColumnBuilder.newColumn('isPresent').withTitle($translate('mmsmp.productService.isPresent')).notSortable().notVisible().renderWith(isPresentHtml),
			DTColumnBuilder.newColumn('confirmPrompt').withTitle($translate('mmsmp.productService.confirmPrompt')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('succPrompt').withTitle($translate('mmsmp.productService.succPrompt')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('canncelPrompt').withTitle($translate('mmsmp.productService.canncelPrompt')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('popularizeStartTime').withTitle($translate('mmsmp.productService.popularizeStartTime')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('popularizeEndTime').withTitle($translate('mmsmp.productService.popularizeEndTime')).notSortable().notVisible(),
			
			DTColumnBuilder.newColumn('isFree').withTitle($translate('mmsmp.productService.isFree')).notSortable().renderWith(isFreeHtml),
			DTColumnBuilder.newColumn('freeTime').withTitle($translate('mmsmp.productService.freeTime')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('billingId').withTitle($translate('mmsmp.productService.billingId')).notSortable(),
			
			DTColumnBuilder.newColumn('discountRemark').withTitle($translate('mmsmp.productService.discountRemark')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('discountId').withTitle($translate('mmsmp.productService.discountId')).notVisible().notSortable(),
	
			
			DTColumnBuilder.newColumn('needConfirm').withTitle($translate('mmsmp.productService.needConfirm')).notVisible().notSortable().renderWith(needConfirmHtml),
			DTColumnBuilder.newColumn('sendNum').withTitle($translate('mmsmp.productService.sendNum')).notSortable(),
			DTColumnBuilder.newColumn('orderCommandMatch').withTitle($translate('mmsmp.productService.orderCommandMatch')).notVisible().notSortable().renderWith(Match),
			
			DTColumnBuilder.newColumn('orderAccess').withTitle($translate('mmsmp.productService.orderAccess')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('orderAccessMatch').withTitle($translate('mmsmp.productService.orderAccessMatch')).notVisible().notSortable().renderWith(Match),
			DTColumnBuilder.newColumn('cancelCommandMatch').withTitle($translate('mmsmp.productService.cancelCommandMatch')).notVisible().notSortable().renderWith(Match),
			
			DTColumnBuilder.newColumn('cancelAccess').withTitle($translate('mmsmp.productService.cancelAccess')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('cancelAccessMatch').withTitle($translate('mmsmp.productService.cancelAccessMatch')).notVisible().notSortable().renderWith(Match),
			DTColumnBuilder.newColumn('onDemandCommandMatch').withTitle($translate('mmsmp.productService.onDemandCommandMatch')).notVisible().notSortable().renderWith(Match),
			
			DTColumnBuilder.newColumn('onDemandAccess').withTitle($translate('mmsmp.productService.onDemandAccess')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('onDemandAccessMatch').withTitle($translate('mmsmp.productService.onDemandAccessMatch')).notVisible().notSortable().renderWith(Match),
			DTColumnBuilder.newColumn('effTime').withTitle($translate('mmsmp.productService.effTime')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('expTime').withTitle($translate('mmsmp.productService.expTime')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('isAloneOrder').withTitle($translate('mmsmp.productService.isAloneOrder')).notVisible().notSortable().renderWith(addtionHtml),
			DTColumnBuilder.newColumn('notifyType').withTitle($translate('mmsmp.productService.notifyType')).notVisible().notSortable().renderWith(addtionHtml),
			
			DTColumnBuilder.newColumn('productCity').withTitle($translate('mmsmp.productService.productCity')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('productServicePeriodGrade').withTitle($translate('mmsmp.productService.productServicePeriodGrade')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('productServiceGrade').withTitle($translate('mmsmp.productService.productServiceGrade')).notVisible().notSortable(),
			
			
			DTColumnBuilder.newColumn('productCredit').withTitle($translate('mmsmp.productService.productCredit')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('status').withTitle($translate('mmsmp.productService.status')).notVisible().notSortable(),
			
			
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
			.renderWith(actionsHtml) ];
	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
    vm.selData=selData
    selData();
    vm.success_color="green";
    vm.error_color="red";
  //表头start
    tableHandle();
    //表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	function isConfirmHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productService.confirm')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productService.unConfirm')+'</span>';
	}else{
		return '';
	}
		
	}
	function isPresentHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.present')+'</span>';
	}else if(data=='0'){
		return '<span>'+$translate.instant('mmsmp.productInfo.noPresent')+'</span>';
	}else {
		return '';
	}
		
	}
	function isPresentHtml(data, type, full, meta)
	{   if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.present')+'</span>';
	}else if(data=='0'){
		return '<span>'+$translate.instant('mmsmp.productInfo.noPresent')+'</span>';
	}else{
		return '';
	}
		
	}
	function isFreeHtml(data, type, full, meta)
	{  if(data=='0'){
		return '<span>'+$translate.instant('mmsmp.productService.notSupport')+'</span>';
	}else if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productService.freeByTime')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productService.freeByDay')+'</span>';
	}else{
		return '';
	}
		
	}
	function needConfirmHtml(data, type, full, meta)
	{
		if(data=='1'){
			return '<span>'+$translate.instant('mmsmp.productService.OK')+'</span>';
		}else if(data=='0'){
			return '<span>'+$translate.instant('mmsmp.productService.NO')+'</span>';
		}else {
			return '';
		}
	}
	function Match(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productService.searchByAccurate')+'</span>';
	}else if(data=='0'){
		return '<span>'+$translate.instant('mmsmp.productService.searchByLike')+'</span>';
	}else{
		return '';
	}
		
	}
	function addtionHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productService.OK')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productService.NO')+'</span>';
	}else{
		return '';
	}
		
	}
	//超长备注处理end
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.productServiceId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.productServiceId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.productServiceId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>&nbsp;'
                '</div>'

	}
	$('#isFree').change(function(){
		 
		if($('#isFree option:selected').val()==0)
		{
			
			vm.isFreeFlag=true;
			vm.isFree=false;
			
		}else
			{
			vm.isFreeFlag=false;
			vm.isFree=true;
			
			}
		
	});
	
	function selectBank(){
		$("#productName").select2();
		$("#productCity").select2();
    	$("#myModal").attr("tabindex","");
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end/	
	}

	function addInit() {
		vm.bean = {};
		selData();
//		vm.ProvinceCityData1=vm.ProvinceCityData;
		selectBank();
		vm.modelTitle = $translate.instant('common.productService.add');
		vm.readonlyID = false;
		vm.statusCode="";
		vm.statusMessage="";
	}
	function selData(){
		productServiceService.searchAllProductInfo().then(function(d){
			vm.productInfoData = d.body;
			
		},
				function(errResponse) {
						handleAjaxError(errResponse);
					console.error('Error while updating Menu.');
				});
		productServiceService.searchAllProvinceCitys().then(function(d){
			vm.ProvinceCityData = d.body;
			
		},
				function(errResponse) {
						handleAjaxError(errResponse);
					console.error('Error while updating Menu.');
				});
	}
	$("#queryBtn").click(function(){
		selectBank();
		selData();
	})
//	 function deleteData(provinceCityId) {
//		    var city = vm.ProvinceCityData;
//		    //alert(name);
//		    for (var i = 0; i < city.length; i++) {
//		      var cur_city = city[i];
//		      if (cur_city.provinceCityId == provinceCityId) {
//		        vm.ProvinceCityData.splice(i, 1);
//		      }
//		    }
//		vm.ProvinceCityData1=vm.ProvinceCityData;
//		  }
	function edit(bean) {
//		selData();
		selectBank()
		
		reloadData();
		vm.modelTitle = $translate.instant('common.productService.edit');
		vm.readonlyID = true;
		vm.bean = bean;	

		vm.bean.isConfirm =  (vm.bean.isConfirm==null?"":vm.bean.isConfirm)+"";
		vm.bean.isPresent = (vm.bean.isPresent==null?"":vm.bean.isPresent)+"";
		vm.bean.isFree = (vm.bean.isFree==null?"":vm.bean.isFree)+"";
		vm.bean.needConfirm =  (vm.bean.needConfirm==null?"":vm.bean.needConfirm)+"";
		vm.bean.orderCommandMatch =(vm.bean.orderCommandMatch==null?"":vm.bean.orderCommandMatch)+"";
		vm.bean.orderAccessMatch = (vm.bean.orderAccessMatch==null?"":vm.bean.orderAccessMatch)+"";
		vm.bean.cancelCommandMatch =(vm.bean.cancelCommandMatch==null?"":vm.bean.cancelCommandMatch)+"";
		vm.bean.cancelAccessMatch = (vm.bean.cancelAccessMatch==null?"":vm.bean.cancelAccessMatch)+"";
		vm.bean.onDemandCommandMatch = (vm.bean.onDemandCommandMatch==null?"":vm.bean.onDemandCommandMatch)+"";
		vm.bean.onDemandAccessMatch = (vm.bean.onDemandAccessMatch==null?"":vm.bean.onDemandAccessMatch)+"";
		vm.bean.isAloneOrder = (vm.bean.isAloneOrder==null?"":vm.bean.isAloneOrder)+"";
		vm.bean.notifyType = (vm.bean.notifyType==null?"":vm.bean.notifyType)+"";
		
		vm.statusCode="";
		vm.statusMessage="";
	}
		

	function submit() {
		if (!vm.readonlyID) {
			vm.bean.popularizeEndTime = timeFormatNew(vm.bean.popularizeEndTime);
			vm.bean.popularizeStartTime = timeFormatNew(vm.bean.popularizeStartTime);
			vm.bean.effTime = timeFormatNew(vm.bean.effTime);
			vm.bean.expTime = timeFormatNew(vm.bean.expTime);
			productServiceService.createProductService(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Role.');
					});
			vm.reset();
		} else {
	      	vm.bean.popularizeEndTime = timeFormatNew(vm.bean.popularizeEndTime);
	      	vm.bean.popularizeStartTime = timeFormatNew(vm.bean.popularizeStartTime);
	      	vm.bean.effTime = timeFormatNew(vm.bean.effTime);
	      	vm.bean.expTime = timeFormatNew(vm.bean.expTime);
			productServiceService.updateProductService(vm.bean, vm.bean.productServiceId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Role.');
					});
		}
	} 
	//原有的
function onSubmitSuccess(data){
	vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('sysRole.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					productServiceService.deleteProductService(bean.productServiceId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Role.');
					});
					dialogItself.close();
				}

			}, {
				label :  $translate.instant('common.cancel'),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			} ]
		});
		
	}
	//超长备注处理start
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

		var productInfoId = vm.beanSer.productInfoId;
		var productServiceId=vm.beanSer.productServiceId;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/productService?productServiceId=' + getValueForSelect(productServiceId)+
				"&productInfoId="+getValueForSelect(productInfoId)));
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