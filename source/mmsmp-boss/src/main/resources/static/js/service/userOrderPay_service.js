'use strict';

App.factory('UserOrderPayService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmInfos: function() {
					return $http.get(apiUrl +"/userOrderPay")
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
			selectArea: function(area) {
				return $http.get(apiUrl +"/provinceCity?parentProvinceCityId="+area)
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
		selProductInfo: function() {
     		return $http.get(apiUrl +"/productInfo")
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
					return $http.post(apiUrl +"/userOrderPay", alarmInfo)
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
		    updateAlarmInfo: function(alarmInfo, id){
					return $http.put(apiUrl +"/userOrderPay/"+id, alarmInfo)
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
					return $http['delete'](apiUrl +"/userOrderPay/"+id)
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
