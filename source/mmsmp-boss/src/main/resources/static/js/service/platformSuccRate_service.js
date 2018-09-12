'use strict';

App.factory('PlatformSuccRateService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllUserRecvs: function() {
					return $http.get(apiUrl +"/userRecv")
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
		    
		    createUserRecv: function(userRecv){
		    	var a =userRecv;
		    	if(a.roleId!=undefined){
			    	var str =a.roleId.join(",");
			    	}
		    	a.roleId=str;
					return $http.post(apiUrl +"/userRecv", userRecv)
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
		    
		    updateUserRecv: function(userRecv, id){
		    	var a =userRecv;
		    	if(a.roleId!=undefined){
		    		if (typeof a.roleId === 'object'){
		             	var str =a.roleId.join(",");
		             	a.roleId=str;
		    		}
		    	}
					return $http.put(apiUrl +"/userRecv/"+id, userRecv)
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
		    
			deleteUserRecv: function(id){
					return $http['delete'](apiUrl +"/userRecv/"+id)
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
			selProvinceCity: function() {
				return $http.get(apiUrl +"/province")
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
