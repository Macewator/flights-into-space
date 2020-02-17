angular.module('app')
    .controller('FlightDeleteController', function($routeParams, $timeout, FlightService) {
        const vm = this;
        const flightId = $routeParams.flightId;
        if(flightId)
            vm.flight = FlightService.get(flightId);

        const errorCallback = err => {
            vm.msg=`Error: ${err.data.message}`;
        };

        const deleteCallback = response =>
            vm.msg='Flight is delete';

        vm.deleteFlight = () => {
            FlightService.delete(vm.flight)
                .then(deleteCallback)
                .catch(errorCallback);
        };
    });