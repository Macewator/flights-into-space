/*
angular.module('app')
    .controller('FlightAddController', function ($routeParams, $location, $timeout, FlightService, Flight) {
        const vm = this;
        vm.flight = new Flight();

        const saveCallback = () => {
            $location.path(`/flight-edit/${vm.flight.id}`);
        };
        const errorCallback = err => {
            vm.msg = `Błąd zapisu: ${err.data.message}`;
        };

        vm.saveFlight = () => {
            FlightService.save(vm.flight)
                .then(saveCallback)
                .catch(errorCallback);
        };
    });*/
