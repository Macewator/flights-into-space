/*
angular.module('app')
    .controller('FlightEditController', function($routeParams, $location, $timeout, FlightService) {
        const vm = this;
        const flightId = $routeParams.flightId;
        if(flightId)
            vm.flight = FlightService.get(flightId);

        const errorCallback = err => {
            vm.msg=`Błąd zapisu: ${err.data.message}`;
        };

        const updateCallback = response =>
        vm.msg='Zapisano zmiany';

        vm.updateFlight = () => {
            FlightService.update(vm.flight)
                .then(updateCallback)
                .catch(errorCallback);
        };

    });*/
