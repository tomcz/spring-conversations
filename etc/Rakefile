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

task :default => [:clean, :run_tests, :make_war]

task :clean do
  ant.delete :dir => BUILD_DIR
  puts
end

task :setup do
  ant.path :id => 'classpath' do
    fileset :dir => COMPILE_DIR
    fileset :dir => RUNTIME_LIB_DIR
    fileset :dir => BUILDTIME_LIB_DIR
  end
end

task :make_jars => :setup do
  make_jar MAIN_SRC_DIR, "#{PROJECT_NAME}.jar"
  make_jar TEST_SRC_DIR, "#{PROJECT_NAME}-tests.jar"
end

def make_jar(source_folder, jar_file_name)
  ant.mkdir :dir => CLASSES_DIR
  ant.javac :srcdir => source_folder, :destdir => CLASSES_DIR, :classpathref => 'classpath',
            :source => "1.6", :target => "1.6", :debug => "yes", :includeantruntime => "no"
  ant.jar :jarfile => "#{COMPILE_DIR}/#{jar_file_name}", :basedir => CLASSES_DIR
  ant.delete :dir => CLASSES_DIR
  puts
end

task :run_tests => :make_jars do
  ant.mkdir :dir => TEST_REPORT_DIR
  ant.junit :fork => "yes", :forkmode => "once", :printsummary => "yes",
            :haltonfailure => "no", :failureproperty => "tests.failed" do
    classpath :refid => 'classpath'
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
  ant.war :warfile => "#{DIST_DIR}/#{PROJECT_NAME}.war", :webxml => "src/main/webapp/WEB-INF/web.xml" do
    fileset :dir => "src/main/webapp", :excludes => "**/web.xml"
    lib :dir => COMPILE_DIR, :excludes => "*-tests.jar"
    classes :dir => "src/main/resources"
    lib :dir => RUNTIME_LIB_DIR
  end
  puts
end

task :run_jetty => [:clean, :make_jars] do
  ant.java :classname => "example.jetty.WebServer", :fork => 'yes', :failonerror => 'yes' do
    classpath :location => "src/main/resources"
    classpath :refid => "classpath"
  end
end
