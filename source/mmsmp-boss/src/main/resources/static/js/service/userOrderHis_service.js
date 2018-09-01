'use strict';

App.factory('UserOrderHisService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllUserOrderHiss: function() {
					return $http.get(apiUrl +"/userServiceSend")
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
		    
		    createUserServiceSend: function(userServiceSend){
		    	var a =userServiceSend;
		    	if(a.roleId!=undefined){
			    	var str =a.roleId.join(",");
			    	}
		    	a.roleId=str;
					return $http.post(apiUrl +"/userServiceSend", userServiceSend)
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
		    
		    updateUserServiceSend: function(userServiceSend, id){
		    	var a =userServiceSend;
		    	if(a.roleId!=undefined){
		    		if (typeof a.roleId === 'object'){
		             	var str =a.roleId.join(",");
		             	a.roleId=str;
		    		}
		    	}
					return $http.put(apiUrl +"/userServiceSend/"+id, userServiceSend)
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
			
		     
		     
//			selectByUserId: function(id){
//					return $http.get(apiUrl +"/consumerGasCoupon/"+id)
//							.then(
//									function(response){
//										return response.data;
//									}, 
//									function(errResponse){
//										handleAjaxError(errResponse);
//										return $q.reject(errResponse);
//									}
//							);
//			},
		    
			deleteUserOrderHis: function(id){
					return $http['delete'](apiUrl +"/userOrderHis/"+id)
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
			selSpInfo: function() {
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
			selUserInfo: function() {
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
		
	};

}]);
