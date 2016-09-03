/*
 * This class will handle controllers, 
 */
angular.module('app').config(['$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('tokenInterceptor');
}]);
