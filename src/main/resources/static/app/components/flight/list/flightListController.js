angular.module('app')
    .controller('FlightListController', function (FlightService) {
        const vm = this;
        vm.page = 1;

        vm.flights = FlightService.getAll({page: vm.page});

        vm.search = (keyword, category) => {
            vm.page = 1;
            vm.flights = FlightService.getAll({keyword, category, page: vm.page});
        };

        vm.getPrevPage = (keyword, category) => {
            vm.page = vm.page - 1;
            vm.flights = FlightService.getAll({keyword, category, page: vm.page});
        };

        vm.getNextPage = (keyword, category) => {
            vm.page = vm.page + 1;
            vm.flights = FlightService.getAll({keyword, category, page: vm.page});
        };
    });