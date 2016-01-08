function runProcessMapTests(){

  module("ProcessMaps test", {
    setup: function(){
      ProcessMap.mElements = {};
    }

  });

  //generate list of all expected joins for process map
  testJoins = function(map, ignoreFuncs){
	 var joins = [];
	 var ignores = ignoreFuncs || [];
	 ignores.push('fStart');

	 for(var i in map.functions){
	   var func = map.functions[i];

	   var ignore = false;
	   //ignore functions
	   for(var m in ignores){
		  if(func.name === ignores[m]){
			ignore = true;
			break;
		  }
	   }
	   if(ignore) continue;

	   //all functions that should have one exit
	   if(func.hasOwnProperty('shape') && func.shape==='square'){
		 //ignore held functions
		 if(!(func.hasOwnProperty('activeFill') && func.activeFill === 'red')){
			var name = func.name;

			if(name.charAt(0)=='f'){
			  name = name.slice(1);
			}
			else{
			  name = name.toUpperCase();
			}
		    joins.push("a"+name+'y');
		 }
	   }

	   //all decision functions (y and n exits)
	   else{
		   var name = func.name;

		   //handle specific case of Specific and Optional functions
		   if(name.charAt(0)=='f' ){
			  name = name.slice(1);
		   }
		   else{
			   name = name.toUpperCase();
		   }
		   joins.push("a"+ name +'y');
		   joins.push("a"+ name +'n');
	   }
	 }

	 for(var i= 0; i< joins.length; i++){
		if(!ProcessMap.mElements[joins[i]]){
		  ok(false, 'join: ' +  joins[i] + ' does not exist');
		}
     }
	 ok(true, "Joins tested: " + joins);
  };


  test("Test onupd_instl joins", function() {
	  var mapName = 'onupd_instl';
	  var ignoreFunctions = ['f13'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_appnt joins", function() {
	  var mapName = 'onupd_appnt';
	  var ignoreFunctions = ['f52'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_deapp joins", function() {
	  var mapName = 'onupd_deapp';
	  var ignoreFunctions = ['f13', 'f45', 'f42'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_deapp_de joins", function() {
	  var mapName = 'onupd_deapp_de';
	  var ignoreFunctions = ['f13', 'f45', 'fDE60'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_deapp_demo joins", function() {
	  var mapName = 'onupd_deapp_demo';
	  var ignoreFunctions = ['f13', 'f45', 'fDE60'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_deapp_ca joins", function() {
	  var mapName = 'onupd_deapp_ca';
	  var ignoreFunctions = ['f42', 'f45', 'fCA31', 'fCA19'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_deapp_cos joins", function() {
	  var mapName = 'onupd_deapp_cos';
	  var ignoreFunctions = ['f42', 'f45', 'fCOS61', 'fCOS33'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_deapp_spec joins", function() {
	  var mapName = 'onupd_deapp_spec';
	  var ignoreFunctions = ['f42', 'f45', 'fSPEC21', 'fSPEC07', 'fSPEC14', 'fSPEC16'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test onupd_remve joins", function() {
	  var mapName = 'onupd_remve';
	  var ignoreFunctions = ['f11','f30', 'f18'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test d0303_exchg joins", function() {
	  var mapName = 'd0303_exchg';
	  var ignoreFunctions = ['fE01','f34', 'f35'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test d0303_instl joins", function() {
	  var mapName = 'd0303_instl';
	  var ignoreFunctions = ['fE01', 'f15', 'f14'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test d0303_deapp joins", function() {
	  var mapName = 'd0303_deapp';
	  var ignoreFunctions = ['fE01','fE02', 'f23', 'f12', 'f14', 'f24'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test d0303_appnt joins", function() {
	  var mapName = 'd0303_appnt';
	  var ignoreFunctions = ['fE01', 'f11', 'f21'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test d0303_remve joins", function() {
	  var mapName = 'd0303_remve';
	  var ignoreFunctions = ['fE01', 'fE02', 'fE03', 'f19', 'f21', 'f05'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

  test("Test d0303_stins joins", function() {
	  var mapName = 'd0303_stins';
	  var ignoreFunctions = ['fE01', 'f13'];
	  ProcessMap.init('processMap', maps[mapName]);
	  testJoins(maps[mapName], ignoreFunctions);
  });

}