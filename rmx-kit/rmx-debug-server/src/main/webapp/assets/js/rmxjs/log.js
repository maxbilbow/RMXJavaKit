/**
 * Created by bilbowm on 23/11/2015.
 */
define(['./cookies','./pubsub'],function($c,$ps){

   var Log = function(msg){

       var user = $c.get('username');
       if (!user) {
           $c.set('username',"unknown user", $c.expire);
       }
       return (function($this)
       {
           $ps.info('A new log was created',$this);
           return $this;
       })({
           id: null,
           logType: 'Message',
           channel: 'debug.info',
           message: msg | '',
           timestamp: +new Date(),
           sender: user
       });
   }
    return Log;
});