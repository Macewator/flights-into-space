angular.module('app')
    .controller('FlightAddTouristsController', function ($routeParams, $location, FlightService, TouristService) {
        const vm = this;
        const flightId = $routeParams.flightId;
        vm.page = 1;

        vm.flight = FlightService.get(flightId);
        vm.availableTourists = TouristService.getAvailableTourists(flightId, vm.page);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = () => {
            vm.availableTourists = TouristService.getAvailableTourists(flightId, vm.page);
            vm.msg = 'Tourists added';
        };

        vm.addTourist = tourist => {
            FlightService.update(vm.flight, tourist, 'add')
                .then(updateCallback)
                .catch(errorCallback);
        };

        vm.getPrevPage = () => {
            vm.page = vm.page - 1;
            vm.availableTourists = TouristService.getAvailableTourists(flightId, vm.page);
        };

        vm.getNextPage = () => {
            vm.page = vm.page + 1;
            vm.availableTourists = TouristService.getAvailableTourists(flightId, vm.page);
        };
    });
