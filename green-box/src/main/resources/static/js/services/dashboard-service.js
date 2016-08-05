angular.module('app').factory('actionService', function($http, $localStorage) {
	return {
		'user': $localStorage.session.user,

		'rootDirectory': user.userDirectory,

		'actualDirectory': rootDirectory,

		'foldersPath': [rootDirectory],

		'getFilesNFolders': function() {
			return getFolders().concat(getFiles());
		},

		'getFiles': function() {
			return actualDirectory.files;	
		},

		'getFolders': function() {
			return actualDirectory.children;	
		},

		'newFolder': function(folderName) {
			$http.post('/server/userdirectory/newfolder/' + folderName, user
		})
			.then(function(response) {
				
				$localStorage.session.user = response.data;
				update();
				
			}, function(response) {
				
				window.alert(response.data.message);
				
		}),

		'goForward': function(folderName) {
			for (int i = 0; i < actualDirectory.children.length; i++) {
				if (actualDirectory.children[i].name == folderName) {
					actualDirectory = actualDirectory.children[i];
					foldersPath.push(actualDirectory);
					break;
				}
			}
		}
	}
	
	function buildFoldersPath(folderName) {
		folders = folderFullPath.split("/");
		currentIndex = 0;

		while (true) {
			for (int i = 0; i < ; i++) {

			}

			currentIndex++;
		}
	}

	function update() {
		user = $localStorage.session.user;
		rootDirectory = user.userDirectory;
	}
	
	function pathclick (clickedPath) {
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
});
