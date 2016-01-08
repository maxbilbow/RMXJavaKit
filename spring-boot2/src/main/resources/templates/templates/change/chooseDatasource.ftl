<#global pageTitle = "Choose Datasources" urlLink="chooseDatasource" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>


<@layout.mainLayout urlLink "chooseDatagroup">
<div class="panel panel-default">
<div class="panel-heading">
	<h3>Choose Catalogues for comparison</h3>
</div>
<div class="panel-body">
<form name="fileChoose" id="datasource_form" action="chooseDatasource" class="form-horizontal" method="post" enctype="multipart/form-data">
	<div class="col-xs-12">
	<div class="row">
	<label class="col-xs-3 control-label" for="catalogue_one">Select the first catalogue to compare:</label>
	<div class="col-xs-6">
	<input class="form-control" type="file" name="primaryFile" id="primaryFile"> 
	</div>
	</div>
	<div class="row">
	<label class="col-xs-3 control-label" for="catalogue_two">Select the second catalogue to compare:</label> 
	<div class="col-xs-6">
	<input class="form-control" type="file" name="secondaryFile" id="secondaryFile">
	</div>
	</div>
	<div class="catalogue-error"></div>
	<div class="row">
	<div class="col-xs-9 col-xs-offset-3">
	<button class="btn btn-sm btn-primary" id="top-loading-start" onclick='doValidateSubmit();'>Start Comparison</button>
	</div>
	</div>
	</div>
</form>
</div>
</div>

<script>

	function doValidateSubmit(){
		var rules = {
				primaryFile: {
					required:true
				}, 
				secondaryFile: {
					required:true
				}
		}
		
		var messages = {
				primaryFile: {
					required: "This field is mandatory"
				}, 
				secondaryFile: {
					required: "This field is mandatory"
				}
		}
			
		$("#datasource_form").validate({
			messages: messages,
			rules: rules,
			errorElement: "div"
		})
		
		if($("#datasource_form").valid()){
			$("#top-loading-start").html("loading <i class='fa fa-spinner fa-spin'></i>")
			$("#top-loading-start").addClass("disabled")
			$("#datasource_form").submit()
		}
	}
	
</script>
</@layout.mainLayout>