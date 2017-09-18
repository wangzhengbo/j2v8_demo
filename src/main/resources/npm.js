var _ = require('lodash');

var arr = [1, 2, 3];
var mappedArr = _.map(arr, item => item * item);
console.log('Lodash -> ', 'arr', arr, 'mappedArr', mappedArr);
