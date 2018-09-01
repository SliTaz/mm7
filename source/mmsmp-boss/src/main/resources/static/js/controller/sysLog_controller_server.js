'use strict';
var App = angular.module('sysLogModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize','LocalStorageModule', 'ui.tree' ]);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

$(function() {
	$("#operateTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#operateTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SysLogService,localStorageService,topleftService) {
	var vm = this;
	//批量start
    vm.selected = {};
    vm.selectAll = false;
    vm.toggleAll = toggleAll;
    vm.toggleOne = toggleOne;
    var titleHtml = '<input type="checkbox" id="checkbox_all_id" style="margin-left:-7px" ng-model="ctrl.selectAll" ng-click="ctrl.toggleAll(ctrl.selectAll, ctrl.selected)">';
    //批量删除按钮
    var titleHtmlDel = 'Actions  '+'<button class="btn btn-primary btn-sm table-tool-left" translate="Delete All" ng-click="ctrl.test()"></button>';
	//批量end
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
    var date = new Date();
    date.setDate(date.getDate());
	var todayTime = getBeforeDayFormatDate(date);
	
    var weekDate = new Date();
    weekDate.setDate(weekDate.getDate()-7);
	var weekTime = getBeforeDayFormatDate(weekDate);
	vm.beanSer.operateTimeStart=timeFormatOld(weekTime);
	vm.beanSer.operateTimeEnd=timeFormatOld(todayTime);
	
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/sysLog?operateTimeStart=' + getValueForSelect(weekTime)+"&operateTimeEnd="+getValueForSelect(todayTime))).withOption(
			'createdRow', createdRow)
			.withOption('headerCallback', function(header) {
            if (!vm.headerCompiled) {
                // Use this headerCompiled field to only compile header once
                vm.headerCompiled = true;
                $compile(angular.element(header).contents())($scope);
            }
        })
        .withPaginationType('full_numbers');

	setDtOptionsServerSide(vm);


	vm.dtColumns = [
	        DTColumnBuilder.newColumn('sysLogId').withTitle("<input type='checkbox' id='checkbox_all_id'>").notSortable()
	        .renderWith(function(data, type, full, meta) {
	            vm.selected[data] = false;
	            return '<input type="checkbox" ng-model="ctrl.selected[\''+ data + '\']" ng-click="ctrl.toggleOne(ctrl.selected)">';
	        }),
			DTColumnBuilder.newColumn('sysLogId').withTitle(
					$translate('sysLog.sysLogId')).notSortable(),
			DTColumnBuilder.newColumn('operTime').withTitle($translate('sysLog.operTime')).notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('operUser').withTitle($translate('sysLog.operUser')).notSortable(),
			DTColumnBuilder.newColumn('operModel').withTitle($translate('sysLog.operModel')).notSortable().renderWith(modelTypes),
			DTColumnBuilder.newColumn('operType').withTitle($translate('sysLog.operType')).notSortable().renderWith(types),
			DTColumnBuilder.newColumn('operContent').withTitle($translate('sysLog.operContent')).notVisible().notSortable().withOption('width', '40%').renderWith(sysContent),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width', '11%').notSortable()
					.renderWith(actionsHtml) ];
	
	
	//---批量start
    //多选
    function toggleAll (selectAll, selectedItems) {
        for (var id in selectedItems) {
            if (selectedItems.hasOwnProperty(id)) {
                selectedItems[id] = selectAll;
            }
        }
        
    }
  
    //单选
    function toggleOne (selectedItems) {
        for (var id in selectedItems) {
            if (selectedItems.hasOwnProperty(id)) {
                if(!selectedItems[id]) {
                    vm.selectAll = false;
                    return;
                }
            }
        }
        vm.selectAll = true;
    }
	//---批量end

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.test = test;
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	function timeRender(data, type, full, meta) {
		if(data==null){
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
	function types(data, type, full, meta){
		if(data=='1'){
			return '<span>'+$translate.instant('common.add')+'</span>';
		}else if(data=='2'){
			return '<span>'+$translate.instant('common.edit')+'</span>';
		}else if(data=='3'){
			return '<span>'+$translate.instant('common.delete')+'</span>';
		}else if(data=='4'){
			return '<span>'+$translate.instant('common.activate')+'</span>';
		}else if(data=='5'){
			return '<span>'+$translate.instant('common.inactivate')+'</span>';
		}else if(data=='6'){
			return '<span>'+$translate.instant('common.islocked')+'</span>';
		}else if(data=='7'){
			return '<span>'+$translate.instant('common.unlocked')+'</span>';
		}else if(data=='8'){
			return '<span>'+$translate.instant('common.resetPwd')+'</span>';
		}else if(data=='9'){
			return '<span>'+$translate.instant('common.resetPayPwd')+'</span>';
		}else if(data=='10'){
			return '<span>'+$translate.instant('common.import')+'</span>';
		}else if(data=='11'){
			return '<span>'+$translate.instant('common.publish')+'</span>';
		}else if(data=='12'){
			return '<span>'+$translate.instant('common.revoked')+'</span>';
		}else if(data=='13'){
			return '<span>'+$translate.instant('common.executed')+'</span>';
		}else if(data=='14'){
			return '<span>'+$translate.instant('common.unexecuted')+'</span>';
		}else if(data=='15'){
			return '<span>'+$translate.instant('common.success')+'</span>';
		}else if(data=='16'){
			return '<span>'+$translate.instant('common.failure')+'</span>';
		}else if(data=='17'){
			return '<span>'+$translate.instant('common.paused')+'</span>';
		}else if(data=='18'){
			return '<span>'+$translate.instant('common.normal')+'</span>';
		}else{
			return '';
		}
	}
	
	function modelTypes(data, type, full, meta){
		if(data=='1'){
			return '<span>'+$translate.instant('common.consumer')+'</span>';
		}else if(data=='2'){
			return '<span>'+$translate.instant('common.merchant')+'</span>';
		}else if(data=='3'){
			return '<span>'+$translate.instant('common.finance')+'</span>';
		}else if(data=='4'){
			return '<span>'+$translate.instant('common.accounting')+'</span>';
		}else if(data=='5'){
			return '<span>'+$translate.instant('common.payments')+'</span>';
		}else if(data=='6'){
			return '<span>'+$translate.instant('common.reconciliation')+'</span>';
		}else if(data=='7'){
			return '<span>'+$translate.instant('common.govUser')+'</span>';
		}else if(data=='8'){
			return '<span>'+$translate.instant('common.couponManage')+'</span>';
		}else if(data=='9'){
			return '<span>'+$translate.instant('common.manageUser')+'</span>';
		}else if(data=='10'){
			return '<span>'+$translate.instant('common.fraultManagement')+'</span>';
		}else if(data=='11'){
			return '<span>'+$translate.instant('common.task')+'</span>';
		}else if(data=='12'){
			return '<span>'+$translate.instant('common.biReport')+'</span>';
		}else if(data=='13'){
			return '<span>'+$translate.instant('common.alarm')+'</span>';
		}else if(data=='14'){
			return '<span>'+$translate.instant('common.appUpdate')+'</span>';
		}else if(data=='15'){
			return '<span>'+$translate.instant('common.notice')+'</span>';
		}else if(data=='16'){
			return '<span>'+$translate.instant('common.systemManage')+'</span>';
		}else{
			return '';
		}
	}
	
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.sysLogId] = data;
		
		addOrChangeTableCheckboxAll();
		
		return  '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.sysLogId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}
	
	
	function sysContent(data, type, full, meta){
		return '<pre class="sysLogValue" style="word-wrap:break-word;white-space: pre-wrap;">'+data+'</pre>'
	}

	function addInit() {
		vm.modelTitle = $translate.instant('sysLog.sysLogAdd');
		vm.readonlyID = false;
		vm.bean = {};
		vm.statusMessage="";
		vm.statusCode="";
	}
	function edit(bean) {
		vm.modelTitle = $translate.instant('sysLog.sysLogEdit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusMessage="";
		vm.statusCode="";
	}

	function submit() {
		// if (vm.bean.sysLogId == null) {
		if (!vm.readonlyID) {
			SysLogService.createSysLog(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating SysLog.');
					});
			vm.reset();
		} else {
			SysLogService.updateSysLog(vm.bean, vm.bean.sysLogId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating SysLog.');
					});
		}
	}
	
	function onSubmitSuccess(data){
		
		vm.statusMessage=data.statusMessage;
		vm.statusCode=data.statusCode;
		reloadData();
	}
	
	//查询一周前时间start
    //获取日期，格式yyyy—mm—dd HH:mm:ss
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
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
 		            + " " + h + seperator2 + m
	            + seperator2 + s; 
	    return currentdate;
	}
	//查询一周前时间end
	
	//批量删除
	function test() {
			var obj = vm.selected;
			var temp = ""; 
			for(var i in obj){
				if(obj[i]==true){   
					temp += i+","; 
				}
	
			} 
		  if(temp==''){
			  alert($translate.instant('common.select.message'));
			  return;
		  }
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SysLogService.deleteSysLogMany(temp).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating SysLog.');
			
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
	
	

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SysLogService.deleteSysLog(bean.sysLogId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating SysLog.');
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
	
	$("#example").on("dblclick", ".sysLogValue", function(){
		var syslogStr = $(this).text();
		var result = syntaxHighlight(JSON.parse(syslogStr));
		layer.open({
		  type: 1,
		  title: $translate.instant('common.operateContent'),
		  offset: '10px',
		  area: ['600px', '600px'], //宽高
		  content: '<pre>'+result+'</pre>'
		});
	});
	
	function syntaxHighlight(json) {
	    if (typeof json != 'string') {
	        json = JSON.stringify(json, undefined, 2);
	    }
	    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
	    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
	        var cls = 'number';
	        if (/^"/.test(match)) {
	            if (/:$/.test(match)) {
	                cls = 'key';
	            } else {
	                cls = 'string';
	            }
	        } else if (/true|false/.test(match)) {
	            cls = 'boolean';
	        } else if (/null/.test(match)) {
	            cls = 'null';
	        }
	        return '<span class="' + cls + '">' + match + '</span>';
	    });
	}
	
	
	//解决批量删除本页全部记录,会把之前访问页面的记录也删除start
	$('#table_id').on( 'page.dt', function () {
	    //var info =  $('#table_id').DataTable().page.info();
		vm.selected={};
	} );
	//解决批量删除本页全部记录,会把之前访问页面的记录也删除end
	
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
		var sysLogId = vm.beanSer.sysLogId;
		var operUser = encodeURI(encodeURI(vm.beanSer.operUser));
		var operModel = vm.beanSer.operModel;
		var operType = vm.beanSer.operType;
		var operateTimeStart = timeFormatNew(vm.beanSer.operateTimeStart);
		var operateTimeEnd = timeFormatNew(vm.beanSer.operateTimeEnd);
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/sysLog?id=' + getValueForSelect(sysLogId)
				+"&operUser="+getValueForSelect(operUser)+getValueForSelect(operModel,"&operModel=")+getValueForSelect(operType,"&operType=")
				+"&operateTimeStart="+getValueForSelect(operateTimeStart)+"&operateTimeEnd="+getValueForSelect(operateTimeEnd)
				));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
		//console.log(json);
		addOrChangeTableCheckboxAll();
	}
	
	function addOrChangeTableCheckboxAll(){
		//批量删除 start
		vm.selectAll = false;
		var $html = $compile(titleHtml)($scope); 
		var obj_p=$("#checkbox_all_id").parent();
		$("#checkbox_all_id").remove();
		obj_p.append($html);
		//批量删除 end
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}