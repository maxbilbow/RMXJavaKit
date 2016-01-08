/**
 * @author Rizvan Fujasuddin
 * @version 2.0
 */

// plugin to draw an arrow
Raphael.fn.arrow = function (x1, y1, x2, y2, size) {
  var angle = Raphael.angle(x1, y1, x2, y2); //line
  var a45 = Raphael.rad(angle - 45);
  var a45m = Raphael.rad(angle + 45);
  var x2a = x2 + Math.cos(a45) * size;
  var y2a = y2 + Math.sin(a45) * size;
  var x2b = x2 + Math.cos(a45m) * size;
  var y2b = y2 + Math.sin(a45m) * size;

  var line = "M" + x1 + " " + y1 + " L" + x2 + " " + y2 + "M" + x2 + " "
    + y2 + " L" + x2a + " " + y2a + "M" + x2 + " " + y2 + " L" + x2b
    + " " + y2b;
  return this.path(line);
};


// plugin to draw an advanced arrow
Raphael.fn.advArrow = function (points, size) {
  var pointsLen = points.length;
  var line = "M" + points[0].x + " " + points[0].y; // move to start

  // draw all lines upto the last one
  for (var i = 0; i < pointsLen; i++) {
    var x = points[i].x;
    var y = points[i].y;
    line += " L" + x + " " + y;
  }

  var x1 = points[points.length - 2].x;
  var y1 = points[points.length - 2].y;
  var x2 = points[points.length - 1].x;
  var y2 = points[points.length - 1].y;

  var angle = Raphael.angle(x1, y1, x2, y2);
  var a45 = Raphael.rad(angle - 45);
  var a45m = Raphael.rad(angle + 45);

  var x2a = x2 + Math.cos(a45) * size;
  var y2a = y2 + Math.sin(a45) * size;
  var x2b = x2 + Math.cos(a45m) * size;
  var y2b = y2 + Math.sin(a45m) * size;

  // arrow head
  line += "M" + x2 + " " + y2 + "L" + x2a + " " + y2a + "M" + x2 + " " + y2
    + "L" + x2b + " " + y2b;
  return this.path(line);
};

