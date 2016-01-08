var maps = maps || {};

var successColour = "#7BFF7B";
var heldColour = "#FFFE7F";
var failedColour = "#FF7F7F";

maps['MRVSFR_1.0'] = {
    functions: [
        {row: 0, col: 0, name: 'fStart', text: 'Start', shape: 'circle', desc: 'Start the process', fill: '#7F7FFF'},
                
        {row: 1, col: 0, name: 'f03', text: '03', shape: 'diamond', desc: 'Find associated record'},
        {row: 1, col: 1, name: 'f05', text: '05', shape: 'square', activeFill: successColour, desc: 'Store Data'},
            
        {row: 2, col: 0, name: 'f04', text: '04\nHeld', shape: 'square', activeFill: heldColour,  desc: 'Hold and Alert'}        
    ],

    joins: [
        {from: 'fStart', to: 'f03', name: 'aStart'},
        {from: 'f03', to: 'f04', name: 'af03-2', text: 'N'},
        {from: 'f03', to: 'f05', name: 'af03-1', text: 'Y'}
    ]
};