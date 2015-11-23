/**
 * Created by bilbowm on 23/11/2015.
 */

define(['jquery','./log'], function ($,Log) {


    var SocketConfig = function (wc) {


        var getUri = function () {
            return $('input#uri').val();
        }


        function parseJSON(helper, data) {
            var msg = data;
            try {
                var log = JSON.parse(data);
                msg = helper.onJSON(log);
            } catch(e){
                console.log('Message is not a JSON');
                msg = '<span style="color: rgb(151, 253, 255);"> >> </span>' + msg;
            }
            return msg;
        }

        var socket;

        var _onopen= function (evt) {
            var msg = '<span style="color: rgb(152, 255, 183);"> CONNECTED</span>';
            if (wc)
                wc.print(msg);
            else
                console.log(msg);
        };
        var _onerror= function (evt) {
            var msg = '<span style="color: rgb(255, 46, 42);">ERROR >> </span>' + evt.data;
            if (wc)
                wc.print(msg);
            else
                console.error(msg);
        };
        var _onmessage = function (evt) {
            var msg = parseJSON(helper,evt.data);
            if (wc)
                wc.print(msg);
            else
                console.log(msg);
        };
        var _onclose= function (evt) {
            var msg = '<span style="color: rgb(255, 188, 91);"> DISCONNECTED </span>';
            if (wc)
                wc.print(msg);
            else
                console.log(msg);
            socket = null;
        };

        var helper =  {
             onopen: function (callback) {
                _onopen = callback;

            }, onerror: function (callback) {
               _onerror = callback;
            }, onmessage: function (callback) {
                _onmessage = callback;
            }, onclose: function (callback) {
                _onclose = callback;
            }, connect: function (uri) {
                if (helper.connected()) {
                    try {
                        socket.close();
                        socket.disconnect();
                    } catch (e) {
                        console.warn(e);
                    }
                }

                if (uri === undefined) {
                    uri = getUri();
                    if (!uri) {
                        throw new Error(uri + ' not a valid uri');
                    }
                }

                socket = new WebSocket(uri);
                console.log('Connecting to: ' + uri);
                socket.onopen = _onopen;
                socket.onerror = _onerror;
                socket.onclose = _onclose;
                socket.onmessage = _onmessage;
                return socket;
            }, send: function (msg) {
                //console.log('Nothing to send.')
                msg = helper.newMessage(msg);
                if (helper.connected()) {
                    socket.send(msg);
                } else {
                    socket = helper.connect();
                    socket.onopen = function(data) {
                        _onopen(data);
                        return socket.send(msg);
                    }
                }
            },
            connected: function () {
                return socket != null;
            },
            newMessage: function (message) {
                return message;
            }, onGetUri: function (getter) {
                if (getter) {
                    getUri = getter;
                } else {
                    alert(getUri());
                }
            }, onJSON : function(data) {
                var result = '';
                var log = data;

                var color = "rgb(151, 253, 255)";
                if (log.logType)
                switch(log.logType) {
                    case 'Warning':
                        color =  'rgb(255, 147, 40)';
                        break;
                    case 'Exception':
                        color = 'rgb(255, 46, 42)';
                        break;
                    case 'Info':
                        color = 'rgb(73, 255, 114)';
                        break;
                }
                var time = new Date(log.timeStamp),
                        h = time.getHours(), // 0-24 format
                        m = time.getMinutes();
                    result += (time = '' + h + ':' + m + ' >> ');

                if (log.sender) {
                    result += '<strong>' + log.sender + ':</strong> ';
                }

                var spacer = '';
                for (var i=0;i<time.length + 4;++i) {
                    spacer += '&nbsp;';
                }
                var msg = log.message.replace(/\n|<br>/gi, '<br/>' + spacer);
                result += '<span style="color: '+color+';">'+
                    msg +'</span>';//.replace('\n','<br/>');
                return result;
            }

        };

        if (wc) {
            wc.onSubmit(function(message){
                try {
                    helper.send(message);
                    return true;
                } catch (e) {
                    wc.print(e);
                    console.error(e);
                    return false;
                }
            });
        }

        return helper;
    };

    return SocketConfig;
});