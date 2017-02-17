(function() {
    'use strict';
    angular
        .module('isoftnetApp')
        .factory('JobStatus', JobStatus);

    JobStatus.$inject = ['$resource', 'DateUtils'];

    function JobStatus ($resource, DateUtils) {
        var resourceUrl =  'api/job-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                        data.updatedOn = DateUtils.convertDateTimeFromServer(data.updatedOn);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
