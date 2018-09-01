'use strict';
var App = angular.module('myModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

//自定义格式化日期格式指令(date-format) start
//用法：<input type="text" class="form-control" id="billDate" placeholder="日期" ng-model="ctrl.bean.billDate" date-format>
App.directive('dateFormat', ['$filter',function($filter) {  
var dateFilter = $filter('date');  
return {  
    require: 'ngModel',  
    link: function(scope, elm, attrs, ctrl) {  

        function formatter(value) {  
            return dateFilter(value, timeFormat()); //format  
        } 
        ctrl.$formatters.push(formatter);  

    }  
};  
}]);
//自定义格式化日期格式指令(date-format) end


$(function() {
	$("#loginTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#loginTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#loginTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, loginHisService,localStorageService,topleftService) {
	
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
	vm.beanSer.loginTimeStart=timeFormatOld(weekTime);
	vm.beanSer.loginTimeEnd=timeFormatOld(todayTime);
	
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/sysUserLoginHis?loginTimeStart=' + getValueForSelect(weekTime)+"&loginTimeEnd="+getValueForSelect(todayTime))).withOption(
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
        DTColumnBuilder.newColumn('consumerUserLoginHisId').withTitle("<input type='checkbox' id='checkbox_all_id'>").notSortable()
        .renderWith(function(data, type, full, meta) {
            vm.selected[data] = false;
            return '<input type="checkbox" ng-model="ctrl.selected[\''+ data + '\']" ng-click="ctrl.toggleOne(ctrl.selected)">';
        }),
			DTColumnBuilder.newColumn('consumerUserLoginHisId').withTitle($translate('sysUserLoginHis.UserLoginHisId')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('userName').withTitle($translate('user.userName')).notSortable(),
			DTColumnBuilder.newColumn('ipAddr').withTitle($translate('clapUseStore.ipAddr')).notSortable(),
			DTColumnBuilder.newColumn('loginTime').withTitle($translate('clapUseStore.loginTime')).notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('bookkeepking.remark'))
			.notSortable().renderWith(remarkDetail),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
			.renderWith(actionsHtml) 
					];

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
    
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	//超长备注处理end
	
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
	
	
	function dateRender(data, type, full, meta) {
		if(data==null){
			return '';
		}else{
		    return  moment(data).format("YYYY-MM-DD"); 
		}
	}
	
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
	
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.consumerUserLoginHisId] = data;
		addOrChangeTableCheckboxAll();
		return  '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.consumerUserLoginHisId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}
     

	function addInit() {
		vm.modelTitle = $translate.instant('Manageuserloginhis.add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.statusCode="";
		vm.statusMessage="";
		vm.HisIdShow=true;
		
		
	}
	function edit(bean) {
		vm.modelTitle = $translate.instant('Manageuserloginhis.edit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
		vm.HisIdShow=false;
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
	
/*	function dateRender(data, type, full, meta) {
		return  moment(data).format("YYYY-MM-DD"); 
	}
	
	function timeRender(data, type, full, meta) {
		return  moment(data).format("YYYY-MM-DD HH:mm:ss"); 
	}*/


	function submit() {
		// if (vm.bean.userId == null) {
		if (!vm.readonlyID) {
			loginHisService.create(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating UserClap.');
					});
			vm.reset();
		} else {
			loginHisService.update(vm.bean, vm.bean.consumerUserLoginHisId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating UserClap.');
					});
		}
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
					loginHisService.deleteSysLogMany(temp).then(reloadData,
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
					loginHisService.deletes(bean.consumerUserLoginHisId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating UserClap.');
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
		var consumerUserLoginHisId = vm.beanSer.consumerUserLoginHisId;
		var userId = vm.beanSer.userId;
		var userName = encodeURI(encodeURI(vm.beanSer.userName));
		var loginTimeStart = timeFormatNew(vm.beanSer.loginTimeStart);
		var loginTimeEnd = timeFormatNew(vm.beanSer.loginTimeEnd);
	/*	var rurl = "";
		if(consumerUserLoginHisId!=null&&consumerUserLoginHisId!=""&&consumerUserLoginHisId!=undefined){
			rurl = rurl + "consumerUserLoginHisId="+consumerUserLoginHisId+"&"
		}
		if(userId!=null&&userId!=""&&userId!=undefined){
			rurl = rurl + "userId="+userId+"&"
		}
		if(ipAddr!=null&&ipAddr!=""&&ipAddr!=undefined){
			rurl = rurl + "ipAddr="+ipAddr+"&"
		}
		if(loginTimeStart!=null&&loginTimeStart!=""&&loginTimeStart!=undefined){
			rurl = rurl + "loginTimeStart="+loginTimeStart+"&"
		}
		if(loginTimeEnd!=null&&loginTimeEnd!=""&&loginTimeEnd!=undefined){
			rurl = rurl + "loginTimeEnd="+loginTimeEnd+"&"
		}
		vm.dtInstance.changeData(getFromSource(apiUrl + '/sysUserLoginHis?' +rurl));*/
		vm.dtInstance.changeData(getFromSource(apiUrl + '/sysUserLoginHis?consumerUserLoginHisId=' + getValueForSelect(consumerUserLoginHisId)+"&userId="+getValueForSelect(userId)+
				"&userName="+getValueForSelect(userName)+"&loginTimeStart="+getValueForSelect(loginTimeStart)+
				"&loginTimeEnd="+getValueForSelect(loginTimeEnd)));
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