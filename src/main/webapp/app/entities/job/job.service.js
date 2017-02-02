(function() {
    'use strict';
    angular
        .module('isoftnetApp')
        .factory('Job', Job);

    Job.$inject = ['$resource', 'DateUtils'];

    function Job ($resource, DateUtils) {
        var resourceUrl =  'api/jobs/:id';

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
