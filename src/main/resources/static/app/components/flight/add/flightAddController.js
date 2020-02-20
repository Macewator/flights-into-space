angular.module('app')
    .controller('FlightAddController', function ($location, FlightService, Flight) {
        const vm = this;
        vm.flight = new Flight();

        const saveCallback = () => {
            $location.path(`/flight-add-tourists/${vm.user.id}`);
        };
        const errorCallback = err => {
            vm.msg = `Save error: ${err.data.message}`;
        };

        vm.saveFlight = () => {
            FlightService.save(vm.flight)
                .then(saveCallback)
                .catch(errorCallback);
        };
    });
