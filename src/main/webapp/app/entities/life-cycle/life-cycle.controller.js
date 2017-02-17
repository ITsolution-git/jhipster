(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('LifeCycleController', LifeCycleController);

    LifeCycleController.$inject = ['$scope', '$state', 'LifeCycle'];

    function LifeCycleController ($scope, $state, LifeCycle) {
        var vm = this;

        vm.lifeCycles = [];

        loadAll();

        function loadAll() {
            LifeCycle.query(function(result) {
                vm.lifeCycles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
