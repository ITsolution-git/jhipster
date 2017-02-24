(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationController', JobApplicationController);

    JobApplicationController.$inject = ['$scope', '$state', 'JobApplication', '$timeout', '$stateParams', '$compile', 'Job'];

    function JobApplicationController ($scope, $state, JobApplication,  $timeout, $stateParams, $compile, Job) {
        var vm = this;

        var vm = this;
        
        function format ( tr ) {

            var selectID = parseInt($(tr[0]).attr("id").slice(3));
            var selectJob;
            for (var i = 0; i < vm.jobApplications.length; i++) {
                if(selectID == vm.jobApplications[i].id){
                    selectJob = i;
                    break;
                }
            }

            childScope = $scope.$new();
            childScope.jobApplication = vm.jobApplications[selectJob];
            childScope.openJobId = vm.openJobId;

            childScope.sendMsg = function(app_id){
                $state.go('job-application-sendmsg', { openJobId : vm.openJobId, id : app_id});
            };
            childScope.receipt = function(app_id){
                
            };
            childScope.reject = function(app_id){
                
            }
            
            var compiledDirective = $compile('<job-application-detail ng-model="jobApplication"></job-application-detail>');
            return compiledDirective(childScope);
        }
        function expandorhide ( tr ) {

            var row = vm.table.row( tr );
            if( !row )
                return;
            if ( row.child.isShown() ) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
                $(this).parent().removeClass("active");
            }
            else {
                // Open this row
                tr.addClass("active");
                row.child( format( tr ) ).show();
                tr.addClass('shown');
            }
        }
        $timeout(function() {
            vm.table = $('#job-application-table').DataTable( {
                "columns": [
                    // { "orderable": false },
                    { "orderable": false },
                    null,
                    null,
                    null,
                    null,
                    { "orderable": false },
                ],
                "order": [[2, 'asc']]
            } );
            // Add event listener for opening and closing details
            $('#job-application-table tbody').on('click', 'td.details-control', function () {
                var tr = $(this).closest('tr');
                expandorhide(tr);
            } );

        }, 1000);
        vm.jobApplications = [];
        var childScope;
        loadAll();
        vm.openJobId = $stateParams.openJobId;

        if(vm.openJobId == -1)
            $state.go('job');

        vm.job = {};
        Job.get({id : $stateParams.openJobId}).$promise
        .then(function(job){
            vm.job = job;
        });
        function loadAll() {
            JobApplication.query(function(result) {
                vm.jobApplications = result;
                vm.searchQuery = null;
            });
        }

        
    }
})();
