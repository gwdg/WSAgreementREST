import de.gwdg.SLA
import de.gwdg.SLATemplate

class BootStrap {

    def init = { servletContext ->
		(1..3).each {
			def slaTemplate = new SLATemplate()
			// TODO read a proper WS-Agreement template as XMLRepresentation
			slaTemplate.xmlRepresentation = "<SLATemplate id=${it} />"
			if(!slaTemplate.save(flush: true)) {
				slaTemplate.errors.each { error ->
					println error
				}
			}
		}

		(1..3).each {
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
    def destroy = {
    }
}
