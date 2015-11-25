/**
 * Created by bilbowm on 25/11/2015.
 */
define(['./pubsub'],function ($ps) {
    var DefaultTerminal = function (webConsole) {
        var FN = 0, DESC = 1;
        /**
         *
         * @type {{echo: computers.echo}} returns true if the command was accepted
         */
        var computers = {
            echo:[function(cmd,txt,terminal){
                terminal.console.log(txt, 'ECHO: ');
                return true;
            },'Echo command'],
            help:[function(cmd,txt,terminal){
                var cmds = '\nClient-side Commands:';
                var keys = Object.keys(computers);
                for (var i=0;i<keys.length;++i)
                    cmds += '\n   /' + keys[i] + ': ' + computers[keys[i]][DESC];
                terminal.console.log(cmds,'HELP: ');
            },'Display available commands'],
            func:[function(cmd,txt,terminal){
                var name = cmd[1];
                var split = 2;
                var desc = 'custom function';
                var response = cmd.slice(split).join(' ');
                terminal.console.log(txt, 'NEW FUNC: ');
                computers[name] = [function (cmd,txt,terminal) {
                    try {
                        txt = new Function('cmd','txt','terminal',response)(cmd,txt,terminal);
                    } catch (e) {
                        txt = response + ': ' + e;
                    }
                    if (txt)
                        terminal.console.log(txt, cmd[0] + ': ');
                    return true;
                },desc];
                return true;
            },'Create a new function. parameters are Array cmd, String txt, Terminal terminal.' +
            '\n    e.g. "/func test return \'IT WORKED! \' + txt;"']
        };

        return {
            cmdKey:'/',console: webConsole || document.console,
            /**
             *
             * @param cmd
             * @returns {boolean} true if a command was accepted
             */
            process: function (txt) { //Returns false if no command was received
                if (txt) {
                    if (this.expectsReply && this.onReply) {
                        return this.onReply(txt, this); //process reply
                    } else if (txt[0] === this.cmdKey) {
                        $ps.info('Received command: ' + cmd);
                        var cmd = txt.substring(1).split(' ');
                        var process = computers[cmd[0]];
                        if (process) {
                            return process[FN](cmd, cmd.slice(1).join(' '), this);
                        } else {
                            return false;
                        }
                    }
                }
                return false;

            },
            /**
             * Add a processor for a given command
             * @param key
             * @param processor
             */
            onProcess: function (key,processor) {
                computers[key] = processor;
            },
            onReply: function(txt,$this){
                $ps.warn('onReply was not set:' + txt);
                return false;
            },
            expectsReply: false
        }
    };
    return DefaultTerminal;
});

