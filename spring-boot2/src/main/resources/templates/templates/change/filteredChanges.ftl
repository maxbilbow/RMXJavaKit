<#global pageTitle = "Registered Roles" urlLink="roleList" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>


<@layout.mainLayout "filteredChanges" "filteredChanges">
<body>
	Filter by:
	<form name="filter" action="filterChanges" method="post">
		<select name="filterType">
			<option value="flowCounter">Flow Counter</option>
			<option value="groupCounter">Group Counter</option>
			<option value="itemCounter">Item Counter</option>
		</select> </br> </br> Filter Criteria: <input type="text" name="filter"> </br> </br> 
		<input type="submit" value="Filter">
	</form>

	</br>
	</br>
	<a href="clear" style="font-weight:bold;" />Clear Filter</a>

	</br>
	</br>
	<table class="table-striped table table-hover">
		<tr>
			<th>Flow Counter</th>
			<th>Group Counter</th>
			<th>Item Counter</th>
			<th>Column Name</th>
			<th>Initial Value</th>
			<th>New Value</th>
		</tr>
		<#list changes as change>
		<tr>
			<td><a href="flowChangeDetails/${change.flowCounter}">${change.flowCounter}</a></td>
			<td>${change.groupCounter!}</td>
			<td>${change.itemCounter!}</td>
			<td>${change.columnName!}</td>
			<td>${change.initialValue!}</td>
			<td>${change.newValue!}</td>
		</tr>
		</#list>
	</table>

</body>
</@layout.mainLayout>
