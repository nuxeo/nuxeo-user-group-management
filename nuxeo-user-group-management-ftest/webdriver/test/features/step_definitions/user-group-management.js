'use strict';

import MANAGEMENT from '../../pages/user-group-management';

module.exports = function () {

  this.Given('I am on the "New $entity" page', (entity) => {
    this.management = MANAGEMENT.get();
    this.management.add(entity);
  });

  this.When('I go to the Users and Groups Management', () => {
    this.management = MANAGEMENT.get();
  });

  this.When('I click the "New $entity" button', (entity) => this.management.add(entity));

  this.Then('I can see the Users and Groups Management page', () => this.management.isVisible.should.be.true);

  this.Then('I can see the toast "$toast"', (toast) => this.management.isToastVisible(toast).should.be.true);
};
