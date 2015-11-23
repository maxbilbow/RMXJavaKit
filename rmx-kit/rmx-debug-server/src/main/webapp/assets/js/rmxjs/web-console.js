/**
 * Created by bilbowm on 23/11/2015.
 */
define(['jquery'], function($){


    var history = [];
    var fwdHistory = [];
    var filters = [];
    var validators = [];
    var completionHandlers = [];

    var _input, _output, _submit;

    var stepBack = function(wc) {
        var txt = wc.inputText();
        if (history.length > 0) {
            if (txt.length > 0) {
                fwdHistory.push(txt);
            }
            wc.inputText(history.pop());
        }
    }

    var stepForward = function(wc){
        var txt = wc.inputText();
        if (txt.length > 0) {
            history.push(txt);
            if (fwdHistory.length > 0 ) {
                wc.inputText(fwdHistory.pop());
            } else {
                wc.inputText('');
            }
        }
    }

    var wc = {
        onComplete: function(callback){
            completionHandlers.push(callback);
        },
        onValidate:function(callback){
          validators.push(callback);
        },
        onFilter:function(callback){
            filters.push(callback);
        },
        output: function(output){
            if (output) {
                _output = output;
            } else {
                return _output;
            }
        },onError: function(e) {
            alert(e);
        }
        ,process: function() {
            var txt = wc.inputText();
            for (var i=0;i<validators.length;++i) {
                var error;
                if (error = validators[i](wc.inputText())) {
                    wc.onError(error);
                    return false;
                }
            }
            var success;
            try {
                fwdHistory = [];
                stepForward(wc);
                success = _submit(wc.inputText());

            } catch (e) {
                success = false;
                wc.onError(e);
            } finally {
                for (var i=0;i<completionHandlers.length;++i) {
                    completionHandlers[i](success);
                }
            }
        }
        ,input: function(input){
            if (input) {
                _input = input;
                _input.keyup(function(evt){
                   switch (evt.keyCode) {
                       case 13: //return
                           wc.process();
                           break;
                       case 38: //up arrow
                           stepBack(wc);
                           break;
                       case 40: //down arrow
                           stepForward(wc);
                           break;
                   }
                });
            } else {
                return _input;
            }
        },
        print: function(obj) {
            //var msg = obj;
            //for (var i=0;i<filters.length;++i) {
            //    msg = filters[i](msg);
            //}
            writeText(obj);
        },
        inputText: function(val) {
            return val ? _input.val(val) : _input.val();
        }, onSubmit: function(callback){
           if (callback) {
               _submit = callback;
           }
        }, last: function() {
            return history.length > 0 ? history[history.length-1] : '.';
        }
    };
    _submit = function(text){
        wc.print('<i>Sent: ' + text + '</i>');
        return true;
    };
    wc.input($('input#wc-input'));
    wc.output(document.getElementById('wc-output'));

    wc.onValidate(function(msg){
        if (msg.length == 0) {
            return 'Message cannot be empty!';
        }
    });

    function writeText(message) {
        var pre = document.createElement("p");
        pre.style.wordWrap = "break-word";
        pre.innerHTML = message;
        if (pre.innerHTML.length > 0)
            wc.output().appendChild(pre);
        //console.log(message);
        try {
            wc.output().scrollTop = wc.output().scrollHeight;
        } catch (e) {
            console.error(e);
        }
    }
    writeText("It worked!");
    return wc;
});