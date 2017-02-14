(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobAddStatusController',JobAddStatusController);

    JobAddStatusController.$inject = ['$uibModalInstance', 'Job', '$stateParams'];

    function JobAddStatusController($uibModalInstance, Job, $stateParams) {
        var vm = this;

            
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        console.log($stateParams.selectIDs);
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
