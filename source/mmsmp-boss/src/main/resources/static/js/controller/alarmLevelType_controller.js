'use strict';
var App = angular.module('alarmLevelTypeModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize','LocalStorageModule', 'ui.tree' ]);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

$(window).on('load', function () {
    $('#userIds').selectpicker({
        'selectedText': 'cat'
    });
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, AlarmLevelTypeService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.alarmLevelData =[];
	vm.alarmTypeData =[];
	vm.appData=[];
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.beanSer = {};
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/alarmLevelType')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		 DTColumnBuilder.newColumn('alarmLevelName').withTitle($translate('alarmLevelType.alarmLevel')).notSortable(),
			
			DTColumnBuilder.newColumn('alarmTypeName').withTitle($translate('alarmLevelType.alarmType')).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	//双主键修改
	vm.doubleEdit = doubleEdit;
	//双主键删除
	vm.doubleDelete = doubleDelete;
	vm.selFun = selFun;
	vm.selTypeFun = selTypeFun;
	selFun();
	selTypeFun();
	
	
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	
	function actionsHtml(data, type, full, meta) {
		
		var key=data.alarmLevelCode+"_"+data.alarmTypeCode;
		vm.beans[key] = data;
		return  '<button class="btn btn-danger" ng-click="ctrl.doubleDelete('
				+ data.alarmLevelCode+','+data.alarmTypeCode+')">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}
	function selFun() {
		AlarmLevelTypeService.selAlarmLevelType()
        .then(
				       function(d) {
					        vm.alarmLevelData = d.body;
				       },
  					function(errResponse){
  						console.error('Error while fetching selAlarmLevelType');
  					}
		       );
	}
	
	
	function selTypeFun() {
		AlarmLevelTypeService.fetchAllAlarmTypes()
        .then(
				       function(d) {
					        vm.alarmTypeData = d.body;
				       },
  					function(errResponse){
  						console.error('Error while fetching alarmTypeData');
  					}
		       );
	}

	function addInit() {
		$('.selectpicker').selectpicker('val', '');
		$('.selectpicker').selectpicker('refresh');
		vm.modelTitle = $translate.instant('alarmLevelType.alarmLevelTypeAdd');
		//vm.readonlyID = false;
		vm.disabledID = false;
		vm.bean = {};
		vm.statusCode="";
		vm.statusMessage="";
	}
	
	//双主键修改
	function doubleEdit(key1,key2) {
		reloadData();
		$('.selectpicker').selectpicker('val', '');
		$('.selectpicker').selectpicker('refresh');
		var key=key1+"_"+key2;
		var bean=vm.beans[key];
	    var arr=bean.alarmTypeCode.split(',');
		  $('#userIds').selectpicker('val', arr);
			vm.modelTitle = $translate.instant('alarmLevelType.alarmLevelTypeEdit');
			vm.readonlyID = true;
			vm.disabledID = true;
			bean.alarmTypeCode =arr;
			vm.bean = bean;
			vm.statusCode="";
			vm.statusMessage="";
	}
	
	//双主键删除
	function doubleDelete(key1,key2) {
		var key=key1+"_"+key2;
		var bean=vm.beans[key];
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					AlarmLevelTypeService.deleteAlarmLevelType(bean.alarmLevelCode,bean.alarmTypeCode).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmLevelType.');
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
	
	function edit(bean) {
		alert("111");
		$('.selectpicker').selectpicker('val', '');
		$('.selectpicker').selectpicker('refresh');
		AlarmLevelTypeService.selectByAlarmLevelId(bean.alarmLevelCode)
        .then(
			       function(d) {
				      vm.appData = d.body;
				      var str = "";
	                  for (var i = 0; i < vm.appData.length; i++){
	                	  var a =vm.appData[i].alarmTypeCode;
	                	  str += a + ",";
	                  }
					  var arr=str.split(',');
					  $('#userIds').selectpicker('val', arr);
						vm.modelTitle = $translate.instant('alarmLevelType.alarmLevelTypeEdit');
						vm.readonlyID = true;
						vm.disabledID = true;
		        		bean.alarmTypeCode =arr;
		        		vm.bean = bean;
		        		vm.statusCode="";
		        		vm.statusMessage="";
			       },
				function(errResponse){
					console.error('Error while fetching appGateways');
				}
	       );
	}

	function submit() {
		// if (vm.bean.alarmLevelTypeCode == null) {
		if (!vm.readonlyID) {
			AlarmLevelTypeService.createAlarmLevelType(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmLevelType.');
					});
			vm.reset();
		} else {
			AlarmLevelTypeService.updateAlarmLevelType(vm.bean, vm.bean.alarmLevelCode,vm.bean.alarmTypeCode).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmLevelType.');
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
					AlarmLevelTypeService.deleteAlarmLevelType(bean.alarmLevelCode,bean.alarmTypeCode).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmLevelType.');
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
		var alarmLevelCode = vm.beanSer.alarmLevelCode;
		var alarmTypeCode = vm.beanSer.alarmTypeCode;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/alarmLevelType?id=' + getValueForSelect(alarmLevelCode)+
				"&alarmTypeCode="+getValueForSelect(alarmTypeCode)
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