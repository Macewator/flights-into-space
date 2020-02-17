angular.module('app')
    .constant('FLIGHT_ENDPOINT', '/api/flights/:id')
    .constant('FLIGHT_TOURISTS_ENDPOINT', '/api/flights/:id/tourists')
    .constant('AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT', '/api/flights/tourists/:id')
    .factory('Flight', function ($resource, FLIGHT_ENDPOINT, FLIGHT_TOURISTS_ENDPOINT, AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT) {
        return $resource(
            FLIGHT_ENDPOINT, {id: '@_id'}, {
                update: {
                    method: 'PUT'
                },
                getTourists: {
                    method: 'GET',
                    url: FLIGHT_TOURISTS_ENDPOINT,
                    params: {id: '@id'},
                    isArray: true
                },
                getAvailableFlights: {
                    method: 'GET',
                    url: AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT,
                    params: {id: '@id'},
                    isArray: true
                }
            });
    })
    .service('FlightService', function (Flight) {
        this.getAll = params => Flight.query(params);
        this.getAvailableFlights = index => Flight.getAvailableFlights({id: index});
        this.get = index => Flight.get({id: index});
        this.getTourists = index => Flight.getTourists({id: index});
        this.save = flight => flight.$save();
        this.update = flight => flight.$update({id: flight.id})
        this.delete = flight => flight.$delete({id: flight.id})
    });
