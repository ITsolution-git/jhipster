(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobStatusController', JobStatusController);

    JobStatusController.$inject = ['$scope', '$state', 'JobStatus', '$stateParams', 'job', 'Principal'];

    function JobStatusController ($scope, $state, JobStatus, $stateParams, job, Principal) {
        $(".container").css('width', '100%');
        var vm = this;

        vm.jobStatuses = [];
        vm.openJobId = $stateParams.openJobId;
        if(vm.openJobId == -1)
            $state.go('job');
       
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
            vm.isOwner = vm.user.id == job.createdBy;
        });
        loadAll();

        function loadAll() {
            JobStatus.query(function(result) {
                vm.jobStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
