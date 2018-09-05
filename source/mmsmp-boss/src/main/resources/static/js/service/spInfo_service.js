'use strict';

App.factory('SpInfoService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmInfos: function() {
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
		    
		    createAlarmInfo: function(alarmInfo){
					return $http.post(apiUrl +"/spInfo", alarmInfo)
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
	fetchProvince: function() {
			return $http.get(apiUrl +"/provinceCity?parentProvinceCityId="+"-1")
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
					return $http.put(apiUrl +"/spInfo/"+id, alarmInfo)
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
					return $http['delete'](apiUrl +"/spInfo/"+id)
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