function ProcessMap(aEl, aMap, aLogEntries, aDrawAllMap) {
  var obj =  {
    mElements: {}, // will hold all elements
    mPaper: null, // will hold raphael canvas once initialised
    mSquareLen: 100,
    mEl: null, //holds process map div elemnt
    mShapeTextStyle: {"font-size": 16, "font-family": "sans-serif"},

    mToolTip: null, // will hold tool jquery element (initialised in init)
    mInfoBox: null, // will hold tool jquery element (initialised in init)

    _dimensions: null, //lazy initialization do not remove

    // draw a function
    // @example addFunction({x: 10, y: 30, name: 'f01', text: '01'})
    // @example addFunction({row: 1, col: 10, name: 'f01', text: '01'})
    addFunction: function (aEl) {
      var x = aEl.col * this.mSquareLen;
      var y = aEl.row * this.mSquareLen;

      x += 25; // for padding at the edge
      y += 25; // for padding at the edge (since diamond goes up)

      aEl.shape = (aEl.shape) ? aEl.shape : 'diamond'; //default shape
      var self = this;

      var shapeRenderers  = {
        diamond : this.drawDiamond,
        square  : this.drawSquare,
        circle  : this.drawCircle,
        hexagon : this.drawHexagon
      };

      var renderedShape = shapeRenderers[aEl.shape].bind(self)(aEl, x, y); //bind function's this and run
      if (aEl.fill) {
        renderedShape.func.attr({fill: aEl.fill});
      }
      else{
        renderedShape.func.attr({"fill": "#DEDEFF"}); //gradient fill
      }

      if (aEl.activeFill) {
        renderedShape.func.data("activeFill", aEl.activeFill);
      }
      else{
        renderedShape.func.data("activeFill", "#FFBE7B"); //gradient fill
      }

      if (aEl.bypassable) { renderedShape.func.data("bypassable", aEl.bypassable); }

      renderedShape.func.data("desc", aEl.desc);

      this.addShapeEvents(renderedShape);

      this.mElements[aEl.name] = renderedShape.func;
      return this;
    },

    drawDiamond: function(aEl, x, y){
      //shape path
      var path =
          this.move(x-5, y+25)
          + this.line(x+25, y-5)
          + this.line(x+55, y+25)
          + this.line(x+25, y+55)
         // + this.line(x+25, y)
         // + this.line(x+25, y+25)
          + "Z";

      //draw shape
      var func   = this.mPaper.path(path);
      var funcBB = func.getBBox();
      var text   = this.mPaper.text(funcBB.x + 30, funcBB.y + 30, aEl.text || '').attr(this.mShapeTextStyle);
      return {func: func, text: text};
    },

    drawSquare: function(aEl, x, y){
      var func = this.mPaper.rect(x, y, 50, 50);
      var funcBB = func.getBBox();
      var text = this.mPaper.text(funcBB.x + 25, funcBB.y + 25, aEl.text || '').attr(this.mShapeTextStyle);
      return {func: func, text: text};
    },


    drawCircle: function(aEl, x, y){
      var func = this.mPaper.circle(x + 25, y + 25, 30);
      var funcBB = func.getBBox();
      var text = this.mPaper.text(funcBB.x + 30, funcBB.y + 30, aEl.text || '').attr(this.mShapeTextStyle);
      return {func: func, text: text};
    },

    drawHexagon: function(aEl, x, y){
      //shape path
      var path =this.move(x, y+25)
                    + this.line(x+10, y)
                    + this.line(x+40, y)
                    + this.line(x+50, y+25)
                    + this.line(x+40, y+50)
                    + this.line(x+10, y+50)
                    + "Z";

      //draw shape
      var func   = this.mPaper.path(path).attr({fill: '#d4d4d4', 'stroke-width': 1, 'stroke': 'black'});
      var funcBB = func.getBBox();
      var text   = this.mPaper.text(funcBB.x + 25, funcBB.y + 25, aEl.text || '').attr(this.mShapeTextStyle);
      return {func: func, text: text};
    },

    move: function(x, y){
      return "M" + x + " "+ y;
    },

    line: function(x, y){
      return "L"+x + " "+ y;
    },

    resizeSVG: function (aHeight, aWidth) {
      this.mEl.style.height = aHeight;
      this.mEl.style.width = aWidth;

      var svgTags = document.getElementsByTagName('svg');
      if (svgTags.length) {
        var svgEl = svgTags[0];
        svgEl.setAttribute('height', aHeight);
        svgEl.setAttribute('width', aWidth);
      }
    },

    resizeWindow: function (aHeight, aWidth) {
      aHeight = (aHeight > window.screen.height) ? window.screen.height : aHeight;
      aWidth = (aWidth > window.screen.width) ? window.screen.width : aWidth;
      window.resizeTo(aWidth, aHeight);
    },

    getDimensions: function (map) {
      if (this._dimensions == null) {
        var functions = map.functions;
        var totalFunctions = functions.length;

        var maxRow = 0;
        var maxCol = 0;

        for (var i = 0; i < totalFunctions; i++) {
          row = functions[i].row;
          col = functions[i].col;

          if (row > maxRow) maxRow = row;
          if (col > maxCol) maxCol = col;
        }

        var height = (maxRow + 1) * this.mSquareLen;
        var width = (maxCol + 1) * this.mSquareLen;

        this._dimensions = {'height': height+30, 'width': width + 10};
      }
      return this._dimensions;
    },

    getMidPoint: function (aEl) {
      var bbx = aEl.getBBox();
      var midX = bbx.x + (bbx.width / 2);
      var midY = bbx.y + (bbx.height / 2);
      return {x: midX, y: midY};
    },

    getDirection: function (aEl1, aEl2) {
      var p1 = this.getMidPoint(aEl1);
      var p2 = this.getMidPoint(aEl2);

      var deltaX = Math.abs(p2.x - p1.x);
      var deltaY = Math.abs(p2.y - p1.y);

      //if diff in x direction is greater
      if (deltaX > deltaY) {
        if (p1.x < p2.x) {
          return 'r';
        }
        else if (p1.x > p2.x) {
          return 'l';
        }
      }
      else //if diff in y direction is greater
      {
        if (p1.y < p2.y) {
          return 'd';
        }
        else if (p1.y > p2.y) {
          return 'u';
        }
      }
    },

    // add hover to element based in element's desc attribute
    addShapeEvents: function (aEl) {
      //draw transparent box to bind events to the element
      var eventArea = this.mPaper.rect().attr(aEl.func.getBBox()).attr({
        fill: "#000", opacity: 0, cursor: "default"
      });

      var self = this;

      eventArea.hover(
        function () {  // mouse in
          var elBB = this.getBBox();
          self.mToolTip.css({
            left: elBB.x2 - 10,
            top: elBB.y2 - 10
          }) // move tool tip
            .html(aEl.func.data('desc'))
            .stop(true, true) //stop event propagation
            .fadeIn();
        },
        function () {
          self.mToolTip.fadeOut();
        }); // mouse out

      eventArea.click(function(){
        var desc = aEl.func.data('desc');
        var msg  = aEl.func.data('msg');
        
        if(msg != null)
    	{
        	msg = (msg) ? msg : "N/A";

            self.mInfoBox.find('#desc').html(desc);
            self.mInfoBox.find('#msg').html(msg);
            self.mInfoBox.fadeIn();
    	}
      });
    },

    // add element to map
    addElement: function (aElem, aName) {
      this.mElements[aName] = aElem;
    },

    // get element from map
    getElem: function (aName) {
      return this.mElements[aName];
    },

    // draw map from json notation
    drawMap: function (aJson) {
      if (aJson.preProcess) aJson.preProcess();

      this.addFunctions(aJson.functions);
      this.addJoins(aJson.joins);
      if (aJson.postProcess) aJson.postProcess.bind(this)();

      var dim = this.getDimensions(aJson);

      //this.resizeWindow(dim.height + 150, dim.width + 50);

      this.resizeSVG(dim.height, dim.width);

      return this;
      
    },

    // add a function to map
    addFunctions: function (aFunctions) {
      for (var i = 0; i < aFunctions.length; i++) {
        this.addFunction(aFunctions[i]);
      }
    },

    // add joins map
    addJoins: function (aJoins) {
      for (var i = 0; i < aJoins.length; i++) {
        var join = aJoins[i];

        //resolve direction if not specified
        if (!join.hasOwnProperty('type')) {
          var el1 = this.getElem(join.from);
          var el2 = this.getElem(join.to);

          join['type'] = this.getDirection(el1, el2);
        }

        if (join.type === 'r') {
          this.joinRight(join.from, join.to, join.name, join.text);
        } else if (join.type === 'd') {
          this.joinDown(join.from, join.to, join.name, join.text);
        } else if (join.type === 'l') {
          this.joinLeft(join.from, join.to, join.name, join.text);
        } else if (join.type === 'u') {
          this.joinUp(join.from, join.to, join.name, join.text);
        }
      }
    },

    // draw an arrow
    // @example addArrow({fromX: 60, fromY: 55, toX: 99, toY: 55, name: 'myarrow',
    // text: "n"})
    addArrow: function (aConf) {
      var arrow = this.mPaper.arrow(aConf.fromX, aConf.fromY, aConf.toX,
        aConf.toY, 8);

      var midX = (aConf.fromX + aConf.toX) / 2;
      var midY = (aConf.fromY + aConf.toY) / 2;

      // box for the text so is readable
      if (aConf.hasOwnProperty('text')) {
        this.mPaper.rect(midX - 5, midY - 5, 10, 10).attr({
          stroke: 'white',
          fill: 'white'
        });
        this.mPaper.text(midX, midY, aConf.text).attr({
          "font-size": 11,
          "font-family": "Arial, Helvetica, sans-serif"
        });
      }

      this.mElements[aConf.name] = arrow;
      return this;
    },

    addLabel: function (x, y, label) {
      this.mPaper.rect(x - 5, y - 5, 10, 10).attr({
        stroke: 'white',
        fill: 'white'
      });
      this.mPaper.text(x, y, label).attr({
        "font-size": 11,
        "font-family": "Arial, Helvetica, sans-serif"
      });
    },

    // add advanced join(elbow join)
    // @example addAdvArrow({points:[{x: 10, y:10}, {x: 30, y:30}, {x: 60, y:50}],
    // name: 'myarrow'})
    addAdvArrow: function (aObj) {
      var arrow = this.mPaper.advArrow(aObj.points, 8);
      this.mElements[aObj.name] = arrow;

      if (aObj.hasOwnProperty('label')) {
        var label = aObj.label;
        this.addLabel(label.x, label.y, label.text);
      }

      return this;
    },

    // @example joinUp('f01', 'f02', 'arrowname')
    joinUp: function (aBottomFunction, aTopFunction, aArrowName, aText) {
      var from = this.topMidCoord(aBottomFunction);
      var to = this.bottomMidCoord(aTopFunction);

      var obj = {
        fromX: from.x,
        fromY: from.y,
        toX: to.x,
        toY: to.y,
        name: aArrowName
      };
      if (typeof (aText) != "undefined")
        obj['text'] = aText;
      this.addArrow(obj);
      return this;
    },

    joinDown: function (aTopFunction, aBottomFunction, aArrowName, aText) {
      var from = this.bottomMidCoord(aTopFunction);
      var to = this.topMidCoord(aBottomFunction);

      var obj = {
        fromX: from.x,
        fromY: from.y,
        toX: to.x,
        toY: to.y,
        name: aArrowName
      };
      if (typeof (aText) != "undefined")
        obj['text'] = aText;
      this.addArrow(obj)

      return this;
    },

    joinLeft: function (aRightFunction, aLeftFunction, aArrowName, aText) {
      var from = this.leftMidCoord(aRightFunction);
      var to = this.rightMidCoord(aLeftFunction);

      var obj = {
        fromX: from.x,
        fromY: from.y,
        toX: to.x,
        toY: to.y,
        name: aArrowName
      };
      if (typeof (aText) != "undefined")
        obj['text'] = aText;
      this.addArrow(obj)

      return this;
    },

    joinRight: function (aLeftFunction, aRightFunction, aArrowName, aText) {
      var from = this.rightMidCoord(aLeftFunction);
      var to = this.leftMidCoord(aRightFunction);

      var obj = {
        fromX: from.x,
        fromY: from.y,
        toX: to.x,
        toY: to.y,
        name: aArrowName
      };
      if (typeof (aText) != "undefined")
        obj['text'] = aText;
      this.addArrow(obj);

      return this;
    },

    // top middle coordinate of a function
    topMidCoord: function (aFunction) {
      var funcBB = this.mElements[aFunction].getBBox();
      var x = funcBB.x + (funcBB.width / 2);
      var y = funcBB.y;
      return {'x': x, 'y': y};
    },

    // right middle coordinate of a function
    rightMidCoord: function (aFunction) {
      var funcBB = this.mElements[aFunction].getBBox();
      var x = funcBB.x2;
      var y = funcBB.y + (funcBB.width / 2);
      return {'x': x, 'y': y };
    },

    // left middle coordinate of a function
    leftMidCoord: function (aFunction) {
      var funcBB = this.mElements[aFunction].getBBox();
      var x = funcBB.x;
      var y = funcBB.y + (funcBB.width / 2);
      return {'x': x, 'y': y};
    },

    // bottom middle coordinate of a function
    bottomMidCoord: function (aFunction) {
      var funcBB = this.mElements[aFunction].getBBox();
      var x = funcBB.x + (funcBB.width / 2);
      var y = funcBB.y2;
      return {'x': x, 'y': y};
    },

    // activate elements
    // @example activateElems(['f01', 'f01y'])
    highlightPath: function (aLogEntries) {
    	//call the function to draw the ALL process map if we call the process map with a special flag
    	if(aDrawAllMap != null && aDrawAllMap)
		{
    		this.drawAllMap(aLogEntries);
		}
    	else
		{
	      var pattern = /[A-Za-z_]*(\d*)/;
	
	      for (var i in aLogEntries) {
	        var entry = aLogEntries[i];
	        var match = pattern.exec(entry.op)
	        if (match) {
	        	//Change the result from true/false from opLog to 1 (yes) and 2(no) for arrow names
	        	if(entry.result)
	    		{
	        		var result = 1;
	    		}
	        	else
	    		{
	    			var result = 2;
	    		}
	        	 
	          var opNumber = match[1];
	          var functionName = "f" + opNumber;
	          var arrowName = "af" + opNumber + "-" + result;
	          var func = this.mElements[functionName];
	
	          func.attr({fill: func.data('activeFill')});
	          var arrow = this.mElements[arrowName];
	
	          if(arrow) arrow.attr({stroke: 'orange', 'stroke-width': '2px'});
	        }
	      }
	      return this;
		}
    },
    
    drawAllMap : function (aLogEntries)
    {
    	var pattern = /[A-Za-z_]*(\d*)/;
    	
    	for (var i in aLogEntries)
		{
    		var entry = aLogEntries[i];
    		var match = pattern.exec(entry.op);
	        var count = entry['count'];
	        
	        if(count != null)
			{
	        	var opNumber = match[1];
	        	var functionName = "f" + opNumber;
	        	if(this.mElements[functionName] != null)
        		{
		        	var func = this.mElements[functionName];
		        	var funcBox = func.getBBox();
	        		func.attr({fill: func.data('activeFill')});
		        	var text = this.mPaper.text(funcBox.x + 45, funcBox.y + 75, count || '').attr('font-size', 18).attr('fill', 'green');
        		}
			}
		}
    	return this;
    },

    init: function (aEl, aMap) {
      var el = document.getElementById(aEl);
      //get map dimensions and resize div
      var dim = this.getDimensions(aMap);
      el.style.height = dim.height + "px";
      el.style.width = dim.width + "px";

      this.mEl = el;
      var bbx = el.getBoundingClientRect(); // need to run after dom ready
      this.mPaper = new Raphael(el, bbx.width, bbx.height);

      this.mToolTip = $("<div></div>").css({
        "position": 'absolute',
        "display": 'none',
        "float": 'left',
        "color": 'white',
         opacity: '0.80',
        "font-weight": 'bold',
        "border-radius": '10px',
        "background-color": '#292929',
        "padding": '5px 5px 5px 5px',
        "max-width": '150px',
        "_width": '150px', // for ie
        "font-size": '12px',
        'font-family': 'sans-serif'
      });

      this.mInfoBox = $("<div> \
                           <div id='close' style='text-align: right; cursor: pointer; font-weight: bold;'>x &nbsp;</div>\
                           <div><b>Description: </b> \
                              <span id='desc'></span>   \
                           </div>                    \
                           <div><b>Message: </b>     \
                            <span id='msg'></span>    \
                           </div> \
                          </div>")
      .css({
        position: 'absolute',
        'display': 'none',
        'border-radius': '10px',
        top: '1px',
        left: '2px',
        width: '100%',
        right: '1px',
        opacity: '0.9',
        color: 'white',
        "background-color": '#292929',
        "padding": '5px 5px 5px 5px',
        "font-size": '14px'
      });

      $(el).append(this.mToolTip);
      $(el).append(this.mInfoBox);
      this.drawMap(aMap);

      var self = this;

      //info box close button event listener
      this.mInfoBox.find('#close').click(function(){
        self.mInfoBox.fadeOut();
      });

      this.highlightPath(aLogEntries);

      return this;
    }
  } // end  obj
  obj.init(aEl, aMap, aLogEntries);
  return obj;
}