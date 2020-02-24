angular.module('app')
    .controller('TouristListController', function(TouristService) {
        const vm = this;
        vm.page = 1;

        vm.tourists = TouristService.getAll({page: vm.page});

        vm.search = lastName => {
            vm.page = 1;
            vm.tourists = TouristService.getAll({lastName, page: vm.page});
        };

        vm.getPrevPage = lastName => {
            vm.page = vm.page - 1;
            vm.tourists = TouristService.getAll({lastName, page: vm.page});
        };

        vm.getNextPage = lastName => {
            vm.page = vm.page + 1;
            vm.tourists = TouristService.getAll({lastName, page: vm.page});
        };
    });