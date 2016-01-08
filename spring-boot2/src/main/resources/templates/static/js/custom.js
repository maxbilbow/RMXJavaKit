/*
 The function will be used in all places that make an ajax call to the server this will prevent the user from
 making successful ajax request even when the session has timed out this will. Should the users session have times out
 the user will be taken back to the login page.
 url : This is the url of the ajax request.
 method: This is the method of the ajax request that you want to make 'GET', 'POST'
 dataType: This is the type of the data that this request will return eg 'html', 'json'
 success: This is a closure of the code that will to be run if the user is allowed to make the request
 fail: This is a closure of the code that will be run if the user is unable to make the request
 data: This is any data that needs to be sent with the request, this could be form data for eg.

 eg Success function
 var success = function(data) { //do something with the data that we get back from the server }
 eg Fail function
 var fail = function(err) { //do something to fail because the call has failed }
 */
function makeAjaxRequest(url, method, dataType, success, fail, requestData) {
    $.ajax({
        url: 'checkAuth.htm',
        type: 'GET',
        dataType: 'text'
    }).success(function(data) {
        if(data === 'YES') {
            $.ajax({
                url: url,
                type: method,
                dataType: dataType,
                data: requestData,
                async: false
            }).success(function(data) {
                success(data);
            }).fail(function(err) {
                fail(err);
            });
        } else {
            fail();
            window.location.reload();
        }
    });
}

function getNestedData(pk, url)
{

var url = url+pk;

 makeAjaxRequest(url, 'get', 'html',
            function(data) {
                addNestedRow(data, pk);
                if($('#nestedToggleIconFor'+pk).hasClass('fa-caret-down'))
            	{
                		$('#nestedToggleIconFor'+pk).removeClass('fa-caret-down');
                		$('#nestedToggleIconFor'+pk).addClass('fa-caret-right');
           		}
                else if($('#nestedToggleIconFor'+pk).hasClass('fa-caret-right'))
            	{
            		$('#nestedToggleIconFor'+pk).removeClass('fa-caret-right');
            		$('#nestedToggleIconFor'+pk).addClass('fa-caret-down');       
            	}
            },
            function() {
                //This is where any fail code would go;
            }, null);

}

function addNestedRow(data, pk)
{
	//function to handle the appending of the row to the desired row by row pk
	//handle in here either adding or removing the row based on whether or not there is already a nested row
	//add a class to the row if a nested row is being display
	//if this class is on this row remove it
	//else add it
	if($('#openNestedFor'+pk).hasClass('hasNestedRow'))
		{
		$('#openNestedFor'+pk).closest('tr').next('tr').remove();
		$('#openNestedFor'+pk).removeClass('hasNestedRow');
		}
	else
		{
		$('#openNestedFor'+pk).after(data);	
		$('#openNestedFor'+pk).addClass('hasNestedRow');
		}
}

function openWindow(urlToOpen, windowWidth, windowHeight, winAttributes)
{
  var windowLeft = (screen.width - windowWidth) / 2;
  var windowTop = (screen.height - windowHeight) / 2;

  if (windowLeft < 0)
  {
    windowWidth = screen.width;
    windowLeft = 0;
  }
  if (windowTop < 0)
  {
    windowHeight = screen.height;
    windowTop = 0;
  }

  var win = window.open(urlToOpen, '', 'width=' + windowWidth + ', height=' + windowHeight + ', left=' + windowLeft + ', top=' + windowTop + ', ' + winAttributes);
  win.resizeTo(windowWidth, windowHeight);
  win.focus();
}