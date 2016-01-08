<#global pageTitle = "Choose Catalogue" urlLink="chooseCatalogue" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>


<@layout.mainLayout "chooseCatalogue" "chooseCatalogue">
<div class="panel panel-default">
<div class="panel-heading">
	<h3>Choose Catalogue to view</h3>
</div>
<div class="panel-body">
<form name="catalogueChoose" id="viewer_form" action="catalogueView" class="form-horizontal" method="post" enctype="multipart/form-data">
	<div class="col-xs-12">
	<div class="row">
	<label class="col-xs-3 control-label" for="catalogue_one">Select the catalogue you would like to view:</label>
	<div class="col-xs-6">
	<input class="form-control" type="file" name="catalogue" id="catalogue"> 
	</div>
	</div>
	<div class="row">
	<div class="col-xs-9 col-xs-offset-3">
	<button type="submit" class="btn btn-sm btn-primary" id="catalogue-viewer" onclick='doValidateSubmit();'>Start Viewer</button>
	</div>
	</div>
	</div>
</form>
</div>
</div>

<script>
			function doValidateSubmit(){
				var rules = {
						catalogue: {
							required:true
						}
				}
				
				var messages = {
						catalogue: {
							required: "This field is mandatory"
						}
				}
					
				$("#viewer_form").validate({
					messages: messages,
					rules: rules,
					errorElement: "div"
				})
				
				if($("#viewer_form").valid()){
					$("#catalogue-viewer").html("loading <i class='fa fa-spinner fa-spin'></i>")
					$("#catalogue-viewer").addClass("disabled")
					$("#viewer_form").submit()
				}
			}
</script>
</@layout.mainLayout>