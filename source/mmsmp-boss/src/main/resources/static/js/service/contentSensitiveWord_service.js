'use strict';

App.factory('ContentSensitiveWordService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllContentSensitiveWords: function() {
					return $http.get(apiUrl +"/contentSensitiveWord")
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
		    
		    createContentSensitiveWord: function(contentSensitiveWord){
					return $http.post(apiUrl +"/contentSensitiveWord", contentSensitiveWord)
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
		    
		    updateContentSensitiveWord: function(contentSensitiveWord, id){
					return $http.put(apiUrl +"/contentSensitiveWord/"+id, contentSensitiveWord)
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
		    
			deleteContentSensitiveWord: function(id){
					return $http['delete'](apiUrl +"/contentSensitiveWord/"+id)
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
