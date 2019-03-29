import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

// relevant notes
// The set of possible levels (TRACE, DEBUG, INFO, WARN and ERROR)
// see: ch.qos.logback.classic.Level class

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

def targetDir = BuildSettings.TARGET_DIR
def appenderList = ['STDOUT']
String logAppName = 'tdstm'
String commonPattern = '%d{ISO8601_STRICT} [%t] %-5p %c{2} %ex - %m%n'

if (System.properties.getProperty('catalina.base')) {
    targetDir = "${System.properties.getProperty('catalina.base')}/logs"
}

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')

        pattern = commonPattern
    }
}

if (targetDir != null) {
    appender('APPLICATION_LOG', FileAppender) {
        file = "${targetDir}/${logAppName}.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = commonPattern
        }
    }
    appenderList.add('APPLICATION_LOG')

    appender('AUDIT_LOG', FileAppender) {
        file = "${targetDir}/${logAppName}-audit.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = commonPattern
        }
    }
}

if (Environment.isDevelopmentMode() && targetDir != null) {
    appender('FULL_STACKTRACE', FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}

// Set level for all application artifacts
logger 'grails.app', INFO, appenderList

// Useful for debugging spring security
logger 'org.springframework.security', INFO, appenderList, false
logger 'grails.plugin.springsecurity', INFO, appenderList, false

// TDS LOG Section
logger 'com.tdssrc.grails.GormUtil', INFO, appenderList, false
logger 'net.transitionmanager', INFO, appenderList, false

// GRAILS FRAMEWORK
logger 'grails.app.controllers', WARN, appenderList, false
logger 'grails.app.services', WARN, appenderList, false
logger 'grails.plugins', WARN, appenderList, false
logger 'org.grails.web.servlet', WARN, appenderList, false
logger 'org.grails.web.mapping', WARN, appenderList, false
logger 'org.grails.plugins.web.controllers', WARN, appenderList, false
logger 'org.grails.spring', WARN, appenderList, false
logger 'org.grails.orm.hibernate', WARN, appenderList, false
// deprecated org.codehaus.groovy.grails.domain.GrailsDomainClassCleaner by MappingContext
logger 'org.grails.datastore.mapping.model.MappingContext', WARN, appenderList, false
logger 'org.grails.datastore.gorm', WARN, appenderList, false
logger 'org.apache.jasper', WARN, appenderList, false
logger 'grails.spring.BeanBuilder', WARN, appenderList, false
logger 'org.hibernate', WARN, appenderList, false
logger 'org.quartz', WARN, appenderList, false
logger 'quartz.QuartzGrailsPlugin', WARN, appenderList, false
logger 'org.apache.catalina', WARN, appenderList, false
logger 'org.apache.coyote', WARN, appenderList, false
logger 'org.apache.naming', WARN, appenderList, false
logger 'net.sf.ehcache', WARN, appenderList, false
logger 'net.sf.ehcache.hibernate', WARN, appenderList, false
logger 'org.springframework', WARN, appenderList, false

logger 'org.hibernate.hql.internal.ast.HqlSqlWalker', ERROR, appenderList, false
logger 'grails.plugin.hibernate', ERROR, appenderList, false
logger 'org.apache.tomcat', ERROR, appenderList, false
logger 'liquibase', ERROR, appenderList, false
logger 'net.bull.javamelody', ERROR, appenderList, false

// SQL Logging
// Enable Hibernate SQL logging with param values
 logger 'org.hibernate.type', TRACE, appenderList, false
 //logger 'org.hibernate.SQL', DEBUG, appenderList, false

// Setup Audit Logging messages to go to their own log file in addition to the application log
logger 'net.transitionmanager.service.AuditService', ERROR, ['AUDIT_LOG'], true

root(INFO, appenderList)
