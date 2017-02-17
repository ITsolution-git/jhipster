(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobAddStatusController',JobAddStatusController);

    JobAddStatusController.$inject = ['$uibModalInstance', 'Job', '$stateParams', '$state', '$http'];

    function JobAddStatusController($uibModalInstance, Job, $stateParams, $state, $http) {
        var vm = this;

            
        vm.clear = clear;
        vm.addStatus = addStatus;
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function addStatus () {
            
            $http.post('api/addJobStatus', {
                comment: vm.comment,
                jobIds: $stateParams.selectIDs
            }).then(function(response){
                $uibModalInstance.close(true);
            })
        }
    }
})();
