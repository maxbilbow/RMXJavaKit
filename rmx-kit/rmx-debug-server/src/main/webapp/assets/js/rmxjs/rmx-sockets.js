/**
 * Created by bilbowm on 23/11/2015.
 */

define(['jquery', './pubsub'], function ($, ps) {
    var sockets = [];
    var defaultConsole = {
        send: console.log,
        log: console.log,
        warn: console.warn,
        error: console.error,
        success: console.log,
        fail: console.error,
        green: function (msg) {
            wc.log(msg)
        },
        orange: function (msg) {
            wc.log(msg)
        },
        red: function (msg) {
            wc.log(msg)
        }
        //color: function (msg,color) {wc.log('Color: ' + color + ', Message: ' + msg)}
    };


    var WSHelper = function (webConsole, decoder) {

        var wc;

        //if (!decoder) {
        //    decoder = {
        //        decode: function (evt) {
        //            ps.warn('No decoder supplied');
        //            return evt.data || evt;
        //        }
        //    };
        //}

        function init($this) {
            ps.info('WSHelper initialized');
            if (webConsole) {
                $this.webConsole(webConsole);
            }
            return $this;
        }

        function isValidEvt(evt){
            return evt.data && evt.data.length > 0;
        }

        return init({
            socket: undefined, decoder: decoder,
            webConsole: function (aWebConsole) {
                if (aWebConsole) {
                    wc = aWebConsole;
                    var $this = this;
                    wc.onSubmit = function (message) {
                        try {
                            $this.send(message);
                        } catch (e) {
                            ps.error(e);
                        }
                    };
                }
                return wc || defaultConsole;
            },
            onOpen: function () {
                wc.success("CONNECTED")
            },
            onError: function (evt) {
                wc.error(evt.data)
            },
            onMessage: function (msg) {
                wc.log(msg);
            },
            onClose: function () {
                wc.orange("DISCONNECTED")
            },
            onValidateUri: function (uri) {
                return uri && uri.length > 5;
            },
            connect: function (aUri) {
                if (this.connected()) {
                    var socket = this.socket;
                    try {
                        ps.info('Attempting to close old connection.');
                        socket.close();
                        socket.disconnect();
                        sockets.remove(socket);
                    } catch (e) {
                        ps.error(e);
                    }
                }
                var uri = aUri || this.onGetUri();
                if (this.onValidateUri(uri) == false) {
                    ps.warn('Not a valid URI: ' + uri);
                    this.onError(uri + ' not a valid uri');
                    return;
                }

                this.socket = new WebSocket(uri);
                sockets.push(this.socket);
                ps.info('Socket: ' + (sockets.length - 1) + ', Connecting to: ' + uri);
                this.socket.onopen = this.onOpen;
                this.socket.onerror = this.onError;
                this.socket.onclose = this.onClose;
                var $this = this;
                this.socket.onmessage = function (evt) {
                    if (!isValidEvt(evt)) return;
                    var msg = evt.data || evt;
                    if ($this.decoder)
                        try {
                            msg = $this.decoder.decode(evt.data);
                        } catch (e) {
                            msg = evt.data || evt;
                            ps.error(msg, e);
                            return;
                        }
                    else
                        ps.warn('Message decoder undefined');
                    $this.onMessage(msg);
                };
                //socket.onopen = function(evt) { this.onOpen(evt)} ;
                //socket.onerror = function(evt) { this.onError(evt)};
                //socket.onclose = function(evt) { this.onClose(evt)};
                //socket.onmessage = function(evt) { this.onMessage(evt)};
                return this.socket;
            }, send: function (msg) {
                ps.info("Sending message: " + msg);
                if (this.connected()) {
                    ps.info('Socket is already open. Sending message');
                    this.socket.send(msg);
                    ps.info('Message sent: ' + msg);
                } else {
                    ps.info('Socket is not open, Trying to connect. Message will be send onOpen');
                    this.socket = this.connect();
                    var socket = this.socket;
                    var $this = this;
                    this.socket.onopen = function (data) {
                        $this.onOpen(data);
                        socket.send(msg);
                        ps.info('Message sent: ' + msg);
                    }
                }
            },
            connected: function () {
                return this.socket != null;
            },
            onGetUri: function () {
                return $('#get-uri').val();
            }
        });
    };

    return WSHelper;
});