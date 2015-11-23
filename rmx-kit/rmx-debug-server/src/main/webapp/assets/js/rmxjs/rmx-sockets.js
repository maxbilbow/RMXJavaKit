/**
 * Created by bilbowm on 23/11/2015.
 */

define(['jquery', '../socket.io'], function ($, WebSocket) {

    var Message = function (message) {
        return {
            id: null,
            timestamp: new Date(),
            message: message | ''
        };
    }


    var SocketConfig = function () {

        var getMessage = function (msg) {
            msg.message = $('input#message-input').val();
            return msg;
        };

        var getUri = function () {
            return $('input#uri').val();
        }
        var properties = {
            username: "unknown",
            cookie: 'SessionID=' + (Math.random() * 10000)
        };

        console.log('cookie: ' + properties.cookie);

        var socket;

        var messageValidators = [];

        var _onopen= function (data) {
            console.log(uri + ' >> OPEN: ' + data);
        };
        var _onerror= function (data) {
            console.error(uri + ' >> ERROR: ' + data);
        };
        var _onmessage= function (data) {
            console.log(uri + ' >> MESSAGE: ' + data);
        };
        var _onclose= function (data) {
                        console.log(uri + ' >> CLOSED: ' + data);
        };
        function sendMessage(msg) {
            if (socket && socket.connected) {
                try {
                    socket.send(msg.message);
                    return "Success after connection: " + msg;
                } catch (e) {
                    _onerror(e);
                    return "Failed to Send: " + msg;
                }
                return false;
            }
        }


        //var onPrepareCallbacks = [];

        return {
            property: function (aProp, value) {
                if (value) {
                    properties[aProp] = value;
                } else {
                    return properties[aProp];
                }
            },
            properties: function (aProps) {
                if (aProps) {
                    properties = aProps;
                } else {
                    return properties;
                }
            }, onopen: function (callback) {
                _onopen = callback;

            }, onerror: function (callback) {
               _onerror = callback;
            }, onmessage: function (callback) {
                _onmessage = callback;
            }, onclose: function (callback) {
                _onclose = callback;
            }, connect: function (uri) {
                if (socket && socket.connected) {
                    try {
                        socket.close();
                        socket.disconnect();
                    } catch (e) {
                        console.warn(e);
                    }
                }

                if (uri === undefined) {
                    uri = getUri();
                }
                socket = new WebSocket(uri);

                socket.onopen = this.onopen;
                socket.onerror = this.onerror;
                socket.onclose = this.onclose;
                socket.onmessage = this.onmessage;
                return socket;
            }, send: function () {
                //console.log('Nothing to send.')
                var msg = new Message();
                msg = getMessage(msg) | msg; //Override should return an object
                for (var i = 0; i < messageValidators.length; ++i) {
                    if (messageValidators[i](msg) != true) {
                        return false;
                    }
                }
                if (!this.connected()) {
                    var socket = this.connect();
                    socket.onopen = function(data) {
                        this.onopen(data);
                        return sendMessage(msg);
                    }
                } else {
                    this.connect();
                    return sendMessage(msg);
                }
            },
            addMessageValidator: function (callback) {
                messageValidators.push(callback);
            },
            connected: function () {
                return socket && socket.connected;
            },
            messageObject: function (obj) {
                if (obj) {
                    Message = obj;
                } else {
                    return new Message();
                }
            }, onGetMessage: function (getter) {
                getMessage = getter;
            }, onGetUri: function (getter) {
                getUri = getter;
            }
        };
    };

    return SocketConfig;
});