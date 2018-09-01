'use strict';

App.factory('BlackWhiteListService', ['$http', '$q', function($http, $q){

	return {
		
			searchAllBlackWhiteList: function() {
					return $http.get(apiUrl +"/blackWhiteList")
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
	
		    createBlackWhiteList: function(blackWhiteList){
					return $http.post(apiUrl +"/blackWhiteList", blackWhiteList)
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
		    
		    updateBlackWhiteList: function(blackWhiteList, id){
					return $http.put(apiUrl +"/blackWhiteList/"+id, blackWhiteList)
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
		
			deleteBlackWhiteList: function(id){
					return $http['delete'](apiUrl +"/blackWhiteList/"+id)
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
