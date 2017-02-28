(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobApplicationController', JobApplicationController);

    JobApplicationController.$inject = ['$scope', '$state', 'JobApplication', '$timeout', '$stateParams', '$compile', 'Job', 'Principal', 'job', '$http'];

    function JobApplicationController ($scope, $state, JobApplication,  $timeout, $stateParams, $compile, Job, Principal, job, $http) {
        $(".container").css('width', '100%');
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
                $http.post('/api/application/received', app_id)
                .then(function(response){
                    // $uibModalInstance.close(true);
                })
            };
            childScope.reject = function(app_id){
                $http.post('/api/application/reject', app_id)
                .then(function(response){
                    // $uibModalInstance.close(true);
                })
            }
            childScope.isOwner = vm.job.createdBy == vm.user.id;
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

        var copyAccount = function (account) {
            return {
                id: account.id,
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };
        Principal.identity().then(function(account) {
            vm.user = copyAccount(account);
            vm.isOwner = vm.user.id == vm.job.createdBy;
            loadAll();
        });
        vm.user = {};
        vm.job = job;

        vm.jobApplications = [];
        var childScope;
        vm.openJobId = $stateParams.openJobId;

        if(vm.openJobId == -1)
            $state.go('job');

        vm.job = job;
        function loadAll() {
            JobApplication.query(function(result) {
                // for (var i = 0; i < result.length; i++) {
                //     if(result[i].userId == vm.user.id)
                //         vm.jobApplications.push(result[i]);
                // }
                vm.jobApplications = result;
                vm.searchQuery = null;
            });
        }

        
    }
})();
