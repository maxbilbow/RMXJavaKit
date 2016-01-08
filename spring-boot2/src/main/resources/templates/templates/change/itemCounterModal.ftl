<div class="modal-dialog">
    <div class="modal-content">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h4 class="modal-title">Item Name: ${kitem.itemName!}</h4>
  </div>
  <div class="modal-body">
    <div class="row">
    	<div class="col-xs-3">
			Item Reference:
		</div>
		<div class="col-xs-9">
			${kitem.itemCounter!}	
		</div>
    </div>
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
		<div class="panel panel">
		<div class="panel-heading">
		<div class="pull-right">
          	<i data-toggle="collapse" data-target="#collapse-validset" class="hi hi-circle-arrow-down fa-2x" style="cursor:pointer"></i>
        	</div>
        	</div>
        	<div class="panel-collapse collapse  col-xs-12" id="collapse-validset">
					<div class="panel-body"  style="max-height:500px; overflow-y:auto;">
				<#list kitem.itemValidSet! as itemValidSet>
				<#if itemValidSet.validSetValueChangeType??>
					<#if itemValidSet.validSetValueChangeType == "VALIDSETVALUE_ADDITION">
						<div class="row text-success bg-success">
						<strong>
						<div class="col-xs-2">
							${itemValidSet.validSetValue!}
							</div>
							<div class="col-xs-10">
							${itemValidSet.validSetValueDescription!}
							</div>
						</strong>
						</div>
					<#elseif itemValidSet.validSetValueChangeType == "VALIDSETVALUE_DELETE">
						<div class="row text-danger bg-danger">
						<strong>
						<div class="col-xs-2">
							${itemValidSet.validSetValue!}
							</div>
							<div class="col-xs-10">
							${itemValidSet.validSetValueDescription!}
							</div>
						</strong>
						</div>
					</#if>
				<#elseif itemValidSet.validSetValueChangeType?? != true>
				<div class="row">
					<div class="col-xs-2">
						${itemValidSet.validSetValue!}
						</div>
						<div class="col-xs-10">
						${itemValidSet.validSetValueDescription!}
						</div>
					</div>
					</#if>
				</#list>
			</div>
			</div>
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
  <div class="modal-footer">
  	<a href="${rc.contextPath}/catalogueViewer/itemToView?itemToView=${kitem.itemCounter}" type="button" class="btn btn-primary">Flows item is in?</a>
    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
  </div>
</div>
</div>