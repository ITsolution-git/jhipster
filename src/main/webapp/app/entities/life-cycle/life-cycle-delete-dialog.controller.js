(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('LifeCycleDeleteController',LifeCycleDeleteController);

    LifeCycleDeleteController.$inject = ['$uibModalInstance', 'entity', 'LifeCycle'];

    function LifeCycleDeleteController($uibModalInstance, entity, LifeCycle) {
        var vm = this;

        vm.lifeCycle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LifeCycle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
