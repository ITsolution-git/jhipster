(function() {
    'use strict';
    angular
        .module('isoftnetApp')
        .factory('JobApplication', JobApplication);

    JobApplication.$inject = ['$resource', 'DateUtils'];

    function JobApplication ($resource, DateUtils) {
        var resourceUrl =  'api/job-applications/:id';

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
