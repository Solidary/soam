'use strict'

var jwt = require('jsonwebtoken');
var redis = require('../../../../../config/redis');
var config = require('../../../../../config/config');

var extractToken = function(headers) {
    if (headers == null) {
        throw new Error('headers is null');
    }
    if (headers.authorization == null) {
        throw new Error('authorization header is null');
    }

    var authorization = headers.authorization;
    var authArr = authorization.split(' ');
    if (authArr.length !== 2) {
        throw new Error('authorization header value is not of length 2');
    }

    var token = authArr[1];
    try {
        jwt.verify(token, config.token.secret);
    } catch(err) {
        throw new Error('the token is not valid');
    }

    return token;
};

var createToken = function(payload, cb) {
    var ttl = config.token.expiration;

    if (payload != null && typeof payload !== 'object') {
        return cb(new Error('payload is an object'));
    }
    if (ttl != null && typeof ttl !== 'number') {
        return cb(new Error('ttl is not a valid number'));
    }

    var token = jwt.sign(payload, config.token.secret, { expiresIn: config.token.expiration });

    // stores a token with payload data for a ttl period of time
    redis.setex(token, ttl, JSON.stringify(payload), function(token, err, reply) {
        if (err) {
            return cb(err);
        }

        if (reply) {
            cb(null, token);
        } else {
            cb(new Error('token not set in redis'));
        }
    }.bind(null, token));
};

var expireToken = function(headers, cp) {
    try {
        var token = extractToken(headers);

        if (token == null) {
            return cb(new Error('token is null'));
        }

        // delete from Redis
        redis.del(token, function(err, reply) {
            if (err) {
                return cb(err);
            }

            if (!reply) {
                return cb(new Error('token not found'));
            }
            return cb(null, true);
        });
    } catch (err) {
        return cb(err);
    }
};

var verifyToken = function(headers, cb) {
    try {
        var token = extractToken(headers);

        if (token == null) {
            return cb(new Error('token is null'));
        }

        // gets the associated data of token
        redis.get(token, function(err, userData) {
            if (err) {
                return cb(err);
            }
            if (!userData) {
                return cb(new Error('token not found'));
            }

            return cb(null, JSON.parse(userData));
        });
    } catch (err) {
        return cb(err);
    }
};

module.exports = {
    createToken: createToken,
    expireToken: expireToken,
    verifyToken: verifyToken
};
