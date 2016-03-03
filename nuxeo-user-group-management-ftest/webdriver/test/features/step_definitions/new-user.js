'use strict';

module.exports = function () {
  this.Then('I can see the New User page', () => this.management.newUser.isVisible.should.be.true);
};
