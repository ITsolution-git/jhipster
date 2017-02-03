(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobRatingDeleteController',JobRatingDeleteController);

    JobRatingDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobRating'];

    function JobRatingDeleteController($uibModalInstance, entity, JobRating) {
        var vm = this;

        vm.jobRating = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JobRating.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
