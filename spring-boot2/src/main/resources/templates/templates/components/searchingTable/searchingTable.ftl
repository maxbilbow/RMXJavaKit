<#macro searchingTable numberOfRows>
<style>
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
<div id="searching-content">
	<div class="tableContent">
		<i class="fa fa-refresh fa-2x text-success pull-right" id="refreshButton"></i>
		<#if numberOfRows != 0>
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
            <span>Total Number Of Records: ${resultsCount}</span>
        </div>
		    <#else>
		        <div class="text-center">
		            <strong>No results were found.</strong>
		        </div>
		    </#if>
	</div>
</div>
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
    var recordsPerPage = ${searchingCommand.recordsPerPage?c};
    var pageNumber = ${searchingCommand.pageNumber?c};
    var numberOfPages = ${searchingCommand.numberOfPages?c};
    var sortKey = '${searchingCommand.sortKey}';
    var sortDirection = '${searchingCommand.sortDirection}';
    var searchType = '${searchingCommand.searchType}';
    var detailsPk = '${searchingCommand.detailsPk!}';

    //run the pagination builder
    var pageNumbers = pagingTable.buildPagination(pageNumber, numberOfPages);
    pagingTable.renderPageNumbers(pageNumbers, pageNumber, numberOfPages);

    //set the hidden fields to the current values for the next page GET
    $('#recordsPerPage').val(recordsPerPage);
    $('#pageNumber').val(pageNumber);
    $('#sortKey').val(sortKey);
    $('#sortDirection').val(sortDirection);
    $('#searchType').val(searchType);
    $('#detailsPk').val(detailsPk);
    var form = $('#filterForm');

    //set the sort icon on the correct col and the direction
    if(sortDirection === 'asc') {
        $("th[data-key='" + sortKey +"']").append('<i class="fa fa-sort-asc pull-right"></i>');
    } else {
        $("th[data-key='" + sortKey +"']").append('<i class="fa fa-sort-desc pull-right"></i>');
    }
    
    //reload the page when the sort key is changed
    $('.js-sort').click(function() {
            //flip the sort direction and update the sort key key and submit
            sortDirection = sortDirection === 'asc' ?  'desc' : 'asc';
            $('#sortDirection').val(sortDirection);
            $('#sortKey').val($(this).data('key'));
            validateAndSubmitFilters();
    });

    //reload the page when the page number is changed
    $('.js-pageNumber').click(function() {
            var pageNumber = $(this).html();
            $('#pageNumber').val(pageNumber);
            validateAndSubmitFilters();
    });

    //reload the page when we move to the first page
    $('.js-pageFirst').click(function() {
            $('#pageNumber').val(1);
            validateAndSubmitFilters();
    });

    //reload the page when we move back a page
    $('.js-pageBack').click(function() {
            var number = parseInt($('#pageNumber').val(), 10);
            $('#pageNumber').val(number - 1);
            validateAndSubmitFilters();
    });

    //reload the page when we move forward a page
    $('.js-pageForward').click(function() {
            var number = parseInt($('#pageNumber').val(), 10);
            $('#pageNumber').val(number + 1);
            validateAndSubmitFilters();
    });

    //reload the page when we move to the last page
    $('.js-pageLast').click(function() {
            $('#pageNumber').val(numberOfPages);
            validateAndSubmitFilters();
    });

    //reload the page when the number per page changes
    $('#recordsPerPage').change(function() {
            $('#pageNumber').val(1);
            validateAndSubmitFilters();
    });

    //reload the page when the apply filters button is clicked
    $('#filterButton').click(function() {
        $('#pageNumber').val(1);
        validateAndSubmitFilters();
    });
    
    $('#refreshButton').click(function() {
    	location.reload();
    });
    
  //submit the filters when the user clicks enter
    $(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            $('#pageNumber').val(1);
            validateAndSubmitFilters();
            event.preventDefault();
        }
    });
});

//Conveinience method for handling clicking on table items
$('.js-openItemDetails').click(function() {
	//window.location=$(this).data('url');
	$('#detailsPk').val($(this).data('pk'));
	
	$('.mtpntFilterRow').each(function(index, object){
        var filterRow = $(object);
        filterRow.find('.js-filterColType').attr('name', 'mtpntFilters[' + index + '].colType');
        filterRow.find('.js-filterColName').attr('name', 'mtpntFilters[' + index + '].colName');
        filterRow.find('.js-filterValue').attr('name', 'mtpntFilters[' + index + '].value');
        filterRow.find('.js-filterCriteria').attr('name', 'mtpntFilters[' + index + '].criteria');
    });
    
    $('.serialFilterRow').each(function(index, object){
        var filterRow = $(object);
        filterRow.find('.js-filterColType').attr('name', 'serialFilters[' + index + '].colType');
        filterRow.find('.js-filterColName').attr('name', 'serialFilters[' + index + '].colName');
        filterRow.find('.js-filterValue').attr('name', 'serialFilters[' + index + '].value');
        filterRow.find('.js-filterCriteria').attr('name', 'serialFilters[' + index + '].criteria');
    });
	
	$('#reqType').val('details');
	$('#filterForm').submit();
});
</script>
</#macro>