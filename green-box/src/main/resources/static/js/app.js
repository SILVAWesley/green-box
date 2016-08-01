var app = angular.module("app", ['ui.router']);

app.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider) {
	$urlRouterProvider.otherwise('/');
	
	$stateProvider.state('home', {
		url: '/',
		templateUrl: 'views/home.html',
		controller: 'homeController'
	});
	
	$stateProvider.state('dashboard', {
		url: '/dashboard',
		templateUrl: 'views/account.html'
	});
}]);