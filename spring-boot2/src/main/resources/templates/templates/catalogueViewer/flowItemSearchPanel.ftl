<#global pageTitle = "Catalogue Flow Viewer" urlLink="roleList" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>

<#macro flowItemSearchPanel>
<div class="panel panel-default">
<div class="panel-heading">
	Catalogue flow and item viewer
</div>
<div class="panel-body">
<div class="col-xs-12">
<div class="row">
<div class="col-xs-4">
<form name="flowChoose" id="flowChoose" action="${rc.contextPath}/catalogueViewer/flowToView" class="form-horizontal" method="GET">

<div class="form-group">
	
		<div class="input-group">
				<span class="input-group-btn">
				<button type="submit" class="btn btn-primary" id="flowSearch" onclick='doSubmitFlow();'>
				<i class="fa fa-search"></i> Flow Search
				</button>
				</span>
			<input class="form-control" type="text" name="flowToView" id="flowToView" placeholder="flow you would like to view"> 
		</div>
	</div>
</form>
</div>
<div class="col-xs-4">
<form name="groupChoose" id="groupChoose" action="${rc.contextPath}/catalogueViewer/groupToView" class="form-horizontal" method="GET">
	

<div class="form-group">
		<div class="input-group">
				<span class="input-group-btn">
				<button type="submit" class="btn btn-primary" id="groupSearch" onclick='doSubmitGroup();'>
				<i class="fa fa-search"></i> Group Search
				</button>
				</span>
			<input class="form-control" type="text" name="groupToView" id="groupToView" placeholder="group you would like to view"> 
		</div>
	</div>
</form>
</div>
<div class="col-xs-4">
<form name="itemChoose" id="itemChoose" action="${rc.contextPath}/catalogueViewer/itemToView" class="form-horizontal" method="GET">
	

<div class="form-group">
		<div class="input-group">
				<span class="input-group-btn">
				<button type="submit" class="btn btn-primary" id="itemSearch" onclick='doSubmitItem();'>
				<i class="fa fa-search"></i> Item Search
				</button>
				</span>
			<input class="form-control" type="text" name="itemToView" id="itemToView" placeholder="item you would like to view"> 
		</div>
	</div>
</form>
</div>
</div>
<div class="row">
<div class="col-xs-12">
<form name="itemNameSearchChoice" id="itemNameSearchChoice" action="${rc.contextPath}/catalogueViewer/itemNameSearch" class="form-horizontal" method="GET">
	

<div class="form-group">
		<div class="input-group">
				<span class="input-group-btn">
				<button type="submit" class="btn btn-primary" id="itemNameSearch" onclick='doSubmitItemByDesc();'>
				<i class="fa fa-search"></i> Item Name Search
				</button>
				</span>
			<input class="form-control" type="text" name="itemNameSearch" id="itemNameSearch" placeholder="part of item name you would like to search for"> 
		</div>
	</div>
</form>
</div>
</div>

</div>
</div>
</div>

<script>

function doSubmitFlow(){
	var rules = {
			flowToView: {
				required:true
			}
	}

	$("#flowChoose").validate({
		rules: rules,
		errorPlacement:function(error, element){
			return true;
		},
		highlight:function(element){
			$(element).attr("placeholder", "This field is mandatory when searching for a flow");
			$(element).closest(".input-group").addClass("has-error");
		},
		unhighlight:function(element){
			$(element).closest(".input-group").removeClass("has-error");
		}
	})
	
	if($("#flowChoose").valid()){
		$("#flowSearch").html("loading <i class='fa fa-spinner fa-spin'></i>")
		$("#flowSearch").addClass("disabled")
		$("#groupSearch").addClass("disabled")
		$("#itemSearch").addClass("disabled")	
		$("#flowChoose").submit()
	}
}

function doSubmitGroup(){
	var rules = {
		 	groupToView: {
				required:true
			}
	}

	$("#groupChoose").validate({
		rules: rules,
		errorPlacement:function(error, element){
			return true;
		},
		highlight:function(element){
			$(element).attr("placeholder", "This field is mandatory when searching for a group");
			$(element).closest(".input-group").addClass("has-error");
		},
		unhighlight:function(element){
			$(element).closest(".input-group").removeClass("has-error");
		}
	})
	
	if($("#itemChoose").valid()){
		$("#groupSearch").html("loading <i class='fa fa-spinner fa-spin'></i>")
		$("#groupSearch").addClass("disabled")
		$("#itemSearch").addClass("disabled")
		$("#flowSearch").addClass("disabled")
		$("#groupChoose").submit()
	}
}

function doSubmitItem(){
	var rules = {
			itemToView: {
				required:true
			}
	}

	$("#itemChoose").validate({
		rules: rules,
		errorPlacement:function(error, element){
			return true;
		},
		highlight:function(element){
			$(element).attr("placeholder", "This field is mandatory when searching for an item");
			$(element).closest(".input-group").addClass("has-error");
		},
		unhighlight:function(element){
			$(element).closest(".input-group").removeClass("has-error");
		}
	})
	
	if($("#itemChoose").valid()){
		$("#itemSearch").html("loading <i class='fa fa-spinner fa-spin'></i>")
		$("#itemSearch").addClass("disabled")
		$("#groupSearch").addClass("disabled")
		$("#flowSearch").addClass("disabled")
		$("#itemChoose").submit()
	}
}

function doSubmitItemByDesc(){
	var rules = {
			itemToView: {
				required:true
			}
	}

	$("#itemDescriptionSearchChoice").validate({
		rules: rules,
		errorPlacement:function(error, element){
			return true;
		},
		highlight:function(element){
			$(element).attr("placeholder", "This field is mandatory when searching for an item");
			$(element).closest(".input-group").addClass("has-error");
		},
		unhighlight:function(element){
			$(element).closest(".input-group").removeClass("has-error");
		}
	})
	
	if($("#itemNameSearchChoice").valid()){
		$("#itemNameSearch").html("loading <i class='fa fa-spinner fa-spin'></i>")
		$("#itemNameSearch").addClass("disabled")
		$("#itemSearch").addClass("disabled")
		$("#groupSearch").addClass("disabled")
		$("#flowSearch").addClass("disabled")
		$("#itemNameSearchChoice").submit()
	}
}
</script>
</#macro>
