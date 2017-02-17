(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationDeleteController',JobApplicationDeleteController);

    JobApplicationDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobApplication'];

    function JobApplicationDeleteController($uibModalInstance, entity, JobApplication) {
        var vm = this;

        vm.jobApplication = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JobApplication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
