<#macro groupViewPanel groupview>
<#assign id = 1>
<#list groupview as group>
<div class="row col-xs-12">
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="pull-right">
			<a href="${rc.contextPath}/catalogueViewer/generateGroupReport/${group.groupPoolCode}/${group.flowCounter}" type="submit" class="btn btn-warning">Generate group report</a>
		</div>
		<div class="row" data-toggle="collapse" data-target="#collapse-${id}" class="fa fa-arrow-down fa-2x" style="cursor:pointer">
			<h4>
			<div class="col-xs-3">
				<div class="col-xs-5">
				Flow: ${group.flowCounter}
				</div>
				<div class="col-xs-7">
				Group Pool Code: ${group.groupPoolCode}
				</div>
			</div>
			<div class="col-xs-5">
			Group Description: ${group.groupDescription}
			</div>
			<div class="col-xs-2">
			Group Range: ${group.groupRange}
			</div>
			</h4>
		</div>	
	</div>
	<div class="panel-body">
	<div class="panel-collapse collapse <#if !(groupview?size > 1)>in</#if>" id="collapse-${id}">
		<#assign id = id + 1>
		<div class="row">
			<strong>
			<div class="col-xs-1">
				<div class="col-xs-6">
					Line
				</div>
				<div class="col-xs-6">
					Item Counter
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
				Logical Length
			</div>
			<div class="col-xs-1">
				# Decimal places
			</div>
			<div class="col-xs-5">
				Description
			</div>
			</strong>
		</div>
		<#list group.dataItems as kitem>
			<div class="row col-xs-12">
				<#if (kitem?counter % 2) == 0>
				<#assign colour = "white">
				<#else>
				<#assign colour = "#f0f0f0" >
				</#if>
				<div id="${kitem.itemCounter}" class="row js-modalOnClick" data-kitem="${kitem.itemCounter}" data-group-counter="${group.groupCounter}" data-group-line-no="${kitem.groupLineNo}" style="cursor:pointer; background:${colour};">
					<div class="col-xs-1">
						<div class="col-xs-6">
							${kitem.groupLineNo}
						</div>
						<div class="col-xs-6">
							${kitem.itemCounter!}
						</div>
					</div>
					<div class="col-xs-2">
						${kitem.itemName!}		
					</div>
					<div class="col-xs-1">
						${kitem.itemOptionality!}
					</div>
					<div class="col-xs-1">
						${kitem.itemDomainName!}
					</div>
					<div class="col-xs-1">
						${kitem.itemLogicalLen!}
					</div>
					<div class="col-xs-1">
						${kitem.itemDecimalLength!}
					</div>
					<div class="col-xs-5">
						${kitem.itemDescription!}
					</div>
				</div>
			</div>
		</#list>
	</div>
</div>
</div>
</div>
</#list>


</#macro>