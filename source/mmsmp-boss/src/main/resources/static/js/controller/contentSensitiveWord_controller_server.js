'use strict';
var App = angular.module('contentSensitiveWordModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(function() {
	$("#createTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#createTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#updateTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#updateTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, ContentSensitiveWordService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
    vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/contentSensitiveWord')).withOption(
			'createdRow', createdRow);
	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('contentSensitiveWordId').withTitle($translate('contentSensitiveWord.contentSensitiveWordId')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('type').withTitle($translate('contentSensitiveWord.type')).notSortable().renderWith(typeDetail),
		DTColumnBuilder.newColumn('word').withTitle($translate('contentSensitiveWord.word')).notSortable(),
		DTColumnBuilder.newColumn('createrUser').withTitle($translate('contentSensitiveWord.createrUser')).notSortable(),
		DTColumnBuilder.newColumn('createTime').withTitle($translate('contentSensitiveWord.createTime')).notSortable().renderWith(timeRender),
		DTColumnBuilder.newColumn('updateTime').withTitle($translate('contentSensitiveWord.updateTime')).notSortable().notVisible().renderWith(timeRender),
		DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('alarmLevel.remark')).renderWith(remarkDetail).notSortable(),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
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
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.contentSensitiveWordId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.contentSensitiveWordId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.contentSensitiveWordId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
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
	
	
	function typeDetail(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('contentSensitiveWord.sensitiveWord');
		}else if(data=='2'){
			return $translate.instant('contentSensitiveWord.uncommonWords');
		}else{
			return '';
		}
	}
	
	function addInit() {
		vm.modelTitle = $translate.instant('contentSensitiveWord.add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('contentSensitiveWord.edit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.bean.type = vm.bean.type + "";
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.createrUser = vm.ltuserName;
		
			ContentSensitiveWordService.createContentSensitiveWord(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmLevel.');
					});
			vm.reset();
		} else {
			ContentSensitiveWordService.updateContentSensitiveWord(vm.bean, vm.bean.contentSensitiveWordId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmLevel.');
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
					ContentSensitiveWordService.deleteContentSensitiveWord(bean.contentSensitiveWordId).then(reloadData,
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
		var contentSensitiveWordId = vm.beanSer.contentSensitiveWordId;
		var word = vm.beanSer.word;
		var createrUser = vm.beanSer.createrUser;
		var type = vm.beanSer.type;
		if(vm.beanSer.createTimeStart >= vm.beanSer.createTimeEnd){
			alert($translate.instant('contentInfo.createTimeRange'));
			return;
		}
		var createTimeStart = timeFormatNew(vm.beanSer.createTimeStart);
		var createTimeEnd = timeFormatNew(vm.beanSer.createTimeEnd);
		var updateTimeStart = timeFormatNew(vm.beanSer.updateTimeStart);
		var updateTimeEnd = timeFormatNew(vm.beanSer.updateTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl + '/contentSensitiveWord?id=' + getValueForSelect(contentSensitiveWordId)+"&word="+getValueForSelect(word)
				+ "&createrUser="+getValueForSelect(createrUser) + "&type="+getValueForSelect(type) + "&createTimeStart="+getValueForSelect(createTimeStart) 
				+ "&createTimeEnd="+getValueForSelect(createTimeEnd) + "&updateTimeStart="+getValueForSelect(updateTimeStart) + "&updateTimeEnd="+getValueForSelect(updateTimeEnd)));
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