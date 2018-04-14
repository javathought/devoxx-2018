'use strict';

angular.module('Authentication')

.controller('LoginController',
    ['$scope', '$rootScope', '$location', '$state', 'AuthenticationService',
    function ($scope, $rootScope, $location, $state, AuthenticationService) {
        // reset login status
        AuthenticationService.ClearCredentials();

        $('#login-modal').modal('toggle');

        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.username, $scope.password, function(response) {
                if(response.success) {
                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                    $('#login-modal').removeClass('fade');$('#login-modal').modal('hide');
                    $state.go('home'); 
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);
