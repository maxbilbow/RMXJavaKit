<#global pageTitle = "Flow Details" urlLink="roleList" urlGroup="sysAdmin"> <#import "/layout/layout.ftl" as layout>


<@layout.mainLayout "filteredChanges" "filteredChanges">

	
	
		<h2>${flowChange}</h2>
		
			<#list groupChanges?keys as gCounter>
			<div class="row">
			
				<div  class="block">
					<div class="block-options pull-right">
						<a href="javascript:void(0)" class="btn btn-alt btn-sm btn-primary active" data-toggle="block-toggle-content"></a>
					</div>
					
					<div class="block-title">							
						<h2>Group : ${gCounter}</h2>													
					</div>
					
					<div class="row">
						<div class="block-content" style="display: none;">			
							<div class="col-xs-6">
								<#if groupChanges[gCounter]["GROUP"]??>
									<#list groupChanges[gCounter]["GROUP"] as gChange>
										<#if gChange.changeType == "GROUP_MODIFIED">	
												<h2>${gChange.groupPoolCode}</h2>
												<h5>Attribute Changed: ${gChange.columnName!}</h5>
												<h5>Old Value: ${gChange.initialValue!}</h5>	
												<h5>New Value: ${gChange.newValue!}</h5>
											</br>	
										</#if>	
										<#if gChange.changeType == "GROUP_ADDITION">
												<h5>Added Group</h5>				
										</#if>			
										<#if gChange.changeType == "ITEM_ADDITION">
												<h5>These Items Were Added:</h5>
										</#if>
										<#if gChange.changeType == "ITEM_MODIFIED">
												<h5>These Items Were Modified:</h5>
										</#if>				
										<#if gChange.changeType == "GROUP_DELETE">
												<h5>The Group Was Deleted:</h5>
										</#if>				
									</#list>
								
									<#else>
										<h3>Item Modifications: </h3>
								</#if>				
							</div>
							
							<div class="col-xs-6">
								<#if groupChanges[gCounter]["ITEM"]??>
									<#list groupChanges[gCounter]["ITEM"] as ichange>
										<#if ichange.groupCounter == gCounter>
											<div class="block">
												<div class="block-options pull-right">
													<a href="javascript:void(0)" class="btn btn-alt btn-sm btn-primary active" data-toggle="block-toggle-content"></a>
												</div>
												<div class="block-title">		
													<h2>${ichange.itemCounter!}</h2>
												</div>
												<div class="block-content" style="display: none;">		
													<#if ichange.changeType == "ITEM_MODIFIED">
														<h5>Attribute Changed: ${ichange.columnName!}</h5>
														<h5>Initial Value: ${ichange.initialValue!}	</h5>
														<h5>New Value:  ${ichange.newValue!}</h5>				
													</#if>	
													<#if ichange.changeType == "ITEM_ADDITION">
														<h5>Added Item</h5>
													</#if>	
													<#if ichange.changeType == "ITEM_DELETE">			
														<h5>Item Deleted</h5>	
													</#if>	
															
												</div> <!-- END OF BLOCK CONTENT  -->
											</div>	
										</#if>	
									</#list>	
								</#if>		
							</div>
							
						</div> <!-- END OF BLOCK CONTENT  -->
					</div> <!-- END OF ROW  -->
				</div>									
			</div>
			</#list>
		
	


</@layout.mainLayout>