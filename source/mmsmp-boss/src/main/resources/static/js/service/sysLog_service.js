'use strict';

App.factory('SysLogService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllSysLogs: function() {
					return $http.get(apiUrl +"/sysLog")
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
			},
		    
		    createSysLog: function(sysLog){
					return $http.post(apiUrl +"/sysLog", sysLog)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
		    },
		    
		    updateSysLog: function(sysLog, id){
					return $http.put(apiUrl +"/sysLog/"+id, sysLog)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
			},
			//批量删除开始
			deleteSysLogMany: function(id){
				return $http['delete'](apiUrl +"/sysLog/deleteMany/"+id)
						.then(
								function(response){
									return response.data;
								}, 
								function(errResponse){
									handleAjaxError(errResponse);
									return $q.reject(errResponse);
								}
						);
		  },
		   //批量删除结束
		    
			deleteSysLog: function(id){
					return $http['delete'](apiUrl +"/sysLog/"+id)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
			}
		
	};

}]);
