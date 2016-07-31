appGreenbox.controller("registerController", function($scope, $http) {
	$scope.user = {};
	$scope.token = "";
	
	$scope.registerUser = function() {
		
		$http.post("/server/users/new", $scope.user).then(function(response) {
			console.log("Cadastrou usuario!");
		}, function(response) {
			console.log("TRATA EXCEPTION AQUI.");
		});
	};
	
})