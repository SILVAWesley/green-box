angular.module('app').factory('tokenInterceptor',['$injector', '$localStorage', function($injector, $localStorage) {
	
	return {
		'request': function(config) {
			config.headers.Authorization = 'Bearer ' + $localStorage.token;
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