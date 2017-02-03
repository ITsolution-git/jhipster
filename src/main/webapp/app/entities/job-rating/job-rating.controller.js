(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobRatingController', JobRatingController);

    JobRatingController.$inject = ['$scope', '$state', 'JobRating'];

    function JobRatingController ($scope, $state, JobRating) {
        var vm = this;

        vm.jobRatings = [];

        loadAll();

        function loadAll() {
            JobRating.query(function(result) {
                vm.jobRatings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
