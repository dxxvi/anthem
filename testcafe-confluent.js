import { ClientFunction, Selector } from 'testcafe';

const fs = require('fs');
const existingPages = [];

// returns all files under the currentDirectory
function findFiles(currentDirectory) {
  return fs.readdirSync(currentDirectory, { withFileTypes: true })
      .flatMap(dirent => {
        if (dirent.isFile())
          return [`${currentDirectory}/${dirent.name}`];
        else if (dirent.isDirectory())
          return findFiles(`${currentDirectory}/${dirent.name}`);
        else
          return []
      })
}

fixture `confluent`
  .page `https://docs.confluent.io/current/`
  ;

test('docs', async t => {
  const getContent = function(cssSelector) {
    return ClientFunction(() => {
      let result = '';
      document.querySelectorAll(_cssSelector).forEach(section => {
          const doc = new DOMParser().parseFromString(section.outerHTML, 'text/html');
          [
            'div.wy-side-nav-search, div#c-versions, script, p.footer-feedback-link',
            'div.rating-section[id^="pd_rating_holder"], div.copy-code-button, div.c-breadcrumbs, .wy-nav-top',
            'div.sticky-menu-column, .rst-content h1 .headerlink, .rst-content h2 .headerlink',
            '.rst-content .toctree-wrapper p.caption .headerlink, .rst-content h3 .headerlink',
            '.rst-content h4 .headerlink, .rst-content h5 .headerlink, .rst-content h6 .headerlink',
            '.rst-content dl dt .headerlink, .rst-content p.caption .headerlink'
          ].forEach(css => {
            doc.querySelectorAll(css).forEach(el => {
              el.parentNode.removeChild(el);
            });
          });
          result = new XMLSerializer().serializeToString(doc)
              .replace('</body></html>', '')
              .replace('<html xmlns="http://www.w3.org/1999/xhtml"><head></head><body>', '');
      });
      return result
    }, {
      dependencies: { _cssSelector: cssSelector }
    })
  };
  const getPageLinks = ClientFunction(() => {
    const result = [];
    document.querySelectorAll('nav.wy-nav-side.show-left-nav a[href$="html"]').forEach(a => {
      if (a.clientHeight > 0) {
        result.push(a.href)
      }
    });
    return result;
  });

  findFiles('.').forEach(file => {
    existingPages.push(file.replace('./', ''))
  });

  await t.maximizeWindow();

  const pageLinks = Array.from(await getPageLinks());
  const alreadyNavigated = [];

  while (pageLinks.length > 0) {
    let n = pageLinks.findIndex(pageLink => { // find a pageLink not in existingPages
      const s = pageLink.replace('https://docs.confluent.io/current/', '');
      return existingPages.indexOf(s) < 0
    });
    if (n < 0) {
      n = Math.floor(Math.random() * pageLinks.length);
    }

    const s = pageLinks[n].replace('https://docs.confluent.io/current/', ''); // s is like security/rbas/mds-api.html

    await t
      .navigateTo(pageLinks[n])
      .wait(2000);
    const i = s.lastIndexOf("/");
    if (i > 0) {
      fs.mkdirSync(s.substring(0, i), { recursive: true });
    }
    if (!fs.existsSync(s)) {
      const content = await getContent('div.wy-grid-for-nav')();
      fs.appendFileSync(s, content);
      console.log(`Found new page ${pageLinks[n]}`)
    }

    alreadyNavigated.push(pageLinks[n]);
    pageLinks.splice(n, 1);
    console.log(`${pageLinks.length} ${alreadyNavigated.length}`);
    const _pageLinks = Array.from(await getPageLinks());
    _pageLinks.forEach(_pageLink => {
      if (pageLinks.indexOf(_pageLink) < 0 && alreadyNavigated.indexOf(_pageLink) < 0) {
        pageLinks.push(_pageLink);
      }
    });
  }
})
