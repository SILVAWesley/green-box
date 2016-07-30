appGreenbox.controller("registerController", function($scope, $http) {
	$scope.user = {};
	$scope.token = "";
	
	$scope.registerUser = function() {
		$http({method:'POST', url:'http://localhost:8080/server/users/new', data:$scope.user}).then(function(response) {
			$scope.token = response.data.token;
			localStorage.setItem("userToken", reponse.data.token);
			console.log("Usuário cadastrado com sucesso! " + "Name: " + user.username + ". Email: " + user.email);
		}, function(response) {
			console.log("TRATA EXCEPTION AQUI.")
		});
	};
	
})