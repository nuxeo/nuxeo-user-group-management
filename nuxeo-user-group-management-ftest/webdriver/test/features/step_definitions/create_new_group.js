'use strict';

module.exports = function () {

  this.When('I create a new group named "$group"', (group) => this.management.newGroup.create(group));

};
