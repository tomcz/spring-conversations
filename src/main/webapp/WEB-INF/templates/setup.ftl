<#import "spring.ftl" as spring />
<#macro servletUrl url><@spring.url "${servletPath}${url}" /></#macro>