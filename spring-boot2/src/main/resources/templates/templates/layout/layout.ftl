<#macro mainLayout urlLink urlGroup hasSideBar=true progressBar="" >
<#import "/layout/sidebar.ftl" as sidebar/>
<!DOCTYPE html>
<!--[if IE 8]><html class="no-js lt-ie9"><![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!--><html class="no-js"><!--<![endif]-->

</style>
    <head>
        <meta charset="utf-8">

        <title>${pageTitle}</title>

        <meta name="description" content="Utilisoft MRV Frontend - V0.1">

        <meta name="robots" content="noindex, nofollow">

        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">

        <!-- Icons -->
        <link rel="shortcut icon" href="${rc.contextPath}/img/favicon.ico">
        <link rel="apple-touch-icon" href="${rc.contextPath}/img/icon57.png" sizes="57x57">
        <link rel="apple-touch-icon" href="${rc.contextPath}/img/icon72.png" sizes="72x72">
        <link rel="apple-touch-icon" href="${rc.contextPath}/img/icon76.png" sizes="76x76">
        <link rel="apple-touch-icon" href="${rc.contextPath}/img/icon114.png" sizes="114x114">
        <link rel="apple-touch-icon" href="${rc.contextPath}/img/icon120.png" sizes="120x120">
        <link rel="apple-touch-icon" href="${rc.contextPath}//img/icon144.png" sizes="144x144">
        <link rel="apple-touch-icon" href="${rc.contextPath}/img/icon152.png" sizes="152x152">
        <!-- End icons -->

        <#--Styles-->
        <!--Bootstrap-->
        <link rel="stylesheet" href="${rc.contextPath}/css/bootstrap.min.css">
        <!-- Theme -->
        <link rel="stylesheet" href="${rc.contextPath}/css/plugins.css">
        <link rel="stylesheet" href="${rc.contextPath}/css/main.css">
        <#--Jquery UI-->
        <link rel="stylesheet" href="${rc.contextPath}/js/vendor/jquery-ui/jquery-ui.css">
        <#--<link rel="stylesheet" href="${rc.contextPath}/js/vendor/jquery-ui/jquery-ui.theme.css">-->
        <!-- Site wide custom CSS-->
        <link rel="stylesheet" href="${rc.contextPath}/css/custom.css">
        <!-- The themes stylesheet of this template (for using specific theme color in individual elements - must included last) -->
        <link rel="stylesheet" href="${rc.contextPath}/css/themes.css">
        <!-- Modernizr (browser feature detection library) & Respond.js (Enable responsive CSS code on browsers that don't support it, eg IE8) -->
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/modernizr-respond.min.js"></script>
        <#--End styles-->

        <#--Required Scripts-->
        <!--JQuery-->
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/jquery-1.11.2.min.js"></script>
        <!-- Bootstrap JS-->
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/bootstrap.min.js"></script>
        <#--JQuery UI-->
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/jquery-ui/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/jquery-ui/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/jquery.validate.min.js"></script>        
    <#--End required Scripts-->
    </head>

    <body>
    	<#assign EDITABLE = false>
	  
		
        <#--Anon user lockout-->
      
            <#--Page container-->
            <div id="page-container" class="">
      
        <#--End anon user lockout-->

        <#--Authenticated user enable-->
    
            <#--Page container-->
            <#if hasSideBar>
            <div id="page-container" class="sidebar-no-animations sidebar-visible-lg">
                <@sidebar.sidebar urlLink urlGroup></@sidebar.sidebar>
            <#else>
            <div id="page-container" class="">
            </#if>
     
        <#--End authenticated user enable-->

            <#--Main content conatiner-->
            <div id="main-container">
                <#--Page header-->
                <header class="navbar navbar-default">
                    <!-- Left side content -->
                    <ul class="nav navbar-nav-custom">
                        <!-- Main Sidebar Toggle Button -->
                        <li>
                            <#--Authenticated users can see the toggle for the nav-->
                          
                            	<#if hasSideBar>
                                <a href="javascript:void(0)" class="js-closeSidebar" onclick="App.sidebar('toggle-sidebar');">
                                    <i class="fa fa-bars fa-fw"></i>
                                </a>
                               	</#if>
                           
                            <#--End authenticated users can see the toggle for the nav-->
                        </li>
                        <li>
                            <span class="navbar-text">${pageTitle}</span>
                        </li>
                    </ul>
                    <#--End left side contenet-->
					<ul class="nav navbar-nav-custom pull-left">					
					</ul>
                    <#--Right side content-->
                    <ul class="nav navbar-nav-custom pull-right">
                    	<li>
                    		<div class="gallery-image">
	                    		<a href="http://www.utiligroup.com/" target="_blank">
			                        <img src="${rc.contextPath}/img/utilisoft-logo.jpg" height="50" alt="Utilisoft">			                        
			                    </a>
		                    </div>
                    	</li>
                    </ul>
                    <#--End right side content-->

                    <#--progressBar content--->
                        <#if (progressBar?has_content)>
                            ${progressBar}
                        </#if>
                    <#--End progresBar content-->
                </header>
                <#--End page header-->


                <#--Content wrapped in this layout-->
                <div id="page-content">
                    <#if messageType?has_content && messageType == "success">
                        <#global messageType = "success" />
                    </#if>
                    <#if messageType?has_content && messageType ==  "info">
                        <#global messageType = "info" />
                    </#if>
                    <#if messageType?has_content && messageType ==  "warning">
                        <#global messageType = "warning" />
                    </#if>
                    <#if messageType?has_content && messageType ==  "error">
                        <#global messageType = "danger" />
                    </#if>
                    <#if message?has_content>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="alert alert-${messageType} alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                ${message}
                                </div>
                            </div>
                        </div>
                    </#if>
                    <#nested/>
                </div>
                <#--End content wrapped in this layout-->

                <#--Page footer-->
                <footer class="clearfix">
                    <#--Right side content-->
                    <div class="pull-right"></div>
                    <#--End right side content-->

                    <#--Left side content-->
                    <div class="pull-left">
                        <span id="year-copy"></span> &copy; <a href="http://www.utiligroup.com/" target="_blank">Utilisoft Ltd</a>
                    </div>
                    <#--End left side content-->
                </footer>
                <#--End page footer-->
            </div>
            <#--End main content container-->
        </div>
        <#--End page container-->

        <!-- Scroll to top link, initialized in js/app.js - scrollToTop() -->
        <a href="#" id="to-top"><i class="fa fa-angle-double-up"></i></a>

        <#--Addiational Scripts-->
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/sock.js"></script>
        <script type="text/javascript" src="${rc.contextPath}/js/vendor/stomp.js"></script>
        <!--Theme JS-->
        <script type="text/javascript" src="${rc.contextPath}/js/theme/plugins.js"></script>
        <script type="text/javascript" src="${rc.contextPath}/js/theme/app.js"></script>
        <#--Site wide custom JS-->
        <script type="text/javascript" src="${rc.contextPath}/js/custom.js"></script>
        <#--End additional scripts-->

        <#--Code for logout-->
        <script>
            $('#logout-link').click(function() {
                $('#logout-form').submit();
            });         
           
        </script>
        <#--End scode for logout-->
    </body>
</html>
</#macro>