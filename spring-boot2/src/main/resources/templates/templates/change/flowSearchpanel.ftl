<#global pageTitle = "Catalogue Flow Viewer" urlLink="flowTreeChanges" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>

<#macro flowSearchPanel>

<div class="panel panel-default">
<div class="panel-heading">
	Flow change comparison viewer
</div>
<div class="panel-body">
<form name="flowChoose" id="flowSearch1" action="${rc.contextPath}/change/flowToView"  class="form-horizontal" method="GET">
<div class="form-group">
	<div class="col-md-12">
		<div class="input-group">
				<span class="input-group-btn">
				<button type="submit" id="search" class="btn btn-primary" onclick='doSubmit();'>
				<i class="fa fa-search"></i> Search
				</button>
				</span>
			<input class="form-control" type="text" name="flowToView" id="flowToView" placeholder="flow you would like to view">
		</div>
	</div>
	</div>
</form>
</div>
</div>

<script>

function doSubmit(){
	var rules = {
			flowToView: {
				required:true
			}
	}
	
	$("#flowSearch1").validate({
		rules: rules,
		errorPlacement:function(error, element){
			return true;
		},
		highlight:function(element){
			$(element).attr("placeholder", "This field is mandatory");
			$(element).closest(".input-group").addClass("has-error");
		},
		unhighlight:function(element){
			$(element).closest(".input-group").removeClass("has-error");
		}
	})
	
	if($("#flowSearch1").valid()){
		$("#search").html("loading <i class='fa fa-spinner fa-spin'></i>")
		$("#search").addClass("disabled")
		$("#flowSearch").submit()
	}
}

</script>

</#macro>