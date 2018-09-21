'use strict';
var App = angular.module('mmsTestModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize',
		'LocalStorageModule', 'ui.tree' ]);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(window).on('load', function() {
	$('#userIds').selectpicker({
		'selectedText' : 'cat'
	});
});

$(function() {
	$("#recvTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#recvTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#recvTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, MmsTestService, localStorageService,
		topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
	// 列的状态start
	vm.columnStatusData = [];
	// 列的状态end
	var pwdFirst;
	vm.reloadData = reloadData;
	vm.selectData = [];
	vm.appData = [];
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userRecv')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('userRecvId').withTitle($translate('userRecv.userRecvId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('phoneNumber').withTitle($translate('userRecv.phoneNumber')).notSortable(),
			DTColumnBuilder.newColumn('messageType').withTitle($translate('userRecv.messageType')).notSortable(),
			DTColumnBuilder.newColumn('isCorrect').withTitle($translate('userRecv.isCorrect')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('isOrder').withTitle($translate('userRecv.isOrder')).notSortable(),
			DTColumnBuilder.newColumn('recvTime').withTitle($translate('userRecv.recvTime')).notSortable(),
			DTColumnBuilder.newColumn('messageContent').withTitle($translate('userRecv.messageContent')).notSortable().notVisible(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width', '15%').notSortable().renderWith(actionsHtml) ];

	vm.submit = submit;
	vm.upload = upload;
	vm.VAR_MAX_FILE_SIZE_M=5;//5M
	// 表头start
	tableHandle();
	// 表头end
	vm.VAR_MAX_FILE_SIZE_M=1;//1M
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.userRecvId] = data;
		var actionsHtml_html = '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'
				+ $translate.instant('common.edit')
				+ '" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.userRecvId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'
				+ $translate.instant('common.delete')
				+ '" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.userRecvId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>' + '</button>&nbsp;'

		$(".dataTables_scrollBody").attr("style",
				"position:relative;max-height:100%;width:100%");
		return actionsHtml_html;

	}
	function submit() {
			var value = $('input[name="radio"]:checked').val();
			if(value == 1){
				BootstrapDialog
				.show({
					title : $translate.instant('smsTest.addr'),
					message : function(dialog) {
						var $message=$(
								'<textArea placeholder="' + $translate.instant('common.manualWrite.Address') +' " id="manualWrite" style="word-break:normal; width:100%; height:80px;display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'+
							    '</textArea>'
						);
		                return $message;
					 },
					buttons : [
							{
								label : $translate.instant('common.yes'),
								cssClass : 'btn btn-danger model-tool-right',
								action : function(dialogItself) {
									vm.bean.phoneNumber = $("#manualWrite").val();
									//发送方法
									MmsTestService.mmsTest(vm.bean.phoneNumber).then(function(d) {
//											vm.smsData = d.body;
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
			}else if(value == 2){
					BootstrapDialog.show({
						title:$translate.instant('smsTest.import.addr'),
						message: function(dialog) {
			                //var $message = $('<div></div>').load(url);
							var $message=$("<span>"+$translate.instant('common.file.message')+"<br/></span>" +
									"<span style='margin-left:10px'>"+$translate.instant('common.file.format')+"UTF-8<br/></span>" +
									"<span style='margin-left:10px'>"+$translate.instant('common.file.quantity')+"1<br/></span>" +
									"<span style='margin-left:10px'>"+$translate.instant('common.file.size')+"<label id='file_max_size_id' style='font-weight:400'></label><br/></span>" +
									"<div><div id='picker'>"+$translate.instant('common.selDocument')+"</div><div id='m_warning_div_id'></div></div>");
			                return $message;
			            },
			            onshown: function(dialogRef){//打开完成
			            	var file_max_size_id_value=vm.VAR_MAX_FILE_SIZE_M+""+"M";
			            	$("#file_max_size_id").text(file_max_size_id_value);
			            	//导入 start
			            	var uploader = WebUploader.create({
			            	    // swf文件路径
			            		//swf: BASE_URL + '/js/Uploader.swf',
			            		auto: true,
			            	    // 文件接收服务端。
			            	    server: apiUrl+'/messageTest/smsTest/Import',
			            	    method: 'POST',
			            	    headers:{"Authorization":localStorageService.get(tokenKey)},
			            	    // 选择文件的按钮。可选。
			            	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
			            	    pick: {id :'#picker',multiple:false},//设置id和设置只能选择一个文件,multiple:表示是否多选
			            	    sendAsBinary : true,
			            	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			            	    resize: false,
			            	    duplicate: true,//是否可重复上传,true表示可重复上传。false表示不可重复上传。默认不可重复上传
			            	    fileNumLimit: 1,
			            	    timeout: 0,
			            	    fileSingleSizeLimit: vm.VAR_MAX_FILE_SIZE_M * 1024 * 1024,//1M
			            	    accept: {
			                        title: 'Files',
			                        extensions: 'txt',
			                        mimeTypes: 'txt'
			                    }
			            	});
			            	
			            	// 文件上传过程中创建进度条实时显示。
			                uploader.on( 'uploadProgress', function( file, percentage ) {
			                    //进度条
			                	$('#m_warning_div_id').text($translate.instant('common.file.progress')).css("color",vm.success_color);
			                });
			            	
			            	//导入 end
			            },
				        buttons: [
				        {
				            label: $translate.instant('common.close'),
				            cssClass: 'btn-primary',
				            action: function(dialogItself){
				                dialogItself.close();
				            }
				        }]
				    });
			}
	}
	function onSubmitSuccess(data) {
		vm.statusCode = data.statusCode;
		vm.statusMessage = data.statusMessage;
		reloadData();
	}

	
	function upload() {  
		BootstrapDialog.show({
			title:$translate.instant('smsTest.sendContent'),
			message: function(dialog) {
				var $message=$("<span>"+$translate.instant('common.file.message')+"<br/></span>" +
						"<span style='margin-left:10px'>"+$translate.instant('common.file.format')+"UTF-8<br/></span>" +
						"<span style='margin-left:10px'>"+$translate.instant('common.file.quantity')+"1<br/></span>" +
						"<span style='margin-left:10px'>"+$translate.instant('common.file.size')+"<label id='file_max_size_id' style='font-weight:400'></label><br/></span>" +
						"<div><div id='picker'>"+$translate.instant('common.selDocument')+"</div><div id='m_warning_div_id'></div></div>");
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            	var file_max_size_id_value=vm.VAR_MAX_FILE_SIZE_M+""+"M";
            	$("#file_max_size_id").text(file_max_size_id_value);
            	//导入 start
            	var uploader = WebUploader.create({
            	    // swf文件路径
            		//swf: BASE_URL + '/js/Uploader.swf',
            		auto: true,
            	    // 文件接收服务端。
            	    server: apiUrl+'/messageTest/upload',
            	    method: 'POST',
            	    headers:{"Authorization":localStorageService.get(tokenKey)},
            	    // 选择文件的按钮。可选。
            	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            	    pick: {id :'#picker',multiple:false},//设置id和设置只能选择一个文件,multiple:表示是否多选
            	    sendAsBinary : true,
            	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            	    resize: false,
            	    duplicate: true,//是否可重复上传,true表示可重复上传。false表示不可重复上传。默认不可重复上传
            	    fileNumLimit: 1,
            	    timeout: 0,
            	    fileSingleSizeLimit: vm.VAR_MAX_FILE_SIZE_M * 1024 * 1024,//1M
            	    accept: {
                        title: 'Files',
                        extensions: 'doc',
                        mimeTypes: 'doc'
                    }
            	});
            	
            	// 文件上传过程中创建进度条实时显示。
                uploader.on( 'uploadProgress', function( file, percentage ) {
                    //进度条
                	$('#m_warning_div_id').text($translate.instant('common.file.progress')).css("color",vm.success_color);
                });
            	
            	//导入 end
            },
	        buttons: [
	        {
	            label: $translate.instant('common.close'),
	            cssClass: 'btn-primary',
	            action: function(dialogItself){
	                dialogItself.close();
	            }
	        }]
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
	$("#example")
			.on(
					"click",
					".spanFun",
					function() {
						var remarkDetail = $(this).text();
						BootstrapDialog
								.show({
									title : $translate.instant('user.remark'),
									message : function(dialog) {
										var $message = $('<span id="content_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'
												+ '</span>');
										return $message;
									},
									onshown : function(dialogRef) {// 打开完成
										$("#content_detail").text(remarkDetail);
									},
									buttons : [ {
										label : $translate
												.instant('common.yes'),
										cssClass : 'btn-primary',
										action : function(dialogItself) {
											dialogItself.close();
										}
									} ]
								});
					});
	// 超长备注处理end
	// 解决查询后保持列的显示start
	$('#table_id').on('init.dt', function(e, settings, column, state) {
		var flag = $(".dataTables_scrollBody").css("overflow");
    	if(flag='visible'){
		}
    	if(flag='auto'){
			$(".dataTables_scroll").attr("style","overflow:auto");
			$(".dataTables_scrollHead").css("overflow", "");
			$(".dataTables_scrollBody").css("overflow", "");
			$(".dataTables_scrollBody").attr("style","border:0px");
			$("#table_id").attr("style","border-bottom:1px solid black");
		}
		vm.columnStatusData = settings.aoColumns;
	});
	// 解决查询后保持列的显示end
	// start
	$('#table_id').on('draw.dt', function() {
		setTimeout(function() {
			$("#loadDiv").hide();
			$("#bth-searchDate").attr("disabled", false);
		}, 500);
	});

	$("#bth-searchDate").click(function() {
		$("#loadDiv").show();

	})
	// end

	function reloadData() {
		$("#bth-searchDate").attr("disabled", true);
		// 解决查询后保持列的显示start
		var columuFinalStatus = vm.columnStatusData;
		if (columuFinalStatus.length > 0) {
			for (var i = 0; i < columuFinalStatus.length; i++) {
				vm.dtColumns[i].bVisible = columuFinalStatus[i].bVisible;
			}
		}
		// 解决查询后保持列的显示end
		var userRecvId = vm.beanSer.userRecvId;
		var phoneNumber = vm.beanSer.phoneNumber;
		var messageType = vm.beanSer.messageType;
		var isCorrect = vm.beanSer.isCorrect;
		if(vm.beanSer.recvTimeStart >= vm.beanSer.recvTimeEnd){
			alert($translate.instant('userRecv.recvTimeRange'));
			return;
		}
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/userRecv?userRecvId=' + getValueForSelect(userRecvId) + "&phoneNumber=" + getValueForSelect(phoneNumber)+ "&messageType="  + getValueForSelect(messageType)
				+ "&isCorrect=" + getValueForSelect(isCorrect)+"&recvTimeStart="+getValueForSelect(recvTimeStart)+"&recvTimeEnd="+getValueForSelect(recvTimeEnd)));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
		// console.log(json);
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}