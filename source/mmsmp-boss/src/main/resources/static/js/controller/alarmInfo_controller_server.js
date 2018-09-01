'use strict';
var App = angular.module('alarmInfoModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);


$(function() {
	$("#alarmTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#handleTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#alarmTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#alarmTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#handleTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#handleTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});





function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, AlarmInfoService,localStorageService,topleftService) {
	var vm = this;
	//批量start
    vm.selected = {};
    vm.selectAll = false;
    vm.toggleAll = toggleAll;
    vm.toggleOne = toggleOne;
    var titleHtml = '<input type="checkbox" id="checkbox_all_id" style="margin-left:-7px" ng-model="ctrl.selectAll" ng-click="ctrl.toggleAll(ctrl.selectAll, ctrl.selected)">';
    //批量删除按钮
    var titleHtmlDel = 'Actions  '+'<button class="btn btn-primary btn-sm table-tool-left" translate="Activation" ng-click="ctrl.test()"></button>';
	//批量end
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
	vm.alarmLevelData =[];
	vm.alarmOriginData =[];
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/alarmInfo')).withOption(
			'createdRow', createdRow)
			.withOption('headerCallback', function(header) {
            if (!vm.headerCompiled) {
                // Use this headerCompiled field to only compile header once
                vm.headerCompiled = true;
                $compile(angular.element(header).contents())($scope);
            }
        })
        .withPaginationType('full_numbers');;

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('alarmInfoCode').withTitle("<input type='checkbox' id='checkbox_all_id'>").notSortable()
        .renderWith(function(data, type, full, meta) {
            vm.selected[data] = false;
            return '<input type="checkbox" ng-model="ctrl.selected[\''+ data + '\']" ng-click="ctrl.toggleOne(ctrl.selected)">';
        }),
		DTColumnBuilder.newColumn('alarmInfoCode').withTitle(
				$translate('alarmInfo.alarmInfoCode')).notVisible().notSortable(),
		DTColumnBuilder.newColumn('alarmLevelName').withTitle($translate('common.alarmLevel')).notSortable(),
		DTColumnBuilder.newColumn('alarmTime').withTitle($translate('alarmInfo.alarmTime')).renderWith(timeRender).notSortable(),
		//DTColumnBuilder.newColumn('alarmOrigin').withTitle($translate('alarmInfo.alarmOrigin')).notSortable(),
		DTColumnBuilder.newColumn('alarmOriginName').withTitle($translate('alarmInfo.alarmOriginName')).notSortable().renderWith(valueAll),
		DTColumnBuilder.newColumn('content').notVisible().withTitle($translate('alarmInfo.content')).notSortable(),
		DTColumnBuilder.newColumn('handle').notVisible().withTitle($translate('alarmInfo.handle')).notSortable(),
		DTColumnBuilder.newColumn('handleTime').withTitle($translate('alarmInfo.handleTime')).renderWith(timeRender).notSortable(),
		DTColumnBuilder.newColumn('status').withTitle($translate('alarmInfo.status')).notSortable().renderWith(statusType),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width','10%').notSortable()
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
	vm.alarmHandle = alarmHandle;
	vm.test = test;
	vm.handleViews = handleViews;
	vm.selFun = selFun;
	selFun();
	vm.selallFun = selallFun;
	selallFun();
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.alarmInfoCode] = data;
		addOrChangeTableCheckboxAll();
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
		+ data.alarmInfoCode
		+ '\'])">'
		+ '   <i class="fa fa-edit"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
		+ data.alarmInfoCode
		+ '\'])">'
		+ '   <i class="fa fa-trash-o"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-info " title="'+$translate.instant('common.handle')+'" ng-click="ctrl.alarmHandle(ctrl.beans[\''
		+ data.alarmInfoCode
		+ '\'])">'
		+ '   <i class="fa fa-file-text-o"></i>'
		+ '</button>';
	}
	//title
	function valueAll(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFunValue" title="'+full.content.replace(/<br\/>/g,"\r\n")+'" >'+data+'</span>'
		}else{
			return '';
		}
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
	
	
	function statusType(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.unHandle');
		}else if(data=='2'){
			return $translate.instant('common.handled');
		}else{
			return '';
		}
	}
	
	
	function selFun() {
		AlarmInfoService.selAlarmLevel()
        .then(
				       function(d) {
					        vm.alarmLevelData = d.body;
		                  //console.log('selAlarmLevel', vm.alarmLevelData);    
				       },
  					function(errResponse){
  						console.error('Error while fetching selAlarmLevel');
  					}
		       );
	}
	
	function selallFun() {
		AlarmInfoService.selAlarmOrigin()
        .then(
				       function(a) {
					        vm.alarmOriginData = a.body;
		                  //console.log('selAlarmOrigin', vm.alarmOriginData);   
				       },
  					function(errResponse){
  						console.error('Error while fetching selAlarmOrigin');
  					}
		       );
	}
	//alarmOrigin下拉框
	function selectBank(){
		$("#selectCouponFun").select2();
		$("#alarmOrigin").select2();
    	//解决 select2在模态框使用，模糊输入框无效
  
	}
	//初始select2
	$("#queryBtn").click(function(){
		selectBank();
	})
	
	function handleViews(){
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
			title : $translate.instant('common.handleOpinions'),
			message: function(dialog) {
				var $message= $("<span>"+$translate.instant('common.handle.opinions')+"<br/></span>" +"<textarea type='text' id='handleView' style='height:120px'  class='form-control' id='remark' name='remark' maxlength='1000'></textarea>");
                return $message;
            },
            
            
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					var handleView=document.getElementById("handleView").value;
					
					vm.timeActivateBean={};
					vm.timeActivateBean.handle=getValueForSelect(handleView);
					
				    AlarmInfoService.alarmHandleView(vm.timeActivateBean, temp).then(onSubmitSuccess,
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
			  alert($translate.instant('common.select'));
			  return;
		  }
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					AlarmInfoService.deleteSysLogMany(temp).then(reloadData,
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
					//location.reload();
				}
			} ]
		});
		
	}
	
	function addInit() {
		vm.modelTitle = $translate.instant('alarmInfo.alarmInfoAdd');
		vm.readonlyID = false;
		vm.bean = {};
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		bean.content=bean.content.replace(/<br\/>/g,"\r\n") ;
		reloadData();
		bean.alarmTime=timeFormatOld(bean.alarmTime);
		bean.handleTime=timeFormatOld(bean.handleTime);
		vm.statusData = [
			{value:1,text:$translate.instant('common.unHandle')},
			{value:2,text:$translate.instant('common.handled')}];
		
		vm.modelTitle = $translate.instant('alarmInfo.alarmInfoEdit');
		vm.readonlyID = true;
		vm.bean = bean;
		$("#alarmOrigin").val(vm.bean.alarmOrigin).select2();
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		// if (vm.bean.alarmInfoCode == null) {
		if (!vm.readonlyID) {
			AlarmInfoService.createAlarmInfo(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmInfo.');
					});
			vm.reset();
		} else {
			vm.bean.alarmTime=timeFormatNew($("#alarmTime").val());
			vm.bean.handleTime=timeFormatNew($("#handleTime").val());
			vm.bean.content=vm.bean.content.replace(/\r\n/g, '<br/>');
			AlarmInfoService.updateAlarmInfo(vm.bean, vm.bean.alarmInfoCode).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmInfo.');
					});
		}
	}

	function onSubmitSuccess(data){
		if(data.body){
			vm.bean.alarmTime=timeFormatOld(data.body.alarmTime);
			vm.bean.handleTime=timeFormatOld(data.body.handleTime);
			vm.bean.content=data.body.content.replace(/<br\/>/g,"\r\n");
		}
		vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
		alarmNew(vm, localStorageService, topleftService);
	}

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					AlarmInfoService.deleteAlarmInfo(bean.alarmInfoCode).then(reloadData,
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
	
	
	//告警处理意见start
	function alarmHandle(bean) {
		if(bean.status=="1"){
		BootstrapDialog.show({
			title : $translate.instant('common.handleOpinions'),
			message: function(dialog) {
				var $message= $("<span>"+$translate.instant('alarmInfo.alarmInfoCode')+":"+bean.alarmInfoCode+"<br/></span>" +
						"<span>"+$translate.instant('common.alarmLevel')+":"+bean.alarmLevelName+"<br/></span>" +
						"<span>"+$translate.instant('alarmInfo.alarmTime')+":"+timeFormatOld(bean.alarmTime)+"<br/></span>" +
						"<span>"+$translate.instant('common.handle.opinions')+"<br/></span>" +
						"<textarea type='text' id='handleView' style='height:120px'   class='form-control' id='remark' name='remark' maxlength='1000'></textarea>");
			
                return $message;
            },
            
            
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					var handleView=document.getElementById("handleView").value;
					
					vm.timeActivateBean={};
					vm.timeActivateBean.handle=getValueForSelect(handleView);
					
				    AlarmInfoService.alarmHandleView(vm.timeActivateBean, bean.alarmInfoCode).then(onSubmitSuccess,
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
		}else if(bean.status=="2"){
			BootstrapDialog.show({
				title : $translate.instant('common.handleOpinions'),
				message: function(dialog) {
					var $message= $("<span>"+$translate.instant('alarmInfo.alarmInfoCode')+":"+bean.alarmInfoCode+"<br/></span>" +
							"<span>"+$translate.instant('common.alarmLevel')+":"+bean.alarmLevelName+"<br/></span>" +
							"<span>"+$translate.instant('alarmInfo.alarmTime')+":"+timeFormatOld(bean.alarmTime)+"<br/></span>" +
							"<span>"+$translate.instant('alarmInfo.handleTime') +":"+timeFormatOld(bean.handleTime)+"<br/></span>" +
							"<span>"+$translate.instant('common.handle.opinions')+"<br/></span>" +
							"<textarea type='text' id='handleView' style='height:120px'   class='form-control' id='remark' name='remark' maxlength='1000'>" +bean.handle+
								"</textarea>");
	                return $message;
	            },
	            
	            
				buttons : [ {
					label : $translate.instant('common.yes'),
					cssClass : 'btn btn-danger model-tool-right',
					action : function(dialogItself) {
						var handleView=document.getElementById("handleView").value;
						
						vm.timeActivateBean={};
						vm.timeActivateBean.handle=getValueForSelect(handleView);
						
					    AlarmInfoService.alarmHandleView(vm.timeActivateBean, bean.alarmInfoCode).then(onSubmitSuccess,
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
	}
	
	//告警处理意见end
	
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
    	//告警不一致
    	alarmNew(vm, localStorageService, topleftService);
    	
    	$("#bth-searchDate").attr("disabled",true);
		 //解决查询后保持列的显示start
		var columuFinalStatus = vm.columnStatusData;
		if(columuFinalStatus.length>0){
		  for(var i = 0; i < columuFinalStatus.length; i++){
			  vm.dtColumns[i].bVisible = columuFinalStatus[i].bVisible;
			  }
		}
		//解决查询后保持列的显示end
		var alarmInfoCode = vm.beanSer.alarmInfoCode;
		var alarmLevelCode = vm.beanSer.alarmLevelCode;
		var alarmOrigin = vm.beanSer.alarmOrigin;
		var status = vm.beanSer.status;
		var alarmTimeStart = timeFormatNew(vm.beanSer.alarmTimeStart);
		var alarmTimeEnd = timeFormatNew(vm.beanSer.alarmTimeEnd);
		var handleTimeStart = timeFormatNew(vm.beanSer.handleTimeStart);
		var handleTimeEnd = timeFormatNew(vm.beanSer.handleTimeEnd);
	/*	var rurl ="";
		if(alarmInfoCode!=null&&alarmInfoCode!=""&&alarmInfoCode!=undefined){
			rurl = rurl + "id="+alarmInfoCode+"&"
		}
		if(alarmLevelCode!=null&&alarmLevelCode!=""&&alarmLevelCode!=undefined){
			rurl = rurl + "alarmLevelCode="+alarmLevelCode+"&"
		}
		if(alarmOrigin!=null&&alarmOrigin!=""&&alarmOrigin!=undefined){
			rurl = rurl + "alarmOrigin="+alarmOrigin+"&"
		}
		if(status!=null&&status!=""&&status!=undefined){
			rurl = rurl + "status="+status+"&"
		}
		if(alarmTimeStart!=null&&alarmTimeStart!=""&&alarmTimeStart!=undefined){
			rurl = rurl + "alarmTimeStart="+alarmTimeStart+"&"
		}
		if(alarmTimeEnd!=null&&alarmTimeEnd!=""&&alarmTimeEnd!=undefined){
			rurl = rurl + "alarmTimeEnd="+alarmTimeEnd+"&"
		}
		if(handleTimeStart!=null&&handleTimeStart!=""&&handleTimeStart!=undefined){
			rurl = rurl + "handleTimeStart="+handleTimeStart+"&"
		}
		if(handleTimeEnd!=null&&handleTimeEnd!=""&&handleTimeEnd!=undefined){
			rurl = rurl + "handleTimeEnd="+handleTimeEnd+"&"
		}
		vm.dtInstance.changeData(getFromSource(apiUrl + '/alarmInfo?' +rurl));*/
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/alarmInfo?id=' + getValueForSelect(alarmInfoCode)+
				"&alarmLevelCode="+getValueForSelect(alarmLevelCode)+"&alarmOrigin="+getValueForSelect(alarmOrigin)+getValueForSelect(status,"&status=")+
				"&alarmTimeStart="+getValueForSelect(alarmTimeStart)+"&alarmTimeEnd="+getValueForSelect(alarmTimeEnd)+
				"&handleTimeStart="+getValueForSelect(handleTimeStart)+
				"&handleTimeEnd="+getValueForSelect(handleTimeEnd)));
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