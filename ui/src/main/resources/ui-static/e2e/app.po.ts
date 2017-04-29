import { browser, element, by } from 'protractor';

export class RevotubeStaticPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('rt-root h1')).getText();
  }
}
