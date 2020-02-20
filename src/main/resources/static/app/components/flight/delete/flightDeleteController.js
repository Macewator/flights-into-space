angular.module('app')
    .controller('FlightDeleteController', function ($routeParams, $location, FlightService) {
        const vm = this;
        const flightId = $routeParams.flightId;
        vm.flight = FlightService.get(flightId);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const deleteCallback = () => {
            $location.path(`/flights`);
            vm.msg = 'Flight is delete';
        };

        vm.deleteFlight = () => {
            FlightService.delete(vm.flight)
                .then(deleteCallback)
                .catch(errorCallback);
        };
    });
