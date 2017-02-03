(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('LifeCycleDetailController', LifeCycleDetailController);

    LifeCycleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LifeCycle'];

    function LifeCycleDetailController($scope, $rootScope, $stateParams, previousState, entity, LifeCycle) {
        var vm = this;

        vm.lifeCycle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isoftnetApp:lifeCycleUpdate', function(event, result) {
            vm.lifeCycle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
