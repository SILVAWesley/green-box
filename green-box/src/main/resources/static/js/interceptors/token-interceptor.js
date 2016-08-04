angular.module('app').factory("tokenInterceptor",[$q, $location, function($q, $location) {
	
	return {
		'request': function(config) {
			config.headers.Authorization = 'Bearer ' + sessionStorage.getItem("user-token");
			return config;
		},
		
		'responseError': function(rejection) {
			if (response.status === 401 || response.status === 403) {
				$state.go('home');
			}
			
			return $q.reject(rejection);
		}
	}
	
}]);