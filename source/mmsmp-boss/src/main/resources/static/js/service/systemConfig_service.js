'use strict';

App.factory('SystemConfigService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAll: function() {
					return $http.get(apiUrl +"/systemConfig")
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
		    
			create: function(systemConfig){
					return $http.post(apiUrl +"/systemConfig", systemConfig)
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
		    
		    update: function(systemConfig, applicationServerCode, code){
					return $http.put(apiUrl +"/systemConfig/"+applicationServerCode+"/"+code, systemConfig)
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
			
			selapplication: function() {
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
		     //Ĭ���޸�
		     doubledefault: function(systemConfig, applicationServerCode, code){
					return $http.put(apiUrl +"/systemConfig/value/"+applicationServerCode+"/"+code, systemConfig)
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
			deleteSys: function(applicationServerCode,code){
					return $http['delete'](apiUrl +"/systemConfig/"+applicationServerCode+"/"+code)
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

