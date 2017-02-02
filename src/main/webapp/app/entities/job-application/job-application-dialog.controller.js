(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationDialogController', JobApplicationDialogController);

    JobApplicationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobApplication'];

    function JobApplicationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JobApplication) {
        var vm = this;

        vm.jobApplication = entity;
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
            if (vm.jobApplication.id !== null) {
                JobApplication.update(vm.jobApplication, onSaveSuccess, onSaveError);
            } else {
                JobApplication.save(vm.jobApplication, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isoftnetApp:jobApplicationUpdate', result);
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
