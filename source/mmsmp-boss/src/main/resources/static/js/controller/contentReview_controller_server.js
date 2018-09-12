'use strict';
var App = angular.module('contentInfoModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(function() {
	$("#startTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#endTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
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
});


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, ContentInfoService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
    vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/contentInfo?status=1')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('contentInfoId').withTitle($translate('contentInfo.contentInfoId')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('contentName').withTitle($translate('contentInfo.contentName')).notSortable(),
		DTColumnBuilder.newColumn('productInfoId').withTitle($translate('contentInfo.productInfoId')).notSortable(),
		DTColumnBuilder.newColumn('cpName').withTitle($translate('contentInfo.cpInfo')).notSortable(),
		DTColumnBuilder.newColumn('status').withTitle($translate('contentInfo.status')).notSortable().renderWith(statusType),
		DTColumnBuilder.newColumn('author').withTitle($translate('contentInfo.author')).notSortable(),
		DTColumnBuilder.newColumn('aduitUser').withTitle($translate('contentInfo.aduitUser')).notSortable(),
		DTColumnBuilder.newColumn('createTime').withTitle($translate('contentInfo.createTime')).notSortable().renderWith(timeRender),
		DTColumnBuilder.newColumn('lastAuditTime').withTitle($translate('contentInfo.lastAuditTime')).notSortable().renderWith(timeRender),
		DTColumnBuilder.newColumn('contentCode').withTitle($translate('contentInfo.contentCode')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('contentPath').withTitle($translate('contentInfo.contentPath')).notSortable().notVisible().renderWith(remarkDetail),
		DTColumnBuilder.newColumn('contentType').withTitle($translate('contentInfo.contentType')).notSortable().renderWith(contentTypeDetail).notVisible(),
		DTColumnBuilder.newColumn('keyWord').withTitle($translate('contentInfo.keyWord')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('isRealtime').withTitle($translate('contentInfo.isRealtime')).notSortable().renderWith(isRealtimeDetail).notVisible(),
		DTColumnBuilder.newColumn('realtimeContentUrl').withTitle($translate('contentInfo.realtimeContentUrl')).notSortable().notVisible().renderWith(remarkDetail),
		DTColumnBuilder.newColumn('contentRemark').withTitle($translate('contentInfo.contentRemark')).notSortable().notVisible().renderWith(remarkDetail),
		DTColumnBuilder.newColumn('rejectReason').withTitle($translate('contentInfo.rejectReason')).notSortable().notVisible().renderWith(remarkDetail),
		DTColumnBuilder.newColumn('isApplyDelete').withTitle($translate('contentInfo.isApplyDelete')).notSortable().notVisible().renderWith(isRealtimeDetail),
		DTColumnBuilder.newColumn('webPic').withTitle($translate('contentInfo.webPic')).notSortable().notVisible().renderWith(remarkDetail),
		DTColumnBuilder.newColumn('startTime').withTitle($translate('contentInfo.startTime')).renderWith(timeRender).notSortable().notVisible(),
		DTColumnBuilder.newColumn('endTime').withTitle($translate('contentInfo.endTime')).renderWith(timeRender).notSortable().notVisible(),
		DTColumnBuilder.newColumn('singer').withTitle($translate('contentInfo.singer')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('featureStr').withTitle($translate('contentInfo.featureStr')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('isSend').withTitle($translate('contentInfo.isSend')).notSortable().renderWith(isSendDetail).notVisible(),
		DTColumnBuilder.newColumn('versionId').withTitle($translate('contentInfo.versionId')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('smsText').withTitle($translate('contentInfo.smsText')).notSortable().notVisible().renderWith(remarkDetail),
		DTColumnBuilder.newColumn('setSendTimeUser').withTitle($translate('contentInfo.setSendTimeUser')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('adSendId').withTitle($translate('contentInfo.adSendId')).notSortable().notVisible(),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
				.renderWith(actionsHtml) ];

//	vm.addInit = addInit;
	vm.edit = edit;
//	vm.submit = submit;
//	vm.deleteBean = deleteBean;
	vm.submitBean = submitBean;
	vm.rejectBean = rejectBean;
	vm.statusType = statusType;
	vm.selData = selData;
	selData();
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.contentInfoId] = data;
		var actionsHtml_html = '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.contentInfoId
				+ '\'])">'
				+ '   <i class="fa fa-search"></i>'
				+ '</button>&nbsp;'
				+ '<div class="btn-group">'
				+ '<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown">'
				+ ' <span class="caret"></span>'
		        +'</button>'
		        + '<ul class="dropdown-menu" style="padding:0px;margin-left:-110px;min-width:10px">'
		     	+ '<li><button class="btn btn-success" style="width:150px" ng-click="ctrl.submitBean(ctrl.beans[\''+data.contentInfoId+'\'])">'+$translate.instant('common.submit')+'</button></li>'
				+ '<li><button class="btn bg-maroon" style="width:150px"   ng-click="ctrl.rejectBean(ctrl.beans[\''+data.contentInfoId+'\'])">'+$translate.instant('common.reject')+'</button></li>'
		        + '</ul>'
		        + '</div>';
				/*+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.contentInfoId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>'*/;
				$(".dataTables_scrollBody").attr("style","position:relative;max-height:100%;width:100%");
				changeDataForHTML();//通过调用该函数。让拼装的html代码生效
				return actionsHtml_html;
	}
	
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
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
		if (data == '0') {
			return $translate.instant('contentInfo.draft');
		} else if (data == '1') {
			return $translate.instant('contentInfo.contentReview');
		}  else if (data == '2') {
			return $translate.instant('contentInfo.testReview');
		}  else if (data == '3') {
			return $translate.instant('contentInfo.sendReview');
		}  else if (data == '4') {
			return $translate.instant('contentInfo.release');
		} else {
			return '';
		}
	}
	
	function contentTypeDetail(data, type, full, meta){
		if (data == '0') {
			return $translate.instant('userServiceSend.MMS');
		} else if (data == '1') {
			return $translate.instant('common.image');
		}  else if (data == '2') {
			return $translate.instant('contentInfo.ring');
		}  else if (data == '3') {
			return $translate.instant('contentInfo.video');
		}  else if (data == '4') {
			return $translate.instant('contentInfo.text');
		} else if (data == '7') {
			return $translate.instant('contentInfo.colorE');
		} else {
			return '';
		}
	}
	
	function isRealtimeDetail(data, type, full, meta){
		if (data == '1') {
			return $translate.instant('common.ok');
		} else if (data == '2') {
			return $translate.instant('common.no');
		} else {
			return '';
		}
	}
	
	function isSendDetail(data, type, full, meta){
		if (data == '0') {
			return $translate.instant('contentInfo.unsent');
		} else if (data == '1') {
			return $translate.instant('contentInfo.sended');
		} else if (data == '2') {
			return $translate.instant('contentInfo.sending');
		} else {
			return '';
		}
	}
	
	function selData(){
		vm.beanSer.status = "1" ;
		ContentInfoService.selCpInfo().then(function(d) {
	        	vm.cpInfoData = d.body;
	       },
			function(errResponse){
				console.error('Error while fetching spInfo');
			}
		);
		
		ContentInfoService.selProductInfo().then(function(d) {
	        vm.productInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching productInfo');
		}
		);
	}
	
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#cpInfo").select2();
		$("#productInfoId").select2();
		$("#myModal").attr("tabindex","");
		//解决selec2在火狐模太框中输入框不能输入start
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end
	}
	
	$("#queryBtn").click(function(){
		selectDevice();
	}) 
	
	
//	function addInit() {
//		selectDevice();
//		$("#cpInfo2").val("").select2();
//		vm.modelTitle = $translate.instant('contentInfo.add');
//		vm.bean = {};
//		vm.readonlyID = false;
//		vm.statusCode="";
//		vm.statusMessage="";
//	}
	function edit(bean) {
		reloadData();
		selectDevice();
		$("#cpInfo2").val(bean.cpInfoId).select2();
		vm.modelTitle = $translate.instant('common.contentReview');
		vm.bean = bean;
		vm.readonlyID = true;
		vm.bean.status = vm.bean.status + "";
		vm.bean.contentType = vm.bean.contentType + "";
		vm.bean.isRealtime = vm.bean.isRealtime + "";
		vm.bean.isApplyDelete = vm.bean.isApplyDelete + "";
		vm.bean.isSend = vm.bean.isSend + "";
		vm.statusCode="";
		vm.statusMessage="";
	}
//	function submit() {
//		if (!vm.readonlyID) {
//			$.fn.dataTable.ext.errMode = 'none';
//			vm.bean.startTime = timeFormatNew(vm.bean.startTime);
//			vm.bean.endTime = timeFormatNew(vm.bean.endTime);
//			vm.bean.status = 1;
//			ContentInfoService.createContentInfo(vm.bean).then(onSubmitSuccess,
//					function(errResponse) {
// 						handleAjaxError(errResponse);
//						console.error('Error while creating AlarmLevel.');
//					});
//			vm.reset();
//		} else {
//			vm.bean.startTime = timeFormatNew(vm.bean.startTime);
//			vm.bean.endTime = timeFormatNew(vm.bean.endTime); 
//			vm.bean.status = 2;
//			vm.bean.aduitUser = vm.ltuserName;
//			ContentInfoService.updateContentInfo(vm.bean, vm.bean.contentInfoId).then(onSubmitSuccess,
//					function(errResponse) {
// 						handleAjaxError(errResponse);
//						console.error('Error while updating AlarmLevel.');
//					});
//		}
//	}

	function onSubmitSuccess(data){
		vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}

//	function deleteBean(bean) {
//		
//		BootstrapDialog.show({
//			title : $translate.instant('common.delete'),
//			message : $translate.instant('common.delete.message'),
//			buttons : [ {
//				label : $translate.instant('common.yes'),
//				cssClass : 'btn btn-danger model-tool-right',
//				action : function(dialogItself) {
//					ContentInfoService.deleteContentInfo(bean.contentInfoId).then(reloadData,
//					function(errResponse) {
// 						handleAjaxError(errResponse);
//						console.error('Error while updating AlarmInfo.');
//					});
//					dialogItself.close();
//				}
//
//			}, {
//				label : $translate.instant('common.cancel'),
//				cssClass : 'btn btn-default model-tool-left',
//				action : function(dialogItself) {
//					dialogItself.close();
//				}
//			} ]
//		});
//		
//	}
	
	function submitBean(bean) {
		BootstrapDialog.show({
			title : $translate.instant('common.submit'),
			message : $translate.instant('common.submit.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					bean.status = '2';
					ContentInfoService.updateContentInfo(bean, bean.contentInfoId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while submit contentMade.');
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
	
	function rejectBean(bean) {
		BootstrapDialog.show({
			title : $translate.instant('cpInfo.rejectReason'),
			message: function(dialog) {
				var $message=$(
						'<textArea placeholder="' + $translate.instant('cpInfo.rejectReason.input') +' " id="rejectReasonInput" style="word-break:normal; width:100%; height:120px;display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'+
					    '</textArea>'
				);
                return $message;
            },
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					bean.status = '0';
					bean.rejectReason = $("#rejectReasonInput").val();
					ContentInfoService.updateContentInfo(bean, bean.contentInfoId).then(reloadData,
					function(errResponse) {http://www.fontawesome.com.cn/icon/reply
 						handleAjaxError(errResponse);
						console.error('Error while revoke contentMade.');
					});
					
					//驳回弹出框
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
		var contentName = vm.beanSer.contentName;
		var status = vm.beanSer.status;
		var productInfoId = vm.beanSer.productInfoId;
		var cpInfoId = vm.beanSer.cpInfoId;
		if(vm.beanSer.createTimeStart >= vm.beanSer.createTimeEnd){
			alert($translate.instant('contentInfo.createTimeRange'));
			return;
		}
		var createTimeStart = timeFormatNew(vm.beanSer.createTimeStart);
		var createTimeEnd = timeFormatNew(vm.beanSer.createTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl + '/contentInfo?contentName=' + getValueForSelect(contentName) + "&productInfoId="+ getValueForSelect(productInfoId) +  getValueForSelect(1,"&status=")
				+ "&cpInfoId="+ getValueForSelect(cpInfoId) + "&createTimeStart="+ getValueForSelect(createTimeStart) + "&createTimeEnd="+ getValueForSelect(createTimeEnd)));
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