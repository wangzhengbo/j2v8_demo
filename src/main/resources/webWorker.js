var w = new Worker('messageHandler = function(e) { print(`WebWorker -> ${e[0]}`); }');
w.postMessage('message to send.');
w.postMessage('another message to send.');
w.terminate();
