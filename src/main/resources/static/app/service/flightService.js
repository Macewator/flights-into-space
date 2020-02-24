angular.module('app')
    .constant('FLIGHT_ENDPOINT', '/api/flights/:id')
    .constant('FLIGHT_TOURISTS_ENDPOINT', '/api/flights/:id/tourists?page=:page')
    .constant('FLIGHT_MANAGE_TOURISTS_ENDPOINT', '/api/flights/:id/tourists/:tr?action=:option')
    .constant('AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT', '/api/flights/tourists/:id?page=:page')
    .factory('Flight',
        function ($resource, FLIGHT_ENDPOINT, FLIGHT_TOURISTS_ENDPOINT,
                  FLIGHT_MANAGE_TOURISTS_ENDPOINT, AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT) {
        return $resource(
            FLIGHT_ENDPOINT, {id: '@_id'}, {
                update: {
                    method: 'PUT',
                    url: FLIGHT_MANAGE_TOURISTS_ENDPOINT,
                    params:
                        {id: '@id', tr: '@tr', option: '@option'}
                },
                getTourists: {
                    method: 'GET',
                    url: FLIGHT_TOURISTS_ENDPOINT,
                    params: {id: '@id', page: '@page'},
                    isArray: true
                },
                getAvailableFlights: {
                    method: 'GET',
                    url: AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT,
                    params: {id: '@id', page: '@page'},
                    isArray: true
                }
            });
    })
    .service('FlightService', function (Flight) {
        this.getAll = params => Flight.query(params);
        this.getAvailableFlights = (index, page) => Flight.getAvailableFlights({id: index, page: page});
        this.get = index => Flight.get({id: index});
        this.getTourists = (index, page) => Flight.getTourists({id: index, page: page});
        this.save = flight => flight.$save();
        this.update = (flight, tourist, option) => flight.$update({id: flight.id, tr: tourist.id, option: option});
        this.delete = flight => flight.$delete({id: flight.id});
    });
