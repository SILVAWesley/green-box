angular.module('app').controller('dashboardController', function($scope) {
	$scope.username =  $localStorage.session.user.userName;
})