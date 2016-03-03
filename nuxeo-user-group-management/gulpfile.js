/*
(C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    Gabriel Barata <gbarata@nuxeo.com>
 */

'use strict';

// Include Gulp & tools we'll use
var gulp = require('gulp');
var $ = require('gulp-load-plugins')();
var del = require('del');
var runSequence = require('run-sequence');
var path = require('path');

var AUTOPREFIXER_BROWSERS = [
  'ie >= 10',
  'ie_mob >= 10',
  'ff >= 30',
  'chrome >= 34',
  'safari >= 7',
  'opera >= 23',
  'ios >= 7',
  'android >= 4.4',
  'bb >= 10'
];

var DIST = 'target/classes/web/nuxeo.war/user-group-management';

var dist = function(subpath) {
  return !subpath ? DIST : path.join(DIST, subpath);
};

var BOWER = dist('bower_components');

var bower_components = function(subpath) {
  return !subpath ? BOWER : path.join(BOWER, subpath);
};

var APP = dist('bower_components/nuxeo-ui-elements')

var app = function(subpath) {
  return !subpath ? APP : path.join(APP, subpath);
};

gulp.task('copy', function() {
  gulp.src([
    bower_components('select2/select2.js'),
    bower_components('select2/select2.css'),
    bower_components('select2/select2.png'),
    bower_components('jquery/dist/jquery.js'),
    bower_components('webcomponentsjs/webcomponents-lite.min.js')
  ]).pipe(gulp.dest(dist()));
});

// Vulcanize granular configuration
gulp.task('vulcanize', function() {
  return gulp.src(app('nuxeo-user-group-management.html'))
      .pipe($.vulcanize({
        stripComments: true,
        inlineCss: true,
        inlineScripts: true
      }))
      //.pipe($.minifyInline())
      .pipe(gulp.dest(dist()))
      .pipe($.size({title: 'vulcanize'}));
});

// Strip unnecessary stuff
gulp.task('strip', function() {
  return del([
    dist('bower_components')
  ]);
});


// Clean output directory
gulp.task('clean', function() {
  return del(['.tmp']);
});

// Build production files, the default task
gulp.task('default', ['clean'], function(cb) {
  runSequence(
      'copy',
      'vulcanize',
      'strip',
      cb);
});
