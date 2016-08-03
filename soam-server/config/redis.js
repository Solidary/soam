var redis = require('redis');
var config = require('./config.js');

var redisClient = redis.createClient(config.redis.port, config.redis.host);
redisClient.on('connect', function() {
    console.log('Redis connected to ' + config.redis.host + ':' + config.redis.port);
});
redisClient.on('error', function(err) {
    console.log('Redis error ' + err);
});

module.exports = redisClient;
