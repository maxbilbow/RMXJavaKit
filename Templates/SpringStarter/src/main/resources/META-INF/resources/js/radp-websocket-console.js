/**
 * Created by Max Bilbow on 23/11/2015.
 */

require(['jquery', 'view/ws-helper', 'view/web-console', 'service/pub-sub', 'service/json-decoder'],
    function ($, WSHelper, WebConsole, ps, Decoder) {
        $(document).ready(function () {
            var wc = WebConsole.getInstance();
            var helper = new WSHelper(wc, new Decoder());
            $('input[name=debug-level]:radio').change(function (evt) {
                //wc.debugLevel = evt.target.value;
                //ps.pub(wc.debugLevel,'Debug mode set to: ' + wc.debugLevel);
                helper.send('/debug ' + evt.target.value);
            });
            ps.info('Debug mode set to: ' + (
                    wc.debugLevel = ps.WARN //$('input[name=debug-level]:checked').val()
                ));

            wc.terminal.removeAll();
            wc.terminal.onProcess('disconnect',function(cmd,txt,terminal){
                helper.disconnect();
                return true;
            },'Disconnect from the server.');


            if (helper.decoder) {
                ps.info('decoder attached');
            } else {
                ps.error('Decoder was not attached: ' + Decoder);
            }

            wc.onComplete(function (success) {
                if (success == false) {
                    wc.print('<i>Failed: ' + wc.last() + '</i>');
                    ps.error('Failed to send: ' + wc.last());
                } else {
                    ps.info('Successfully send command: ' + wc.last());
                }
            });

            var uriOpt = $('select#uri-options');
            var updateUri = function (uri) {
                $('.get-uri').val(uri || $('select#uri-options  option:selected').text());
            };

            updateUri('ws://' + window.location.host + '/console');
            uriOpt.change(function(){
                updateUri();
                ps.info('Connection changed to: '+helper.onGetUri());
                helper.connect();
            });
            


            helper.connect();

            $('#buildQueue').click(function(){
                helper.send("/queue -build");
            });

            $('#startInserts').click(function () {
                helper.send('/queue -insert');
            });

            $('#stopRadp').click(function () {
                helper.send('/stop');
            });
        });
    });