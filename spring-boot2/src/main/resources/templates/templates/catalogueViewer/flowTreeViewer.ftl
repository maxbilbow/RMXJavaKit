<#global pageTitle = "Registered Roles" urlLink="flowTreeViewer" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>
<#import "/catalogueViewer/flowItemSearchPanel.ftl" as catalogue>
<#import "/catalogueViewer/itemView.ftl" as itemViewPanel>
<#import "/catalogueViewer/groupView.ftl" as groupViewPanel>
<#import "/catalogueViewer/itemGroupFlowResults.ftl" as results>

<#macro groups macroFlow macroGroups levelOfParent>

<#list macroGroups as childGroup>
<div class="row">
<div class="col-xs-12">
	<article id="${childGroup.groupPoolCode!}" class="panel panel-default" style="border: 1px solid #337ab7">
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
			</div>
			</div>
		<section class="panel-body">
		<div class="row">
		<div class="col-xs-1">
						<div class="col-xs-6">
							Line No
						</div>
						<div class="col-xs-6">
							Ref#
						</div>
					</div>
					<div class="col-xs-2">
						Name	
					</div>
					<div class="col-xs-1">
						Optionality
					</div>
					<div class="col-xs-1">
						Domain
					</div>
					<div class="col-xs-1">
						Length
					</div>
					<div class="col-xs-1">
						Decimal Places
					</div>
					<div class="col-xs-5">
						Description
					</div>
					</div>
			<#list childGroup.dataItems as item>
			<#if (item?counter % 2) == 0>
			<#assign colour = "white">
			<#else>
			<#assign colour = "#f0f0f0" >
			</#if>
				<div id="${item.itemCounter}" class="row js-modalOnClick" data-kitem="${item.itemCounter}" data-group-counter="${childGroup.groupCounter}" data-group-line-no="${item.groupLineNo}" style="cursor:pointer; background:${colour}">
					<div data-toggle="tooltip" data-placement="right" title="${macroFlow.flowCounter} ${childGroup.groupPoolCode!}">
					<div class="col-xs-1">
						<div class="col-xs-6">
							${item.groupLineNo}
						</div>
						<div class="col-xs-6">
							${item.itemCounter!}
						</div>
					</div>
					<div class="col-xs-2">
						${item.itemName!}		
					</div>
					<div class="col-xs-1">
						${item.itemOptionality!}
					</div>
					<div class="col-xs-1">
						${item.itemDomainName!}
					</div>
					<div class="col-xs-1">
						${item.itemLogicalLen!}
					</div>
					<div class="col-xs-1">
						${item.itemDecimalLength!}
					</div>
					<div class="col-xs-5">
						${item.itemDescription!}
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


<@layout.mainLayout "flowTreeViewer" "filteredChanges">
<@catalogue.flowItemSearchPanel/>
<#if flowGroupUsage?has_content>
<@results.itemGroupFlowResults/>
</#if>
<#if kitemview??>
<@itemViewPanel.itemViewPanel kitemview/>
</#if>
<#if groupview??>
<@groupViewPanel.groupViewPanel groupview/>
</#if>
<#if dataFlow??>
<#list dataFlow as listedFlow>
<div class="row">
	<div class="col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="pull-right">
					<a href="${rc.contextPath}/catalogueViewer/generateFlowReport/${listedFlow.flowCounter}" type="submit" class="btn btn-warning">Generate flow report</a>
				</div>
			<div class="row" data-toggle="collapse" data-target="#collapse-${listedFlow.flowCounter}" class="fa fa-arrow-down fa-2x" style="cursor:pointer">
			<div class="col-xs-12">
			<h2>${listedFlow.flowCounter!}</h2>
			<h4>${listedFlow.flowName!}</h4>
			<h5>Flow Owner: ${listedFlow.flowOwner!}</h5>
			<h5>Network Owner: ${listedFlow.networkOwner!}</h5>
			</div>
	          	</div>
			</div>
			<div class="panel-body">
			<div class="panel-collapse collapse <#if !(dataFlow?size > 1)>in</#if>" id="collapse-${listedFlow.flowCounter}">
				<#list listedFlow.topLevelGroups as topLevelgroup>
				<article id="${topLevelgroup.groupPoolCode!}" class="panel panel-primary">
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
							</div>
							</div>
					<div class="panel-collapse collapse" id="collapse-${topLevelgroup.groupCounter!}">
					<section class="panel-body">
					<div class="row">
		<div class="col-xs-1">
						<div class="col-xs-6">
							Line No
						</div>
						<div class="col-xs-6">
							Ref#
						</div>
					</div>
					<div class="col-xs-2">
						Name	
					</div>
					<div class="col-xs-1">
						Optionality
					</div>
					<div class="col-xs-1">
						Domain
					</div>
					<div class="col-xs-1">
						Length
					</div>
					<div class="col-xs-1">
						Decimal Places
					</div>
					<div class="col-xs-5">
						Description
					</div>
					</div>
						<div class="row">
							<div class="col-xs-12">
							<#list topLevelgroup.dataItems as item>
							<#if (item?counter % 2) == 0>
							<#assign colour = "white">
							<#else>
							<#assign colour = "#f0f0f0" >
							</#if>
								<div id="${item.itemCounter}" class="row js-modalOnClick" data-kitem="${item.itemCounter}" data-group-counter="${topLevelgroup.groupCounter}" data-group-line-no="${item.groupLineNo}" style="cursor:pointer; background:${colour};">
									<div data-toggle="tooltip" data-placement="right" title="${listedFlow.flowCounter} ${topLevelgroup.groupPoolCode!}">
									<div class="col-xs-1">
										<div class="col-xs-6">
											${item.groupLineNo}
										</div>
										<div class="col-xs-6">
											${item.itemCounter!}
										</div>
									</div>
									<div class="col-xs-2">
										${item.itemName!}		
									</div>
									<div class="col-xs-1">
										${item.itemOptionality!}
									</div>
									<div class="col-xs-1">
										${item.itemDomainName!}
									</div>
									<div class="col-xs-1">
										${item.itemLogicalLen!}
									</div>
									<div class="col-xs-1">
										${item.itemDecimalLength!}
									</div>
									<div class="col-xs-5">
										${item.itemDescription!}
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
	$.ajax({url:"${rc.contextPath}/catalogueViewer/flowTreeViewer/kItemRequest", type: "GET", data: {kitem:itemCode, groupCounter:groupCounter, groupLineNo:groupLineNo}})
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
