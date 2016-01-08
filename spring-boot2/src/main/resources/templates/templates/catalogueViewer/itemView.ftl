<#macro itemViewPanel kitemview>

<#list kitemview as kitem>
<#if kitem?has_content>
<div class="row">
<div class="col-xs-12">
<div class="panel panel-primary">
    <div class="panel-heading">
  		<div class="row" data-toggle="collapse" data-target="#collapse-${kitem.itemCounter!}" class="fa fa-arrow-down fa-2x" style="cursor:pointer">
    		<div class="col-xs-12">
    		<h4>Item Ref: ${kitem.itemCounter!} Item Name: ${kitem.itemName!}</h4>
    		</div>
    	</div>
  	</div>
  <div class="panel-body">
  <div class="panel-collapse collapse <#if !(kitemview?size > 1)>in</#if>" id="collapse-${kitem.itemCounter!}">
    <div class="row">
    	<div class="col-xs-3">
			Item Ownership:
		</div>
		<div class="col-xs-9">
			${kitem.itemOwner!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Item Description:
		</div>
		<div class="col-xs-9">
			${kitem.itemDescription!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Units:
		</div>
		<div class="col-xs-9">
			${kitem.itemUnits!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Valid Set:
		</div>
		<div class="col-xs-9">
			<#list kitem.itemValidSet! as itemValidSet>
				<div class="row">
				<div class="col-xs-1">
					${itemValidSet.validSetValue!}
					</div>
					<div class="col-xs-4">
					${itemValidSet.validSetValueDescription!}
					</div>
				</div>
			</#list>
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Validation:
		</div>
		<div class="col-xs-9">
			${kitem.itemValidation!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Domain:
		</div>
		<div class="col-xs-9">
			${kitem.itemDomainName!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Logical Format:
		</div>
		<div class="col-xs-9">
		<#if kitem.itemLogicalType == "NUM">
			INT (${kitem.itemLogicalLen!})
			<#else>
			${kitem.itemLogicalType!} (${kitem.itemLogicalLen!})
			</#if>
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Physical Length:
		</div>
		<div class="col-xs-9">
			${kitem.itemLogicalLen!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Decimal Places:
		</div>
		<div class="col-xs-9">
			${kitem.itemDecimalLength!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Has Synonyms:
		</div>
		<div class="col-xs-9">
			${kitem.itemSynonymOf!}	
		</div>
    </div>
    <div class="row">
    	<div class="col-xs-3">
			Has Aliases:
		</div>
		<div class="col-xs-9">
			${kitem.itemAliasOf!}	
		</div>
    </div><div class="row">
    	<div class="col-xs-3">
			Notes:
		</div>
		<div class="col-xs-9">
			${kitem.itemNotes!}	
		</div>
    </div><div class="row">
    	<div class="col-xs-3">
			Version Created:
		</div>
		<div class="col-xs-9">
			${kitem.versionCreated!}	
		</div>
    </div>
  </div>
</div>
</div>
</div>
</div>
</#if>
</#list>
</#macro>