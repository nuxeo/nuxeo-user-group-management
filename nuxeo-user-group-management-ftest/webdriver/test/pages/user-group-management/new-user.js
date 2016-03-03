'use strict';

export default class NewUser {

  constructor(selector) {
    this.selector = selector;
  }

  get isVisible() {
    return driver.waitForVisible(this.selector) && driver.waitForVisible('form');
  }
}
