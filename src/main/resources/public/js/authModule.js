var authModule = angular.module('authModule', []);

authModule.run(['$rootScope', '$location', 'authProvider', function ($rootScope, $location, authProvider) {
    $rootScope.$on('$routeChangeStart', function (event) {

        if (!authProvider.isLoggedIn()) {
            console.log('DENY : Redirecting to Login');
            event.preventDefault();
            $location.path('/login');
        }
        else {
            console.log('ALLOW');
        }
    });
}])