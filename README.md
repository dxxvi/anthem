https://XXX.webex.com/XXX/j.php?MTID=mfe87c077cd7d6e53439d18d3fd0479a7 9am PT

t-mobile store: 149 Market Street, Newark, NJ, 07102 (Market & Broad)

```javascript
import { Selector, ClientFunction } from 'testcafe';

const browserscroll = ClientFunction(function() {
    window.scrollBy(0,300)
});

fixture `Getting Started`
    .page `https://demo.docusign.net/member/Home.aspx`;

test('My first test', async t => {
    await t
        .setTestSpeed(.8)
        .maximizeWindow()
        .typeText('#ds_hldrBdy_txtDocuLogin', 'XXXX')
        .typeText('#ds_hldrBdy_txtDocuPassword', 'YYYY')
        .click('#ds_hldrBdy_btnDSLogin_btnInline')
        .wait(10000)
        .click(Selector('a[href="/templates"]'))
        .wait(5000);

    const data_qa = await t.eval(() => {
    	const nodeList = document.querySelectorAll('span');
    	const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
            const span = nodeList.item(i);
            if (span.innerText === 'CA Employer Application2019' && span.getAttribute('data-qa') !== null)
            	return span.getAttribute('data-qa');
    	}
    	return 'not_found';
    });

    await t
        .click(Selector('span').withAttribute('data-qa', data_qa))
        .click(Selector('button[data-qa="edit-btn"]'))
        .wait(2000)
        .click(Selector('button[data-qa="add-fields-link"]'))
        .wait(2000)
        .click(Selector('div.toolbar_main > div[tagger-zoom-buttons] > button'))
        .wait(1000)
        .click(Selector('button[data-qa="zoom-level-50"]'));

	let text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '1 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(2000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '2 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(2000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '3 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text, 'SCROLL ME');
	await t.click(Selector(text)).wait(2000);

	await t.click(Selector('div.documentPage > span.pageNumber').withText("4")).wait(5000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '4 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(20000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '5 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(2000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '6 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(2000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '7 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(2000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '8 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(2000);

	text = await t.eval(() => {
        const nodeList = document.querySelectorAll('g[data-view-name="PageView"] > text');
        const n = nodeList.length;
    	for (let i = 0; i < n; i++) {
    		const node = nodeList.item(i);
    		if (node.innerHTML === '9 of 9') return `g[data-view-name="PageView"][data-view-id="${node.parentElement.getAttribute('data-view-id')}"] > text`;
    	}
    	return 'not_found';
	});
	console.log(text);
	await t.click(Selector(text)).wait(2000);

	await t.wait(2000);

    const dataViewIdsString = await t.eval(() => {
    	const nodeList = document.querySelectorAll('g[data-view-name][data-view-id] > rect[fill="#ffffff"]');
    	const n = nodeList.length;
    	const array = [];
    	for (let i = 0; i < n; i++) {
            const g = nodeList.item(i).parentElement;
            if (g.style.display !== 'none')
            	array.push(g.getAttribute('data-view-id'));
    	}
    	return array.join(',');
    });
    const dataViewIds = dataViewIdsString.split(',');
    for (let i = 0; i < dataViewIds.length; i++) {
    	console.log('click', dataViewIds[i]);
    	await t
    	    .click(Selector(`g[data-view-id="${dataViewIds[i]}"`))
    	    .wait(2000);
    }
});
```
