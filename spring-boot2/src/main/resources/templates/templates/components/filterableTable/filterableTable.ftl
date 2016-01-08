<#macro filterableTable numberOfrows>
<script>
    if(!!navigator.userAgent.match(/firefox/i) || !!navigator.userAgent.match(/trident/i)) {
        $('body').css({position: 'relative'});
    }
</script>

<style>
    #filter-container {
        margin-bottom: 10px;
        border: 1px solid rgba(0, 0, 0, 0.15);
        border-radius: 10px;
        padding: 5px;
        background-color: #BDE7CD;
    }

    .filterRow {
        margin-bottom: 5px;
    }

    .form-control[disabled] {
        background-color: #ffffff !important;
    }

    .customRow {
        width: 100%;
    }

    .customCol {
        width: 30%;
        display: inline-block;
    }

    .errorBorder {
        border: 2px solid #f00000;
    }
    
    #refreshButton {
    	cursor: pointer;
    }
</style>

<div id="filterable-content">
    <form id="filterForm" action="" method="get">
        <#if pagingCommand.filterHidden = false>
        <#if (filterOptions?size != 0 && numberOfrows != 0) || (appliedFiltersCount gt 0)>
            <div id="filter-container">
                <div class="row" id="filter-toggle-row" style="cursor:pointer;">
                    <h4 id="filter-toggle" class="col-md-12">
                        <i id="filter-toggle-icon" class="fa fa-caret-down"></i>
                        Filters
                    </h4>
                </div>
                <div id="filter-toggle-content">
                    <div class="">
                        <span>Select Column To Add Filter:</span>

                        <select name="" id="filterSelector">
                            <option value="start" selected>-- Select Column --</option>
                            <#list filterOptions as filter>
                                <option value="${filter.colName}" data-operator-options="${filter.operatorOptions}" data-value-options="${filter.valueOptions}" data-type="${filter.colType}">${filter.screenName}</option>
                            </#list>
                        </select>
                    </div>
                    <button id="filterButton" class="btn btn-primary btn-sm" type="button">Apply Filters</button>
                    <button id="delteFiltersButton" class="btn btn-danger btn-sm" type="button" style="display: none">Delete All Filters</button>
                    <span id="errorMessage" style="color: #E74C3C; display: none;"><strong>The form contains unapplied filters, please apply them to adjust the options</strong></span>
                </div>
            </div>
        </#if>
        </#if>
        <div id="tableContainer">
        	<div class="row">
	        	<div class="col-md-offset-11 col-md-1" style="padding-top:5px; padding-bottom:10px;">
	        		<i id="refreshButton" class="fa fa-refresh fa-2x text-success"></i> 
	        	</div>
        	</div>
            <div class="tableContent">
                <#if numberOfrows != 0>
                    <#nested/>
                    <div class="text-center">
                        <div id="pagingContainer" class="pagination"></div>
                    </div>
                    <div class="text-center">
                        <span>Records per page</span>
                        <select name="recordsPerPage" id="recordsPerPage" style="margin-left: 5px;">
                            <option value="10">10</option>d
                            <option value="25">25</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                            <option value="250">250</option>
                            <option value="500">500</option>
                            <option value="1000">1000</option>
                        </select>
                    </div>
                    <div class="text-center" style="margin-top: 10px">
                        <span>Total Number Of Records: ${count}</span>
                    </div>
                <#else>
                    <div class="text-center">
                        <strong>No results were found.</strong>
                    </div>
                </#if>
            </div>
        </div>

        <input id="pageNumber" type="hidden" name="pageNumber" value=""/>
        <input id="sortKey" type="hidden" name="sortKey" value=""/>
        <input id="sortDirection" type="hidden" name="sortDirection" value=""/>
        <input id="filterCollapsed" type="hidden" name="filterCollapsed"/>
        <input id="formSubmitted" type="hidden" name="formSubmitted" value="true"/>
    </form>
</div>

