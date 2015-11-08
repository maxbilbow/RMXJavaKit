/**
 * Created by bilbowm on 20/10/2015.
 */
var output;
var uri;
var wss = false;
var stompClient;
var socketLibs = {
    socketIo: 'socket.io',
    sockJs: 'SockJS'
}


var messageIds = [];

function useLibrary(lib) {
    var opt = wsUri();//$('select#socketLibrary option:selected').text();
    if (opt.indexOf("ws://") > -1 || opt.indexOf("wss://") > -1)
        opt = socketLibs.socketIo;
    else if (opt.indexOf("http://") > -1 || opt.indexOf("https://") > -1)
        opt = socketLibs.sockJs;
    return lib != null ? opt === lib : opt;
}

//function stayConnected(val) {
//    if (val)
//        $('#stayConnected').prop('checked', val);
//    else
//        return $('#stayConnected').prop('checked');
//}

function setConnected(connected) {
    updateUri();
    updateWss();
    if (connected == true) {
        $('input#customSocket').attr('disabled', 'disabled');
        $('input#customSocket').attr('hidden');
        //$('#wss').attr('disabled', 'disabled');
        $('select#toSocket').attr('disabled', 'disabled');
        $('div#openSocket').attr('disabled', 'disabled');
        $('div#closeSocket').removeAttr('disabled');
        $('div#sendButton').removeAttr('disabled');
        //$('select#socketLibrary').attr('disabled', 'disabled');
        $('input#chatBroker').attr('disabled', 'disabled');
        console.log('CONNECTED >>> ');
    } else {
        $('input#customSocket').removeAttr('disabled');
        $('input#customSocket').removeAttr('hidden');
        //$('#wss').removeAttr('disabled');
        $('select#toSocket').removeAttr('disabled');
        $('div#openSocket').removeAttr('disabled');
        $('div#closeSocket').attr('disabled', 'dissabled');
        $('div#sendButton').attr('disabled', 'disabled');
        //$('select#socketLibrary').removeAttr('disabled');
        $('input#chatBroker').removeAttr('disabled');
        console.log('DISCONNECTED <<<');
    }

}

function getMessage() {
    return $('textarea#sendMessage').val();
}

function wsUri() {
    updateUri();
    return uri;
};

function init() {
    setConnected(false);
    console.log("uri: '" + wsUri() + "'");
    output = document.getElementById("output");
}

function updateUri() {
    uri = $('select#toSocket  option:selected').text();
    if (uri.indexOf('--custom--') > -1) {
        uri = $('input#customSocket').val();
        $('#wss').attr('disabled', 'disabled');
        $('input#customSocket').removeAttr('disabled');
        $('input#customSocket').removeAttr('hidden');//.prop('disabled', false);//.disabled(false);
    } else {
        $('#wss').removeAttr('disabled');
        $('input#customSocket').attr('disabled', 'disabled');
        $('input#customSocket').attr('hidden', 'hidden');
    }
    console.log("uri: '" + uri + "'");
}

function updateWss() {
    //wss = $('#wss').prop('checked');
    console.log("uri: '" + wsUri() + "'");
}

function sendMessage() {
    var message = getMessage();

    console.log("sending: '" + message + "', to: " + wsUri() + ', on topic...' + chatTopic());
    try {
        switch (useLibrary()) {
            case socketLibs.socketIo:
                if (window.websocket) {
                    writeToScreen("SENT: " + message);
                    websocket.send(message);
                } else {
                    console.log("NO CONNECTION!");
                }
                break;
            case socketLibs.sockJs:
                var id = Math.floor(Math.random() * 1000000);
                stompClient.send(chatTopic(), {
                    priority: 9
                }, JSON.stringify({
                    message: message,
                    id: id
                }));
                messageIds.push(id);
                break;
        }
    } catch (e) {
        writeErrToScreen(e);
    }
}
function chatBroker() {
    return $('input#chatBroker').val();
}

function chatTopic() {
    return $('input#chatTopic').val();
}
function disconnect() {

    if (window.websocket)
        websocket.close();
    if (stompClient) {
        stompClient.disconnect();
        writeToScreen("DISCONNECTED");
        setConnected(false);
    }
}

function getUsername() {
    return $('input#username').val();
}

function getPassword() {
    return $('input#password').val();
}

