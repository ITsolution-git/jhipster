(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('ReferenceDeleteController',ReferenceDeleteController);

    ReferenceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Reference'];

    function ReferenceDeleteController($uibModalInstance, entity, Reference) {
        var vm = this;

        vm.reference = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Reference.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
