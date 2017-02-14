"use strict";

var socialLogin = angular.module('socialLogin', []);

socialLogin.provider("social", function(){
	var fbKey, fbApiV, googleKey, linkedInKey, wLiveKey;
	return {
		setFbKey: function(obj){
			fbKey = obj.appId;
			fbApiV = obj.apiVersion;
			var d = document, fbJs, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
			fbJs = d.createElement('script'); 
			fbJs.id = id; 
			fbJs.async = true;
			fbJs.src = "//connect.facebook.net/en_US/sdk.js";

			fbJs.onload = function() {
				FB.init({ 
					appId: fbKey,
					status: true, 
					cookie: true, 
					xfbml: true,
					version: fbApiV
				});
	        };

			ref.parentNode.insertBefore(fbJs, ref);
		},
		setGoogleKey: function(value){
			googleKey = value;
			var d = document, gJs, ref = d.getElementsByTagName('script')[0];
			gJs = d.createElement('script');
			gJs.async = true;
			gJs.src = "//apis.google.com/js/platform.js"

			gJs.onload = function() {
				var params ={
					client_id: value,
					scope: 'email'
				}
				gapi.load('auth2', function() {
        			gapi.auth2.init(params);
      			});
		    };

		    ref.parentNode.insertBefore(gJs, ref);
		},
		setLinkedInKey: function(value){
			linkedInKey = value;
			var lIN, d = document, ref = d.getElementsByTagName('script')[0];
			lIN = d.createElement('script');
			lIN.async = false;
			lIN.src = "//platform.linkedin.com/in.js?async=false";
			lIN.text = ("api_key: " + linkedInKey).replace("\"", "");
	        ref.parentNode.insertBefore(lIN, ref);
	    },
		setWidowsLiveKey: function(value){
			wLiveKey = value;
			var wLIVE, d = document, ref = d.getElementsByTagName('script')[0];
			wLIVE = d.createElement('script');
			wLIVE.async = false;
			wLIVE.src = "//js.live.net/v5.0/wl.js";
			wLIVE.onload = function() {
				WL.init({
					client_id: value,
				    redirect_uri: "http://localhost:8080/",
				    scope: ["wl.basic", "wl.contacts_emails"],
				    response_type: "token"
				});
    		};

        	ref.parentNode.insertBefore(wLIVE, ref);
	    },
		$get: function(){
			return{
				fbKey: fbKey,
				googleKey: googleKey,
				linkedInKey: linkedInKey,
				wLiveKey:wLiveKey,
				fbApiV: fbApiV
			}
		}
	}
});

socialLogin.factory("socialLoginService",[ "$window","$rootScope" ,function($window, $rootScope){
	return {
		logout: function(){
			var provider = $window.localStorage.getItem('_login_provider');
			switch(provider) {
				case "google":
					//its a hack need to find better solution.
					var gElement = document.getElementById("gSignout");
					if (typeof(gElement) != 'undefined' && gElement != null)
					{
					  gElement.remove();
					}
					var d = document, gSignout, ref = d.getElementsByTagName('script')[0];
					gSignout = d.createElement('script');
					gSignout.src = "https://accounts.google.com/Logout";
					gSignout.type = "text/javascript";
					gSignout.id = "gSignout";
					$window.localStorage.removeItem('_login_provider');
					$rootScope.$broadcast('event:social-sign-out-success', "success");
					ref.parentNode.insertBefore(gSignout, ref);
			        break;
				case "linkedIn":
					IN.User.logout(function(){
						$window.localStorage.removeItem('_login_provider');
					 	$rootScope.$broadcast('event:social-sign-out-success', "success");
					}, {});
					break;
				case "facebook":
					FB.logout(function(res){
						$window.localStorage.removeItem('_login_provider');
					 	$rootScope.$broadcast('event:social-sign-out-success', "success");
					});
					break;
			}
		},
		setProvider: function(provider){
			$window.localStorage.setItem('_login_provider', provider);
		}
	}
}]);

