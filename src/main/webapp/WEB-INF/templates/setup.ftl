<#ftl strip_whitespace=true>
<#import "/spring.ftl" as spring />
<#assign htmlEscape=true in spring>
<#assign xhtmlCompliant=false in spring>
<#macro servletUrl url>${contextPath}${servletPath}${url}</#macro>