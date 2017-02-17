(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationController', JobApplicationController);

    JobApplicationController.$inject = ['$scope', '$state', 'JobApplication'];

    function JobApplicationController ($scope, $state, JobApplication) {
        var vm = this;

        vm.jobApplications = [];

        loadAll();

        function loadAll() {
            JobApplication.query(function(result) {
                vm.jobApplications = result;
                vm.searchQuery = null;
            });
        }
    }
})();
