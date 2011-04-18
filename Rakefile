require 'rake/clean'
require 'ant'

PROJECT_NAME = 'spring-conversations'

MAIN_SRC_DIR = 'src/main/java'
TEST_SRC_DIR = 'src/test/java'

RUNTIME_LIB_DIR = 'lib/runtime'
BUILDTIME_LIB_DIR = 'lib/buildtime'

BUILD_DIR = 'build'
DIST_DIR = "#{BUILD_DIR}/dist"
COMPILE_DIR = "#{BUILD_DIR}/compile"
CLASSES_DIR = "#{COMPILE_DIR}/classes"
TEST_REPORT_DIR = "#{BUILD_DIR}/report"

CLEAN.include(BUILD_DIR)
CLOBBER.include('out')

desc "Create WAR file and run all tests"
task :default => [:clean, :run_tests, :make_war] do
  puts 'Build successful'
  exit
end

task :setup do
  ant.typedef :resource => "org/apache/maven/artifact/ant/antlib.xml" do
    classpath :location => "bootstrap/maven-ant-tasks-2.1.2.jar"
  end
  ant.pom :id => "pom", :file => "pom.xml"
  ant.dependencies :pomRefId => "pom", :pathId => "test.classpath", :useScope => "test"
  ant.dependencies :pomRefId => "pom", :pathId => "compile.classpath", :useScope => "compile"
  ant.dependencies :pomRefId => "pom", :filesetId => "runtime.libs", :useScope => "runtime"
end

task :make_jars => :setup do
  make_jar MAIN_SRC_DIR, "#{PROJECT_NAME}.jar", "compile.classpath"
  make_jar TEST_SRC_DIR, "#{PROJECT_NAME}-tests.jar", "test.classpath"
end

def make_jar(source_folder, jar_file_name, classpath)
  ant.mkdir :dir => CLASSES_DIR
  ant.javac :srcdir => source_folder, :destdir => CLASSES_DIR, :source => "1.6", :target => "1.6",
            :debug => "yes", :includeantruntime => "no" do
    classpath do
      fileset :dir => COMPILE_DIR, :includes => "*.jar"
      path :refid => classpath
    end
  end
  ant.jar :jarfile => "#{COMPILE_DIR}/#{jar_file_name}", :basedir => CLASSES_DIR
  ant.delete :dir => CLASSES_DIR
  puts
end

task :run_tests => :make_jars do
  ant.mkdir :dir => TEST_REPORT_DIR
  ant.junit :fork => "yes", :forkmode => "once", :printsummary => "yes",
            :haltonfailure => "no", :failureproperty => "tests.failed" do
    classpath do
      fileset :dir => COMPILE_DIR, :includes => "*.jar"
      path :refid => 'test.classpath'
    end
    formatter :type => "xml"
    batchtest :todir => TEST_REPORT_DIR do
      fileset :dir => TEST_SRC_DIR, :includes => '**/*Test.java'
    end
  end
  if ant.project.getProperty("tests.failed")
    ant.junitreport :todir => TEST_REPORT_DIR do
      fileset :dir => TEST_REPORT_DIR, :includes => "TEST-*.xml"
      report :todir => "#{TEST_REPORT_DIR}/html"
    end
    ant.fail :message => "One or more tests failed. Please check the test report for more info."
  end
  puts
end

task :make_war => :make_jars do
  ant.mkdir :dir => DIST_DIR
  ant.copy :todir => "#{DIST_DIR}/libs", :overwrite => 'yes', :flatten => 'yes' do
    fileset :refid => 'runtime.libs'
  end
  ant.war :warfile => "#{DIST_DIR}/#{PROJECT_NAME}.war", :webxml => "src/main/webapp/WEB-INF/web.xml" do
    fileset :dir => "src/main/webapp", :excludes => "**/web.xml"
    classes :dir => "src/main/resources"
    lib :dir => COMPILE_DIR, :excludes => "*-tests.jar"
    lib :dir => "#{DIST_DIR}/libs"
  end
  puts
end

desc "Run application in Jetty"
task :run_jetty => [:clean, :make_jars] do
  ant.java :classname => "example.jetty.WebServer", :fork => 'yes', :failonerror => 'yes' do
    classpath do
      pathelement :location => 'src/main/resources'
      fileset :dir => COMPILE_DIR, :includes => "*.jar"
      path :refid => 'test.classpath'
    end
  end
end
