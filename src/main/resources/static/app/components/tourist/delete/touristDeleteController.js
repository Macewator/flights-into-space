angular.module('app')
    .controller('TouristDeleteController', function ($routeParams, $location, TouristService) {
        const vm = this;
        const touristId = $routeParams.touristId;
        vm.tourist = TouristService.get(touristId);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const deleteCallback = () => {
            $location.path(`/tourists`);
            vm.msg = 'Tourist is delete';
        };

        vm.deleteTourist = () => {
            TouristService.delete(vm.tourist)
                .then(deleteCallback)
                .catch(errorCallback);
        };
    });