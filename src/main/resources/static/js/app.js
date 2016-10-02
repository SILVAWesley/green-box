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
		templateUrl: 'views/snippets/directories.html',
		controller: 'directoriesController'
	});
	
	$stateProvider.state('dashboard.about', {
		url: '/about/',
		templateUrl: 'views/snippets/about.html',
	});
	
	$stateProvider.state('dashboard.contact', {
		url: '/contact/',
		templateUrl: 'views/snippets/contact.html',
	});
	
	$stateProvider.state('dashboard.file', {
		url: '/file',
		templateUrl: 'views/snippets/file.html',
		controller: 'fileController'
	});
	
}]);