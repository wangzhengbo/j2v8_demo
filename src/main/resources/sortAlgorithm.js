function merge(left, right) {
	var result = [], il = 0, ir = 0;
	while (il < left.length && ir < right.length) {
		if (left[il] < right[ir]) {
			result.push(left[il++]);
		} else {
			result.push(right[ir++]);
		}
	}
	return result.concat(left.slice(il)).concat(right.slice(ir));
};

function sort(data) {
	if (data.length === 1) {
		return [ data[0] ];
	} else if (data.length === 2) {
		if (data[1] < data[0]) {
			return [ data[1], data[0] ];
		} else {
			return data;
		}
	}
	var mid = Math.floor(data.length / 2);
	var first = data.slice(0, mid);
	var second = data.slice(mid);
	return merge(_sort(first), _sort(second));
}
