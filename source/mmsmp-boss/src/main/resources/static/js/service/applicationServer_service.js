'use strict';

App.factory('ApplicationServerService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAll: function() {
					return $http.get(apiUrl +"/applicationServer")
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
		    
			create: function(applicationServer){
					return $http.post(apiUrl +"/applicationServer", applicationServer)
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
		    
		    update: function(applicationServer, applicationServerCode){
					return $http.put(apiUrl +"/applicationServer/"+applicationServerCode, applicationServer)
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
			deleteAPP: function(applicationServerCode){
					return $http['delete'](apiUrl +"/applicationServer/"+applicationServerCode)
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

