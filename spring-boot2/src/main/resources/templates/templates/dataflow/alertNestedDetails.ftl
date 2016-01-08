<#import "/spring.ftl" as spring>
<tr>
	<#if columnsToSpan??>
	<td colspan="${columnsToSpan}">
	<#else>
	<td colspan='6'>
	</#if>
	<table class='table table-bordered table-condensed'>
		<thead>
	        <tr class='active'>				
				<th class='text-center'><@spring.message "field.alert.details" /></th>
	        </tr>
        </thead>
	        <#if alertMessagesType == "success">	
	        <#list alertMessages as alertMessage>        
	        <tr>
	     		<td class="text-center">${alertMessage}</td>
	        </tr>
	        </#list>
	        <#else>
	        <tr>
	        	<td class="text-center"><@spring.message "message.alert.details.none.found" /></td>
	        </tr>
	        </#if>    
	</table>
	</td>
</tr>