socialLogin.factory("fbService", ['$q', function($q){
	var service = {};
	service.uid = "",
	service.login = function(){
		var deferred = $q.defer();
		FB.login(function(res){ 
			deferred.resolve(res);
		}, {scope: 'email,public_profile,user_friends', auth_type: 'rerequest'});
		return deferred.promise;
	};
	service.getUserDetails = function(){
		var deferred = $q.defer();
		FB.api('/me?fields=name,email,picture,friends,first_name', function(res){
			if(!res || res.error){
				deferred.reject('Error occured while fetching user details.');
			}else{
				service.uid = res.id;
				deferred.resolve(res);
			}
		},{scope: 'email,public_profile,user_friends'});
		return deferred.promise;
	},

	service.getUserFriends = function(){
		var deferred = $q.defer();
		FB.api('/me/friends?fields=fist_name,id', function(res){
			if(!res || res.error){
				deferred.reject('Error occured while fetching user details.');
			}else{
				deferred.resolve(res);
			}
		},{scope: 'email,public_profile,user_friends'});
		return deferred.promise;
	},

	service.getUser = function(uid){
		var deferred = $q.defer();
		FB.api('/'+uid+'?fields=name,email,picture,friends,first_name,last_name,middle_name',function(res){
			if(!res || res.error){
				console.log(res);
				deferred.reject('Error occured while fetching user details.');
			}else{
				deferred.resolve(res);
			}
		});
		return deferred.promise;
	}
	return service;
}]);

socialLogin.directive("linkedIn", ["$rootScope", "social", "socialLoginService", "$window",function($rootScope, social, socialLoginService, $window){
	return {
		restrict: 'EA',
		scope: {},
		link: function(scope, ele, attr){
		    ele.on("click", function(){
		  		IN.User.authorize(function(){
					IN.API.Raw("/people/~:(id,first-name,last-name,email-address,picture-url)").result(function(res){
						socialLoginService.setProvider("linkedIn");
						var userDetails = {name: res.firstName + " " + res.lastName, email: res.emailAddress, uid: res.id, provider: "linkedIN", imageUrl: res.pictureUrl};
						$rootScope.$broadcast('event:social-sign-in-success', userDetails);
				    });
				});
			})
		}
	}
}])

socialLogin.directive("gLogin",["$rootScope", "social", "socialLoginService", "$window", function($rootScope, social, socialLoginService){
	return {
		restrict: 'EA',
		scope: {},
		replace: true,
		link: function(scope, ele, attr){
			ele.on('click', function(){
		    	if(typeof(scope.gauth) == "undefined")
		    		scope.gauth = gapi.auth2.getAuthInstance();	
	        	scope.gauth.signIn().then(function(googleUser){
	        		var profile = googleUser.getBasicProfile();
	        		var idToken = googleUser.getAuthResponse().id_token
	        		socialLoginService.setProvider("google");
	        		$rootScope.$broadcast('event:social-sign-in-success', {token: idToken, name: profile.getName(), email: profile.getEmail(), uid: profile.getId(), provider: "google", imageUrl: profile.getImageUrl()});
	        	}, function(err){
	        		console.log(err);
	        	})
	        });
		}
	}
}]);

socialLogin.directive("fbLogin", ["$rootScope", 'fbService',"social", "socialLoginService", "$window","Job",function($rootScope, fbService, social, socialLoginService,Job){
	return {
		restrict: 'A',
		scope: {},
		replace: true,
		link: function(scope, ele, attr){
			ele.on('click', function(){
				fbService.login().then(function(res){
					if(res.status == "connected"){
						fbService.getUserDetails().then(function(user){
							socialLoginService.setProvider("facebook");
							var userDetails = {name: user.name, email: user.email, uid: user.id, provider: "facebook", imageUrl: user.picture.data.url, friends:user.friends}
							$rootScope.$broadcast('event:social-sign-in-success', userDetails);
							console.log(user);
							angular.forEach(user.friends.data, function(friend){
								
								fbService.getUser(friend.id).then(function(user){
												
									$rootScope.$broadcast('event:contact-created', {lastName:user.last_name, middleName:user.middle_name, firstName:user.first_name,fullName:user.name, groupName:"facebook", primaryEmail:user.email || ""});

								}, function(err){
									console.log(err);
								});					
							})
						
						}, function(err){
							console.log(err);
						});

					}
				}, function(err){
					console.log(err);
				});
			});
		}
	}
}])

socialLogin.directive("wliveLogin", ["$rootScope","social", "socialLoginService", "$window","Job",function($rootScope,social, socialLoginService,Job){
	return {
		restrict: 'AE',
		scope: {},
		replace: true,
		link: function(scope, ele, attr){
			ele.on('click', function(){
				WL.login({
			        scope: ["wl.basic", "wl.contacts_emails"]
			    }).then(function (response) 
			    {
			    	console.log(response);
					WL.api({
			            path: "me/contacts",
			            method: "GET"
			        }).then(
			            function (response) {
		                        //your response data with contacts 
			            	console.log(response.data);
			            },
			            function (responseFailed) {
			            	//console.log(responseFailed);
			            }
			        );
			        
			    },
			    function (responseFailed) 
			    {
			        //console.log("Error signing in: " + responseFailed.error_description);
			    });
			});
		}
	}
}])