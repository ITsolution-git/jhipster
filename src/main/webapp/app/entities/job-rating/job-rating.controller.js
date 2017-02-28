(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobRatingController', JobRatingController);

    JobRatingController.$inject = ['$scope', '$state', 'JobRating', '$stateParams', 'Principal', 'job'];

    function JobRatingController ($scope, $state, JobRating, $stateParams, Principal, job) {
        $(".container").css('width', '100%');
        var vm = this;

        vm.jobRatings = [];

        loadAll();

        vm.openJobId = $stateParams.openJobId;
        vm.job = job;
        var copyAccount = function (account) {
            return {
                id: account.id,
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };
        Principal.identity().then(function(account) {
            vm.user = copyAccount(account);
        });
        vm.user = {};

        if(vm.openJobId == -1)
            $state.go('job');

        function loadAll() {
            JobRating.query(function(result) {
                vm.jobRatings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
