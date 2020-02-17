angular.module('app')
    .controller('TouristDeleteController', function($routeParams, $timeout, TouristService) {
        const vm = this;
        const touristId = $routeParams.touristId;
        if(touristId)
            vm.tourist = TouristService.get(touristId);

        const errorCallback = err => {
            vm.msg=`Error: ${err.data.message}`;
        };

        const deleteCallback = response =>
            vm.msg='Tourist is delete';

        vm.deleteTourist = () => {
            TouristService.delete(vm.tourist)
                .then(deleteCallback)
                .catch(errorCallback);
        };
    });