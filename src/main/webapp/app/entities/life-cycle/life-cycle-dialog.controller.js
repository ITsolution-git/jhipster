(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('LifeCycleDialogController', LifeCycleDialogController);

    LifeCycleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LifeCycle'];

    function LifeCycleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LifeCycle) {
        var vm = this;

        vm.lifeCycle = entity;
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
            if (vm.lifeCycle.id !== null) {
                LifeCycle.update(vm.lifeCycle, onSaveSuccess, onSaveError);
            } else {
                LifeCycle.save(vm.lifeCycle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isoftnetApp:lifeCycleUpdate', result);
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
