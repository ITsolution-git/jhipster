(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobRatingDetailController', JobRatingDetailController);

    JobRatingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JobRating'];

    function JobRatingDetailController($scope, $rootScope, $stateParams, previousState, entity, JobRating) {
        var vm = this;

        vm.jobRating = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isoftnetApp:jobRatingUpdate', function(event, result) {
            vm.jobRating = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
