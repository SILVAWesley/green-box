angular.module('app').controller('fileController', function($localStorage, $scope, $http, $rootScope, $state) {
	$scope.path = $localStorage.path;
	console.log($scope.path + " EM INICIO DE FILECONT");
	
	$scope.saveFile = function() {
		path = "";
		console.log($scope.path);
		
		for (i = 1; i < $scope.path.length - 1; i++) {
			path += $scope.path[i].name + "-";
		}
		 
		if ($scope.path.length > 1) {
			path += $scope.path[$scope.path.length - 1].name + '/';
		}
		
		console.log('/server/userdirectory/newfile/' + path + $scope.newFileName);
		
		$http.post('/server/userdirectory/newfile/' + path + $scope.newFileName, $scope.user)
		.then(function(response) {
			
			$localStorage.session.user = response.data;
			$rootScope.update();
			
		}, function(response) {
			
			window.alert(response.data.message);
			
	});
	}
	
	$scope.directoriesView = function() {
		$state.go('dashboard.directories');
		
	}
});