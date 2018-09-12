'use strict';

App.factory('AccessInfoService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllCooperKeys: function() {
					return $http.get(apiUrl +"/cooperKey")
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
		    
		    createCooperKey: function(cooperKey){
		    	var a =cooperKey;
		    	if(a.roleId!=undefined){
			    	var str =a.roleId.join(",");
			    	}
		    	a.roleId=str;
					return $http.post(apiUrl +"/cooperKey", cooperKey)
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
		    
		    updateCooperKey: function(cooperKey, id){
		    	var a =cooperKey;
		    	if(a.roleId!=undefined){
		    		if (typeof a.roleId === 'object'){
		             	var str =a.roleId.join(",");
		             	a.roleId=str;
		    		}
		    	}
					return $http.put(apiUrl +"/cooperKey/"+id, cooperKey)
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
		    
			deleteCooperKey: function(id){
					return $http['delete'](apiUrl +"/cooperKey/"+id)
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
