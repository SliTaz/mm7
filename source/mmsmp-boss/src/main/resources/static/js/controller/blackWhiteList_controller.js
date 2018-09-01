'use strict';
var App = angular.module('blackWhiteListModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree'  ]);

configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, BlackWhiteListService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
    vm.columnStatusData=[];
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/blackWhiteList')).withOption(
			'createdRow', createdRow);
	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('blackWhiteList').withTitle($translate('mmsmp.blackWhiteList.blackWhiteList')).notSortable(),
			DTColumnBuilder.newColumn('blackWhiteListType').withTitle($translate('mmsmp.blackWhiteList.blackWhiteListType')).notSortable().renderWith(blackWhiteListType),
			DTColumnBuilder.newColumn('numberType').withTitle($translate('mmsmp.blackWhiteList.numberType')).notSortable().renderWith(numberType),
			DTColumnBuilder.newColumn('remark').withTitle($translate('mmsmp.blackWhiteList.remark')).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
			.renderWith(actionsHtml) ];
	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
//	vm.selData = selData;
//	selData();
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
	//超长备注处理end
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.blackWhiteList] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.blackWhiteList
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.blackWhiteList
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>&nbsp;'
                '</div>'

	}
	function blackWhiteListType(data, type, full, meta){
		if(data=='1'){
			return '<span>'+$translate.instant('common.BlackList')+'</span>';
		}else if(data=='2'){
			return '<span>'+$translate.instant('common.WhiteList')+'</span>';
		}else{
			return '';
		}
	}
	function numberType(data, type, full, meta){
		if(data=='1'){
			return '<span>'+$translate.instant('common.phoneNumberAndServiceId')+'</span>';
		}else if(data=='2'){
			return '<span>'+$translate.instant('common.ServiceId')+'</span>';
		}else if(data=='3'){
			return '<span>'+$translate.instant('common.SpConnection')+'</span>';
		}else{
			return '';
		}
	}
//	function selectBank(){
//		$("#provinceId").select2();
//		$("#cityId").select2();
//		$("#areaCode").select2();
//		$("#provinceId1").select2();
//		$("#cityId1").select2();
//		$("#areaCode1").select2();
////		$("#myModal").attr("tabindex","");
//		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
//		};
		//解决selec2在火狐模太框中输入框不能输入end
//	}

	function addInit() {
		vm.bean = {};
//		selData();
//		vm.ProvinceCityData1=vm.ProvinceCityData;
//		selectBank();
		vm.modelTitle = $translate.instant('mmsmp.blackWhiteList.add');
		vm.readonlyID = false;
		vm.statusCode="";
		vm.statusMessage="";
	}
//	$("#provinceId").change(function(){
//	     //要触发的事件
//		vm.cityIdData = vm.MobileSegementData.filter(function (e) { return e.provinceId ==$('#provinceId option:selected').val(); });
//		vm.areaCodeData=vm.cityIdData;
//	    });
//	$("#cityId").change(function(){
//	     //要触发的事件
//		vm.areaCodeData = vm.cityIdData.filter(function (e) { return e.cityId ==$('#cityId option:selected').val(); });
//	
//	    });
//	$("#provinceId1").change(function(){
//	     //要触发的事件
//		vm.cityIdData1 = vm.MobileSegementData.filter(function (e) { return e.provinceId ==$('#provinceId1 option:selected').val(); });
//	   vm.areaCodeData1=vm.cityIdData1;
//	});
//	function initSelect()
//	{
//		vm.cityIdData1 = vm.MobileSegementData.filter(function (e) { return e.provinceId ==vm.bean.provinceId; });
//		vm.areaCodeData1 = vm.cityIdData1.filter(function (e) { return e.cityId ==vm.bean.cityId; });
//		console.log(vm.cityIdData1);
//	}
//	$("#cityId1").change(function(){
//	     //要触发的事件
//		vm.areaCodeData1 = vm.cityIdData1.filter(function (e) { return e.cityId ==$('#cityId1 option:selected').val(); });
//	
//	    });
//	function selData(){
//		MobileSegementService.searchAllMobileSegement().then(function(d){
//			vm.MobileSegementData = d.body;
//			vm.cityIdData=d.body;
//			vm.areaCodeData=d.body;
//			vm.cityIdData1=d.body;
//			vm.areaCodeData1=d.body;
//			
//		},
//				function(errResponse) {
//						handleAjaxError(errResponse);
//					console.error('Error while updating Menu.');
//				});
//	}
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
//    	selectBank()
		
		reloadData();
		vm.modelTitle = $translate.instant('mmsmp.blackWhiteList.edit');
		vm.readonlyID = true;
		vm.bean = bean;	
		vm.bean.blackWhiteListType = vm.bean.blackWhiteListType+"";
		vm.bean.numberType = vm.bean.numberType + "";
		
//		$("#blackWhiteListType").val(vm.bean.blackWhiteListType);
//		$("#areaCode1").val(vm.bean.areaCode).select2();
//		initSelect();
		
		vm.bean.status = vm.bean.status+"";
		vm.statusCode="";
		vm.statusMessage="";
	}
		

	function submit() {
		if (!vm.readonlyID) {
			BlackWhiteListService.createBlackWhiteList(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Role.');
					});
			vm.reset();
		} else {
			BlackWhiteListService.updateBlackWhiteList(vm.bean, vm.bean.blackWhiteList).then(onSubmitSuccess,
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
					BlackWhiteListService.deleteBlackWhiteList(bean.blackWhiteList).then(reloadData,
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
		//解决查询后保持列的显示end
		
		var blackWhiteList = vm.beanSer.blackWhiteList;
		var blackWhiteListType = vm.beanSer.blackWhiteListType;
		var numberType=vm.beanSer.numberType;
		var remark= vm.beanSer.remark;
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/blackWhiteList?blackWhiteList=' + getValueForSelect(blackWhiteList)+"&blackWhiteListType="+getValueForSelect(blackWhiteListType)+
		"&numberType="+getValueForSelect(numberType)+"&remark="+getValueForSelect(remark)));
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