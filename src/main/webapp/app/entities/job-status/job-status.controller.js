(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobStatusController', JobStatusController);

    JobStatusController.$inject = ['$scope', '$state', 'JobStatus'];

    function JobStatusController ($scope, $state, JobStatus) {
        var vm = this;

        vm.jobStatuses = [];

        loadAll();

        function loadAll() {
            JobStatus.query(function(result) {
                vm.jobStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
