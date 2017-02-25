(function() {
    'use strict';
    angular
        .module('isoftnetApp')
        .controller('JobController', JobController);
    JobController.$inject = ['$scope', '$state', 'Job', '$timeout', '$stateParams', '$compile', 'Principal'];
    function JobController ($scope, $state, Job, $timeout, $stateParams, $compile, Principal) {
        $(".container").css('width', '100%');
        //  $rootScope.$on('event:social-sign-in-success', function(event, userDetails){
        //     // console.log(userDetails);
        // });
        // $rootScope.$on('event:social-sign-out-success', function(event, logoutStatus){
        //     console.log(logoutStatus);
        // });
        // $rootScope.$on('event:contact-created', function(event, contactInfo){
        //     function onSaveSuccess (result) {
        //         $rootScope.$broadcast('isoftnetApp:contactCreate', result);
        //     }
        //     function onSaveError (err) {
        //         console.log(err);
        //     }
        //     Contact.save(contactInfo, onSaveSuccess, onSaveError);
        // });
        // $scope.signoutSocialMedia = function() {
        //     socialLoginService.logout('facebook');
        // };
        
        // $scope.authenticate = function(provider) {
        //     $auth.authenticate(provider)
        //         .then(function(response) {
        //             // Signed in with Google.
        //             console.log(response);
        //         })
        //         .catch(function(response) {
        //             // Something went wrong.
        //         });
        // };
        var vm = this;
        
        function format ( d ) {

            var selectID = parseInt($(d[2]).html());
            var selectJob;
            for (var i = 0; i < vm.jobs.length; i++) {
                if(selectID == vm.jobs[i].id){
                    selectJob = i;
                    break;
                }
            }
            childScope = $scope.$new();
            childScope.job = vm.jobs[selectJob];
            vm.openJobId = selectID;
            var compiledDirective = $compile('<job-detail ng-model="job"></job-detail');
            return compiledDirective(childScope);
        }
        function hide ( tr ) {
            var row = vm.table.row( tr );
            if( !row )
                return;
            row.child.hide();
            tr.removeClass('shown');    
            $(this).parent().removeClass("active");
        }
        function expand ( tr ) {
            var row = vm.table.row( tr );
            if( !row )
                return;
            tr.addClass("active");
            row.child( format( row.data()) ).show();
            tr.addClass('shown');
        }
        $timeout(function() {
            vm.table = $('#job-table').DataTable( {
                "columns": [
                    { "orderable": false },
                    { "orderable": false },
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    // null,
                    { "orderable": false },
                ],
                "order": [[2, 'asc']]
            } );
            // Add event listener for opening and closing details
            $('#job-table tbody').on('click', 'td.details-control', function () {
                // This row is already open - close it
                if(vm.openJobId != -1){
                    var tr = $(this).closest('tr');
                    var row = vm.table.row( tr );
                    if( !row )
                        return;
                    var d = row.data()
                    var selectID = parseInt($(d[2]).html());
                    if(vm.openJobId == selectID){
                        hide(tr);
                        vm.openJobId = -1;
                    }
                    else{
                        tr = $("tr#tr_"+vm.openJobId);
                        hide(tr);
                        tr = $(this).closest('tr');
                        expand(tr);
                    }
                }
                else{
                    var tr = $(this).closest('tr');
                    expand(tr);
                }
                console.log(vm.openJobId);
            } );

            if ($stateParams.openJobId != -1) {
                var tr = $("tr#tr_"+$stateParams.openJobId);
                expand(tr);
            }
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
        });
        vm.user = {};
        vm.selectIDs = [];
        vm.jobs = [];
        vm.openJobId = $stateParams.openJobId;
        loadAll();
        var childScope;

        $scope.openJob = function(id){
            $state.go("job", {
                openJobId : id
            });
        }
        $scope.addCheck = function(id){
            if( vm.selectIDs.indexOf(id) == -1 )
                vm.selectIDs.push(id);
            else
                vm.selectIDs.splice(vm.selectIDs.indexOf(id), 1);
        }
        $scope.addStatus = function(){
            $state.go("job.addstatus", {
                selectIDs : vm.selectIDs
            });
            // var arr = $("#job-table tr td:nth-child(1) input:checked");
            // for (var i = 0; i < arr.length; i++) {
            //     console.log($(arr[i]).parent().parent().children("td:nth-child(3)").val());
            // }
        }
        $scope.closeJobs = function(){
            $state.go("job.closejobs", {
                selectIDs : vm.selectIDs
            });
            // var arr = $("#job-table tr td:nth-child(1) input:checked");
            // for (var i = 0; i < arr.length; i++) {
            //     console.log($(arr[i]).parent().parent().children("td:nth-child(3)").val());
            // }
        }
        $scope.deleteJobs = function(){
            $state.go("job.deletejobs", {
                selectIDs : vm.selectIDs
            });
            // var arr = $("#job-table tr td:nth-child(1) input:checked");
            // for (var i = 0; i < arr.length; i++) {
            //     console.log($(arr[i]).parent().parent().children("td:nth-child(3)").val());
            // }
        }
        function loadAll() {
            Job.query(function(result) {
                vm.jobs = result;
                vm.searchQuery = null;
            });
        }
    }
})();