<#global pageTitle = "Catalogue Flow Viewer" urlLink="roleList" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>

<#macro itemGroupFlowResults>
<div class="panel panel-default">
	<div class="panel-heading" data-toggle="collapse" data-target="#collapse-body" style="cursor:pointer">
	<div class="pull-right">
     	<i class="fa fa-arrow-down fa-2x"></i>
   	</div>
		This item appears in the following flows and groups
	</div>
	<div class="panel-collapse collapse" id="collapse-body">
	<div class="panel-body">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-3">
					<h4>Flow Counter</h4>
				</div>
				<div class="col-xs-3">
					<h4>Parent Group</h4>
				</div>
				<div class="col-xs-3">
					<h4>Group Pool Code</h4>
				</div>
				<div class="col-xs-3">
					<h4>Group Line Number</h4>
				</div>
			</div>
	        <#list flowGroupUsage as result>
	        <#if (result?counter % 2) == 0>
			<#assign colour = "white">
			<#else>
			<#assign colour = "#f0f0f0" >
			</#if>
			<div class="row" style="background:${colour};">
			<div class="col-xs-3">
					<a href="${rc.contextPath}/catalogueViewer/flowToView?reqType=jump&flowToView=${result.flowCounter!}&parentPoolCode=${result.parentGroupPoolCode!}&groupPoolCode=${result.groupPoolCode!}&itemCounter=${result.itemCounter!}&groupLineNo=${result.groupLineNo!}">${result.flowCounter!}</a>
				</div>
				<div class="col-xs-3">
					${result.parentGroupPoolCode!}  
				</div>
				<div class="col-xs-3">
					${result.groupPoolCode!}  
				</div>
				<div class="col-xs-3">
					${result.groupLineNo!}  
				</div> 
			</div>	
	        </#list>
			
		</div>
	</div>
	</div>
</div>
</#macro>