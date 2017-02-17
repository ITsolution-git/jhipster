
(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobDeleteJobsController',JobDeleteJobsController);

    JobDeleteJobsController.$inject = ['$uibModalInstance', 'Job', '$stateParams', '$http', '$state'];

    function JobDeleteJobsController($uibModalInstance, Job, $stateParams, $http, $state) {
        var vm = this;

            
        vm.clear = clear;
        vm.deleteJobs = deleteJobs;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }   
        function deleteJobs () {
            // $http.delete('api/jobs', 
            //     {data:$stateParams.selectIDs})
            // .then(function(response){
            //     $uibModalInstance.close(true);
            // })
            $http.post('api/closeJobs', $stateParams.selectIDs)
            .then(function(response){
                $uibModalInstance.close(true);
            })
        }
    }
})();
