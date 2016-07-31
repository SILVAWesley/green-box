appGreenbox.controller("loginController", function($scope, $http, $location) {
	$scope.user = {};
	$scope.entrydata = "";
	$scope.token = "";

	$scope.authorize = function() {
		prepareModel();
		console.log($scope.user);
		$http.post("/server/login/authenticate", $scope.user).then(
				function(response) {
					console.log("Logou");
					console.log($scope.user);
					$scope.token = response.data.token;
					localStorage.setItem("userToken", response.data.token);
					$location.path("/userdirectory");
					resetFields();
				}, function(response) {
					console.log($scope.user);
					window.alert(response.data.message);
					console.log("TRATA EXCEPTION AQUI.");
					resetFields();
				});
	};

	function prepareModel() {
		if ($scope.entrydata.indexOf('@') != -1) {
			$scope.user.email = $scope.entrydata;
		} else {
			$scope.user.username = $scope.entrydata;
		}
	}

	function resetFields() {
		$scope.entrydata = "";
		$scope.user.password = "";
	}
})