(function() {
    'use strict';

    angular
        .module('isoftnetApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'socialLogin',
            'ngRateIt'
        ])
        //77hfi22ynd7v4c from client
        .config(['socialProvider', function(socialProvider){
            // socialProvider.setGoogleKey("895971488718-htjr9oq08sg5e6i9gft0jbeseeof1n7v.apps.googleusercontent.com");
            // socialProvider.setLinkedInKey("81lwvq51gtdut9");
            // socialProvider.setFbKey({appId: "247821388979264", apiVersion: "v2.8"});
            // socialProvider.setWidowsLiveKey("373f6e99-90d5-40dd-8427-2e3e7aafb346");
        }])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
