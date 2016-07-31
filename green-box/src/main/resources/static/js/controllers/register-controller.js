appGreenbox.controller("registerController", function($scope, $http) {
	$scope.user = {};
	$scope.token = "";
	
	$scope.registerUser = function() {
		
		$http.post("/server/users/new", $scope.user).then(function(response) {
			console.log("Cadastrou usuario!");
			console.log($scope.user);
		}, function(response) {
			window.alert(response.data.message)
			console.log("TRATA EXCEPTION AQUI.");
		});
	};
	
})