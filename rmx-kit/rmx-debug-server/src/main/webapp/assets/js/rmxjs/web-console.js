/**
 * Created by bilbowm on 23/11/2015.
 */
define(['jquery'], function($){

    var _output =  ocument.getElementById('wc-output');//$('div#wc-output');
    var _input = document.getElementById('wc-input');
    function writeText(message) {
        var pre = document.createElement("p");
        pre.style.wordWrap = "break-word";
        pre.innerHTML = message;
        if (pre.innerHTML.length > 0)
            _output.appendChild(pre);
        //console.log(message);
        try {
            _output.scrollTop = _output.scrollHeight;
        } catch (e) {
            console.error(e);
        }
    }

    var parsers = [];
    writeText("It worked!");
    return {
        output: function(output){
            if (output) {
                _output = output;
            } else {
                return _output;
            }
        },input: function(input){
            if (output) {
                _input = input;
            } else {
                return _input;
            }
        }, addParser: function(parser) {
            parsers.push(parser);
        },
        print: function(obj) {
            var msg = obj;
            for(var parse in parsers) {
                msg = parse(msg);
            }
            writeText(msg);
        }
    }
});