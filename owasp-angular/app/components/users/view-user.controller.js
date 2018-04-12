angular.module('devoxxApp.controllers').controller('ViewUserController', ['$scope', '$http', '$log', '$stateParams', '$state', 'Users',
      function ($scope, $http, $log, $stateParams ,$state, Users) {


        // $scope.users = User.query();

         $scope.state = "new";

            // Check if we have a ressource
         	if ($stateParams.id) {
         		$scope.state = "update";
         		$scope.user = Users.get({id: $stateParams.id},
                    function(data) {
                        $scope.admin = data.roles.findIndex(function(element) {
                            return element.role ==="super";
                        }) !== -1;
                    });//, $scope.admin = user.roles.indexOf({role: "super"})!== -1);
         	} else {
            $scope.user = {name: ""}
          }
         	$scope.getBtnLabel = function() {
         		return ($scope.state == "new") ? "Créer" : "Modifier";
         	};

            $scope.saveUserWithAdmin = function () {
                $scope.admin = true;
                $scope.saveUser();
            };

         	$scope.saveUser = function() {

                var index = $scope.user.roles.findIndex(function(element) {
                    return element.role ==="super";
                });

                if ($scope.admin) {
                    if (index === -1) $scope.user.roles.push({role: "super"})
                } else {
                    if (index !== -1) $scope.user.roles.splice(index, 1);
                }

         		if ($scope.state === "new") {
                    Users.save($scope.user,
                      function(success) {
                         $scope.alerts.push({type: 'success', msg: 'Utilisateur ' + success.name + ' enregistré'});
                         $state.go('users');
                      },
                      function(error) {
                          $scope.alerts.push({type: 'danger', msg: 'Impossible d\'enregistrer l\'utilisateur ' + '  : ' + error.status + "-" + error.statusText, details: error.data});
                       });
         		} else {
         	        Users.update($scope.user,
                      function(success) {
                         $scope.alerts.push({type: 'success', msg: 'Utilisateur ' + success.name + ' modifié'});
                         $state.go('users');
                      },
                      function(error) {
                         $scope.alerts.push({type: 'danger', msg: 'Impossible d\'enregistrer les modifications de l\'utilisateur '  +' : ' + error.status + "-" + error.statusText, details: error.data});
                      });
         		}

         	}

    }]);
