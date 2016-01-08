<#macro sidebar urlLink urlGroup>

<#import "/spring.ftl" as spring/>
<!-- Main Sidebar -->
<div id="sidebar">
<div class="col-xs-12">
    <!-- Wrapper for scrolling functionality -->
    <div class="sidebar-scroll">
        <!-- Sidebar Content -->
        <div class="sidebar-content">
            <!-- Brand -->
            <a href="${rc.contextPath}/layout/dashboard" class="sidebar-brand">
                <img src="${rc.contextPath}/img/WorkBenchIcon2.png" alt="avatar"  style="height:15px;width:15px;padding: 0px;margin: 0px;margin-top: -8px; margin-left: -2px;">
               UtiliTool
            </a>
           <#-- <h5 style="font-size:12px;"><@spring.message "version"/></h5>         -->
            <!-- END brand -->

            <!-- User Info -->
            <div class="sidebar-section">
	                <div class="sidebar-user-name"><h2></h2></div>
	                <div class="sidebar-user-links">
	                   
	                    <!--  <a id="logout-link" href="#" title="Logout"><i class="gi gi-exit"></i> </a>
	                   
	                    <div hidden="hidden">
	                        <form id="logout-form" action="/logout" method="post">
	                        	<#if _csrf??>
	                       		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	                       		</#if>
	                        </form>
	                    </div> -->
	                </div>
            </div>
            <!-- END User Info -->

            <!-- Sidebar Navigation -->
            <ul class="sidebar-nav">
            <li class="sidebar-header">
                <span class="sidebar-header-title">
                Catalogue Comparator
                </span>
                </li>
                <li>               
                    <a href="${rc.contextPath}/change/chooseDatasource" class="<#if urlLink == 'chooseDatasource'>active</#if>"><i class="gi gi-database_plus sidebar-nav-icon"></i>Choose datasources</a>
				</li>
               	<#if Session.catalogueOld?has_content>
               	<li class="sidebar-header">
                	<span class="sidebar-header-title">
                	<i class="fa fa-caret-right"></i> ${Session.catalogueOld!}
                	</span>
                </li>
                <li class="sidebar-header">
                	<span class="sidebar-header-title">
                	<i class="fa fa-caret-right"></i> ${Session.catalogueNew!}
                	</span>
                </li>
                </#if>                
                <li>
                	<a href="${rc.contextPath}/changeNew" class="<#if urlLink == 'listChanges'>active</#if>"><i class="gi gi-list sidebar-nav-icon"></i>View change list</a>
                </li>
                
                <li>
                	<a href="${rc.contextPath}/change/flowTreeChanges" class="<#if urlLink == 'flowTreeChanges'>active</#if>"><i class="fa fa-search sidebar-nav-icon"></i>Flows</a>
                </li>
                <li class="sidebar-header">
                <span class="sidebar-header-title">
                Catalogue Viewer
                </span>
                </li>
                <li>               
                        <a href="${rc.contextPath}/catalogueViewer/catalogueView" class="<#if urlLink == 'chooseCatalogue'>active</#if>"><i class="hi hi-book sidebar-nav-icon"></i>Choose catalogue</a>
                </li>
                <#if Session.catalogue?has_content>
                <li class="sidebar-header">
                	<span class="sidebar-header-title">
                	<i class="fa fa-caret-right"></i> ${Session.catalogue!}
                	</span>
                </li>
                </#if>
                <li>               
                        <a href="${rc.contextPath}/catalogueViewer/flowTreeViewer" class="<#if urlLink == 'flowTreeViewer'>active</#if>"><i class="fa fa-search sidebar-nav-icon"></i>Flows, Groups or Items</a>
                </li>
                 <li class="sidebar-header">
                <span class="sidebar-header-title">
                Valid8 Utility
                </span>
				<li>               
                        <a href="${rc.contextPath}/valid8/valid8utility" class="<#if urlLink == 'valid8flows'>active</#if>"><i class="fa fa-check-circle-o sidebar-nav-icon"></i>Valid8 Flows</a>
                </li>
                <!-- <li>               
                        <a href="${rc.contextPath}/valid8/valid8items" class="<#if urlLink == 'valid8items'>active</#if>"><i class="fa fa-check-circle sidebar-nav-icon"></i>Valid8 items</a>
                </li>    -->              
                <!-- <li class="sidebar-header">
                <span class="sidebar-header-title">
                Shutdown Application
                </span>
                 <li>               
                        <a href="${rc.contextPath}/shutdown" class="<#if urlLink == 'shutdown'>active</#if>"><i class="hi hi-off sidebar-nav-icon"></i>Shutdown</a>
                </li>
                </li> -->
            </ul>
            <!-- END Sidebar Navigation -->
        </div>
        <!-- END Sidebar Content -->
    </div>
    <!-- END Wrapper for scrolling functionality -->
    </div>
</div>
<!-- END Main Sidebar -->
</#macro>