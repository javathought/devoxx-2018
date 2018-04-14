var proxy = require('http-proxy-middleware');

var apiProxy = proxy('/myapp', {
    target: 'http://localhost:8084',
    changeOrigin: true   // for vhosted sites
});

module.exports = {
  "port": 9095,
  "files" : [" ./**/*.{js, html, css} "],
  "server" : {
    "baseDir" : ".",
    "middleware": {
      1: apiProxy
    },
  "https": true,
  "httpOnly": true,  // dont let browser javascript access cookie ever
  "secure": true // only use cookie over https
  }

};
