<#import "/components/filterableTable/filterableTable.ftl" as filterableTable/>
<@filterableTable.filterableTable numberOfrows=rows?size>
<style>
	.js-nestedToggleIcon{
		cursor: pointer;
	}	
	.js-sort{
		cursor: pointer;
	}
</style>
<div class="table-responsive">
	<table class="table table-bordered table-condensed">
	    <thead>
	        <tr>		
	        	<th data-key="c.flowCounter" class="js-sort">Flow Counter</th>
	        	<th data-key="c.groupCounter" class="js-sort">Group Counter</th>
	        	<th data-key="c.groupPoolCode" class="js-sort">Group Pool Code</th>
	        	<th data-key="c.itemCounter" class="js-sort">Item Counter</th>
	        	<th data-key="c.changeType" class="js-sort">Change Type</th>
	        </tr>
	     </thead>
	     <tbody>
	        <#list rows as row>
	        <tr>
	        	<td><a href="${rc.contextPath}/change/flowChangeDetails/${row.flowCounter}">${row.flowCounter!}</a> - <a href="${rc.contextPath}/change/flowTreeChanges/${row.flowCounter}">View flow structure</a></td>
	        	<td>${row.groupCounter!}</td>
	        	<td>${row.groupPoolCode!}</td>
	        	<td>${row.itemCounter!}</td>
	        	<td>${row.changeType!}</td>	        	
	        </tr>
	        </#list>
	    </tbody>
	</table>
</div>
</@>