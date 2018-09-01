'use strict';
var App = angular.module('alarmMangerModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);


/*$(function() {
	$("#firstTimeSec").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#firstTimeSecStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#firstTimeSecEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});*/





function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, AlarmMangerService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.alarmLevelData =[];
	vm.alarmEmailManageData =[];
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/alarmManger')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('alarmKey').withTitle(
				$translate('alarmManger.alarmKey')).notSortable(),
		DTColumnBuilder.newColumn('name').withTitle($translate('alarmManger.name')).notSortable(),
		 DTColumnBuilder.newColumn('alarmLevelName').withTitle($translate('alarmLevelType.alarmLevel')).notSortable(),
		 DTColumnBuilder.newColumn('alarmEmailManageName').withTitle($translate('alarmLevel.alarmEmailManageId')).notSortable(),
		DTColumnBuilder.newColumn('firstTimeSec').withTitle($translate('alarmManger.firstTimeSec')).notSortable(),
		DTColumnBuilder.newColumn('frequencyTimeSec').withTitle($translate('alarmManger.frequencyTimeSec')).notSortable(),
		DTColumnBuilder.newColumn('handleClass').withTitle($translate('alarmManger.handleClass')).notSortable(),
		DTColumnBuilder.newColumn('classParamter').notVisible().withTitle($translate('alarmManger.classParamter')).notSortable(),
		DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('alarmManger.remark')).notSortable().renderWith(remarkDetail),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width','10%').notSortable()
				.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.selFun = selFun;
	selFun();
	vm.selalarmEmailManage = selalarmEmailManage;
	selalarmEmailManage();
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
		vm.beans[data.alarmKey] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
		+ data.alarmKey
		+ '\'])">'
		+ '   <i class="fa fa-edit"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
		+ data.alarmKey
		+ '\'])">'
		+ '   <i class="fa fa-trash-o"></i>'
		+ '</button>';
	}

	function timeRender(data, type, full, meta) {

		if(data==null){
			return '';
		}else{
			return  moment(data).format("YYYY-MM-DD HH:mm:ss"); 
		}
	}

	function selFun() {
		AlarmMangerService.selAlarmLevelType()
        .then(
				       function(d) {
					        vm.alarmLevelData = d.body;
				       },
  					function(errResponse){
  						console.error('Error while fetching selAlarmLevelType');
  					}
		       );
	}	
	function selalarmEmailManage() {
		AlarmMangerService.selalarmEmailManage()
		.then(
				function(d) {
					vm.alarmEmailManageData = d.body;
				},
				function(errResponse){
					console.error('Error while fetching selAlarmLevelType');
				}
		);
	}	
	//bank下拉框
	function selectBank(){
		$("#selectCouponFun").select2();
		$("#alarmEmailManageId").select2();
		$("#alarmLevelCode").select2();
		$("#alarmLevelCodes").select2();
		$("#myModal").attr("tabindex","");
		$.fn.modal.Constructor.prototype.enforceFocus = function () {};
    	//解决 select2在模态框使用，模糊输入框无效
	}
	//初始select2
	$("#queryBtn").click(function(){
		selectBank();
	})

	function addInit() {
		selectBank();//解决初始化查询框输入不了值
		vm.modelTitle = $translate.instant('alarmManger.alarmMangerAdd');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.name=null;
		 $("#alarmEmailManageId").val("").select2();
		 $("#alarmLevelCode").val("").select2();
		vm.bean.alarmLevelCode=null;
		vm.bean.frequencyTimeSec=null;
		vm.bean.handleClass=null;
		vm.bean.classParamter=null;
		vm.bean.remark=null;
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		selectBank();//解决初始化查询框输入不了值
		reloadData();
		vm.modelTitle = $translate.instant('alarmManger.alarmMangerEdit');
		$("#alarmEmailManageId").val(bean.alarmEmailManageId).select2();
		$("#alarmLevelCode").val(bean.alarmLevelCode).select2();
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		// if (vm.bean.alarmKey == null) {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			AlarmMangerService.createAlarmManger(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmManger.');
					});
			vm.reset();
		} else {
			AlarmMangerService.updateAlarmManger(vm.bean, vm.bean.alarmKey).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmManger.');
					});
		}
	}

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
					AlarmMangerService.deleteAlarmManger(bean.alarmKey).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmManger.');
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
		var alarmKey = vm.beanSer.alarmKey;
		var name = vm.beanSer.name;
		var alarmEmailManageId = vm.beanSer.alarmEmailManageId;
		var alarmLevelCode = vm.beanSer.alarmLevelCode;
		var firstTimeSec = vm.beanSer.firstTimeSec;
/*		var rurl ="";
		if(alarmKey!=null&&alarmKey!=""&&alarmKey!=undefined){
			rurl = rurl + "id="+alarmKey+"&"
		}
		if(name!=null&&name!=""&&name!=undefined){
			rurl = rurl + "name="+name+"&"
		}
		if(alarmLevelCode!=null&&alarmLevelCode!=""&&alarmLevelCode!=undefined){
			rurl = rurl + "alarmLevelCode="+alarmLevelCode+"&"
		}
		if(firstTimeSecStart!=null&&firstTimeSecStart!=""&&firstTimeSecStart!=undefined){
			rurl = rurl + "firstTimeSecStart="+firstTimeSecStart+"&"
		}
		if(firstTimeSecEnd!=null&&firstTimeSecEnd!=""&&firstTimeSecEnd!=undefined){
			rurl = rurl + "firstTimeSecEnd="+firstTimeSecEnd+"&"
		}
		vm.dtInstance.changeData(getFromSource(apiUrl + '/alarmManger?' +rurl));*/
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/alarmManger?id=' + getValueForSelect(alarmKey)+
				"&name="+getValueForSelect(name)+"&alarmLevelCode="+getValueForSelect(alarmLevelCode)+"&alarmEmailManageId="+getValueForSelect(alarmEmailManageId)+
				"&firstTimeSec="+getValueForSelect(firstTimeSec)));
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