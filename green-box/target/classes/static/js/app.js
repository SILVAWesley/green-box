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
		controller: 'dashboardController'
	});
	
	$stateProvider.state('dashboard.directories', {
		url: '/directories/{folderPath:.*}',
		templateUrl: 'views/snippets/directorybox.html',
		controller: 'directoriesController'
	});
	
	$stateProvider.state('dashboard.file', {
		url: '/file',
		templateUrl: 'views/snippets/file.html',
		controller: 'fileController'
	});
	
}]);