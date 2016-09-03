angular.module('app').controller('fileController', function($localStorage, $scope, $http, $rootScope, $state) {
	$scope.path = $localStorage.session.currentPath;
	$scope.user = $localStorage.session.user;
	$scope.content = "";
	$scope.extensions = ["txt", "md"];
	$scope.currentExtension = "txt";
	
	$scope.newFileName = "";
	
	if ($localStorage.clickedFile != undefined) {
		$scope.newFileName = $localStorage.clickedFile.name;
		$scope.content = $localStorage.clickedFile.content;
	}
	
	$scope.saveFile = function() {
		path = formatPathToApiPattern($scope.path);
		
		requestData = {};
		requestData.user = $scope.user;
		requestData.fileName = $scope.newFileName;
		requestData.fileExtension = $scope.currentExtension;
		requestData.fileContent = $scope.content;
		requestData.filePath = $scope.path;
		
		console.log("Path: " + $scope.path);
		
		if (requestData.fileName == "") {
			
			window.alert("File name cannot be empty.");
			
		} else if ($localStorage.clickedFile == undefined){
			$http.post("/server/userdirectory/newfile", requestData)
			.then(function(response) {
				
				$localStorage.session.user = response.data;
				window.alert("File successfully created.");
				$state.go('dashboard.directories', {'folderPath': $localStorage.session.currentPath});
				$scope.path = $localStorage.session.currentPath;
				
			}, function(response) {
				
				window.alert(response.data.message);
				
			});
		} else {
			path = formatPathToApiPattern2($scope.path);
			console.log("Path 2: " + path);
			$http.put("/server/userdirectory/editfile", requestData)
			.then(function(response){
				
				$localStorage.session.user = response.data;
				window.alert("File successfully edited");
				$state.go('dashboard.directories', {'folderPath': $localStorage.session.currentPath});
				
			}, function(response){
				window.alert(response.data.message);
			});
		}
	}
	
	function getNamelessPath(path){
		var splitPath = path.split("/");
		result = "";
		for(i = 0; i < splitPath.length -1; i++){
			result += splitPath[i];
		}
		
		return result;
		
	}
	
	function formatPathToApiPattern(path) {
		tempPath = path.replace(new RegExp('/', 'g'), '-').replace("root/", "").replace("root", "");
		return tempPath.substring(1, tempPath.length) + "/" + $scope.newFileName;
	}
	
	function formatPathToApiPattern2(path) {
		tempPath = path.replace(new RegExp('/', 'g'), '-');
		return tempPath.substring(1, tempPath.length);
	}
	
	$scope.directoriesView = function() {
		$state.go('dashboard.directories', {folderPath: $localStorage.session.currentPath});
	}
});