function connect(quietly) {
    try {
        disconnect();
        switch (useLibrary()) {
            case socketLibs.sockJs:
                var uri = wsUri();
                window.websocket = new SockJS(uri);//'/hello');
                var socket = window.websocket;
                stompClient = Stomp.over(socket);
                var headers = getUsername().length > 0 ? {
                    login: getUsername(),
                    passcode: getPassword(),
                    persistent: true,
                    // additional header
                    //'client-id': 'fjwa',
                    //'heart-beat':'30000,30000'
                } : {};
                stompClient.heartbeat.outgoing = 0;
                stompClient.heartbeat.incoming = 0;

                var on_connect = function (frame) {
                    writeToScreen('Connected: ' + frame);
                    setConnected(true);
                    stompClient.subscribe(chatBroker(), function (data) {
                        writeToScreen(data);
                    });
                    stompClient.onclose = function () {
                        setConnected(false);
                    };

                    //stompClient.debug = pipe('#second');
                };

                var on_error = function () {
                    console.log('error');
                };
                if (getUsername().length > 0)
                    stompClient.connect(getUsername(), getPassword(), on_connect, on_error, '/');
                else
                    stompClient.connect('', '', on_connect, on_error, '/');

                break;
            case socketLibs.socketIo:

                window.websocket = new WebSocket(wsUri());
                websocket.onopen = function (evt) {
                    onOpen(evt);
                };
                websocket.onclose = function (evt) {
                    onClose(evt)
                };
                websocket.onmessage = function (evt) {
                    onMessage(evt)
                };
                websocket.onerror = function (evt) {
                    onError(evt);

                };
                break;
        }
    } catch (e) {
        writeErrToScreen(e);
    }
}


function onOpen(evt) {
    writeToScreen("CONNECTED");
    setConnected(true);
}
function onClose(evt) {

    writeToScreen("DISCONNECTED");
    setConnected(false);
}
function onMessage(evt) {
    writeToScreen(evt.data, '<span style="color: rgb(151, 253, 255);">RESPONSE:</span> ');
    //websocket.close();
}
function onError(evt) {
    writeToScreen('<span style="color: rgb(255, 46, 42);">ERROR:</span> ' + evt.data);
}

function writeErrToScreen(err) {
    writeToScreen('<span style="color: rgb(255, 147, 40);">ERROR:</span> ' + err);
}
//Message("debug.log"), Warning("debug.warning"), Exception("debug.error");
function tryParseLog(data) {
    try {
        var result = '';
        var log = JSON.parse(data);

        var color = "white";
        switch(log.logType) {
            case 'Message':
                color = 'rgb(151, 253, 255)';
                break;
            case 'Warning':
                color =  'rgb(255, 147, 40)';
                break;
            case 'Exception':
                color = 'rgb(255, 46, 42)';
                break;

        }


        var time = new Date(log.timeStamp),
            h = time.getHours(), // 0-24 format
            m = time.getMinutes();
        result += (time = '' + h + ':' + m + ' >> ');

        var spacer = '';
        for (var i=0;i<time.length + 4;++i) {
            spacer += '&nbsp;';
        }
        var msg = log.message.replace(/\n|<br>/gi, '<br/>' + spacer);
        result += '<span style="color: '+color+';">'+
            msg +'</span>';//.replace('\n','<br/>');

        result += '<br/>&nbsp;&nbsp;&nbsp;>> SENDER: ' + log.sender;

        return result;
    }catch (e) {
        console.log(e);
        return false;
    }
}
function tryParseJson(body) {
    var parsed = 'JSON:: ';
    try {
        var json = JSON.parse(body);
        var message = json.message, time = json.timeStamp;
        if (message) {
            if (time) {
                var time = new Date(time),
                    h = time.getHours(), // 0-24 format
                    m = time.getMinutes();
                parsed += h + ':' + m + " >> ";
            }
            parsed += tryParseJson(message) | message;
        } else {
            $.each(json, function (k, v) {
                //display the key and value pair
                parsed += '<br/> --> ' + k + ': ' + v;
            });
        }
    } catch (e) {
        console.log(e);
        return false;//"As String >> " + body;
    }

    return parsed.length > 0 ? parsed : false;//'UN-PARSED: ' + json;
}
function tryParseList(data) {
    try {
        var result;
        var json = JSON.parse(data);
        if (Object.prototype.toString.call(json) === '[object Array]') {
            result = 'LIST::';
            for (var i = 0; i < json.length; ++i) {
                result += '<br/>ENTRY ' + i + ': ' + json[i];
                //var element = JSON.parse(data[i]);
                $.each(json[i], function (k, v) {
                    //display the key and value pair
                    result += '<br/> --> ' + k + ': ' + v;
                });
            }
        }
        else {
            result = "JSON::"
            $.each(json, function (k, v) {
                //display the key and value pair
                result += '<br/> --> ' + k + ': ' + v;
            });
        }
        return result.length <= 6 ? false : result;
    } catch (e) {
        console.log('Could not parse as JSON: ' + data);
        console.log(e);
        return false;
    }
}

function tryParse(data, prefix) {

    var result = tryParseLog(data);

    if (result == false)
        result = tryParseLog(data.body);
    //if (result == false)
    //    result = tryParseJson(data.body);
    //if (result == false)
    //    result = tryParseJson(data);
    //if (result == false)
    //    result = tryParseList(data);
    if (result == false)
        return (prefix ? prefix : '') + data;
    else
        return result;//.replace('\n','<br/>');
}

function writeToScreen(message, prefix) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = tryParse(message, prefix);
    output.appendChild(pre);
    //console.log(message);
    try {
        output.scrollTop = output.scrollHeight;
    } catch (e) {
        console.log(e);
    }
}


window.addEventListener("load", init, false);
