(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobStatusDetailController', JobStatusDetailController);

    JobStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JobStatus'];

    function JobStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, JobStatus) {
        var vm = this;

        vm.jobStatus = entity;
        vm.previousState = previousState;
        vm.openJobId = $stateParams.openJobId;

        var unsubscribe = $rootScope.$on('isoftnetApp:jobStatusUpdate', function(event, result) {
            vm.jobStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
