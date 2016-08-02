angular.module('app').controller("accountController", function($scope, $http, $rootScope, $location) {
	/*function Directory(name) {
		this.files = [];
		this.children = [];
		this.name = name;
	}
	
	function File(name) {
		this.name = name;
	}
	
	function basicDirectory() {
		var loucuraDirectory = new Directory("Loucura");
		loucuraDirectory.files.push(new File("sozueira.txt"));
		
		
		var loucuraDoidaDirectory = new Directory("LoucuraDoida");
		loucuraDirectory.files.push(new File("sozueira.txt"));
		
		var zueraDirectory = new Directory("Zuera");
		zueraDirectory.files.push(new File("mulhernua.png"));
		zueraDirectory.files.push(new File(".txt"));
		
		loucuraDirectory.children.push(zueraDirectory);
		
		$scope.userdirectory.children.push(loucuraDirectory);
		$scope.userdirectory.children.push(loucuraDoidaDirectory);
	
		$scope.userdirectory.files.push(new File("hello.txt"));
		$scope.userdirectory.files.push(new File("hello2.txt"));
		$scope.userdirectory.files.push(new File("hello3.txt"));
		
		$scope.allfiles = $scope.userdirectory.files.concat($scope.userdirectory.children);
	}*/
	function update() {
		$scope.loggedUser = JSON.parse(sessionStorage.getItem("logged-user"));
		$scope.userDirectory = $scope.loggedUser.userDirectory;
		$scope.allfiles = $scope.userDirectory.files.concat($scope.userDirectory.children);
	}
	
	update();
	$scope.directories = [];
	$scope.folderName = "";
	
	//basicDirectory();
	console.log($scope.loggedUser.userDirectory);
	$scope.directoryclick = function(clickedDirectory) {
		
		$scope.allfiles = clickedDirectory.children.concat(clickedDirectory.files);
		$scope.directories.push($scope.userDirectory);
		$scope.userDirectory = clickedDirectory;
		
	}

	$scope.addFolder = function() {
		console.log(sessionStorage.getItem('user-token') + " loucura");
		$http({method: 'POST', url: '/server/userdirectory/newfolder/' + $scope.folderName, headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("user-token")}, data: JSON.parse(sessionStorage.getItem("logged-user"))
		})
		.then(function(response) {
			
			console.log(response);
			sessionStorage.setItem("logged-user", JSON.stringify(response.data));
			update();
			
		}, function(response) {
			
			window.alert(response.data.message);
			
		});
		
	}
	
	$scope.pathclick = function(clickedPath) {
		for (i = 0; i < $scope.directories.length; i++) {
			console.log($scope.directories[i]);
			if ($scope.directories[i] == clickedPath) {
				$scope.newdirectories = [];
				
				for (j = 0; j < i; j++) {
					$scope.newdirectories.push($scope.directories[j]);
				}
				$scope.directories = $scope.newdirectories;
				console.log($scope.directories);
				break;
			}
		}
		
		$scope.allfiles = clickedPath.children.concat(clickedPath.files);
		$scope.userDirectory = clickedPath;
	};
})