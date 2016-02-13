/**
 * Created by Max Bilbow on 24/11/2015.
 */
define(['./pub-sub'], function ($ps) {

    var JsonDecoder = function () {
        function tryParseJSON(data, desc) {
            try {
                $ps.info('Attempting to parse as JSON: ' + desc,data);
                return JSON.parse(data);
            } catch (e) {
                $ps.info('Could not parse data... ', e);
            }
        }
        $ps.info('LogDecoder initialized.');

        function parseDebug(evtData)
        {
            var clazz = '';

            if (evtData && evtData.length > 4) {
                var db = evtData.substr(0,4);
                switch (db){
                    case 'INFO':
                        clazz = 'class="debug-info"';
                        break;
                    case 'WARN':
                        clazz = 'class="debug-warning"';
                        break;
                    case 'ERROR':
                        clazz = 'class="debug-error"';
                        break;
                }

            }
            return '<span ' + clazz + '>' + evtData + '</span>';

        }
        return {
            decode: function (evtData) {
                if (!evtData) {
                    throw new Error('Event data was undefined!');
                }
                var data = tryParseJSON(evtData,'evt.data');
                if (!data) {
                    $ps.info('failed to parse as JSON. Returning original...', evtData);
                    return parseDebug(evtData);
                }
                var json = data.body || data;
                $ps.info('Attempting to decode JSON: ',json);


                var result = 'JSON DATA:  ';

                for (var key in json) {
                    if (json.hasOwnProperty(key)) {
                        result += '<br/>' + key + ': ' + json[key];
                    }
                }
                return result;
            }
        };
    };
    return JsonDecoder;
});