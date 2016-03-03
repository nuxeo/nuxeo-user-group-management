'use strict';

module.exports = function () {
  this.Then('I can see the New Group page', () => this.management.newGroup.isVisible.should.be.true);
};
