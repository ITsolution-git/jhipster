(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('JobController', JobController);

    JobController.$inject = ['$scope', '$state', 'Job', '$timeout'];

    function JobController ($scope, $state, Job, $timeout) {
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
        var mock1 = {
            "id" : 0,
            "title" : "Django",
            "profession" : "python",
            "duration" : "30",
            "applicantsCount" : 2,
            "referralFee" : 299,
            "company" : "SteelPlant",
            "shortDescription" : "web developer position",
            "longDescription" : "sb who",
            "type" : "FULL_TIME",
            "status" : "OPEN",
            "workPermit" : "ANY"
        };
        vm.mockData = [];
        $timeout(function() {

            for (var i = 0; i < 20; i++) {
                var temp = {};
                angular.copy(mock1, temp);
                temp.id = i+1;
                vm.mockData.push(temp);
            }
            $timeout(function(){
                var table = $('#job-table').DataTable( {
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
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        { "orderable": false },
                    ],
                    "order": [[2, 'asc']]
                } );
                function format ( d ) {
                // `d` is the original data object for the row
                    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
                        '<tr>'+
                            '<td>Extra info:</td>'+
                            '<td>And any further details here (images etc)...</td>'+
                        '</tr>'+
                    '</table>';
                }
         
                // Add event listener for opening and closing details
                $('#job-table tbody').on('click', 'td.details-control', function () {
                    var tr = $(this).closest('tr');
                    var row = table.row( tr );
             
                    if ( row.child.isShown() ) {
                        // This row is already open - close it
                        row.child.hide();
                        tr.removeClass('shown');
                    }
                    else {
                        // Open this row
                        row.child( format( row.data()) ).show();
                        tr.addClass('shown');
                    }
                } );


            }, 100);
        }, 1000);
        

        vm.jobs = [];

        loadAll();

        function loadAll() {
            Job.query(function(result) {
                vm.jobs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
