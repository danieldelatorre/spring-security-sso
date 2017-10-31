/*$( "#logout" ).click(function() {
	$.ajax({
	    url : "http://localhost:8081/logout",
	    type: "POST",
	    success: function(data, textStatus, jqXHR)
	    {
	    	window.location.href = "/";
	    },
	    error: function (jqXHR, textStatus, errorThrown)
	    {
	    	alert(textStatus);
	    }
	});

});*/

angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home',
		controllerAs : 'controller'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';

}).controller('navigation',

function($rootScope, $http, $location, $route) {

	var self = this;

	self.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	$http.get('user').then(function(response) {
		if (response.data.name) {
			$rootScope.authenticated = true;
		} else {
			$rootScope.authenticated = false;
		}
	}, function() {
		$rootScope.authenticated = false;
	});

	self.credentials = {};

	/*self.logout = function() {
		$http.post('logout', {}).finally(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		});
	}*/
	
	$( "#logout" ).click(function() {
		var res = $http.post('http://localhost:8081/revokeToken');
		res.success(function(data, status, headers, config) {
			$rootScope.authenticated = false;
			$location.path("/");
		});
		res.error(function(data, status, headers, config) {
			alert( "error");
		});
	});

}).controller('home', function($http) {
	var self = this;
	$http.get('resource/').then(function(response) {
		self.greeting = response.data;
	})
});