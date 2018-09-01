'use strict';

App.factory('CpInfoService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllCpInfos: function() {
					return $http.get(apiUrl +"/cpInfo")
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
		    
		    createCpInfo: function(cpInfo){
		    	var a =cpInfo;
		    	if(a.roleId!=undefined){
			    	var str =a.roleId.join(",");
			    	}
		    	a.roleId=str;
					return $http.post(apiUrl +"/cpInfo", cpInfo)
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
		    
		    updateCpInfo: function(cpInfo, id){
		    	var a =cpInfo;
		    	if(a.roleId!=undefined){
		    		if (typeof a.roleId === 'object'){
		             	var str =a.roleId.join(",");
		             	a.roleId=str;
		    		}
		    	}
					return $http.put(apiUrl +"/cpInfo/"+id, cpInfo)
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
		    
			deleteCpInfo: function(id){
					return $http['delete'](apiUrl +"/cpInfo/"+id)
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
		
	};

}]);
