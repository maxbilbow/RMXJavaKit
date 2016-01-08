<#global pageTitle = "Registered Roles" urlLink="flowTreeChanges" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>
<#import "/change/flowSearchpanel.ftl" as change>

<#macro groups macroFlow macroGroups levelOfParent>

<#list macroGroups as childGroup>
<div class="row">
<div class="col-xs-12">
<#assign panelType = "panel-default">
<#if childGroup.groupChanges?has_content>
	<#if childGroup.groupChanges[0].changeType == "GROUP_ADDITION">
	<#assign panelType = "panel-success">
	<#elseif childGroup.groupChanges[0].changeType == "GROUP_MODIFIED">
	<#assign panelType = "panel-warning">
	<#elseif childGroup.groupChanges[0].changeType == "GROUP_DELETE">
	<#assign panelType = "panel-danger">
	</#if>
</#if>

	<article class="panel ${panelType}" style="border: 1px solid #337ab7">
		<div class="panel-heading">
			<div class="row">
          	<div class="col-xs-1">
				Group: ${childGroup.groupPoolCode!}
			</div>
			<div class="col-xs-4">
				Description: ${childGroup.groupDescription!}	
			</div>
			<div class="col-xs-1">
				Range: ${childGroup.groupRange!}
			</div>
			<div class="col-xs-1">
				Group Level: ${levelOfParent + 1}
			</div>
		
			<#list childGroup.groupChanges as childGroupChange>
				<div class="row">
					<div class="col-xs-12">
						<#if panelType == "panel-success">
						<div class="pull-right">
							<strong>
							NEW GROUP
							</strong>
						</div>
						<#elseif panelType == "panel-warning">
						<div class="pull-right">
							<strong>
							Column affected: </strong>${childGroupChange.columnName!"--BLANK--"}
							<br/>
							<strong>
							Previous value: </strong>${childGroupChange.initialValue!"--BLANK--"}
							<br/>
							<strong>
							New value: </strong>${childGroupChange.newValue!"--BLANK--"}	
						</div>
						<#elseif panelType == "panel-danger">
						<div class="pull-right">
							<strong>
							GROUP REMOVED
							</strong>
						</div>
						</#if>
					</div>
				</div>
			</#list>
			</div>
			</div>
		<section class="panel-body">
			<#list childGroup.dataItems as item>
			<#if (item?counter % 2) == 0>
			<#assign colour = "white">
			<#else>
			<#assign colour = "#f0f0f0" >
			</#if>
				<div class="row js-modalOnClick" data-kitem="${item.itemCounter}" data-group-counter="${childGroup.groupCounter}" data-group-line-no="${item.groupLineNo}" style="cursor:pointer; background:${colour}">
					<div data-toggle="tooltip" data-placement="right" title="${macroFlow.flowCounter} ${childGroup.groupPoolCode}">
					<div class= "col-xs-1">
						<div class= "col-xs-6">
							${item?counter}
						</div>
						<div class="col-xs-6">
							${item.itemCounter!}
						</div>
					</div>
					<div class="col-xs-4">
						${item.itemName!}		
					</div>
					<div class="col-xs-1">
						${item.itemOptionality!}
					</div>
					<div class="col-xs-6">
						<#list item.itemChanges as itemChange>
							<div class="row">
								<#if itemChange.changeType == "ITEM_ADDITION">
								<div class="col-xs-12 text-success bg-success">
									<strong>
									NEW ITEM
									</strong>
								</div>
								</#if>
								<#if itemChange.changeType == "ITEM_MODIFIED">
								<div class="col-xs-12 text-warning bg-warning">
									<strong>
									Column affected: </strong>${itemChange.columnName!"--BLANK--"}
									<br/>
									<strong>
									Previous value: </strong>${itemChange.initialValue!"--BLANK--"}
									<br/>
									<strong>
									New value: </strong>${itemChange.newValue!"--BLANK--"}
									
								</div>
								</#if>
								<#if itemChange.changeType == "ITEM_DELETE">
								<div class="col-xs-12 text-danger bg-danger">
									<strong>
									ITEM REMOVED
									</strong>
								</div>
								</#if>
								<#if itemChange.changeType == "VALIDSETVALUE_MODIFIED">
								<div class="col-xs-12 text-warning bg-warning">
									<strong>
									VALIDSETVALUE MODIFIED
									</strong>
								</div>
								</#if>
							</div>
						</#list>
					</div>
					</div>
				</div>
			</#list>
			<br/>
			<@groups macroFlow childGroup.childGroups levelOfParent+1 />
		</section>
	</article>
	</div>
		</div>
													
