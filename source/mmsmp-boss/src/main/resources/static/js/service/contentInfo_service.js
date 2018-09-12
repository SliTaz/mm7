'use strict';

App.factory('ContentInfoService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllContentInfos: function() {
					return $http.get(apiUrl +"/contentInfo")
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
		    
		    createContentInfo: function(contentInfo){
		    	var a =contentInfo;
		    	if(a.roleId!=undefined){
			    	var str =a.roleId.join(",");
			    	}
		    	a.roleId=str;
					return $http.post(apiUrl +"/contentInfo", contentInfo)
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
		    
		    updateContentInfo: function(contentInfo, id){
					return $http.put(apiUrl +"/contentInfo/"+id, contentInfo)
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
		    
			deleteContentInfo: function(id){
					return $http['delete'](apiUrl +"/contentInfo/"+id)
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
			selCpInfo: function() {
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
		
	};

}]);
