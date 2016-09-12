angular.module('app').factory("authService", function($localStorage, 
													  $http, 
													  Constants,
													  SessionService) {
	var authenticationService = {};
	
	authenticationService.login = function(entryinfo, password, callback) {
		var user = createUser(entryinfo, password);
		authenticate(user, callback);
	}
	
	authenticationService.logout = function() {
		delete $localStorage.session;
	}
	
	authenticationService.register = function(username, email, password, callback) {
		requestData = {};
		requestData.username = username;
		requestData.email = email;
		requestData.password = password;
		
		$http.post(Constants.POST_REGISTER_URL, requestData)
		.then(function(response) {
		    $("#registerSuccessfulModal").modal("show");
		    callback(true);
		}, function(response) {
			$("#registerErrorModal").modal("show");
		});
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
		$http.post(Constants.POST_LOGIN_URL, userInfo)
		.then(function(response) {
			SessionService.generateSession(response.data.user, response.data.token);
			callback(true);
		}, function(response) {
			$("#loginErrorModal .modal-body").html(response.data.message);
		    $("#loginErrorModal").modal("show");
			callback(false);
		});
	}
	
	return authenticationService;
});