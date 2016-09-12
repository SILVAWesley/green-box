angular.module('app').factory('SessionService', function($http, $localStorage, Constants) {
	var Session = {};
	Session.generateSession = function(user, token) {
		$localStorage.session = {'user': user,
								 'token': token};
	}
	
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
	
	Session.removeSession = function() {
		$localStorage.session;
	}
	
	Session.fetchUser = function() {
		$http.get(Constants.GET_USER_URL + Session.getUser().username)
		.then(function(response) {
			$localStorage.session.user = response.data;
		}, function(response) {
			$("#serviceErrorModal .modal-body").html("'Session' while trying to make a request to the server.");
			$("#serviceErrorModal").modal("show");
		});
	}
	
	return Session;
});
