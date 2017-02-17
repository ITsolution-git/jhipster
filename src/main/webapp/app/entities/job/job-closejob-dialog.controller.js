(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobCloseJobsController',JobCloseJobsController);

    JobCloseJobsController.$inject = ['$uibModalInstance', 'Job', '$stateParams', '$http', '$state'];

    function JobCloseJobsController($uibModalInstance, Job, $stateParams, $http, $state) {
        var vm = this;

            
        vm.clear = clear;
        vm.closeJobs = closeJobs;

        function clear () {
            $uibModalInstance.dismiss('cancel');    
        }
        function closeJobs () {
            $http.post('api/closeJobs', $stateParams.selectIDs)
            .then(function(response){
                $uibModalInstance.close(true);
            })
        }
    }
})();
