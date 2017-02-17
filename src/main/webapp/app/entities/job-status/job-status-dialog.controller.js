(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobStatusDialogController', JobStatusDialogController);

    JobStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobStatus'];

    function JobStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JobStatus) {
        var vm = this;

        vm.jobStatus = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jobStatus.id !== null) {
                JobStatus.update(vm.jobStatus, onSaveSuccess, onSaveError);
            } else {
                JobStatus.save(vm.jobStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isoftnetApp:jobStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.updatedOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
