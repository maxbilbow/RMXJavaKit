/**
 * Created by bilbowm on 14/10/2015.
 */
{
    var fjwa = {
        errorCount: 0,
        logCount: 0
    };


    function listenForErrors() {

    }

    function checkForErrors(root) {
        var path;
        if (root == null)
            path = '';//errorLog.json';
        else if (root.last() != "/")
            path = root + '/';//errorLog.json';

        $(document).ready(function () {
            $.getJSON(path + 'errorLog.json', {
                ajax: 'true'
            }, function (data) {
                var errors = data[0];
                var logs = data[1];
                var output = '';
                if (errors.length == fjwa.errorCount && logs.length == 0)// fjwa.logCount)
                    return;

                if (errors.length > 0) {
                    fjwa.errorCount = errors.length;
                    output += '<div id="fjwa-errors"><h3>ERRORS:</h3><ul>';
                    for (var i = 0; i < fjwa.errorCount; ++i)
                        output += '<li>' + errors[i] + '</li>';
                    output += '</ul></div>';
                }

                if (logs.length > 0) {
                    fjwa.logCount = logs.length;
                    output += '<div id="fjwa-log"><h3>Logs:</h3><ul>';
                    for (var i = 0; i < fjwa.logCount; ++i)
                        output += '<li>' + logs[i] + '</li>';
                    output += '</ul></div>';
                }
                $("div.rmx-error-log").html(output);
            });
        });


    }
}