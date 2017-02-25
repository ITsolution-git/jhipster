(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationSendmsgDialogController', JobApplicationSendmsgDialogController);

    JobApplicationSendmsgDialogController.$inject = ['$scope', '$rootScope', '$stateParams', 'JobApplication', '$uibModalInstance', '$http'];

    function JobApplicationSendmsgDialogController($scope, $rootScope, $stateParams, JobApplication, $uibModalInstance, $http) {
        var vm = this;
        
        vm.clear = clear;
        vm.sendMsg = sendMsg;
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function sendMsg () {
            $http.post('api/message', {
                we: vm.message,
                id: $stateParams.id
            }).then(function(response){
                $uibModalInstance.close(true);
            })
        }
    }
})();
