angular.module('app').controller("loginController", 
								['$scope', 
								 '$http', 
								 '$state', 
								 'authService',
								 '$localStorage',

function($scope, $http, $state, authService, $localStorage) {
	
	$scope.entrydata = "";
	$scope.password = "";
	
	$scope.login = function() {
		authService.login($scope.entrydata, $scope.password, function(result) {
			if (result) {
				$state.go('dashboard.directories', {folderPath: $localStorage.session.currentPath});
			} else {
				window.alert("Login not successful");
			}	
		});
	}
}])