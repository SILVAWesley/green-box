angular.module('app').run(['$state', '$cookies', '$rootScope', 'SessionService', function($state, 
																						  $cookies, 
																						  $rootScope,
																						  SessionService) {
	$rootScope.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams) {
		  e.preventDefault();
		  SessionService.fetchUser();
	});
}]);