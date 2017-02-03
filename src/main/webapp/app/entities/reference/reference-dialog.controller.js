(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('ReferenceDialogController', ReferenceDialogController);

    ReferenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reference'];

    function ReferenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reference) {
        var vm = this;

        vm.reference = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reference.id !== null) {
                Reference.update(vm.reference, onSaveSuccess, onSaveError);
            } else {
                Reference.save(vm.reference, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isoftnetApp:referenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
