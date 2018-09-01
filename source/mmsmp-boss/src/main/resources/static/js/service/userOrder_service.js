'use strict';

App.factory('UserOrderService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmInfos: function() {
					return $http.get(apiUrl +"/userOrder")
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
					return $http.post(apiUrl +"/userOrder", alarmInfo)
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
	fetchUserInfo: function() {
		return $http.get(apiUrl +"/userInfo")
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
		    updateAlarmInfo: function(UserOrder, id){
					return $http.put(apiUrl +"/userOrder/"+id, UserOrder)
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
					return $http['delete'](apiUrl +"/userOrder/"+id)
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
