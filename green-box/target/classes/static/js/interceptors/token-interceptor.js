angular.module('app').factory('tokenInterceptor',['$injector', '$localStorage', '$q', function($injector, $localStorage, $q) {
	
	return {
		'request': function(config) {
			if ($localStorage.session != undefined) {
				config.headers.Authorization = 'Bearer ' + $localStorage.session.token;
			}
			
			return config;
		},
		
		'responseError': function(rejection) {
			/*(if (rejection.status === 401 || rejection.status === 403) {
				$state.go('home');
			}*/
			
			return $q.reject(rejection);
		}
	}
	
}]);