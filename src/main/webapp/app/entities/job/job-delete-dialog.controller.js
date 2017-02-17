(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobDeleteController',JobDeleteController);

    JobDeleteController.$inject = ['$uibModalInstance', 'Job'];

    function JobDeleteController($uibModalInstance, Job) {
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
