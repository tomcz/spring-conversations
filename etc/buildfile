repositories.remote << 'http://repository.springsource.com/maven/bundles/release'
repositories.remote << 'http://repository.springsource.com/maven/bundles/external'
repositories.remote << 'http://www.ibiblio.org/maven2'

SLF4J = ['org.slf4j:slf4j-api:jar:1.6.1', 'org.slf4j:jcl-over-slf4j:jar:1.6.1', transitive('ch.qos.logback:logback-classic:jar:0.9.27')]
SPRING_WEB = transitive('org.springframework:org.springframework.web.servlet:jar:3.0.5.RELEASE')
VALIDATOR = transitive('org.hibernate:hibernate-validator:jar:4.1.0.Final')
FREEMARKER = 'org.freemarker:freemarker:jar:2.3.16'
COMMONS = 'commons-lang:commons-lang:jar:2.5'

JUNIT = 'junit:junit:jar:4.8.2'
JETTY = transitive('org.mortbay.jetty:jetty:jar:6.1.26')
SPRING_TEST = 'org.springframework:org.springframework.test:jar:3.0.5.RELEASE'

define 'spring-conversations' do
  
  project.version = '1.0-SNAPSHOT'
  
  compile.with SLF4J, SPRING_WEB, VALIDATOR, FREEMARKER, COMMONS
  test.with JUNIT, JETTY, SPRING_TEST
  
  package(:war).with :libs => [SLF4J, SPRING_WEB, VALIDATOR, FREEMARKER, COMMONS]
  
end
