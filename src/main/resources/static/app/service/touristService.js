angular.module('app')
    .constant('TOURIST_ENDPOINT', '/api/tourists/:id')
    .constant('TOURIST_FLIGHTS_ENDPOINT', '/api/tourists/:id/flights')
    /*.constant('TOURIST_FLIGHT_DELETE_ENDPOINT', '/api/tourists/:idT/flights/:idF')*/
    .factory('Tourist', function ($resource, TOURIST_ENDPOINT, TOURIST_FLIGHTS_ENDPOINT) {
        return $resource(
            TOURIST_ENDPOINT, {id: '@_id'}, {
                update: {
                    method: 'PUT'
                },
                getFlights: {
                    method: 'GET',
                    url: TOURIST_FLIGHTS_ENDPOINT,
                    params: {id: '@id'},
                    isArray: true
                },
                /*removeTouristFlight: {
                    method: 'DELETE',
                    url: TOURIST_FLIGHT_DELETE_ENDPOINT,
                    params: {
                        idT: '@id',
                        idF: '@id'
                    },
                    isArray: true
                },*/
            });
    })
    .service('TouristService', function (Tourist) {
        this.getAll = params => Tourist.query(params);
        this.get = index => Tourist.get({id: index});
        this.getFlights = index => Tourist.getFlights({id: index});
        this.save = tourist => tourist.$save();
        this.update = tourist => tourist.$update({id: tourist.id})
        this.delete = tourist => tourist.$delete({id: tourist.id})
    });