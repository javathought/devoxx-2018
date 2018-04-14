angular.module('devoxxApp.services').factory('Users', function ($resource, Common) {

  var API_URI = Common.root_api + '/users/:id';

  return $resource(API_URI , {id: '@id'}, {
    update:  {method: 'PUT'},
    current: {method: 'GET', url: Common.root_api +'/users/current'},
    todos:   {method: 'GET', url: Common.root_api +'/users/:id/todos', isArray: true}
  });

});
