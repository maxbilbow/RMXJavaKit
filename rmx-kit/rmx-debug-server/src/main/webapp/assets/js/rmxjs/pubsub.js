/**
 * Created by bilbowm on 24/11/2015.
 */
define(function () {
    var cache = {};

    function toStack(e) {
        return e.stack ? e.stack + ' >> ' + e : e;
    }
    return (function($this){
        $this.sub($this.ERROR,console.error);
        return $this;
    })({
        INFO: 'pubsub-info', WARN : 'pubsub-warn', ERROR : 'pubsub-error',
        info:function(msg,e) {
            console.log(msg,e || '');
            this.pub(this.INFO,toStack(msg),e);
        },
        warn:function(msg,e) {
            console.warn(msg,e || '');
            this.pub(this.WARN,toStack(msg),e);
        },
        error:function(msg,e) {

            console.error(msg,e || '');
            this.pub(this.ERROR,toStack(msg),e);
        },
        pub: function (id) {
            var args = [].slice.call(arguments, 1);

            if (!cache[id]) {
                cache[id] = {
                    callbacks: [],
                    args: [args]
                };
            } else {
                cache[id].args.push(args);
            }

            for (var i = 0, il = cache[id].callbacks.length; i < il; i++) {
                cache[id].callbacks[i].apply(null, args);
            }
        },
        sub: function (id, fn) {
            if (!cache[id]) {
                cache[id] = {
                    callbacks: [fn],
                    args: []
                };
            } else {
                cache[id].callbacks.push(fn);

                for (var i = 0, il = cache[id].args.length; i < il; i++) {
                    fn.apply(null, cache[id].args[i]);
                }
            }
            return this;
        },
        unsub: function (id, fn) {
            var index;
            if (!cache[id]) {
                return;
            }

            if (!fn) {
                cache[id] = {
                    callbacks: [],
                    args: []
                };
            } else {
                index = cache[id].callbacks.indexOf(fn);
                if (index > -1) {
                    cache[id].callbacks = cache[id].callbacks.slice(0, index).concat(cache[id].callbacks.slice(index + 1));
                }
            }
        }
    });
});