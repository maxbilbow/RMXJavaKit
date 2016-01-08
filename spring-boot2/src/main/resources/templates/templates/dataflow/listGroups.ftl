<html>
<body>

<table>
<tr>
	<td>
		<table border="1">
			<tr>
				<th>primary flow counter</th> <th>primary flow version number</th>
			</tr>
			<#list primaryDataflowGroups as pdataflowgroup>
			<tr>
				<td>${pdataflowgroup.counter} </td>
			<td>${pdataflowgroup.version}</td>
			</tr>
			</#list>
		</table>
	</td>
	<td>
		<table border="1">
			<tr>
				<th>secondary flow counter</th> <th>secondary flow version number</th>
			</tr>
			<#list secondaryDataflowGroups as sdataflowgroup>
			<tr>
				<td>${sdataflowgroup.counter} </td>
				<td>${sdataflowgroup.version}</td>
			</tr>
			</#list>
		</table>
	</td>
</tr>
</table>


</body>
</html>