angular.module('app').factory("authService", function($localStorage, $http) {
	return {
		'login' : function(entryinfo, password, callback) {
			var user = createUser(entryinfo, password);
			authenticate(user, callback);
		},
		
		'logout' : function() {
			delete $localStorage.session;
		}
	}
	
	function createUser(entryinfo, password) {
		var user = {};
		
		if (entryinfo.indexOf('@') != -1) { 
			user.email = entryinfo;
		} else {
			user.username = entryinfo;
		}
		
		user.password = password;
		return user;
	}
	
	function authenticate(userInfo, callback) {
		
		$http.post("/server/login/authenticate", userInfo)
			.then(function(response) {
				
				$localStorage.session = {'user': response.data.user,
						 				 'token': response.data.token};
				callback(true);
				
			}, function(response) {
			
				window.alert(response.data.message);
				callback(false);
				
		});
	
	}
});