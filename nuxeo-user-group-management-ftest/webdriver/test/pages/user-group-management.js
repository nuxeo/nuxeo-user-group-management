'use strict';

import NewGroup from './user-group-management/new-group';
import NewUser from './user-group-management/new-user';

export default class MANAGEMENT {

  constructor() {
    this.newGroup = new NewGroup('nuxeo-create-group');
    this.newUser = new NewUser('nuxeo-create-user');
  }

  add(entity) {
    driver.click(`paper-button[id='createButton']`);
    driver.waitForVisible('paper-menu');
    driver.click(`span=${entity}`);
  }

  isToastVisible(toast) {
    return driver.waitForVisible(`span=${toast}`) && driver.waitForVisible('input');
  }

  get isVisible() {
    return driver.waitForVisible('nuxeo-user-group-search') && driver.waitForVisible('input');
  }

  static get() {
    driver.url('/user-group-management');
    driver.waitForExist('nuxeo-user-group-management');
    return new MANAGEMENT();
  }

}
