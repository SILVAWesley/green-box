var appGreenbox = angular.module("appGreenbox", ['ngRoute']);

appGreenbox.config(function($routeProvider, $locationProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "views/home.html",
		controller: "homeController"
	})
	.otherwise({redirectTo: "/home"});
	
	$locationProvider.html5Mode(true);
});