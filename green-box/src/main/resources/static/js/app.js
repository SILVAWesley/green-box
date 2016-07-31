var appGreenbox = angular.module("appGreenbox", ['ngRoute']);

appGreenbox.config(function($routeProvider, $locationProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "views/home.html",
		controller: "homeController"
	})
	.when("/userdirectory", {
		templateUrl: "views/account.html",
		controller: "accountController"
	})
	.otherwise({redirectTo: "/home"});
	
	$locationProvider.html5Mode(true);
});

/*appGreenbox.config(function($httpProvider) {
	$httpProvider.interceptors.push('loginInterceptor');
	console.log("hello app");
});*/