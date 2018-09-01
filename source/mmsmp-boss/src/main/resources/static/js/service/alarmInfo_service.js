'use strict';

App.factory('AlarmInfoService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmInfos: function() {
					return $http.get(apiUrl +"/alarmInfo")
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
					return $http.post(apiUrl +"/alarmInfo", alarmInfo)
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
					return $http.put(apiUrl +"/alarmInfo/"+id, alarmInfo)
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
					return $http['delete'](apiUrl +"/alarmInfo/"+id)
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
			deleteSysLogMany: function(id){
				return $http['delete'](apiUrl +"/alarmInfo/deleteMany/"+id)
						.then(
								function(response){
								//	location.reload();
									return response.data;
								}, 
								function(errResponse){
									handleAjaxError(errResponse);
									return $q.reject(errResponse);
								}
						);
		  },
			selAlarmLevel: function() {
				return $http.get(apiUrl +"/alarmLevel")
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
		selAlarmOrigin: function() {
			return $http.get(apiUrl +"/alarmManger")
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
	    alarmHandleView: function(alarmInfo, id){
			return $http.put(apiUrl +"/alarmInfo/handleView/"+id, alarmInfo)
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