</#list>

</#macro>


<@layout.mainLayout urlLink "filteredChanges">

<#if !dataFlow??>
<@change.flowSearchPanel>
</@change.flowSearchPanel>
<#else>
<@change.flowSearchPanel>
</@change.flowSearchPanel>
<#list dataFlow as listedFlow>
<div class="row">
<#assign flowPanelType = "panel-default">
<#if listedFlow.flowChanges?has_content>
	<#if listedFlow.flowChanges[0].changeType == "FLOW_ADDITION">
	<#assign flowPanelType = "panel-success">
	<#elseif listedFlow.flowChanges[0].changeType == "FLOW_MODIFIED">
	<#assign flowPanelType = "panel-warning">
	<#elseif listedFlow.flowChanges[0].changeType == "FLOW_DELETE">
	<#assign flowPanelType = "panel-danger">
	</#if>
</#if>
	<div class="col-xs-12">
		<div class="panel ${flowPanelType}">
			<div class="panel-heading" data-toggle="collapse" data-target="#collapse-${listedFlow.flowCounter}" style="cursor:pointer">
			<h2>${listedFlow.flowCounter}</h2>
			<h4>${listedFlow.flowName}</h4>
			<div class="row">
			<#list listedFlow.flowChanges as flowChange>
			<div class="col-xs-12">
			<#if flowPanelType == "panel-success">
				<strong>
				NEW FLOW
				</strong>
			<#elseif flowPanelType == "panel-warning">
				<strong>
				Column affected: ${flowChange.columnName!"--BLANK--"},
				Previous value: ${flowChange.initialValue!"--BLANK--"},
				New value: ${flowChange.newValue!"--BLANK--"}
				</strong>
			<#elseif flowPanelType == "panel-danger">
				<strong>
				FLOW REMOVED
				</strong>
			</#if>
			</div>
			</#list>
			</div>
			</div>
			<div class="panel-body">
			<div class="panel-collapse collapse <#if !(dataFlow?size > 1)>in</#if>" id="collapse-${listedFlow.flowCounter}">
				<#list listedFlow.topLevelGroups as topLevelgroup>
				
				<#assign panelType = "panel-primary">
				<#if topLevelgroup.groupChanges?has_content>
					<#if topLevelgroup.groupChanges[0].changeType == "GROUP_ADDITION">
					<#assign panelType = "panel-success">
					<#elseif topLevelgroup.groupChanges[0].changeType == "GROUP_MODIFIED">
					<#assign panelType = "panel-warning">
					<#elseif topLevelgroup.groupChanges[0].changeType == "GROUP_DELETE">
					<#assign panelType = "panel-danger">
					</#if>
				</#if>
				
				<article class="panel ${panelType}">
					<div class="panel-heading" data-toggle="collapse" data-target="#collapse-${topLevelgroup.groupCounter}" style="cursor:pointer">
			          	<div class="row">
			          	<div class="col-xs-1">
							Group: ${topLevelgroup.groupPoolCode!}
						</div>
						<div class="col-xs-4">
							Description: ${topLevelgroup.groupDescription!}	
						</div>
						<div class="col-xs-1">
							Range: ${topLevelgroup.groupRange!}
						</div>
						<div class="col-xs-1">
							Group Level: 1
						</div>
							<#list topLevelgroup.groupChanges as groupChange>
								<div class="row">
									<div class="col-xs-12">
										<#if panelType == "panel-success">
										<div class="pull-right">
											<strong>
											NEW GROUP
											</strong>
										</div>
										<#elseif panelType == "panel-warning">
										<div class="pull-right">
											<strong>
											Column affected: ${groupChange.columnName!"--BLANK--"}
											<br/>
											Previous value: ${groupChange.initialValue!"--BLANK--"}
											<br/>
											New value: ${groupChange.newValue!"--BLANK--"}
											</strong>
										</div>
										<#elseif panelType == "panel-danger">
										<div class="pull-right">
											<strong>
											GROUP REMOVED
											</strong>
										</div>
										</#if>
									</div>
								</div>
							</#list>
							</div>
							</div>
					<div class="panel-collapse collapse" id="collapse-${topLevelgroup.groupCounter}">
					<section class="panel-body">
						<div class="row">
							<div class="col-xs-12">
							<#list topLevelgroup.dataItems as item>
							<#if (item?counter % 2) == 0>
							<#assign colour = "white">
							<#else>
							<#assign colour = "#f0f0f0" >
							</#if>
								<div class="row js-modalOnClick" data-kitem="${item.itemCounter}" data-group-counter="${topLevelgroup.groupCounter}" data-group-line-no="${item.groupLineNo}" style="cursor:pointer; background:${colour};">
									<div data-toggle="tooltip" data-placement="right" title="${listedFlow.flowCounter} ${topLevelgroup.groupPoolCode}">
									<div class= "col-xs-1">
										<div class= "col-xs-6">
											${item?counter}
										</div>
										<div class="col-xs-6">
											${item.itemCounter!}
										</div>
									</div>
									<div class="col-xs-4">
										${item.itemName!}		
									</div>
									<div class="col-xs-1">
										${item.itemOptionality!}
									</div>
									<div class="col-xs-6">
										<#list item.itemChanges as itemChange>
											<div class="row">
												<#if itemChange.changeType == "ITEM_ADDITION">
												<div class="col-xs-12 text-success bg-success">
													<strong>
													NEW ITEM
													</strong>
												</div>
												</#if>
												<#if itemChange.changeType == "ITEM_MODIFIED">
												<div class="col-xs-12 text-warning bg-warning">
													<strong>
													Column affected: ${itemChange.columnName!"--BLANK--"}
													<br/>
													Previous value: ${itemChange.initialValue!"--BLANK--"}
													<br/>
													New value: ${itemChange.newValue!"--BLANK--"}
													</strong>
												</div>
												</#if>
												<#if itemChange.changeType == "ITEM_DELETE">
												<div class="col-xs-12 text-danger bg-danger">
													<strong>
													ITEM REMOVED
													</strong>
												</div>
												</#if>
												<#if itemChange.changeType == "VALIDSETVALUE_MODIFIED">
												<div class="col-xs-12 text-warning bg-warning">
													<strong>
													VALIDSETVALUE MODIFIED
													</strong>
												</div>
												</#if>
											</div>
										</#list>
									</div>
									</div>
								</div>
							</#list>
							</div>
						</div>
						<br/>
						<@groups listedFlow topLevelgroup.childGroups 1/>
					</section>
					</div>
				</article>
				</#list>
			</div>
			</div>
		</div>
	</div>
</div>
</#list>
</#if>
<div class="container">
  <!-- Modal -->
  <div class="modal fade" id="kitemModal" role="dialog">
    
  </div>
  
</div>

<script>
	$(".js-modalOnClick").click(function(){
		var itemCode = $(this).data("kitem");
		var groupCounter = $(this).data("group-counter");
		var groupLineNo = $(this).data("group-line-no");
		console.log("click");
		$.ajax({url:"${rc.contextPath}/change/flowTreeChanges/kItemRequest", type: "GET", data: {kitem:itemCode, groupCounter:groupCounter, groupLineNo:groupLineNo}})
		.success(function(aResponse){
			$("#kitemModal").html(aResponse)
			$("#kitemModal").modal()
		})
		.fail(function(){
			console.log("ajax error")
		});
	});
	
	$(document).ready(function(){
	    $('[data-toggle="tooltip"]').tooltip(); 
	});
</script>
</@layout.mainLayout>
