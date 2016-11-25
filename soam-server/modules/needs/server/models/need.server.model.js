'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * Need Schema
 */
var NeedSchema = new Schema({
  description: {
    type: String,
    required: 'Please fill need description',
    trim: true
  },
  tags: [{
      type: String
  }],
  list: [{
      element: {
          type: Schema.ObjectId,
          ref: 'Element'
      },
      quantity: {
          type: Number,
          min: 1
      }
  }],
  created: {
    type: Date,
    default: Date.now
  },
  user: {
    type: Schema.ObjectId,
    ref: 'User'
  }
});

mongoose.model('Need', NeedSchema);
