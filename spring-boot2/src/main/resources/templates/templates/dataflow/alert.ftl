<#global pageTitle = "Messages" urlLink="alert" urlGroup="system">
<#import "/layout/layout.ftl" as layout/>
<#import "/spring.ftl" as spring>
<@layout.mainLayout urlLink urlGroup>
<style>
    #filter-container {
        margin-bottom: 10px;
    }
</style>

<div class="widget">
    <div class="widget-extra themed-background-dark">
        <div class="row">
            <h5 class="widget-content-light col-md-8 header-text"><strong><@spring.message "field.alerts" /></strong></h5>
        </div>
    </div>
    <div id="requests-content" class="widget-extra-full">
        <#include "tableContent.ftl"/>
    </div>
</div>

<script>

</script>

</@layout.mainLayout>