var appGreenbox = angular.module("appGreenbox", ['ngRoute']);

appGreenbox.config(function($routeProvider, $locationProvider) {
	$routeProvider
	.when("/", {
		templateUrl: "views/home.html",
		controller: "homeController"
	})
	.when("/userdirectory", {
		templateUrl: "views/account.html",
		controller: "accountController"
	})
	.otherwise({redirectTo: "/"});
	
	$locationProvider.html5Mode(false);
});

appGreenbox.config(function($httpProvider) {
	$httpProvider.interceptors.push('loginInterceptor');
});