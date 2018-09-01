'use strict';

App.factory('SystemConfigKeyService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllTasks: function() {
					return $http.get(apiUrl +"/systemConfigKey")
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

