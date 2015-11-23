/**
 * Created by bilbowm on 23/11/2015.
 */

require(['jquery', 'rmxjs/rmx-sockets', 'rmxjs/web-console'], function($,SocketConfig,wc){
    $(document).ready(function () {
        var helper = new SocketConfig(wc);

        var btnOpenSocket = $('#openSocket');


        btnOpenSocket.click(function(){
           helper.connect();
        });

        wc.onComplete(function(success){
            if (!success) {
                wc.print('<i>Failed: ' + wc.last() + '</i>');
            }
        });

        var uriOpt = $('select#uri-options');
        var updateUri;
        (updateUri = function() {
            $('input#uri').val($('select#uri-options  option:selected').text());
        })();

        uriOpt.change(updateUri);


    });
});