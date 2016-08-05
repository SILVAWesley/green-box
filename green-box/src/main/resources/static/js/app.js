var app = angular.module("app", ['ui.router']);

app.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider) {
	$urlRouterProvider.otherwise('/');
	
	$stateProvider.state('home', {
		url: '/',
		templateUrl: 'views/home.html'
	});
	
	$stateProvider.state('dashboard', {
		url: '/dashboard',
		templateUrl: 'views/account.html',
		controller: 'accountController'
	});
	
	$stateProvider.state('file', {
		url: '/newFile',
		templateUrl: 'views/file.html'
	});
}]);