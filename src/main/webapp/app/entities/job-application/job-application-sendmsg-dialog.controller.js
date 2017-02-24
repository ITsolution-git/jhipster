(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationSendmsgDialogController', JobApplicationSendmsgDialogController);

    JobApplicationSendmsgDialogController.$inject = ['$scope', '$rootScope', '$stateParams', 'JobApplication', '$uibModalInstance'];

    function JobApplicationSendmsgDialogController($scope, $rootScope, $stateParams, JobApplication, $uibModalInstance) {
        var vm = this;
        
        vm.clear = clear;
        vm.sendMsg = sendMsg;
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function sendMsg () {
            console.log("sendmsg job_id" + $stateParams.openJobId + "    id" + $stateParams.id);
            // $http.post('api/addJobStatus', {
            //     comment: vm.comment,
            //     jobIds: $stateParams.selectIDs
            // }).then(function(response){
            //     $uibModalInstance.close(true);
            // })
        }
    }
})();
