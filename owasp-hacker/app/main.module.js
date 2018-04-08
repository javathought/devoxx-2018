//

angular.
module('devoxxApp', [
  'ui.bootstrap',
  'ui.router',
  'ngResource',
  'ngMessages',
  'devoxxApp.controllers',
  'devoxxApp.directives',
  'devoxxApp.services',
  'ngCookies',
  'ui.validate',
  'ngSanitize'
])
 
.config( function ($stateProvider,$urlRouterProvider,$locationProvider,
  $rootScopeProvider, $httpProvider) {

    $httpProvider.defaults.headers.common['X-Requested-By'] = 'XMLHttpRequest';

    $stateProvider
          .state('home', {
            url         : '/',
            templateUrl : 'app/components/home/home.html',
            controller  : 'HomeController'
          })
        .state('todo', {
            url         : '/nice',
            templateUrl : 'app/components/todos/view-todo.html',
            controller  : 'ViewTodoController'
          })
;
    $urlRouterProvider.otherwise('/');
		// $locationProvider.html5Mode(true);

})
  
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh


    }]);
