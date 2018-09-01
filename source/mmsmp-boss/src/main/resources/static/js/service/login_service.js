'use strict';

login.factory('LoginService', ['$http', '$q', function($http, $q){

	return {
		
			login: function(bean) {
					return $http.post(apiUrl +"/login",bean)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
			}
	};

}]);
