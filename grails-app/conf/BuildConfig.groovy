grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
		compile 'com.sun.xml.bind:jaxb-xjc:2.1.12', 'com.sun.xml.bind:jaxb-impl:2.1.12' 

		runtime('org.codehaus.groovy.modules:groovyws:0.5.2') { 
			excludes 'geronimo-servlet_2.5_spec', 'servlet-api', 'jaxb-xjc', 'jaxb-impl', 'xml-apis', 'saaj-impl', 'junit', 'slf4j-jdk14', 'xmlParserAPIs', 'jaxb-api', 'saaj-api', 'xmlbeans', 'jaxen', 'geronimo-stax-api_1.0_spec', 'geronimo-activation_1.0.2_spec', 'abdera-client', 'geronimo-activation_1.1_spec' 
		} 

	}
}
