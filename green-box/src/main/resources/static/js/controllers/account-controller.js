appGreenbox.controller("accountController", function($scope, $http, $rootScope, $location) {
	function Directory(name) {
		this.files = [];
		this.parent = null;
		this.children = [];
		this.name = name;
	}
	
	function File(name) {
		this.name = name;
	}
	
	function basicDirectory() {
		console.log($scope.userdirectory.children);
		var loucuraDirectory = new Directory("Loucura");
		loucuraDirectory.files.push(new File("sozueira.txt"));
		var loucuraDoidaDirectory = new Directory("LoucuraDoida");
		$scope.userdirectory.children.push(loucuraDirectory);
		$scope.userdirectory.children.push(loucuraDoidaDirectory);
		$scope.userdirectory.files.push(new File("hello.txt"));
		$scope.userdirectory.files.push(new File("hello2.txt"));
		$scope.userdirectory.files.push(new File("hello3.txt"));
		$scope.allfiles = $scope.userdirectory.files.concat($scope.userdirectory.children);
		loucuraDirectory.parent = $scope.userdirectory;
	}
	
	$scope.user = $rootScope.user;
	$scope.userdirectory = $scope.user.userDirectory;
	$scope.paths = [];
	basicDirectory();
	
	console.log($scope.userdirectory.children);
	$rootScope.activetab = $location.path();
	$scope.directoryclick = function(clickedDirectory) {
		$scope.allfiles = clickedDirectory.children.concat(clickedDirectory.files);
		$scope.paths.push($scope.userdirectory.name);
		$scope.userdirectory = clickedDirectory;
	}
})