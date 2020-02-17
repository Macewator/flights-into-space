angular.module('app')
    .config(function ($routeProvider) {
        $routeProvider
            .when('/tourists', {
                templateUrl: 'app/components/tourist/list/touristList.html',
                controller: 'TouristListController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-edit/:touristId', {
                templateUrl: 'app/components/tourist/edit/touristEdit.html',
                controller: 'TouristEditController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-add', {
                templateUrl: 'app/components/tourist/add/touristAdd.html',
                controller: 'TouristAddController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-delete/:touristId', {
                templateUrl: 'app/components/tourist/delete/touristDelete.html',
                controller: 'TouristDeleteController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-flights/:touristId', {
                templateUrl: 'app/components/tourist/flights/touristFlights.html',
                controller: 'TouristEditController',
                controllerAs: 'ctrl'
            })
            .when('/flight-delete/:flightId', {
                templateUrl: 'app/components/flight/delete/dupa.html',
                controller: 'FlightDeleteController',
                controllerAs: 'ctrl'
            })
            .otherwise({
                redirectTo: '/tourists'
            });
    });