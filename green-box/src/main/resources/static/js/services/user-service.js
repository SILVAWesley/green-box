angular.module('app').factory('userService', function($http) {
	var service = {};
	
	service.user = null;
	
	service.getUser = function() {
		return service.user;
	}
	
	service.setUser = function(newUser) {
		service.user = newUser;
	}
	
	service.fetchUser = function() {
		
		$http.get("/server/users/get/" + service.user.id)
			.then(function(response) {
				
				service.user = response.data.user
				$localStorage.session.user = response.data.user;
				
			}, function(response) {
			
				window.alert(response.data.message);
				
		});
		
	}
	
	return service;
});
