(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationDetailController', JobApplicationDetailController);

    JobApplicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JobApplication'];

    function JobApplicationDetailController($scope, $rootScope, $stateParams, previousState, entity, JobApplication) {
        var vm = this;

        vm.jobApplication = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isoftnetApp:jobApplicationUpdate', function(event, result) {
            vm.jobApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
