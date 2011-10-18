package de.gwdg.restagreement

import grails.test.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.JSON
import de.gwdg.*

class TemplateTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
		println 'setup'
		setupTemplates()
		setupAgreements()
    }

    protected void tearDown() {
        super.tearDown()
		println 'tear down'
		clearAll()
    }

    void testListTemplates() {
		def http = new HTTPBuilder("http://localhost:8080/WSAgreementREST")

		http.request(Method.GET, JSON) {
			url.path = '/templates'
			response.success = {resp, json ->
				println json
			}
		}
    }

	private void setupTemplates() {
		(1..13).each {
			def slaTemplate = new SLATemplate()
			slaTemplate.xmlRepresentation = "<SLATemplate id=${it} />"
			if(!slaTemplate.save(flush: true)) {
				slaTemplate.errors.each { error ->
					println error
				}
			}
		}
	}

	private void setupAgreements() {
		(1..17).each {
			def sla = new SLA()
			sla.state = SLA.SLAState.PENDING
			sla.xmlRepresentation = "<SLA id=${it} />"
			def templates = SLATemplate.list()
			Collections.shuffle(templates)
			sla.slaTemplate = templates[0]
			if(!sla.save(flush: true)) {
				sla.errors.each { error ->
					println error
				}
			}
		}
	}

	private void clearAll() {
		SLATemplates.each { template ->
			template.delete(flush: true)
		}
	}
}
