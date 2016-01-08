var maps = maps || {};

var successColour = "#7BFF7B";
var heldColour = "#FFFE7F";
var failedColour = "#FF7F7F";

maps['MRVJOB_1.0'] = {
    functions: [
        {row: 1, col: 2, name: 'f03', text: '03\nHeld', shape: 'square', activeFill: heldColour, desc: 'Hold and alert'},    
        {row: 1, col: 14, name: 'f44', text: '44', shape: 'square', desc: 'Issue bundled Ubr File (U13, U14)'},    
        
        
        {row: 2, col: 2, name: 'f02', text: '02', shape: 'diamond', desc: 'Check read to be replaced exists'},
    	{row: 2, col: 3, name: 'f04', text: '04', shape: 'square', desc: 'Update read to be replaced'},
    	{row: 2, col: 14, name: 'f43', text: '43', shape: 'square', desc: 'Mark pending reads as INDUSTRY_ISSUED'},
    	{row: 2, col: 18, name: 'f32', text: '32', shape: 'square', desc: 'Store data'},
    	{row: 2, col: 19, name: 'f33', text: '33', shape: 'square', activeFill: successColour, desc: 'Issue UDR file'},
                
        {row: 3, col: 0, name: 'fStart', text: 'Start', shape: 'circle', desc: 'Start the process', fill: '#7F7FFF'},
        {row: 3, col: 1, name: 'f55', text: '55', shape: 'diamond', desc: 'Are valid set values valid?'},
        {row: 3, col: 2, name: 'f01', text: '01', shape: 'diamond', desc: 'Is replacement read?'},
        {row: 3, col: 3, name: 'f05', text: '05', shape: 'diamond', desc: 'Check read already exists'},
        {row: 3, col: 4, name: 'f07', text: '07', shape: 'diamond', desc: 'Check asset read type is record only'},
        {row: 3, col: 5, name: 'f09', text: '09', shape: 'diamond', desc: 'Is validation on?'},
        {row: 3, col: 6, name: 'f10', text: '10', shape: 'diamond', desc: 'Check last accepted reads present'},
        {row: 3, col: 7, name: 'f12', text: '12', shape: 'diamond', desc: 'CV lookup successful?'},
        {row: 3, col: 8, name: 'f14', text: '14', shape: 'diamond', desc: 'Check mandatory field present'},
        {row: 3, col: 9, name: 'f16', text: '16', shape: 'square', desc: 'Calculate energy'},
        {row: 3, col: 10, name: 'f17', text: '17', shape: 'square', desc: 'Calculate tolerances'},
        {row: 3, col: 11, name: 'f18', text: '18', shape: 'diamond', desc: 'Is asset read valid?'},
        {row: 3, col: 12, name: 'f21', text: '21', shape: 'diamond', desc: 'Is check read?'},
        {row: 3, col: 13, name: 'f24', text: '24', shape: 'diamond', desc: 'Is settlement class 3?'},
        {row: 3, col: 14, name: 'f42', text: '42', shape: 'diamond', desc: 'Are there any reads in pending for this MtpntRef and Serial Number?'},
        {row: 3, col: 17, name: 'f31', text: '31', shape: 'diamond', desc: 'Is Settlement class 2?'},
        
        {row: 4, col: 1, name: 'f59', text: '59', shape: 'square', activeFill: failedColour, desc: 'Issue URS Read Rejection (UO2, S72)'},
        {row: 4, col: 3, name: 'f60', text: '60', shape: 'square', activeFill: failedColour, desc: 'Issue URS Read Rejection (UO2, S72)'},
        {row: 4, col: 4, name: 'f08', text: '08', shape: 'square', activeFill: successColour, desc: 'Store record only reads'},
        {row: 4, col: 6, name: 'f11', text: '11\nHeld', shape: 'square', activeFill: heldColour, desc: 'Hold and alert'},
        {row: 4, col: 7, name: 'f13', text: '13\nHeld', shape: 'square', activeFill: heldColour, desc: 'Hold and alert'},
        {row: 4, col: 8, name: 'f15', text: '15\nHeld', shape: 'square', activeFill: heldColour, desc: 'Hold and alert'},
        {row: 4, col: 11, name: 'f19', text: '19', shape: 'square', desc: 'Store data'},
        {row: 4, col: 12, name: 'f22', text: '22', shape: 'square', desc: 'Store data'},
        {row: 4, col: 13, name: 'f45', text: '45', shape: 'diamond', desc: 'Check is opening read?'},
        {row: 4, col: 14, name: 'f57', text: '57', shape: 'square', desc: 'Mark all previous pending reads as issued.'},  
        {row: 4, col: 15, name: 'f46', text: '46', shape: 'square', desc: 'Store Data'},
        {row: 4, col: 16, name: 'f47', text: '47', shape: 'square', activeFill: successColour, desc: 'Issue bundled UBR file (U13, U14).'},  
        {row: 4, col: 18, name: 'f58', text: '58', shape: 'diamond', desc: 'Is Validation On?'},
        {row: 4, col: 19, name: 'f36', text: '36', shape: 'diamond', desc: 'Passes periodic validation?'},
        {row: 4, col: 20, name: 'f39', text: '39', shape: 'square', desc: 'Store data'},
        {row: 4, col: 21, name: 'f40', text: '40', shape: 'square', activeFill: successColour, desc: 'Issue UMR file'},
        
        {row: 5, col: 11, name: 'f20', text: '20', shape: 'square', activeFill: failedColour, desc: 'Issue URS read rejection'},
        {row: 5, col: 12, name: 'f23', text: '23', shape: 'square', activeFill: successColour, desc: 'Issue SFN file'}, 
        {row: 5, col: 13, name: 'f48', text: '48', shape: 'diamond', desc: 'Does batch read frequency match the last pending read batch read frequency?'}, 
        {row: 5, col: 14, name: 'f49', text: '49', shape: 'square', desc: 'Mark all pending reads as INDUSTRY_ISSUED'}, 
        {row: 5, col: 15, name: 'f50', text: '50', shape: 'square', desc: 'Issue Bundled UBR File (U13, U14).'},
        {row: 5, col: 16, name: 'f51', text: '51', shape: 'square', activeFill: successColour, desc: 'Store Data'}, 
        {row: 5, col: 19, name: 'f37', text: '37', shape: 'square', desc: 'Store data'},
        
        {row: 6, col: 13, name: 'f25', text: '25', shape: 'square', desc: 'Store data'},
        {row: 6, col: 19, name: 'f38', text: '38', shape: 'square', activeFill: failedColour, desc: 'Issue URS read rejection'},        
        
        {row: 7, col: 13, name: 'f52', text: '52', shape: 'diamond', desc: 'Do ISSUED reads exist after this read date?'},
        {row: 7, col: 14, name: 'f53', text: '53', shape: 'square', desc: 'Store Data'},
        {row: 7, col: 15, name: 'f54', text: '54', shape: 'square', activeFill: successColour, desc: 'Issue Bundled UBR File (U13, U14).'},
        
        {row: 8, col: 13, name: 'f26', text: '26', shape: 'diamond', desc: 'Does PENDING count match batch frequency'},
        {row: 8, col: 14, name: 'f27', text: '27', shape: 'diamond', desc: 'Are they in sequence?'},  
        {row: 8, col: 15, name: 'f30', text: '30\nHeld', shape: 'square', activeFill: heldColour, desc: 'Hold and alert'},        
        
        {row: 9, col: 13, name: 'f41', text: '41', shape: 'square', activeFill: successColour, desc: 'Success'},
        {row: 9, col: 14, name: 'f28', text: '28', shape: 'square', desc: 'Store data'},
        
        {row: 10, col: 14, name: 'f29', text: '29', shape: 'square', activeFill: successColour, desc: 'Issue Bundled UBR file'}
    ],

    joins: [
    	//af*-1  == Y / true -- af*-2 == N / false
		{from: 'fStart', to: 'f55', name: 'aStart'},
		{from: 'f01', to: 'f02', name: 'af01-1', text: 'Y'},
		{from: 'f01', to: 'f05', name: 'af01-2', text: 'N'},
		{from: 'f02', to: 'f03', name: 'af02-2', text: 'N'},
		{from: 'f02', to: 'f04', name: 'af02-1', text: 'Y'},
		{from: 'f05', to: 'f60', name: 'af05-1', text: 'Y'},
		{from: 'f05', to: 'f07', name: 'af05-2', text: 'N'},
		{from: 'f07', to: 'f08', name: 'af07-1', text: 'Y'},
		{from: 'f07', to: 'f09', name: 'af07-2', text: 'N'},
		{from: 'f09', to: 'f10', name: 'af09-1', text: 'Y'},
		{from: 'f10', to: 'f11', name: 'af10-2', text: 'N'},
		{from: 'f10', to: 'f12', name: 'af10-1', text: 'Y'},
		{from: 'f12', to: 'f13', name: 'af12-2', text: 'N'},
		{from: 'f12', to: 'f14', name: 'af12-1', text: 'Y'},
		{from: 'f14', to: 'f15', name: 'af14-2', text: 'N'},
		{from: 'f14', to: 'f16', name: 'af14-1', text: 'Y'},
		{from: 'f16', to: 'f17', name: 'af16-1'},
		{from: 'f17', to: 'f18', name: 'af17-1'},
		{from: 'f18', to: 'f19', name: 'af18-2', text: 'N'},
		{from: 'f18', to: 'f21', name: 'af18-1', text: 'Y'},
		{from: 'f19', to: 'f20', name: 'af19-1'},
		{from: 'f21', to: 'f22', name: 'af21-1', text: 'Y'},
		{from: 'f21', to: 'f24', name: 'af21-2', text: 'N'},
		{from: 'f22', to: 'f23', name: 'af22-1'},		
		{from: 'f24', to: 'f45', name: 'af24-1', text: 'Y'},
		{from: 'f24', to: 'f42', name: 'af24-2', text: 'N'},
		
		{from: 'f25', to: 'f52', name: 'af25-1'},
		{from: 'f26', to: 'f41', name: 'af26-2', text: 'N'},
		{from: 'f26', to: 'f27', name: 'af26-1', text: 'Y'},
		
		{from: 'f27', to: 'f28', name: 'af27-1', text: 'Y'},
		{from: 'f28', to: 'f29', name: 'af28-1'},		

		{from: 'f32', to: 'f33', name: 'af32-1'},
		
		{from: 'f36', to: 'f37', name: 'af36-2', text: 'N'},
		{from: 'f36', to: 'f39', name: 'af36-1', text: 'Y'},
		{from: 'f37', to: 'f38', name: 'af37-1'},
		
		{from: 'f39', to: 'f40', name: 'af39-1'},
		
		{from: 'f42', to: 'f43', name: 'af42-1', text: 'Y'},
		{from: 'f42', to: 'f31', name: 'af42-2', text: 'N'},
		
		{from: 'f43', to: 'f44', name: 'af43-1'},
		
		{from: 'f45', to: 'f57', name: 'af45-1', text: 'Y'},
		{from: 'f45', to: 'f48', name: 'af45-2', text: 'N'},
		
		{from: 'f46', to: 'f47', name: 'af46-1'},
		
		{from: 'f48', to: 'f25', name: 'af48-1', text: 'Y'},
		{from: 'f48', to: 'f49', name: 'af48-2', text: 'N'},
		
		{from: 'f49', to: 'f50', name: 'af49-1'},
		
		{from: 'f50', to: 'f51', name: 'af50-1'},
		
		{from: 'f52', to: 'f26', name: 'af52-2', text:'N'},
		{from: 'f52', to: 'f53', name: 'af52-1', text:'Y'},
		
		{from: 'f53', to: 'f54', name: 'af53-1'},
		
		{from: 'f55', to: 'f01', name: 'af55-1', text: 'Y'},
		{from: 'f55', to: 'f59', name: 'af55-2', text: 'N'},
		
		{from: 'f57', to: 'f46', name: 'af-57-1'},
		
		{from: 'f58', to: 'f36', name: 'af-58-1', text: 'Y'}
    ],

postProcess : function(){
    //------------------------------------------------------------------------
    var start  = this.topMidCoord("f09");
    var end    = this.topMidCoord("f21");


    this.addAdvArrow({
        name   : 'af09-2',
        points : [start,
                 {x: start.x, y: start.y-40},
                 {x: end.x, y: start.y-40},
                 end],
       label  : {x: start.x+((end.x-start.x)/2), y: start.y-40, text: 'N'}
    });
    
  //------------------------------------------------------------------------
    var start  = this.rightMidCoord("f04");
    var end    = this.topMidCoord("f07");


    this.addAdvArrow({
        name   : 'af04-1',
        points : [start,
                 {x: end.x, y: start.y},
                 end]
    });
    
  //------------------------------------------------------------------------
    var start  = this.topMidCoord("f31");
    var end    = this.leftMidCoord("f32");


    this.addAdvArrow({
        name   : 'af31-1',
        points : [start,
                 {x: start.x, y: end.y},
                 end],
        label : {x: start.x-5, y: end.y-5, text:'Y'}
    });
    
  //------------------------------------------------------------------------
    var start  = this.bottomMidCoord("f31");
    var end    = this.leftMidCoord("f58");


    this.addAdvArrow({
        name   : 'af31-2',
        points : [start,
                 {x: start.x, y: end.y},
                 end],
        label : {x: start.x-5, y: end.y+5, text:'N'}
    });
    
    //------------------------------------------------------------------------

    var start = this.rightMidCoord("f27");
    var end = this.leftMidCoord("f30");
    
    this.addAdvArrow({
    	name: 'af27-2',
    	points:[start,
    	        end],
        label: {x:start.x+((end.x-start.x)/2), y:(start.y+(end.y-start.y)/2), text:'N'}
    });
    
    //-----------------------------------------------------------------------------
    	
	var start = this.rightMidCoord("f44");
	var end = this.leftMidCoord("f31");
	
	this.addAdvArrow({
		name: 'af44-1',
		points: [start,
		         {x: end.x, y: start.y},
		         end]		
	});
	
	//------------------------------------------------------------------------------
	
	var start = this.topMidCoord("f58");
	var end = this.topMidCoord("f39");
	
	this.addAdvArrow({
		name:	'af58-2',
		points:	[start,
		       	 {x: start.x, y: (start.y-25)},
		       	 {x: end.x, y: (start.y-25)},
		       	 end],
		label:	{x: start.x+((end.x-start.x)/2), y: (start.y-25), text:'N'}
	});
	

}
};