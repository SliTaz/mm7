'use strict';

var App = angular.module('myApp',[]);

//解决页面隐藏后,列名字与内容有错位的问题start
$(".sidebar-toggle").click(function(){
	var timesRun = 0;
	var interval = setInterval(function(){
	timesRun += 1;
	if(timesRun === 1){
	clearInterval(interval);
	}
	try{
	  $("#table_id").dataTable().fnAdjustColumnSizing(false);//重新计算列宽
	}catch(e){
		
	 }
	}, 300);
	
})
//解决页面隐藏后,列名字与内容有错位的问题end
