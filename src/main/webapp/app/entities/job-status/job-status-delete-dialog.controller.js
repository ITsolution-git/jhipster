(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobStatusDeleteController',JobStatusDeleteController);

    JobStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobStatus'];

    function JobStatusDeleteController($uibModalInstance, entity, JobStatus) {
        var vm = this;

        vm.jobStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JobStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
