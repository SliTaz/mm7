'use strict';

App.factory('MmsTestService', ['$http', '$q', function($http, $q){

	return {
		
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
			mmsTest: function(phoneNumber){
					return $http.post(apiUrl +"/messageTest/mmsTest/manualWrite", phoneNumber)
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
