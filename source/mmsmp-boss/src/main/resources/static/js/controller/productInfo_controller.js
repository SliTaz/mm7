'use strict';
var App = angular.module('productInfoModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree'  ]);

configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);
$(function(){
	$("#updateTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#createTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});	
	$("#applyTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});	
});
function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, productInfoService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.companyNameFlag=true;
	vm.companyCoreData="";

	vm.beanSer = {};
    vm.columnStatusData=[];
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/productInfo')).withOption(
			'createdRow', createdRow);
	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('productInfoId').withTitle($translate('mmsmp.productInfo.productInfoId')).notSortable(),
			DTColumnBuilder.newColumn('productName').withTitle($translate('mmsmp.productInfo.productName')).notSortable(),
			DTColumnBuilder.newColumn('companyCode').withTitle($translate('mmsmp.productInfo.companyCode')).notSortable(),
			DTColumnBuilder.newColumn('companyName').withTitle($translate('mmsmp.productInfo.companyName')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('productType').withTitle($translate('mmsmp.productInfo.productType')).notSortable().renderWith(productTypeHtml),
			DTColumnBuilder.newColumn('orderType').withTitle($translate('mmsmp.productInfo.orderType')).notSortable().renderWith(orderTypeHtml),
			DTColumnBuilder.newColumn('cpAccessId').withTitle($translate('mmsmp.productInfo.cpAccessId')).notSortable(),
			DTColumnBuilder.newColumn('cpAccessIdExtend').withTitle($translate('mmsmp.productInfo.cpAccessIdExtend')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('productOrderId').withTitle($translate('mmsmp.productInfo.productOrderId')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('status').withTitle($translate('mmsmp.productInfo.status')).notSortable().renderWith(statusHtml),
			
			DTColumnBuilder.newColumn('runStatus').withTitle($translate('mmsmp.productInfo.runStatus')).notSortable().renderWith(runStatusHtml),
			DTColumnBuilder.newColumn('createTime').withTitle($translate('mmsmp.productInfo.createTime')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('updateTime').withTitle($translate('mmsmp.productInfo.updateTime')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('remark').withTitle($translate('mmsmp.productInfo.remark')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('orderFee').withTitle($translate('mmsmp.productInfo.orderFee')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('onDemandFee').withTitle($translate('mmsmp.productInfo.onDemandFee')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('onDemandRemark').withTitle($translate('mmsmp.productInfo.onDemandRemark')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('feeRemark').withTitle($translate('mmsmp.productInfo.feeRemark')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('sendContentMode').withTitle($translate('mmsmp.productInfo.sendContentMode')).notVisible().notSortable().renderWith(sendContentModeHtml),
			
			DTColumnBuilder.newColumn('sendMode').withTitle($translate('mmsmp.productInfo.sendMode')).notVisible().notSortable().renderWith(sendModeHtml),
			DTColumnBuilder.newColumn('isDefault').withTitle($translate('mmsmp.productInfo.isDefault')).notVisible().notSortable().renderWith(isDefaultnHtml),
			DTColumnBuilder.newColumn('keyWorld').withTitle($translate('mmsmp.productInfo.keyWorld')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('classification').withTitle($translate('mmsmp.productInfo.classification')).notVisible().notSortable().renderWith(classificationHtml),
			DTColumnBuilder.newColumn('orderCommand').withTitle($translate('mmsmp.productInfo.orderCommand')).notSortable(),
			DTColumnBuilder.newColumn('webPicUrl').withTitle($translate('mmsmp.productInfo.webPicUrl')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('wapPicUrl').withTitle($translate('mmsmp.productInfo.wapPicUrl')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('onDemandCommand').withTitle($translate('mmsmp.productInfo.onDemandCommand')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('productSource').withTitle($translate('mmsmp.productInfo.productSource')).notVisible().notSortable().renderWith(productSourceHtml),
			
			DTColumnBuilder.newColumn('productOnDemandId').withTitle($translate('mmsmp.productInfo.productOnDemandId')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('bookmanName').withTitle($translate('mmsmp.productInfo.bookmanName')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('cooperationProportion').withTitle($translate('mmsmp.productInfo.cooperationProportion')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('composeTimeinterval').withTitle($translate('mmsmp.productInfo.composeTimeinterval')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('auditTimeinterval').withTitle($translate('mmsmp.productInfo.auditTimeinterval')).notVisible().notSortable(),
			
			
			DTColumnBuilder.newColumn('proofTimeinterval').withTitle($translate('mmsmp.productInfo.proofTimeinterval')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('proAuditTimeinterval').withTitle($translate('mmsmp.productInfo.proAuditTimeinterval')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('auditUser').withTitle($translate('mmsmp.productInfo.auditUser')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('auditRemark').withTitle($translate('mmsmp.productInfo.auditRemark')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('productRemark').withTitle($translate('mmsmp.productInfo.productRemark')).notVisible().notSortable(),
			
			DTColumnBuilder.newColumn('operationId').withTitle($translate('mmsmp.productInfo.operationId')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('isPackage').withTitle($translate('mmsmp.productInfo.isPackage')).notVisible().notSortable().renderWith(isPackageHtml),
			DTColumnBuilder.newColumn('priority').withTitle($translate('mmsmp.productInfo.priority')).notSortable(),
			
            DTColumnBuilder.newColumn('dealStatus').withTitle($translate('mmsmp.productInfo.dealStatus')).notVisible().notSortable().renderWith(dealStatusHtml),
			
			DTColumnBuilder.newColumn('applyTime').withTitle($translate('mmsmp.productInfo.applyTime')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('queueNum').withTitle($translate('mmsmp.productInfo.queueNum')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('isPresent').withTitle($translate('mmsmp.productInfo.isPresent')).notVisible().notSortable().renderWith(isPresentHtml),
			
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
			.renderWith(actionsHtml) ];
	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean=deleteBean;
	vm.selData = selData;
	selData();
	vm.activate =activate;
	vm.inactivate = inactivate;
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
	function orderTypeHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.orderByMonth')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productInfo.orderByTimes')+'</span>';
	}else{
		return '';
	}
		
	}
	function sendContentModeHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.getNewest')+'</span>';
	}else{
		return '';
	}
		
	}
	function classificationHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.orderByMonth')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productInfo.orderByTimes')+'</span>';
	}else{
		return '';
	}
		
	}
	function sendModeHtml(data, type, full, meta)
	{
		if(data=='1'){
			return '<span>'+$translate.instant('mmsmp.productInfo.sendByWeek')+'</span>';
		}else if(data=='2'){
			return '<span>'+$translate.instant('mmsmp.productInfo.sendByDay')+'</span>';
		}else if(data=='0'){
			return '<span>'+$translate.instant('mmsmp.productInfo.sendByNo')+'</span>';
		}else {
			return '';
		}
	}
	function productSourceHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.localService')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productInfo.nationalService')+'</span>';
	}else{
		return '';
	}
		
	}
	function statusHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.apply')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productInfo.common')+'</span>';
	}else if(data=='3'){
		return '<span>'+$translate.instant('mmsmp.productInfo.pause')+'</span>';
	}else if(data=='4'){
		return '<span>'+$translate.instant('mmsmp.productInfo.logout')+'</span>';
	}else if(data=='5'){
		return '<span>'+$translate.instant('mmsmp.productInfo.preLogout')+'</span>';
	}else{
		return '';
	}
		
	}
	function isDefaultnHtml(data, type, full, meta)
	{ if(data=='1'){
		return '<span>'+$translate.instant('common.ok')+'</span>';
	}else if(data=='0'){
		return '<span>'+$translate.instant('common.no')+'</span>';
	}else{
		return '';
	}
		
	}
	function runStatusHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.up')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productInfo.down')+'</span>';
	}else if(data=='3'){
		return '<span>'+$translate.instant('mmsmp.productInfo.upAndDown')+'</span>';
	}else if(data=='4'){
		return '<span>'+$translate.instant('mmsmp.productInfo.stop')+'</span>';
	}else{
		return '';
	}
		
	}
	function dealStatusHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.preDeal')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productInfo.Dealing')+'</span>';
	}else if(data=='0'){
		return '<span>'+$translate.instant('mmsmp.productInfo.notDeal')+'</span>';
	}else {
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
	function productTypeHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.m')+'</span>';
	}else if(data=='2'){
		return '<span>'+$translate.instant('mmsmp.productInfo.s')+'</span>';
	}else if(data=='3'){
		return '<span>'+$translate.instant('mmsmp.productInfo.e')+'</span>';
	}else if(data=='4'){
		return '<span>'+$translate.instant('mmsmp.productInfo.c')+'</span>';
	}else if(data=='5'){
		return '<span>'+$translate.instant('mmsmp.productInfo.p')+'</span>';
	}else if(data=='6'){
		return '<span>'+$translate.instant('mmsmp.productInfo.w')+'</span>';
	}else if(data=='7'){
		return '<span>'+$translate.instant('mmsmp.productInfo.d')+'</span>';
	}else{
		return '';
	}
		
	}
	function isPackageHtml(data, type, full, meta)
	{  if(data=='1'){
		return '<span>'+$translate.instant('mmsmp.productInfo.yes')+'</span>';
	}else if(data=='0'){
		return '<span>'+$translate.instant('mmsmp.productInfo.no')+'</span>';
	}else{
		return '';
	}
		
	}
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	//超长备注处理end
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.productInfoId] = data;
		if(data.classification==1)
		{  
			var classificationButton='<button class="btn bg-maroon" title="'+$translate.instant('common.inactivate')+'"   ng-click="ctrl.inactivate(ctrl.beans[\''+data.productInfoId+'\'])"> <i class="fa  fa-times-circle"></i> </button>';
		}else
		{ 
			var classificationButton='<button class="btn btn-success" title="'+$translate.instant('common.activate')+'"  ng-click="ctrl.activate(ctrl.beans[\''+data.productInfoId+'\'])"> <i class="fa fa-check-circle"></i> </button>';
			
		}
		changeDataForHTML();
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.productInfoId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.productInfoId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
		        + '</button>&nbsp;'
                +
                classificationButton
              
                +'</div>'


	}
	   function activate(bean) {
			
			BootstrapDialog.show({
				title : $translate.instant('common.activate'),
				message : $translate.instant('common.activate.message'),
				buttons : [ {
					label : $translate.instant('common.yes'),
					cssClass : 'btn btn-danger model-tool-right',
					action : function(dialogItself) {
						productInfoService.enable(bean.productInfoId).then(reloadData,
						function(errResponse) {
								handleAjaxError(errResponse);
							console.error('Error while activating GovUser.');
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


			function inactivate(bean) {
				
				BootstrapDialog.show({
					title : $translate.instant('common.inactivate'),
					message : $translate.instant('common.inactivate.message'),
					buttons : [ {
						label : $translate.instant('common.yes'),
						cssClass : 'btn btn-danger model-tool-right',
						action : function(dialogItself) {
							productInfoService.enable(bean.productInfoId).then(reloadData,
							function(errResponse) {
								handleAjaxError(errResponse);
								console.error('Error while inactivating GovUser.');
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
	function selectBank(){
		$("#companyCode").select2();
		$("#companyName").select2();
		$("#packageId").select2();
		$("#myModal").attr("tabindex","");
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end
          }
	$("#companyCode").change(function(){
	     //要触发的事件
		changeData();
	    });
	function changeData()
	{
		if($('#companyCode option:selected').val()=='')
		{ 
			$('#companyName').val("");
		}
		else
		{  
		      var temp =$('#companyCode option:selected').val(); 
			vm.companyNameData = vm.companyCoreData.filter(function (e) { return e.companyCode ==temp;});
			$('#companyName').val(vm.companyNameData[0].companyName);
//			$("#cityId1").disabled=true;
		
		}
	}
	function addInit() {
		vm.bean = {};
//		selData();
		changeData();
		$('#packageIdChange').hide();
//		vm.ProvinceCityData1=vm.ProvinceCityData;
		selectBank();
		vm.modelTitle = $translate.instant('common.productInfo.add');
		vm.statusCode="";
		vm.statusMessage="";
	}
	
	//根据是否为套餐，隐藏显示套餐编号
//	$('#isPackage').change(function(){
//	     //要触发的事件
//		changePackage();
//	    });
//	function changePackage()
//	{	
//		if($('#isPackage option:selected').val()==0){ 
//			$('#packageIdChange').hide();
//		}
//		else if($('#isPackage option:selected').val()==1){  
//			$('#packageIdChange').show();
//		}else{
//			$('#packageIdChange').hide();
//		}
//	}
	function selData(){
		productInfoService.searchAllSpInfo().then(function(d){
			vm.companyCoreData = d.body;	
			vm.companyNameData=d.body;
		},
				function(errResponse) {
						handleAjaxError(errResponse);
					console.error('Error while updating Menu.');
				});	
		vm.bean.isPackage = 1;
		productInfoService.searchProductInfoIsPackage(vm.bean.isPackage).then(function(d){
			vm.productInfoData = d.body;
		},
				function(errResponse) {
						handleAjaxError(errResponse);
					console.error('Error while updating Menu.');
				});	
		
	}
	$("#queryBtn").click(function(){
//		selectBank();
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
//		selectBank()
		changeData();
		vm.bean = bean;								
		$("#companyCode").val(vm.bean.companyCode).select2();
		changeData();
	
		reloadData();
		vm.modelTitle = $translate.instant('common.productInfo.edit');
		vm.readonlyID = true;
		vm.bean.orderType = (vm.bean.orderType==null?"":vm.bean.orderType)+"";
		vm.bean.status = (vm.bean.status==null?"":vm.bean.status)+"";
		vm.bean.runStatus = (vm.bean.runStatus==null?"":vm.bean.runStatus)+"";
		vm.bean.sendContentMode = (vm.bean.sendContentMode==null?"":vm.bean.sendContentMode)  +   "";		
		vm.bean.sendMode = (vm.bean.sendMode==null?"":vm.bean.sendMode)+"";
		vm.bean.isDefault = (vm.bean.isDefault==null?"":vm.bean.isDefault)+"";
		vm.bean.classification = (vm.bean.classification==null?"":vm.bean.classification)+"";
		vm.bean.productSource =(vm.bean.productSource==null?"":vm.bean.productSource)+"";
		vm.bean.productType = (vm.bean.productType==null?"":vm.bean.productType)+"";
		vm.bean.isPackage = (vm.bean.isPackage==null?"":vm.bean.isPackage)+"";
		vm.bean.isPresent = (vm.bean.isPresent==null?"":vm.bean.isPresent)+"";
		vm.bean.dealStatus =(vm.bean.dealStatus==null?"":vm.bean.dealStatus)+"";

		vm.bean.companyCore=(vm.bean.companyCore==null?"":vm.bean.companyCore)+"";
		vm.statusCode="";
		vm.statusMessage="";
	}
		

	function submit() {
		if (!vm.readonlyID) {
			vm.bean.applyTime = timeFormatNew(vm.bean.applyTime);
			productInfoService.createProductInfo(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Role.');
					});
			vm.reset();
		} else {
			vm.bean.companyName=$("#companyName").val();
			vm.bean.applyTime = timeFormatNew(vm.bean.applyTime);
			productInfoService.updateProductInfo(vm.bean, vm.bean.productInfoId).then(onSubmitSuccess,
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
					productInfoService.deleteProductInfo(bean.productInfoId).then(reloadData,
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
		var status = vm.beanSer.status;
		var priority = vm.beanSer.priority;
		var cpAccessId = vm.beanSer.cpAccessId;
		var productName = encodeURI(encodeURI(vm.beanSer.productName));
		var orderType=vm.beanSer.orderType;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/productInfo?productInfoId=' + getValueForSelect(productInfoId)+
				"&productName="+getValueForSelect(productName)+
				"&status="+getValueForSelect(status)+
				"&priority="+getValueForSelect(priority)+
				"&cpAccessId="+getValueForSelect(cpAccessId)+
				"&orderType="+getValueForSelect(orderType)));
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