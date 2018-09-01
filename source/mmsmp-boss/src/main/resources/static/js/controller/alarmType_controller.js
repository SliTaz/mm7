'use strict';
var App = angular.module('alarmTypeModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize','LocalStorageModule', 'ui.tree' ]);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, AlarmTypeService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/alarmType')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('alarmTypeCode').withTitle(
					$translate('alarmType.alarmTypeCode')).notSortable(),
			DTColumnBuilder.newColumn('name').withTitle($translate('alarmType.name')).notSortable(),
			DTColumnBuilder.newColumn('handleClass').withTitle($translate('alarmType.handleClass')).notSortable(),
			DTColumnBuilder.newColumn('classParamter').withTitle($translate('alarmType.classParamter')).notSortable(),
			DTColumnBuilder.newColumn('deleteFlag').notVisible().withTitle($translate('alarmType.deleteFlag')).notSortable(),
			DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('alarmType.remark')).renderWith(remarkDetail).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width', '10%').notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
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
		vm.beans[data.alarmTypeCode] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.alarmTypeCode
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.alarmTypeCode
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}

	function addInit() {
		vm.modelTitle = $translate.instant('alarmType.alarmTypeAdd');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.name=null;
		vm.bean.handleClass=null;
		vm.bean.classParamter=null;
		vm.bean.remark=null;
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('alarmType.alarmTypeEdit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		// if (vm.bean.alarmTypeCode == null) {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			AlarmTypeService.createAlarmType(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmType.');
					});
			vm.reset();
		} else {
			AlarmTypeService.updateAlarmType(vm.bean, vm.bean.alarmTypeCode).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmType.');
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
					AlarmTypeService.deleteAlarmType(bean.alarmTypeCode).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmType.');
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
		var alarmTypeCode = vm.beanSer.alarmTypeCode;
		var name = vm.beanSer.name;
		var handleClass = vm.beanSer.handleClass;
		var classParamter = vm.beanSer.classParamter;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/alarmType?id=' + getValueForSelect(alarmTypeCode)+
				"&name="+getValueForSelect(name)+"&handleClass="+getValueForSelect(handleClass)+"&classParamter="+getValueForSelect(classParamter)
				));
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