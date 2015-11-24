/**
 * Created by bilbowm on 23/11/2015.
 */
define(['jquery','./pubsub'], function ($,ps) {
    var webConsoles = [];

    var WebConsole = function (inputField, outputField) {
        var history = [],
            fwdHistory = [],
            filters = [],
            validators = [],
            completionHandlers = [];

        var last = '';
        var input, output;

        function init($this) {
            output = outputField || document.getElementById('wc-output')
            $this.input(inputField || $('input#wc-input'));
            $this.onValidate(function () {
                if ($this.inputText().length == 0) {
                    return 'Message cannot be empty!';
                }
            });
            ps.sub(ps.INFO,function(msg){
                if ($this.debug === ps.INFO){
                    $this.print(msg,'color: grey;');
                }
            }).sub(ps.WARN,function(msg){
                if ($this.debug === ps.INFO || $this.debug === ps.WARN){
                    $this.warn(msg);
                }
            }).sub(ps.ERROR,function(msg){
                if ($this.debug){
                    $this.error(msg);
                }
            });
            return $this;
        }

        var $this = {
            id: webConsoles.length, debug:false,
            onComplete: function (callback) {
                completionHandlers.push(callback);
            },
            onValidate: function (callback) {
                validators.push(callback);
            },
            onFilter: function (callback) {
                filters.push(callback);
            },
            output: function (aOutput) {
                if (aOutput) {
                    output = aOutput;
                } else {
                    return output;
                }
            }, onError: function (e) {
                alert(e);
            }, stepBack: function () {
                var txt = $this.inputText();
                if (history.length > 0) {
                    if (txt.length > 0) {
                        fwdHistory.push(txt);
                    }
                    $this.inputText(history.pop());
                }
            }, stepForward: function () {
                var txt = $this.inputText();
                if (txt.length > 0) {
                    history.push(txt);
                    if (fwdHistory.length > 0) {
                        $this.inputText(fwdHistory.pop());
                    } else {
                        $this.inputText('');
                    }
                }
            }
            , submit: function () {
                for (var i = 0; i < validators.length; ++i) {
                    var error;
                    if (error = validators[i]($this.input())) {
                        $this.onError(error);
                        return false;
                    }
                }
                var success;
                try {
                    fwdHistory = [];
                    last = $this.inputText();
                    $this.stepForward();
                    success = $this.onSubmit(last);
                } catch (e) {
                    success = false;
                    $this.onError(e);
                } finally {
                    for (var i = 0; i < completionHandlers.length; ++i) {
                        completionHandlers[i](success);
                    }
                }
            }
            , input: function (aInput) {
                if (aInput) {
                    input = aInput;
                    input.keyup(function (evt) {
                        switch (evt.keyCode) {
                            case 13: //return
                                $this.submit();
                                break;
                            case 38: //up arrow
                                $this.stepBack();
                                break;
                            case 40: //down arrow
                                $this.stepForward();
                                break;
                        }
                    });
                } else {
                    return input;
                }
            },
            print: function (msg,prefix,style) {
                if (style === undefined) { //If color not given, assume prefix is color.
                    style = prefix;
                    prefix = null;
                }
                var pre = document.createElement("p");
                pre.style.wordWrap = "break-word";
                pre.innerHTML = '<span style="' +
                    (style || '') +'"> '+
                    (prefix || msg ) +' </span>' +
                    (prefix ? msg : '');
                if (pre.innerHTML.length > 0) {
                    $this.output().appendChild(pre);
                    $this.output().scrollTop = $this.output().scrollHeight;
                }
            },log: function(msg,prefix,style) {
                $this.print(msg,prefix || '>> ', style || 'color: rgb(151, 253, 255);');
            },
            error:function(msg,prefix,style) {
                $this.print(msg,prefix || 'ERROR >> ',style ||'color: rgb(255, 46, 42);');
            },
            warn:function(msg,prefix,style) {
                $this.print(msg,prefix || 'WARNING >> ', style || 'color: rgb(255, 188, 91);');
            },
            success:function(msg) { $this.green(msg)},
            fail:function(msg) { $this.print(msg,'FAIL >> ', 'color: rgb(255, 46, 42);')},
            green:function(msg,prefix) {$this.print( msg, prefix,'color: rgb(152, 255, 183);')},
            orange:function(msg,prefix) {$this.print( msg, prefix,'color: rgb(255, 188, 91);')},
            red:function(msg,prefix) { $this.print(msg,prefix,'color: rgb(255, 46, 42);')},
            inputText: function (val) {
                return val ? input.val(val) : input.val();
            }, onSubmit: function (text) {
                $this.print('<i>Sent: ' + text + '</i>');
            }, last: function () {
                return last;
            }, getHistory: function () {
                return history
            },
            getFwdHistory: function () {
                return fwdHistory
            }
        };

        return init($this);
    };

    return {
        getInstance: function (id) {
            if (id) {
                for (var i = 0; i < webConsoles.length; ++i) {
                    if (webConsoles[i].id === id) {
                        return webConsoles[i];
                    }
                }
                var wc = new WebConsole();
                wc.id = id;
                webConsoles.push(wc);
                return wc;
            } else if (webConsoles.length > 0) {
                return webConsoles[0];
            } else {
                var wc = new WebConsole();
                webConsoles.push(wc);
                return wc;
            }
        },
        newInstance: function(id) {
            if (id) {
                for (var i = 0; i < webConsoles.length; ++i) {
                    if (webConsoles[i].id === id) {
                        //webConsoles[i] = new WebConsole();
                        //webConsoles[i].id = id;
                        return webConsoles[id];
                    }
                }
            } else {
                var wc = new WebConsole();
                webConsoles.push(wc);
                return wc;
            }
        }
    };

});