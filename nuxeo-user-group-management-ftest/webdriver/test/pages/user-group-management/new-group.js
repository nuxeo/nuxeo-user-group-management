'use strict';

export default class NewGroup {

  constructor(selector) {
    this.selector = selector;
  }

  get isVisible() {
    return driver.waitForVisible(this.selector) && driver.waitForVisible('form');
  }

  create(groupName) {
    driver.waitForVisible('nuxeo-create-group #groupName');
    driver.setValue('nuxeo-create-group  #groupName', groupName);
    driver.click('nuxeo-create-group #createButton');
  }
}
