<#global pageTitle = "Choose Catalogue" urlLink="roleList" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>


<@layout.mainLayout "valid8flows" "valid8">
<#include "/valid8/valid8flows.ftl"/>

<#if Session.parsedFiles?has_content>
<div class="panel panel-default">
	<div class="panel-body">
	<#assign x = 0>
		<#list Session.parsedFiles?keys?chunk(2) as flowPairs>
		<div class="row">
		<#list flowPairs as flow>
		
			<#if Session.parsedFiles[flow].genericFlow?has_content>
			<div class="col-xs-6">
			<div class="panel panel-success text-success">
				<div class="panel-heading" data-toggle="collapse" data-target="#collapse-${x}" style="cursor:pointer">
					<div class="row">
						<div class="col-xs-1">
						<i class="fa fa-check-circle fa-3x"></i>
						</div>
					<div class="col-xs-10">
					<h4>
						File: ${Session.parsedFiles[flow].fileName!}
					</h4>
					</div>
						<div class="col-xs-1">
						<form action="${rc.contextPath}/valid8/removeFlowByKey" method="post">
							<button type="submit" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></button>
							<input type="hidden" id="key" name="key" value="${flow}">
							<input type="hidden" id="validity" name="validity" value="valid">
						</form>
						</div>
					</div>
				</div>
				<div class="panel-collapse collapse" id="collapse-${x}">
				<#assign x = x+1>
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-12">
							${Session.parsedFiles[flow].fileName} is validated as flow type of: ${Session.parsedFiles[flow].genericFlow.flow!}
						</div>
					</div>
					</div>
				</div>
			</div>
			</div>
		
		<#else>
			<div class="col-xs-6">
			<div class="panel panel-danger text-danger">
				<div class="panel-heading" data-toggle="collapse" data-target="#collapse-${x}" style="cursor:pointer">
					<div class="row">
						<div class="col-xs-1">
						<i class="fa fa-times-circle fa-3x"></i>
						</div>
					<div class="col-xs-10">
					<h4>
						File: ${Session.parsedFiles[flow].fileName!}
					</h4>
					</div>
						<div class="col-xs-1">
						<form action="${rc.contextPath}/valid8/removeFlowByKey" method="post">
							<button type="submit" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></button>
							<input type="hidden" id="key" name="key" value="${flow}">
							<input type="hidden" id="validity" name="validity" value="invalid">
						</form>
						</div>
					</div>
				</div>
				<div class="panel-collapse collapse" id="collapse-${x}">
				<#assign x = x+1>
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-12">
							${Session.parsedFiles[flow].fileName} is invalid
						</div>
					</div>
					<#list Session.parsedFiles[flow].v8Errors as errors>
					<#if (errors?counter % 2) == 0>
					<#assign colour = "white">
					<#else>
					<#assign colour = "#f0f0f0" >
					</#if>
					<div class="row" style="background:${colour};">
						<div class="col-xs-12">
							${errors.message!}
						</div>
					</div>
					</#list>
				</div>
				</div>
			</div>
			</div>
		</#if>
		</#list>
		</div>
		</#list>
	</div>
</div>
</#if>
</@layout.mainLayout>