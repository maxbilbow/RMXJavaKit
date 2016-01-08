<#import "/components/filterableTable/filterableTable.ftl" as filterableTable/>
<#import "/spring.ftl" as spring>
<@filterableTable.filterableTable numberOfrows=rows?size>
<style>
	.js-nestedToggleIcon{
		cursor: pointer;
	}	
	.js-sort{
		cursor: pointer;
	}
</style>
<table class="table table-bordered table-condensed">
    <thead>
        <tr>		
        	<th></th>
			<th data-key="type" class="js-sort"><@spring.message "field.type" /></th>
			<th data-key="message" class="js-sort"><@spring.message "field.message" /></th>
			<th data-key="operation" class="js-sort"><@spring.message "field.operation" /></th>
			<th data-key="operationDescription" class="js-sort"><@spring.message "field.description" /></th>
			<th data-key="timeRaised" class="js-sort"><@spring.message "field.time.raised" /></th>	
        </tr>
     </thead>
     <tbody>
        <#list rows as row>
        <tr id='openNestedFor${row.pk?c}' data-url="alert.htm?reqType=getNested&id=${row.pk?c}" data-pk="${row.pk?c}">
        	<td><i  id='nestedToggleIconFor${row.pk?c}' class="js-nestedToggleIcon fa fa-2x fa-caret-right" data-url="alert.htm?reqType=getNested&id=" data-pk="${row.pk?c}"></i></td>
            <td>${row.type}</td>
            <td>${row.message!}</td>
            <td>${row.operation!}</td>
			<td>${row.operationDescription!}</td>
			<td>${row.screenTimeRaised}</td>	
        </tr>
        </#list>
    </tbody>
</table>
<script>	
	$('.js-nestedToggleIcon').click(function(){
		var pk = $(this).data("pk");
		var url = $(this).data("url");
		
		getNestedData(pk, url);
	});
</script>
</@filterableTable.filterableTable>