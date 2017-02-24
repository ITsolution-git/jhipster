(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobRatingDialogController', JobRatingDialogController);

    JobRatingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'JobRating'];

    function JobRatingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, JobRating) {
        var vm = this;

        vm.jobRating = entity;
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
            if (vm.jobRating.id !== null) {
                JobRating.update(vm.jobRating, onSaveSuccess, onSaveError);
            } else {
                JobRating.save(vm.jobRating, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isoftnetApp:jobRatingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
