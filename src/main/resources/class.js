'use strict';

class User {
	constructor(name) {
		if (typeof name === 'undefined') {
			name = 'World';
		}
		this.name = name;
	}
	
	sayHello() {
		console.log(`Hello ${this.name}!`);
	}
}

let user = new User();
user.sayHello();

user = new User('wangzhengbo');
user.sayHello();
