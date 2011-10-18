package de.gwdg

import grails.converters.JSON

import org.slasoi.gslam.syntaxconverter.SLASOITemplateParser
import org.slasoi.gslam.syntaxconverter.SLASOITemplateRenderer
import org.slasoi.gslam.syntaxconverter.WSAgreementTemplateRenderer
import org.slasoi.gslam.syntaxconverter.WSAgreementTemplateParser
import org.slasoi.slamodel.sla.SLATemplate as SLAT
import org.slasoi.slamodel.vocab.bnf
import eu.slaatsoi.slamodel.SLATemplateDocument

import groovyx.net.ws.WSClient

class TemplateController {

    def index = {
		redirect action: 'list'
	}

	def list = {
		// GET w/o id supplied
		def templates = SLATemplate.list()
		def responseList = [:]
		templates.each { template ->
			responseList[template.id] = template.xmlRepresentation
		}
		render responseList as JSON
	}

	def show = {
		// GET
		if(!params.id) {
			redirect action: 'list'
		}
		
		def slaTemplate = SLATemplate.get(params.id)
		if(slaTemplate == null) {
			render status: 404, text: "SLA Template with id ${params.id} is not available."
			return
		}
		def responseList = [:]
		responseList[slaTemplate.id] = slaTemplate

		render responseList as JSON
	}

	def update = {
		// POST
		if(!params.id) {
			render status: 400, text: "Please provide an id for the template to POST on"
			return
		}
		
		def slaTemplate = SLATemplate.get(params.id)
		if(!slaTemplate) {
			render status: 404, text: "The template to update could not be found"
			return
		}

		if(!params.xmlRepresentation) {
			render status: 400, text: "Please provide a new XML representaiton for the POST"
			return
		}

		slaTemplate.xmlRepresentation = params.xmlRepresentation
		render status: 200
		return
	}

	def save = {
		// PUT
		def xmlTemplate = params.template
		if(!xmlTemplate) {
			render status: 400, text: "Please provide an XML-representation of the template to PUT"
			return
		}

		def slaTemplate = new SLATemplate(xmlRepresentation: xmlTemplate)
		if(!slaTemplate.save(flush: true)) {
			slaTemplate.errors.each { error ->
				println error
			}

			render status: 500, text: "Error while trying to save the new template"
			return
		}

		render status: 200
	}

	def delete = {
		// DELETE
		if(!params.id) {
			render status: 400, text: "Please provide an id for the template to DELETE"
			return
		}
		
		def slaTemplate = SLATemplate.get(params.id)
		if(!slaTemplate) {
			render status: 404, text: "The template to DELETE could not be found"
			return
		}

		if(slaTemplate.delete(flush: true)) {
			slaTemplate.error.each { error ->
				println error
			}
			render status: 500, text: "Error while deleting an SLA template"
			return
		}

		render status: 200
	}

	def queryTemplates = {
		if(!params.query) {
			render status: 400, text: "Please provide a query-string"
			return
		}

		// TODO execute query on the templates
		def templates = SLATemplate.list()
		def responseList = [:]
		templates.each { template ->
			responseList[template.id] = template.xmlRepresentation
		}
		render responseList as JSON
	}
	

	// XXX for development only; remove once done!
	def parse = {
		try {

			// read the SLA model template from file
			String templatePath = "/Users/pchronz/Desktop/"
			SLATemplateDocument slaXml = SLATemplateDocument.Factory.parse(new File(templatePath + "a4infra.xml"))
			SLASOITemplateParser slasoiParser = new SLASOITemplateParser()
			SLAT slaTemplate = slasoiParser.parseTemplate(slaXml.xmlText())
			String htmlRenderingPre = bnf.render(slaTemplate, true)
			new File(templatePath + "templatePre.html").write(htmlRenderingPre)

			// render to WSAgreement template
			WSAgreementTemplateRenderer wsagRenderer = new WSAgreementTemplateRenderer()
			def wsagTemplate = wsagRenderer.renderSLATemplate(slaTemplate)
			def wsagFile = new File(templatePath + "wsagInfra.xml")
			wsagFile.write(wsagTemplate)

			// parse the ws ag template
			def wsagStr = wsagFile.readLines()[0]
			def wsagParser = new WSAgreementTemplateParser()
			slaTemplate = wsagParser.parseTemplate(wsagStr)

			// render the sla* template
			def slaRenderer = new SLASOITemplateRenderer()
			def slasoiTemplateString = slaRenderer.renderSLATemplate(slaTemplate)

			new File(templatePath + "a4infraPost.xml").write(slasoiTemplateString)

			String htmlRenderingPost = bnf.render(slaTemplate, true)
			new File(templatePath + "templatePost.html").write(htmlRenderingPost)
		}
		catch(e) {
			log.error e.toString()
			render status: 500, text: e.toString()
			return
		}

		render status: 200, text: "All fine"
	}

	// XXX even more development code
	def consume = {
		String wsdlUrl = "http://localhost:8080/" + resource(dir: "wsdl", file: "ISNegotiation.wsdl").toString()
		def proxy = new WSClient(wsdlUrl, this.class.classLoader)
        proxy.initialize()

		def result = proxy.initiateNegotiation("foo")
		def message = "${90} degrees Celsius are ${result} degrees Fahrenheit"
			
		render status: 200, text: message
	}
}