<#-- Filter Row Template -->
<script id="filterRow-template" type="text/x-handlebars-template">
    <div class="filterRow customRow">
        <input name="" value="{{screenName}}" type="text" class="customCol noselect" disabled/>

        <select name="filters.criteria" class="customCol js-filterCriteria">
            {{#each colOperatorOptions}}
            <option value="{{this}}" {{#is ../criteria this}} selected {{/is}}>{{this}}</option>
            {{/each}}
        </select>

        {{#is colType 'enum'}}
            <select name="filters.value" class="customCol js-filterValue">
                {{#each colValueOptions}}
                <option value="{{this}}" {{#is ../value this }} selected {{/is}}>{{this}}</option>
                {{/each}}
            </select>
        {{else}}
            {{#is colType 'time'}}
                <input name="filter.value" id="{{datepickerId}}" value="{{#if value}}{{value}}{{/if}}" type="text" class="customCol js-filterValue timepicker" autocomplete="off"/>
            {{else}}
			{{#is colType 'date'}}
				<input name="filter.value" id="{{datepickerId}}" value="{{#if value}}{{value}}{{/if}}" type="text" class="customCol js-filterValue datePicker" autocomplete="off"/>
			{{else}}
				{{#if colValueOptions}}
					<select name="filter.value" class="customCol js-filterValue">
					{{#each colValueOptions}}
                		<option value="{{this}}" {{#is ../value this }} selected {{/is}}>{{this}}</option>
					{{/each}}
					</select>
				{{else}}
                	<input name="filters.value" value="{{#if value}}{{value}}{{/if}}" type="text" class="customCol js-filterValue {{#is colType 'numeric'}}numeric{{/is}}" maxlength=""/>
            	{{/if}}
			{{/is}}
			{{/is}}
        {{/is}}

        <button type="button" class="js-removeFilterButton"><i class="fa fa-times"></i></button>
        <input class="js-filterColType" type="hidden" name="filters.colType" value="{{colType}}"/>
        <input class="js-filterColName" type="hidden" name="filters.colName" value="{{colName}}"/>
    </div>
</script>

<#-- Scripts -->
<link rel="stylesheet" href="${rc.contextPath}/css/vendor/datetimepicker.css"/>
<script type="text/javascript" src="${rc.contextPath}/js/vendor/datetimepicker.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/vendor/handlebars-v2.0.0.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/vendor/swag.js"></script>
<script>Swag.registerHelpers(Handlebars);</script>
<script>
    Handlebars.registerHelper("varComp", function(arg1, arg2, options) {
        var first = arg1;
        var second = arg2;
        if(first === second) {
            return options.fn(this);
        }
        return options.inverse(this);
    });
</script>
<script>
    var pagingTable = {
        buildPagination: function (pageNumber, numberOfPages) {
            var pageNumbers = [pageNumber];
            var left = pageNumber;
            var right = pageNumber;
            var leftStop = false;
            var rightStop = false;

            while(pageNumbers.length < 5 && !(rightStop && leftStop)) {
                if(--left > 0) {
                    pageNumbers.push(left);
                    leftStop = false;
                } else {
                    leftStop = true;
                }
                if(++right <= numberOfPages) {
                    pageNumbers.push(right);
                    rightStop = false;
                } else {
                    rightStop = true;
                }
            }

            pageNumbers.sort(function(a, b) {
                if (a == b)
                    return 0;
                if (a < b)
                    return -1;
                else
                    return 1;
            });

            return pageNumbers;
        },

        renderPageNumbers: function(pageNumbers, pageNumber, numberOfPages) {
            var paginationHTML = '';

            //check if we need the previous page arrow
            if(numberOfPages > 1 && pageNumber > 1) {
                paginationHTML = paginationHTML + '<li><a class="js-pageFirst" href="#">First</a></li>';
                paginationHTML = paginationHTML + '<li><a class="js-pageBack" href="#">&laquo;</a></li>';
            }

            for(var i = 0; i < pageNumbers.length; i++) {
                if(pageNumbers[i] == pageNumber) {
                    paginationHTML = paginationHTML + '<li class="active"><a class="js-pageNumber" href="#">' + pageNumbers[i] + '</a></li>'
                } else {
                    paginationHTML = paginationHTML + '<li><a class="js-pageNumber" href="#">' + pageNumbers[i] + '</a></li>'
                }
            }

            //check if we need the next page arrow
            if(numberOfPages > 1 && pageNumber < numberOfPages) {
                paginationHTML = paginationHTML + '<li><a class="js-pageForward" href="#">&raquo;</a></li>';
                paginationHTML = paginationHTML + '<li><a class="js-pageLast" href="#">Last</a></li>';
            }

            $('#pagingContainer').html(paginationHTML);
        }
    };

    //This code is run on page load to create the applied filters and the pagination controls
    //It also sets up triggers to reload the page when the options change
    $(function(){
        //grab all of the vars needed out of freemarker
        var recordsPerPage = ${pagingCommand.recordsPerPage?c};
        var pageNumber = ${pagingCommand.pageNumber?c};
        var numberOfPages = ${pagingCommand.numberOfPages?c};
        var sortKey = '${pagingCommand.sortKey}';
        var sortDirection = '${pagingCommand.sortDirection}';
        var filterCollapsed = ${pagingCommand.filterCollapsed?string("true", "false")};
        var appliedFilters = ${appliedFilters};

        //run the pagination builder
        var pageNumbers = pagingTable.buildPagination(pageNumber, numberOfPages);
        pagingTable.renderPageNumbers(pageNumbers, pageNumber, numberOfPages);

        //set the hidden fields to the current values for the next page GET
        $('#recordsPerPage').val(recordsPerPage);
        $('#pageNumber').val(pageNumber);
        $('#sortKey').val(sortKey);
        $('#sortDirection').val(sortDirection);
        $('#filterCollapsed').val(filterCollapsed);
        var form = $('#filterForm');

        //set the sort icon on the correct col and the direction
        if(sortDirection === 'asc') {
            $("th[data-key='" + sortKey +"']").append('<i class="fa fa-sort-asc pull-right"></i>');
        } else {
            $("th[data-key='" + sortKey +"']").append('<i class="fa fa-sort-desc pull-right"></i>');
        }

        //if the filters are collapsed then collapse them
        if(filterCollapsed === true) {
            $('#filter-toggle-icon').removeClass('fa-caret-down').addClass('fa-caret-right');
            $('#filter-toggle-content').hide();
        }
        
        //reload the page when the sort key is changed
        $('.js-sort').click(function() {
            if(!checkForUnappliedFilter()) {
                //flip the sort direction and update the sort key key and submit
                sortDirection = sortDirection === 'asc' ?  'desc' : 'asc';
                $('#sortDirection').val(sortDirection);
                $('#sortKey').val($(this).data('key'));
                validateAndSubmitFilters();
            }
        });

        //reload the page when the page number is changed
        $('.js-pageNumber').click(function() {
            if(!checkForUnappliedFilter()) {
                var pageNumber = $(this).html();
                $('#pageNumber').val(pageNumber);
                validateAndSubmitFilters();
            }
        });

        //reload the page when we move to the first page
        $('.js-pageFirst').click(function() {
            if(!checkForUnappliedFilter()) {
                $('#pageNumber').val(1);
                validateAndSubmitFilters();
            }
        });

        //reload the page when we move back a page
        $('.js-pageBack').click(function() {
            if(!checkForUnappliedFilter()) {
                var number = parseInt($('#pageNumber').val(), 10);
                $('#pageNumber').val(number - 1);
                validateAndSubmitFilters();
            }
        });

        //reload the page when we move forward a page
        $('.js-pageForward').click(function() {
            if(!checkForUnappliedFilter()) {
                var number = parseInt($('#pageNumber').val(), 10);
                $('#pageNumber').val(number + 1);
                validateAndSubmitFilters();
            }
        });

        //reload the page when we move to the last page
        $('.js-pageLast').click(function() {
            if(!checkForUnappliedFilter()) {
                $('#pageNumber').val(numberOfPages);
                validateAndSubmitFilters();
            }
        });

        //reload the page when the number per page changes
        $('#recordsPerPage').change(function() {
            if(!checkForUnappliedFilter()) {
                $('#pageNumber').val(1);
                validateAndSubmitFilters();
            }
        });

        //reload the page when the apply filters button is clicked
        $('#filterButton').click(function() {
            $('#pageNumber').val(1);
            validateAndSubmitFilters();
        });

        $('#delteFiltersButton').click(function() {
            $(this).hide();
            $('.filterRow').remove();
            $('.js-titleRow').remove();
        });

        //function to compile a new handlebars template for the filter row
        //and add it above the filter button
        $('#filterSelector').change(function() {
            var selected = $(this);
            var screenName = selected.find(':selected').html();
            var colName = selected.find(':selected').val();
            var colType = selected.find(':selected').data('type');
            var operatorOptions = selected.find(':selected').data('operator-options');
            var valueOptions = selected.find(':selected').data('value-options');
            
            
            var operatorsJson = JSON.parse(operatorOptions.replace(/'/g, '"'));

            if(valueOptions.length > 0)
            	{
            var valuesJson = JSON.parse(valueOptions.replace(/'/g, '"'));
            	}
            
            var templateHtml = $('#filterRow-template').html();
            var template = Handlebars.compile(templateHtml);
            var timepickerId = Math.floor(Math.random() * 10000);
            var context = {
                datepickerId: timepickerId,
                screenName: screenName,
                colName: colName,
                colType: colType,
                colOperatorOptions: operatorsJson,
                colValueOptions: valuesJson
            };

            var html = template(context);

            //put some column headers in place so that the user knows what each of the fields are for.
            if($('.filterRow').length === 0) {
                var titleHTML = '<div class="customRow js-titleRow" style="margin-top: 5px;">' +
                        '<span class="customCol"><strong>Column</strong></span>' +
                        '<span class="customCol" style="margin-left: 5px"><strong>Operator</strong></span>' +
                        '<span class="customCol" style="margin-left: 4px"><strong>Value</strong></span>' +
                        '</div>';
                $('#filterButton').before(titleHTML);
            }

            //add marker class so that we know this is unapplied filter
            var newFilterRow = $(html);
            newFilterRow.addClass('js-unappliedFilter');

            $('#filterButton').before(newFilterRow);

            if(colType === 'time') {
                $('#' + timepickerId).datetimepicker({
                	format				: 'd/m/Y H:i:s',
               		validateOnBlur		: false
                	});
            }
            
            if(colType === 'date'){
            	$('#' + timepickerId).datetimepicker({
           		format					: 'd/m/Y',
           		validateOnBlur			: false
            	});
            }

            selected.find('option:contains("-- ")').prop('selected', true);

            //if there is now more than one filter row then show the delete all button
            if($('.filterRow').length > 0) {
                $('#delteFiltersButton').show();
            }
        });

        //catch remove filter events on the container and remove the filter from the list
        $('#filter-container').on('click', '.js-removeFilterButton', function(){
            $(this).closest('.filterRow').remove();
            if($('.filterRow').length === 0) {
                $('.js-titleRow').remove();
            }
        });

        //slide up the filters when the toggle is clicked
        $('#filter-toggle-row').click(function() {
            var filterToggleIcon = $('#filter-toggle-icon');
            var filterToggleContent = $('#filter-toggle-content');
            if(filterToggleContent.is(":visible")) {
                filterToggleIcon.removeClass('fa-caret-down').addClass('fa-caret-right');
                $('#filterCollapsed').val("true");
            } else {
                filterToggleIcon.removeClass('fa-caret-right').addClass('fa-caret-down');
                $('#filterCollapsed').val("false");
            }
            filterToggleContent.slideToggle(150);
        });

        //submit the filters when the user clicks enter
        $(document).keypress(function(event){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if(keycode == '13'){
                $('#pageNumber').val(1);
                validateAndSubmitFilters()
            }
        });

        //make sure that all of the numeric filters are not empty and have a valid number
        //before they are sent to the server to be processed
        var validateAndSubmitFilters = function () {
            var invalidFields = 0;
            $.each($('.numeric'), function(index, element) {
                var field = $(element);
                field.removeAttr('placeholder');
                if(!/^.*-?[0-9]+\s?(,[0-9]+)*,?$/g.test(field.val().trim())) {
                    field.val('');
                    field.attr('placeholder', 'This Field Requires a Number');
                    field.addClass('errorBorder');
                    invalidFields++;
                } else {
                    field.removeClass('errorBorder');
                }

                var attr = field.attr('placeholder');
                if(field.val() === '' || attr === 'This Field Requires a Number') {
                    field.attr('placeholder', 'This Field Requires a Number');
                    field.addClass('errorBorder');
                    invalidFields++;
                }
            });

            $.each($('.timepicker'), function(index, element) {
               var field = $(element);
                field.removeAttr('placeholder');
                var attr = field.attr('placeholder');
                if(field.val() === '' || attr === 'This Field Requires A Valid Timestamp' || !isDateValid($(element).val().trim())){
                    field.val('');
                    field.attr('placeholder', 'This Field Requires A Valid Timestamp');
                    field.addClass('errorBorder');
                    invalidFields++;
                } else {
                    field.removeClass('errorBorder');
                }
            });
            
            $.each($('.datePicker'), function(index, element) {
                var field = $(element);
                 field.removeAttr('placeholder');
                 var attr = field.attr('placeholder');
                 if(field.val() === '' || attr === 'This Field Requires A Valid Timestamp' || !isDateValid($(element).val().trim())){
                     field.val('');
                     field.attr('placeholder', 'This Field Requires A Valid Timestamp');
                     field.addClass('errorBorder');
                     invalidFields++;
                 } else {
                     field.removeClass('errorBorder');
                 }
             });

            if(invalidFields === 0) {
                //take each of the filter rows and index the names so that they have the correct index
                $('.filterRow').each(function(index, object){
                    var filterRow = $(object);
                    filterRow.find('.js-filterColType').attr('name', 'filters[' + index + '].colType');
                    filterRow.find('.js-filterColName').attr('name', 'filters[' + index + '].colName');
                    filterRow.find('.js-filterValue').attr('name', 'filters[' + index + '].value');
                    filterRow.find('.js-filterCriteria').attr('name', 'filters[' + index + '].criteria');
                });

                //append the var to say that this is a form submit
                form.submit();
            }
        };

        var checkForUnappliedFilter = function() {
            if($('.js-unappliedFilter').length > 0) {
                $('html, body').animate({scrollTop: 0}, 400, function() {
                    console.log('got to the top');
                    $('#filterButton').switchClass('btn-primary', 'btn-danger');
                    $('#errorMessage').fadeIn();
                });
                return true;
            }
            return false;
        };

        //put some column headers in place so that the user knows what each of the fields are for.
        if($('.filterRow').length === 0 && appliedFilters.length > 0) {
            var titleHTML = '<div class="customRow js-titleRow" style="margin-top: 5px;">' +
                    '<span class="customCol"><strong>Column</strong></span>' +
                    '<span class="customCol" style="margin-left: 5px"><strong>Operator</strong></span>' +
                    '<span class="customCol" style="margin-left: 4px"><strong>Value</strong></span>' +
                    '</div>';
            $('#filterButton').before(titleHTML);
        }

        //create rows for the currently applied filters
        $.each(appliedFilters, function(index, object) {
            var templateHtml = $('#filterRow-template').html();
            var template = Handlebars.compile(templateHtml);
            var selectedSelect = $('option[value="' + object.colName + '"]');
            var screenName = selectedSelect.html();
           

            if(selectedSelect.length !== 0) {
                var colOperatorOptions = JSON.parse(selectedSelect.data('operator-options').replace(/'/g, '"'));
                if(!selectedSelect.data('value-options').length > 0)
                	{
                	var colValueOptions = "";
                	}
                else
                	{
                	var colValueOptions = JSON.parse(selectedSelect.data('value-options').replace(/'/g, '"'));           
                	}
                     
            }

            var context = {
                screenName: screenName,
                colName: object.colName,
                colType: object.colType,
                colOperatorOptions: colOperatorOptions,
                colValueOptions: colValueOptions,
                value: object.value,
                criteria: object.criteria
            };
            var html = template(context);
            $('#filterButton').before(html);
        });

        //init any of the time picker fields
        $('.timepicker').datetimepicker({
        			format			: 'd/m/Y H:i',
        			validateOnBlur	: false
        		});
        
        $('.datePicker').datetimepicker({
        	timepicker: false,
			format			: 'd/m/Y',
			validateOnBlur	: false
		});
        
        $('.timepicker').blur( function (){
    		var dateTimeString = $(this).val().trim();

    		var isValid = isDateValid(dateTimeString);    	
    		
    		if(!isValid)
   			{
                $(this).attr('placeholder', 'This Field Requires A Valid Timestamp');
                $(this).addClass('errorBorder');
   			}
    		else
   			{
    			$(this).removeAttr('placeholder');
                $(this).removeClass('errorBorder');
   			}
    	});   
        
        $('.datepicker').blur( function (){
    		var dateTimeString = $(this).val().trim();

    		var isValid = isDateValid(dateTimeString);    	
    		
    		if(!isValid)
   			{
                $(this).attr('placeholder', 'This Field Requires A Valid Timestamp');
                $(this).addClass('errorBorder');
   			}
    		else
   			{
    			$(this).removeAttr('placeholder');
                $(this).removeClass('errorBorder');
   			}
    	}); 
        
        if($('.filterRow').length > 0) {
            $('#delteFiltersButton').show();
        }
    });
    
    function isDateValid(dateTimeString)
    {
		if(dateTimeString.length == 10)
		{

				var year = dateTimeString.substring(6, 10);
				var month = dateTimeString.substring(3, 5);
				var day = dateTimeString.substring(0, 2);
				
				var isoDateTimeString = year+"-"+month+"-"+day;
				var date = new Date(isoDateTimeString);	
				
				return isValid(date);
		}
    	
    	if(dateTimeString.length == 13)
   		{
   				var year = dateTimeString.substring(6, 10);
				var month = dateTimeString.substring(3, 5);
				var day = dateTimeString.substring(0, 2);
				
				var hour = dateTimeString.substring(11, 13);
				
				var isoDateTimeString = year+"-"+month+"-"+day+"T"+hour+":00";
				var date = new Date(isoDateTimeString);	
				
				return isValid(date);
   		}
    	
    	if(dateTimeString.length == 16)
   		{
   			
   				var year = dateTimeString.substring(6, 10);
				var month = dateTimeString.substring(3, 5);
				var day = dateTimeString.substring(0, 2);
				
				var hour = dateTimeString.substring(11, 13);
				var minute = dateTimeString.substring(14,16);
				
				var isoDateTimeString = year+"-"+month+"-"+day+"T"+hour+":"+minute;
				var date = new Date(isoDateTimeString);	
				
				return isValid(date);
   		}
    	
    	if(dateTimeString.length == 19)
   		{
    		var year = dateTimeString.substring(6, 10);
			var month = dateTimeString.substring(3, 5);
			var day = dateTimeString.substring(0, 2);
			
			var hour = dateTimeString.substring(11, 13);
			var minute = dateTimeString.substring(14,16);
			var second = dateTimeString.substring(17, 19);
			
			var isoDateTimeString = year+"-"+month+"-"+day+"T"+hour+":"+minute+":"+second;
			var date = new Date(isoDateTimeString);	
			
			return isValid(date);
   		}
    	
    	return false;
    }
    
    function isValid(date)
    {
    	if ( Object.prototype.toString.call(date) === "[object Date]" ) 
    	{ 
   		  if ( isNaN( date.getTime() ) ) 
   		  {  
   		    return false;
   		  }
   		  else 
   		  {
   		    return true;
   		  }
   		}
   		else 
   		{
   		  return false;
   		}
    }
    
$('#refreshButton').click(function () {
	location.reload();
});
</script>
</#macro>