(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobCloseJobsController',JobCloseJobsController);

    JobCloseJobsController.$inject = ['$uibModalInstance', 'Job', '$stateParams'];

    function JobCloseJobsController($uibModalInstance, Job, $stateParams) {
        var vm = this;

            
        vm.clear = clear;
        vm.closeJobs = closeJobs;
        console.log($stateParams.selectIDs);

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function closeJobs () {
            Job.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
