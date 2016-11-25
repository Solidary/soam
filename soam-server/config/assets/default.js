'use strict';

module.exports = {
  client: {
    lib: {
      css: [
        // bower:css
        'public/lib/bootstrap/dist/css/bootstrap.css',
        // 'public/lib/bootstrap/dist/css/bootstrap-theme.css',
        'public/lib/components-font-awesome/css/font-awesome.css'
        // endbower
      ],
      js: [
        // bower:js
        'public/lib/angular/angular.js',
        'public/lib/angular-resource/angular-resource.js',
        'public/lib/angular-animate/angular-animate.js',
        'public/lib/angular-messages/angular-messages.js',
        'public/lib/angular-ui-router/release/angular-ui-router.js',
        'public/lib/angular-bootstrap/ui-bootstrap-tpls.js',
        'public/lib/angular-file-upload/dist/angular-file-upload.js',
        'public/lib/owasp-password-strength-test/owasp-password-strength-test.js',
        // endbower
        'public/lib/jquery/jquery.min.js',
        "public/lib/bootstrap/dist/js/bootstrap.min.js",
        "public/lib/smoothscroll/dist/smoothscroll.js",
        "public/lib/jquery.scrollTo/jquery.scrollTo.min.js",
        "public/lib/jquery.localScroll/jquery.localScroll.min.js",
        "public/lib/owl.carousel/dist/owl.carousel.min.js",
        "public/lib/nivo-lightbox/dist/nivo-lightbox.min.js",
        "public/lib/jquery-simple-expand/src/simple-expand.min.js",
        "public/lib/wow/dist/wow.min.js",
        "public/lib/jquery.stellar/jquery.stellar.min.js",
        "public/lib/retinajs/dist/retina.min.js",
        "public/lib/jQuery-One-Page-Nav/jquery.nav.js",
        // "public/lib/jquery.onepage-scroll/dist/jquery.onepage-scroll.min.js",
        "public/lib/matchmedia/matchMedia.js",
        "public/lib/ajaxchimp/jquery.ajaxchimp.min.js",
        "public/lib/fitvids/jquery.fitvids.js",
      ],
      tests: ['public/lib/angular-mocks/angular-mocks.js']
    },
    css: [
      'modules/*/client/css/*.css'
    ],
    less: [
      'modules/*/client/less/*.less'
    ],
    sass: [
      'modules/*/client/scss/*.scss'
    ],
    js: [
      'modules/core/client/app/config.js',
      'modules/core/client/app/init.js',
      'modules/*/client/*.js',
      'modules/*/client/**/*.js'
    ],
    img: [
      'modules/**/*/img/**/*.jpg',
      'modules/**/*/img/**/*.png',
      'modules/**/*/img/**/*.gif',
      'modules/**/*/img/**/*.svg'
    ],
    views: ['modules/*/client/views/**/*.html'],
    templates: ['build/templates.js']
  },
  server: {
    gruntConfig: ['gruntfile.js'],
    gulpConfig: ['gulpfile.js'],
    allJS: ['server.js', 'config/**/*.js', 'modules/*/server/**/*.js'],
    models: 'modules/*/server/models/**/*.js',
    routes: ['modules/!(core)/server/routes/**/*.js', 'modules/core/server/routes/**/*.js'],
    sockets: 'modules/*/server/sockets/**/*.js',
    config: ['modules/*/server/config/*.js'],
    policies: 'modules/*/server/policies/*.js',
    views: ['modules/*/server/views/*.html']
  }
};
