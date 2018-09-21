'use strict';

App.factory('SmsTestService', ['$http', '$q', function($http, $q){

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
			smsTest: function(sms){
					return $http.post(apiUrl +"/messageTest/smsTest/manualWrite", sms)
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
