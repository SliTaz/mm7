'use strict';

App.factory('SpAccessService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmInfos: function() {
					return $http.get(apiUrl +"/spAccess")
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
		    
		    createAlarmInfo: function(alarmInfo){
					return $http.post(apiUrl +"/spAccess", alarmInfo)
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
		    
		    fetchSpInfo: function() {
				return $http.get(apiUrl +"/spInfo")
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
		    updateAlarmInfo: function(alarmInfo, id){
					return $http.put(apiUrl +"/spAccess/"+id, alarmInfo)
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
		    
			deleteAlarmInfo: function(id){
					return $http['delete'](apiUrl +"/spAccess/"+id)
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
		
	};

}]);
