(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobRatingController', JobRatingController);

    JobRatingController.$inject = ['$scope', '$state', 'JobRating', '$stateParams'];

    function JobRatingController ($scope, $state, JobRating, $stateParams) {
        var vm = this;

        vm.jobRatings = [];

        loadAll();

        vm.openJobId = $stateParams.openJobId;

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
