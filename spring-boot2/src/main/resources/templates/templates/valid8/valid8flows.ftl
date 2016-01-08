<div class="panel panel-default">
<div class="panel-heading">
	<h3>Choose flow to Valid8</h3>
</div>
<div class="panel-body">
<form name="valid8Flows" id="valid8_form" action="valid8utility" class="form-horizontal" method="post" enctype="multipart/form-data">
	<div class="col-xs-12">
	<div class="row">
	<label class="col-xs-3 control-label" for="catalogue_one">Select the flow/s for validation:</label>
	<div class="col-xs-3">
	<input class="form-control" type="file" name="flowsToValid8" id="flowsToValid8" multiple> 
	</div>
	</div>
	<div class="row">
	<div class="col-xs-9 col-xs-offset-3">
	<button type="submit" class="btn btn-sm btn-primary" id="valid8flow-viewer" onclick='doValidateSubmit();'>Valid8</button>
	<a class="btn btn-sm btn-danger" id="clear-validated-flows" href="clearvalid8">Clear</a>
	</div>
	</div>
	<br/>
	<#if parsedFiles?has_content>
	<div class="row">
	<#assign i = parsedFiles?size>
	<#assign j = validFiles>
	<#assign k = invalidFiles>
		<i class="fa fa-file-text fa-2x" ></i> Files parsed: ${i}  <i class="fa fa-file-text fa-2x text-success" ></i> Valid Files: ${j} <i class="fa fa-file-text fa-2x text-danger" ></i> Invalid Files: ${k}
	</div>
	</#if>
	</div>
</form>
</div>
</div>

<script>
			function doValidateSubmit(){
				var rules = {
						flowsToValid8: {
							required:true
						}
				}
				
				var messages = {
						flowsToValid8: {
							required: "This field is mandatory"
						}
				}
					
				$("#valid8_form").validate({
					messages: messages,
					rules: rules,
					errorElement: "div"
				})
				
				if($("#valid8_form").valid()){
					$("#valid8flow-viewer").html("loading <i class='fa fa-spinner fa-spin'></i>")
					$("#valid8flow-viewer").addClass("disabled")
					$("#valid8_form").submit()
				}
			}
</script>