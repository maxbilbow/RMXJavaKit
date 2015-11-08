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
    var opt = $('select#socketLibrary option:selected').text();
    return lib != null ? opt === lib : opt;
}
function setConnected(connected) {
    updateUri();
    updateWss();
    if (connected == true) {
        $('input#customSocket').attr('disabled', 'disabled');
        $('#wss').attr('disabled', 'disabled');
        $('select#toSocket').attr('disabled', 'disabled');
        $('div#openSocket').attr('disabled', 'disabled');
        $('div#closeSocket').removeAttr('disabled');
        $('div#sendButton').removeAttr('disabled');
        $('select#socketLibrary').attr('disabled', 'disabled');
        $('input#chatBroker').attr('disabled', 'disabled');
        console.log('CONNECTED >>> ');
    } else {
        $('input#customSocket').removeAttr('disabled');
        $('#wss').removeAttr('disabled');
        $('select#toSocket').removeAttr('disabled');
        $('div#openSocket').removeAttr('disabled');
        $('div#closeSocket').attr('disabled', 'dissabled');
        $('div#sendButton').attr('disabled', 'disabled');
        $('select#socketLibrary').removeAttr('disabled');
        $('input#chatBroker').removeAttr('disabled');
        console.log('DISCONNECTED <<<');
    }

}

function getMessage() {
    return $('textarea#sendMessage').val();
}

function wsUri() {
    if ($('input#customSocket').prop('disabled'))
        if (useLibrary(socketLibs.socketIo))
            return (wss ? "wss://" : "ws://") + uri;
        else
            return (wss ? "https://" : "http://") + uri;
    else
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
        $('input#customSocket').removeAttr('disabled');//.prop('disabled', false);//.disabled(false);
    } else {
        $('#wss').removeAttr('disabled');
        $('input#customSocket').attr('disabled', 'disabled');
    }
    console.log("uri: '" + wsUri() + "'");
}

function updateWss() {
    wss = $('#wss').prop('checked');
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


function connect() {
    try {
        disconnect();
        switch (useLibrary()) {
            case socketLibs.sockJs:
                var uri = wsUri();
                window.websocket = new SockJS(uri);//'/hello');
                var socket = window.websocket;
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    setConnected(true);
                    writeToScreen('Connected: ' + frame);
                    stompClient.subscribe(chatBroker(), function(data) {
                        writeToScreen(data);
                    });
                });

                break;
            case socketLibs.socketIo:

                window.websocket = new WebSocket(wsUri());
                websocket.onopen = function (evt) {
                    onOpen(evt);
                    setConnected(true);
                };
                websocket.onclose = function (evt) {
                    onClose(evt)
                    setConnected(false);
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
    //doSend(message);
}
function onClose(evt) {
    writeToScreen("DISCONNECTED");
}
function onMessage(evt) {
    writeToScreen('<span style="color: rgba(0, 255, 248, 1);">RESPONSE: ' + evt.data + '</span>');
    //websocket.close();
}
function onError(evt) {
    writeToScreen('<span style="color: rgba(255, 170, 167, 1);">ERROR:</span> ' + evt.data);
}

function writeErrToScreen(err) {
    writeToScreen('<span style="color: rgba(255, 170, 167, 1);">ERROR:</span> ' + err);
}

function tryParse(data) {
    if (data.body) {
        var parsed = '';
        try {
            var json = JSON.parse(data.body);
            var message = json.message, time = json.time;
            if (time)
                var time = new Date(time),
                    h = time.getHours(), // 0-24 format
                    m = time.getMinutes();
            parsed += h +':' + m + " >> ";

            if (message)
                parsed += message;
        } catch (e) {
            writeErrToScreen(e);
        }
        return parsed.length > 0 ? parsed : 'UN-PARSED: ' + json;
    } else {
        console.log('Could not parse as JSON: ' + data);
        return data;
    }
}

function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = tryParse(message);
    output.appendChild(pre);
    console.log(message);
    try {
        output.scrollTop = output.scrollHeight;
    } catch (e) {
        console.log(e);
    }
}


window.addEventListener("load", init, false);
