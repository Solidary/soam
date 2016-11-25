'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * Element Schema
 */
var ElementSchema = new Schema({
  name: {
    type: String,
    required: 'Please fill element name',
    trim: true
  },
  price: {
      type: Number,
      required: 'Please fill element price',
      min: 0
  },
  tags: [{
      type: String
  }],
  elements: [{
      type: Schema.ObjectId,
      ref: 'Element'
  }]
});

mongoose.model('Element', ElementSchema);
