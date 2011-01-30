<#ftl strip_whitespace=true>
<#import "/spring.ftl" as spring />
<#assign htmlEscape=true in spring>
<#assign xhtmlCompliant=false in spring>
<#macro servletUrl url><#escape x as x?html>${contextPath}${servletPath}${url}</#escape></#macro>