import { RevotubeStaticPage } from './app.po';

describe('revotube-static App', () => {
  let page: RevotubeStaticPage;

  beforeEach(() => {
    page = new RevotubeStaticPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('rt works!');
  });
});
