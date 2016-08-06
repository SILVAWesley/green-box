angular.module('app', ['ui.router', 'ngStorage', 'summernote']);

angular.module('app').config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider) {
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
	
	$stateProvider.state('dashboard.directories', {
		url: '/dashboard.directories',
		templateUrl: 'views/snippets/directorybox.html',
		controller: 'accountController'
	});
	
	$stateProvider.state('dashboard.file', {
		url: '/dashboard.file',
		templateUrl: 'views/snippets/file.html',
		controller: 'fileController'
	});
	
}]);