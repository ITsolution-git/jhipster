(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobStatusController', JobStatusController);

    JobStatusController.$inject = ['$scope', '$state', 'JobStatus', '$stateParams'];

    function JobStatusController ($scope, $state, JobStatus, $stateParams) {
        var vm = this;

        vm.jobStatuses = [];
        vm.openJobId = $stateParams.openJobId;
        if(vm.openJobId == -1)
            $state.go('job');
        loadAll();

        function loadAll() {
            JobStatus.query(function(result) {
                vm.jobStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
