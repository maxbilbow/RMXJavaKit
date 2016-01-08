<#global pageTitle = "Choose application" urlLink="roleList" urlGroup="sysAdmin">
<#import "/layout/layout.ftl" as layout>


<@layout.mainLayout "dashboard" "dashboard">

<div class="row">
	<div class="col-xs-4">
	<a href="${rc.contextPath}/change/chooseDatasource" class="widget widget-hover-effect1">
		<div class="widget-simple">
			<div class="widget-icon pull-left themed-background-autumn animation-fadeIn">
			 <i class="gi gi-database_plus"></i>
			</div>
		<h3 class="widget-content text-right animation-pullDown">
			Catalogue 
			<strong>Comparator</strong>
		</h3>
		</div>
	</a>
	</div>
	
	<div class="col-xs-4">
	<a href="${rc.contextPath}/catalogueViewer/catalogueView" class="widget widget-hover-effect1">
		<div class="widget-simple">
			<div class="widget-icon pull-left themed-background-amethyst animation-fadeIn">
			 <i class="hi hi-book"></i>
			</div>
		<h3 class="widget-content text-right animation-pullDown">
			Catalogue 
			<strong>Viewer</strong>
		</h3>
		</div>
	</a>
	</div>
<!-- 	</div>
	
	<div class="row"> -->
	<div class="col-xs-4">
	<a href="${rc.contextPath}/valid8/valid8utility" class="widget widget-hover-effect1">
		<div class="widget-simple">
			<div class="widget-icon pull-left themed-background-fire animation-fadeIn">
			 <i class="fa fa-check-circle-o"></i>
			</div>
		<h3 class="widget-content text-right animation-pullDown">
			Valid8 
			<strong>Flows</strong>
		</h3>
		</div>
	</a>
	</div>
	
	<!-- <div class="col-xs-6">
	<a href="${rc.contextPath}/valid8/items" class="widget widget-hover-effect1">
		<div class="widget-simple">
			<div class="widget-icon pull-left themed-background-spring animation-fadeIn">
			 <i class="fa fa-check-circle"></i>
			</div>
		<h3 class="widget-content text-right animation-pullDown">
			Valid8 
			<strong>Items</strong>
		</h3>
		</div>
	</a>
	</div> -->
</div>

</@layout.mainLayout>