(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobDeleteJobsController',JobDeleteJobsController);

    JobDeleteJobsController.$inject = ['$uibModalInstance', 'Job'];

    function JobDeleteJobsController($uibModalInstance, Job) {
        var vm = this;

            
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
