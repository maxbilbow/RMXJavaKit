/**
 * Created by Max Bilbow on 23/11/2015.
 */

define(['jquery', 'service/pub-sub'], function ($, ps) {
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

    var getSocketConfigView = function (webConsole) {
        ps.info('init getSocketConfig');
        var view = $('.ws-config');

        if (view.length > 0) {
            if (view.find('.get-uri') > 0) {
                ps.info('Socket Config view already exists');

                return view;
            }
            ps.info('Socket Config view exists but is empty. Generating...');
        } else {
            //else append view to body
            ps.info('Generating NEW SocketConfigView');
            view = $(document.createElement('div'))
                .addClass('ws-config');
        }
        if (webConsole) {
            webConsole.view.append(view);
        } else {
            view.appendTo('body');
            view.addClass('hero-unit');
        }
        view.prepend(
            $(document.createElement('div'))
                .addClass('row-fluid')
                .append(
                    $(document.createElement('input'))
                    .addClass('span10').addClass('get-uri')
                        .attr('type', 'text')
                        .css('float','left')
                        .val('ws://echo.websocket.org')
                ).append(
                $(document.createElement('span'))
                .addClass('btn').addClass('btn-primary')
                .addClass('span2').addClass('ws-connect')
                .html('Connect')
            ));
        return view;

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
            var view = $($this.view);
            view.find('.get-uri').keyup(function(evt){
                if (evt.keyCode == 13){
                    $this.connect();
                }
            });
            view.find('.ws-connect').mouseup(function(){
                $this.connect();
            });

            return $this;
        }

        function isValidEvt(evt) {
            return evt.data && evt.data.length > 0;
        }


        return init({
            socket: undefined, decoder: decoder, view:$(getSocketConfigView(webConsole)),
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
            }, disconnect: function () {
                ps.info('Attempting to close old connection.');
                if (this.socket)
                    try {
                        this.socket.close();
                        sockets.remove(this.socket);
                    } catch (e) {
                        ps.error(e);
                    }
                //socket.disconnect();

                this.socket = null;
            },
            connect: function (aUri, onOpen) {
                if (this.connected()) {
                    this.disconnect();
                }
                var uri = aUri || this.onGetUri();
                if (this.onValidateUri(uri) == false) {
                    ps.warn('Not a valid URI: ' + uri);
                    this.onError(uri + ' not a valid uri');
                    return;
                }

                try {
                    this.socket = new WebSocket(uri);
                } catch (e) {
                    ps.error('Could not connect to ' + uri,e);
                    this.disconnect();
                    return;
                }
                sockets.push(this.socket);
                ps.info('Socket: ' + (sockets.length - 1) + ', Connecting to: ' + uri);
                this.socket.onopen = onOpen || this.onOpen;
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
                return this.socket;
            }, send: function (msg) {
                ps.info("Sending message: " + msg);
                if (this.connected()) {
                    ps.info('Socket is already open. Sending message');
                    try {
                        this.socket.send(msg);
                        ps.info('Message sent: ' + msg);
                    } catch (e) {
                        ps.warn('Sending failed. Will try to reconnect', e);
                        this.disconnect();
                        this.send(msg);
                    }
                } else {
                    ps.info('Socket is not open, Trying to connect. Message will be send onOpen');
                    var socket = this.connect(null,function () {
                        socket.send(msg);
                        ps.info('Message sent: ' + msg);
                    });
                    this.socket = socket;

                    //this.socket.onopen =
                }
            },
            connected: function () {
                return this.socket != null;
            },
            onGetUri: function () {
                return this.view.find('.get-uri').val();
            }
        });
    };

    return WSHelper;
});