'use strict';

angular.module('devoxxApp.controllers')
.controller('ListUsersController',
    ['$scope', '$rootScope', '$state', 'Users',
    function ($scope, $rootScope, $state, Users) {

      refresh();
      // action bouton Rafraichir
      $scope.refresh = refresh;

      function refresh() {
        $scope.users = Users.query();
//          $scope.clients = Client.query({page: $scope.currentPage});
      }

      $scope.deleteuser = function(user) {
        user.$delete(function(success) {
           $scope.alerts.push({type: 'success', msg: 'Utilisateur supprimé'});
           var index = $scope.users.indexOf(user);
           $scope.users.splice(index, 1);

           // $state.go('users');
        },
        function(error) {
           $scope.alerts.push({type: 'danger', msg: 'Impossible de supprimer l\'utilisateur '  +' : ' + error.status + "-" + error.statusText, details: error.data});
        });
      };

    }]);
