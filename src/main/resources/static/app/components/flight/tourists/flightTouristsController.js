angular.module('app')
    .controller('FlightTouristsController', function ($routeParams, $location, FlightService) {
        const vm = this;
        const flightId = $routeParams.flightId;
        vm.page = 1;

        vm.flight = FlightService.get(flightId);
        vm.flightTourists = FlightService.getTourists(flightId, vm.page);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = () => {
            vm.flightTourists = FlightService.getTourists(flightId, vm.page);
            /*$location.path(`/flight-tourists/${vm.flight.id}`);*/
            vm.msg = 'Tourist removed';
        };

        vm.removeTourist = tourist => {
            FlightService.update(vm.flight, tourist, 'remove')
                .then(updateCallback)
                .catch(errorCallback);
        };

        vm.getPrevPage = () => {
            vm.page = vm.page - 1;
            vm.flightTourists = FlightService.getTourists(flightId, vm.page);
        };

        vm.getNextPage = () => {
            vm.page = vm.page + 1;
            vm.flightTourists = FlightService.getTourists(flightId, vm.page);
        };
    });
