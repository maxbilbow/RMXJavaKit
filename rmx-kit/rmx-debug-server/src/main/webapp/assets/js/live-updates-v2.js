/**
 * Created by bilbowm on 23/11/2015.
 */

require(['jquery', 'rmxjs/rmx-sockets', 'rmxjs/web-console'], function($,SocketConfig,wc){
    $(document).ready(function () {
        var helper = new SocketConfig();

        var messageInput = wc.input();
        var btnOpenSocket = $('#openSocket');
        helper.onmessage = helper.onconnect = helper.onmessage = helper.onerror = function(data) {
            wc.print(data);
        };


        btnOpenSocket.click(function(){
           var socket = helper.connect();
        });



        helper.addMessageValidator(function(msg){
            if (msg.message.length == 0) {
                return false;
            }
        });

        messageInput.keyup(function(evt){
            if (evt.keyCode == 13) {
                helper.send();
            }
        });


        var uriOpt = $('select#uri-options');
        uriOpt.change(function() {
            $('input#uri').val($('select#uri-options  option:selected').text());
        });

    });
});