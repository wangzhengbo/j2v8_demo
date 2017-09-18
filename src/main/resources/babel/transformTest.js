var babel = require("babel-core");

var options = {
	presets: ['es2015', 'es2016', 'es2017', 'react', 'flow'],
	plugins: ['transform-object-rest-spread']
};

var result = babel.transform("() => {print('Hello');}", options);
console.log('Babel.transform ->', result.code);

result = babel.transform(`
	class User {
		constructor(name = 'World') {
			this.name = name;
		}
	}
`, options);
console.log('Babel.transform ->', result.code);

result = babel.transform(`
	var obj1 = {a: 1, b: 2};
	var obj2 = {c: 3, d: 4};
	var concatObj = {...obj1, ...obj2};
`, options);
console.log('Babel.transform ->', result.code);

result = babel.transform(`
	export default React.createClass({
	  getInitialState() {
	    return { num: this.getRandomNumber() };
	  },
	
	  getRandomNumber(): number {
	    return Math.ceil(Math.random() * 6);
	  },
	
	  render(): any {
	    return <div>
	      Your dice roll:
	      {this.state.num}
	    </div>;
	  }
	});
`, options);
console.log('Babel.transform ->', result.code);
