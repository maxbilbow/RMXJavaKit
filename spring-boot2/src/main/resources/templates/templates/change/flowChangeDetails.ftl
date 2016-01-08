<#global pageTitle = "Flow Details" urlLink="roleList"
urlGroup="sysAdmin"> <#import "/layout/layout.ftl" as layout>


<@layout.mainLayout "filteredChanges" "filteredChanges">
	<table class="table">
	<thead>
		<tr>
			<th>Flow Counter</th>	
			
		</tr>
	</thead>

	<tbody>
			<tr>
				<td><h2>${flowChange}</h2></td>			
			</tr>
			<tr>			
				<td colspan="2">
				<#list groupChanges?keys as gCounter>
				
				<div class="block">
				
				<div class="block-options pull-right">
				<a href="javascript:void(0)" class="btn btn-alt btn-sm btn-primary active" data-toggle="block-toggle-content"></a>
				</div>
				<div class="block-title">							
								<h2>Group : ${gCounter}</h2>													
				</div>
				<div class="block-content" style="display: none;">						
						<table class="table">
						<#list groupChanges[gCounter] as gChange>
							<tr>	
								<#if gChange.changeType == "GROUP_MODIFIED">
									<td>
									Attribute Changed: ${gChange.columnName!}
									</br>
									Old Value: ${gChange.initialValue!}	
									</br>
									New Value: ${gChange.newValue!}
									</td>
									
								</#if>	
								<#if gChange.changeType == "GROUP_ADDITION">
									<td>
										Added Group
									</td>
								</#if>			
								<#if gChange.changeType == "ITEM_ADDITION">
									<td>
										These Items Were Added:
									</td>
								</#if>
								<#if gChange.changeType == "ITEM_MODIFIED">
									<td>
										These Items Were Modified:
									</td>
								</#if>				
								<#if gChange.changeType == "GROUP_DELETE">
									<td>
										The Group Was Deleted:
									</td>
								</#if>		
								
								<td>
								<#if gChange.changeType != "GROUP_DELETE">
									<#list itemChanges as ichange>
									
									
								  		<#if ichange.itemCounter == ichange.itemCounter>
								  	<div class="block">
									<div class="block-options pull-right">
										<a href="javascript:void(0)" class="btn btn-alt btn-sm btn-primary active" data-toggle="block-toggle-content"></a>
									</div>
											<div class="block-title">		
												<h2>${ichange.itemCounter!}</h2>
											</div>
											<div class="block-content" style="display: none;">		
											<table class="table">	
											<tr>
												<#if ichange.changeType == "ITEM_MODIFIED">
													<td>
														Attribute Changed: ${ichange.columnName!}
													</br>
														Initial Value: ${ichange.initialValue!}	
														</br>
														New Value:  ${ichange.newValue!}	
														</td>
													</#if>	
													<#if ichange.changeType == "ITEM_ADDITION">
														<td>
															Added Item
														</td>
													</#if>	
													<#if ichange.changeType == "ITEM_DELETE">
														<td>
															Item Deleted
														</td>
													</#if>	
												</tr>
											</table>
											</div>
											</div>
										</#if>
									</#list>
									</#if>
								</td>
							</tr>
						</#list>
					</table> 
					</div>
					</div>
				</#list>
				</td>
	
			</tr>
		
		</tbody>
	</table>


<script>
	$(document).ready(function() {
	   $(".btn btn-alt btn-sm btn-primary").trigger('click');
	});

	
	
	
	function checkForFiles()
	{
		
	}
	
	</script>
</@layout.mainLayout>
