<#global pageTitle = "Change List" urlLink="listChanges" urlGroup="listChange">
<#import "/layout/layout.ftl" as layout>
<@layout.mainLayout urlLink urlGroup>
<style>
    #filter-container {
        margin-bottom: 10px;
    }
</style>
<div class="widget" style="margin-bottom: 10px">
    <div class="widget-extra themed-background-dark">
        <div class="row">
            <h5 class="widget-content-light col-md-8 header-text"><strong>ListChanges </strong></h5>
        </div>
    </div>
    <div id="requests-content" class="widget-extra-full">
     <#include "tableContent.ftl"/>
    </div>
</div>
</@layout.mainLayout>