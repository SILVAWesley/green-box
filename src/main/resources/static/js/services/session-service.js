angular.module('app').factory('SessionService', function($http, $localStorage, Constants) {
	var Session = {};
	
	Session.getUser = function() {
		return $localStorage.session.user;
	}
	
	Session.setUser = function(user) {
		$localStorage.session.user = user;
	}
	
	Session.getToken = function() {
		return $localStorage.session.token;
	}
	
	Session.setToken = function(token) {
		$localStorage.session.token = token;
	}
	
	Session.getClickedFile = function() {
		return $localStorage.session.clickedFile;
	}

	Session.setClickedFile = function(clickedFile) {
		$localStorage.session.clickedFile = clickedFile;
	}
	
	Session.fetchUser = function() {
		$http.get(Constants.GET_USER_URL + Session.getUser().username)
		.then(function(response) {
			$localStorage.session.user = response.data;
		}, function(response) {
			window.alert("Error at Service: 'Session' while trying to make a request to the server.");
		});
	}
	
	return Session;
});
