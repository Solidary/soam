'use strict';

/**
 * Module dependencies
 */
var passport = require('passport'),
  LocalStrategy = require('passport-local').Strategy,
  User = require('mongoose').model('User');

module.exports = function () {
  // Use local strategy
  passport.use(new LocalStrategy({
    usernameField: 'email', // 'phone', //'username',
    passwordField: 'password'
  },
  function(email, password, done) { // function (username, password, done) {
    console.log(email + " --- " + password);
    User.findOne({
      // username: username.toLowerCase()
      // phone: phone
      email: email
    }, function (err, user) {
      if (err) {
        return done(err);
      }
      if (!user || !user.authenticate(password)) {
        return done(null, false, {
          // message: 'Invalid username or password'
          message: 'Invalid email or password'
        });
      }

      return done(null, user);
    });
  }));
};
