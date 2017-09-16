'use strict';

if (typeof process !== 'undefined') {
	process.on('unhandledRejection', (err) => {
	    console.error('Unhandled promise rejection', err);
	});

	process.on('uncaughtException', (err) => {
	    console.error('Uncaught exception', err);
	});
}

