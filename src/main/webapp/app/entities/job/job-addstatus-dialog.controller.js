(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobAddStatusController',JobAddStatusController);

    JobAddStatusController.$inject = ['$uibModalInstance', 'entity', 'Job'];

    function JobAddStatusController($uibModalInstance, entity, Job) {
        var vm = this;

        vm.job = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Job.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
