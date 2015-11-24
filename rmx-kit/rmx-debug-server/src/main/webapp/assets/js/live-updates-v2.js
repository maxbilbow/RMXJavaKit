/**
 * Created by bilbowm on 23/11/2015.
 */

require(['jquery', 'rmxjs/rmx-sockets', 'rmxjs/web-console','rmxjs/pubsub', 'rmxjs/log-decoder'],
    function($,WSHelper,WebConsole,ps,Decoder){
    $(document).ready(function () {
        var wc = WebConsole.getInstance();

            $('input[name=debug-level]:radio').change(function(evt){
                wc.debug = evt.target.value;
                ps.pub(wc.debug, 'Debug mode set to: ' + wc.debug);
            });
        ps.info('Debug mode set to: ' + (
                wc.debug =  $('input[name=debug-level]:checked').val()
            ));
        //$('input[name=debug-level]:radio').find(ps.INFO).checked = true;

        var helper = new WSHelper(wc, new Decoder());

        if (helper.decoder) {
            ps.info('decoder attached');
        } else {
            ps.error('Decoder was not attached: ' + Decoder);
        }
        var btnOpenSocket = $('#openSocket');


        btnOpenSocket.click(function(){
           helper.connect();
            ps.info('Return key pressed');
        });

        wc.onComplete(function(success){
            if (success == false) {
                wc.print('<i>Failed: ' + wc.last() + '</i>');
                ps.error('Failed to send: ' + wc.last());
            } else {
                ps.info('Successfully send command: ' + wc.last());
            }
        });

        var uriOpt = $('select#uri-options');
        var updateUri = function() {
            $('#get-uri').val($('select#uri-options  option:selected').text());
        };
        uriOpt.change(updateUri);
        updateUri();


        //$('#debug-level input.radio-button').select({
        //
        //})


    });
});