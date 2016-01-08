<html>
<body>

<table>
<tr>
	<td>
		<table border="1">
			<tr>
				<th>primary flow counter</th> <th>primary flow version number</th>
			</tr>
			<#list primaryDataflows as pdataflow>
			<tr>
				<td>${pdataflow.counter} </td>
			<td>${pdataflow.version}</td>
			</tr>
			</#list>
		</table>
	</td>
	<td>
		<table border="1">
			<tr>
				<th>secondary flow counter</th> <th>secondary flow version number</th>
			</tr>
			<#list secondaryDataflows as sdataflow>
			<tr>
				<td>${sdataflow.counter} </td>
				<td>${sdataflow.version}</td>
			</tr>
			</#list>
		</table>
	</td>
</tr>
</table>


</body>
</html>