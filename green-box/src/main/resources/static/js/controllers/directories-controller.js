angular.module('app').controller("directoriesController", function($scope, $state, $localStorage, $http, $rootScope, $stateParams) {
	$scope.user = $localStorage.session.user;
	$scope.rootDirectory = $scope.user.userDirectory;
	$scope.currentDirectory = $scope.rootDirectory;
	$scope.openedFolders = [$scope.rootDirectory];
	
	$localStorage.session.currentPath = $stateParams.folderPath;
	
	$scope.newFolderName = "";
	
	$scope.folderClick = function(folder) {
		$state.go('dashboard.directories', {folderPath: folder.path});
	}
	
	$scope.fileClick = function(file) {
		$localStorage.clickedFile = file;
		$state.go('dashboard.file');
	}
	
	$scope.newFolder = function() {
		
		requestData = {};
		requestData.user = $scope.user;
		requestData.folderName = $scope.newFolderName;
		//requestData.folderPath = formatPathToApiPattern2($scope.currentDirectory.path);
		requestData.folderPath = $scope.currentDirectory.path;
		//console.log("Path: " + $scope.path);
		
		
		
		$http.post("/server/userdirectory/newfolder", requestData)
			.then(function(response) {
				
				$localStorage.session.user = response.data;
				update();
				goToPath($stateParams.folderPath);
				
			}, function(response) {
				
				window.alert(response.data.message);
				
		});
	} 
	
	$scope.getFilesNFolders = function() {
		return $scope.getFolders().concat($scope.getFiles());
	}
	
	$scope.getFiles = function() {
		return $scope.currentDirectory.files;	
	}	

	$scope.getFolders = function() {
		return $scope.currentDirectory.children;
	}
	
	$scope.newFilePage = function() {
		$localStorage.clickedFile = null;
		$state.go('dashboard.file');
	}
	
	$scope.filesNFoldersToShow = $scope.getFilesNFolders();
	goToPath($stateParams.folderPath);
	
	function findFileOrFolderByName(name, directory) {
		var foldersNFiles = directory.children.concat(directory.files);
		
		for (j = 0; j < foldersNFiles.length; j++) {
			if (foldersNFiles[j].name == name) {
				return foldersNFiles[j];
			}
		}
	
		return null;
	}
	
	function formatPathToApiPattern(path) {
		tempPath = path.replace(new RegExp('/', 'g'), '-').replace("root/", "").replace("root", "")
		return tempPath.substring(1, tempPath.length) + "/" + $scope.newFolderName;
	}
	
	function formatPathToApiPattern2(path) {
		tempPath = path.replace(new RegExp('/', 'g'), '-').replace("root/", "").replace("root", "")
		return tempPath.substring(1, tempPath.length);
	}
	
	function update () {
		$scope.user = $localStorage.session.user;
		$scope.rootDirectory = $scope.user.userDirectory;
		$scope.currentDirectory = $scope.rootDirectory;
		$scope.openedFolders = [$scope.rootDirectory];
	}
	
	function goToPath(path) {
		var directoryNames = path.split("/");
		$scope.currentDirectory = $scope.rootDirectory;
		$scope.filesNFoldersToShow = $scope.getFilesNFolders();
		
		for (i = 1; i < directoryNames.length; i++) {
			$scope.currentDirectory = findFileOrFolderByName(directoryNames[i], $scope.currentDirectory);
			goForward($scope.currentDirectory);
		}
	}
	
	function goForward(folder) {
		$scope.currentDirectory = folder;
		$scope.openedFolders.push(folder);
		$scope.filesNFoldersToShow = $scope.getFilesNFolders();
	}
});