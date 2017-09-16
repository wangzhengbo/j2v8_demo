'use strict';

new Promise((resolve, reject) => {
	setTimeout(() => {
		resolve('World');
	}, 200);
}).then(name => console.log(`Promise -> Hello ${name}!`))
  .catch(error => console.error(`Promise -> ${error}.`));

new Promise((resolve, reject) => {
	setTimeout(() => {
		reject('Test error');
	}, 200);
}).then(name => console.log(`Promise -> Hello ${name}!`))
  .catch(error => console.error(`Promise -> ${error}.`));

new Promise((resolve, reject) => {
	setTimeout(() => {
		reject('Unhandled reject');
	}, 200);
});
