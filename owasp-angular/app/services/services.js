angular.module('devoxxApp.services', ['ngResource']);


angular.module('devoxxApp.services').factory('alert', function(){
    return { alerts: []}
});


//angular.module('devoxxApp.services').factory('User', function ($resource, Common) {

//  var API_URI = Common.root_api + '/users';

//  return $resource(API_URI , {}, {
//    getRoles:  {method: 'GET', url: Common.root_api +'/authenticate', isArray:true}
//  });

//});

angular.module('devoxxApp.services').service('Common', function () {
  this.root_api = '/myapp';
  this.parseRange = parseRange;

  function parseRange(hdr) {
      var m = hdr && hdr.match(/^(?:items )?(\d+)-(\d+)\/(\d+|\*)$/);
      if(m) {
          return {
              from: +m[1],
              to: +m[2],
              total: m[3] === '*' ? Infinity : +m[3]
          };
      } else if(hdr === '*/0') {
          return { total: 0 };
      }
      return null;
  }

  });
