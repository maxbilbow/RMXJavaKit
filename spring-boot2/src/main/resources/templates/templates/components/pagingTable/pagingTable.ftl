<#macro pagingTable>
<div id="table-container">
    <form action="" method="post" id="messageAuditForm">
        <div id="table-content" class="table-content">
        <#--this is where the table will go-->
        <#nested/>
        </div>

        <div class="text-center">
            <ul id="pagination-container" class="pagination"></ul>
        </div>

    <#--Start page numbers selection-->
        <div class="text-center">
            <span>Records per page</span>
            <select name="recordsPerPage" id="recordsPerPage" style="margin-left: 5px;">
                <option value="10">10</option>
                <option value="25">25</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
        </div>
    <#--End page numbers selection-->

        <input id="pageNumber" type="hidden" name="pageNumber" value="1"/>
        <input id="sortKey" type="hidden" name="sortKey" value=""/>
        <input id="sortDirection" type="hidden" name="sortDirection" value=""/>
        <input type="hidden" name="reqType" value="refresh"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
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
                        pageNumbers.push(right)
                        rightStop = false;
                    } else {
                        rightStop = true;
                    }
                }

                pageNumbers.sort(function(a, b) {
                    return a > b;
                });

                return pageNumbers;
            },

            renderPageNumbers: function(pageNumbers, pageNumber, numberOfPages) {
                var paginationHTML = '';

                //check if we need the previous page arrow
                if(numberOfPages > 1 && pageNumber > 1) {
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
                }

                $('#pagination-container').html(paginationHTML);
            },

            refreshContent:  function() {
                var formData = $('#messageAuditForm').serialize();
                makeAjaxRequest('', 'GET', 'html',
                    function(data) {
                        $('#table-container').html(data);
                    },
                    function() {

                    }, formData);
            }
        };

        $(function() {
            //make sure that all of the page options are set correctly
            var recordsPerPage = '${command.recordsPerPage}';
            var pageNumber = '${command.pageNumber}';
            var numberOfPages = '${command.numberOfPages}';
            var sortKey = '${command.sortKey}';
            var sortDirection = '${command.sortDirection}';

            //run the pagination builder
            var intPageNumber = parseInt(pageNumber, 10);
            var intNumberOfPages = parseInt(numberOfPages, 10);
            var pageNumbers = pagingTable.buildPagination(intPageNumber, intNumberOfPages);
            pagingTable.renderPageNumbers(pageNumbers, intPageNumber, intNumberOfPages);

            //set the hidden fields
            $('#recordsPerPage').val(recordsPerPage);
            $('#pageNumber').val(pageNumber);
            $('#sortKey').val(sortKey)
            $('#sortDirection').val(sortDirection);

            //add the icon to the col that is being sorted
            if(sortDirection === 'asc') {
                $("th[data-key='" + sortKey +"']").append('<i class="fa fa-sort-asc pull-right"></i>');

            } else {
                $("th[data-key='" + sortKey +"']").append('<i class="fa fa-sort-desc pull-right"></i>');
            }

            $('.js-sort').click(function() {
                //flip the sort direction and update the hidden fields
                $('#sortDirection').val() === 'asc' ? sortDir = 'desc' : sortDir = 'asc'
                $('#sortDirection').val(sortDir)
                //update the sort key
                $('#sortKey').val($(this).data('key'));
                pagingTable.refreshContent();
            });

            $('.js-pageNumber').click(function() {
                $('#pageNumber').val($(this).html());
                pagingTable.refreshContent();
            });

            $('.js-pageBack').click(function() {
                var newPageNumber =  parseInt($('#pageNumber').val()) - 1;
                $('#pageNumber').val(newPageNumber);
                pagingTable.refreshContent();
            })

            $('.js-pageForward').click(function() {
                var newPageNumber =  parseInt($('#pageNumber').val()) + 1;
                $('#pageNumber').val(newPageNumber);
                pagingTable.refreshContent();
            })

            $('#recordsPerPage').change(function() {
                $('#pageNumber').val(1);
                pagingTable.refreshContent();
            });
        });
    </script>
</div>
</#macro>