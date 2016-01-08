<#global pageTitle = "Choose Datasources" urlLink="roleList" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>


<@layout.mainLayout "error" "error">
<div class="panel panel-danger">
<#if exception?has_content>
	<#if response.status == 404>
	<div class="panel-heading">
		Error 404: Page Not Found
	</div>
	<div class="panel-body">
		return to <a href="${rc.contextPath}/layout/dashboard" class="<#if urlLink == 'dashboard'>active</#if>">home</a> or go <a href="${backLink}">back</a>
	</div>
	</#if>
	<#else>
	
	<div class="panel-heading">
		Error 500: Internal Server Error
	</div>
	<div class="panel-body">
		return to <a href="${rc.contextPath}/layout/dashboard" class="<#if urlLink == 'dashboard'>active</#if>">home</a>
		<br/>	
		Exception: ${exception!}
		<br/>
		
	</div>
</#if>
</div>

</@layout.mainLayout>