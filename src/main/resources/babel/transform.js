var babel = require("babel-core");

var options = {
	presets: ['es2015', 'es2016', 'es2017', 'react', 'flow'],
	plugins: ['transform-object-rest-spread']
};

module.exports = function transformScript(script) {
	return babel.transform(script, options).code;
};
