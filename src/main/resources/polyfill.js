'use strict';

// https://stackoverflow.com/questions/40057984/a-java-application-with-j2v8-crashes-when-an-exception-happened-in-a-some-deferr
/*
let nativeSetTimeout = setTimeout;
setTimeout = function(fn, t){
    let safeFn = () => {
        try {
            fn();
        } catch(e){
            console.log(e);
        }
    };
    return nativeSetTimeout(safeFn,t);
} 


let nativeNextTick = process.nextTick;
process.nextTick = function(){
    let args = Array.prototype.slice.call(arguments);
    let fn = args[0];
    let safeFn = () => {
        try {
            fn.apply(null,args.slice(1));
        } catch(e){
            console.log(e);
        }
    };
    return nativeNextTick(safeFn);
}
*/
