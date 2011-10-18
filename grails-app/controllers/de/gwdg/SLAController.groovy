package de.gwdg

import grails.converters.JSON

class SLAController {

    def index = {
		println 'The index action of SLAController should never be called... It has just been called.'  
	}

	def list = {
		def slas = SLATemplate.list()
		def responseList = [:]
		slas.each { sla ->
			responseList[sla.id] = sla.xmlRepresentation
		}

		render text: responseList as JSON
	}

	def show = {
		// GET
		if(!params.id) {
			redirect action: 'list'
		}
		
		def sla= SLA.get(params.id)
		if(sla== null) {
			render status: 404, text: "SLA with id ${params.id} is not available."
			return
		}

		render status: 200, text: sla.xmlRepresentation
	}

	def update = {
		// PUT
		render status: 400, text: "PUT is not allowed on SLAs"
	}

	def save = {
		// POST

		// this is equivalent to WS-Agreement createAgreement
		String xmlRepresentation = params.sla

		// TODO validate agreement

		// TODO retrieve the template id
		def templateId = 1L

		// retrieve the referenced template
		def template = SLATemplate.get(templateId)

		// create a new agreement based on the offer
		def sla = new SLA(xmlRepresentation: xmlRepresentation, state: SLA.SLAState.COMPLETED, slaTemplate: template)
		if(!sla.save(flush: true)) {
			log.error "Error while trying to create an SLA"
			sla.errors.each { error ->
				log.error error
				render status: 500, text: "Error while trying to create the SLA."
			}
		}
		

		// return the id of the new agreement
		render status: 200, text: sla.id as String
	}

	def delete = {
		// DELETE
		if(!params.id) {
			render status: 400, text: "Please provide an id for the SLA to DELETE"
			return
		}
		
		def sla= SLA.get(params.id)
		if(!sla) {
			render status: 404, text: "The SLA to DELETE could not be found"
			return
		}

		if(sla.delete(flush: true)) {
			sla.error.each { error ->
				println error
			}
			render status: 500, text: "Error while deleting an SLA"
			return
		}

		render status: 200
	}

	def showState = {
		if(!params.id) {
			render status: 400 , text: "No id provided for required state"
			return
		}

		def sla = SLA.get(params.id)

		if(!sla) {
			render status: 404, text: "SLA with id ${params.id} not found"
			return
		}
		
		// render sla.state as JSON
		render sla.state.toString()
	}
		
	// PUT
	def updateState = {
		if(!params.id) {
			render status: 400 , text: "No id provided for required state"
			return
		}

		def sla = SLA.get(params.id)

		if(!sla) {
			render status: 404, text: "SLA with id ${params.id} not found"
			return
		}

		if(!params.state) {
			render status: 400, text: "Please provide a state string"
			return
		}

		try {
			sla.state = Enum.valueOf(SLAState, params.state)
		}
		catch(IllegalArgumentException iae) {
			println iae.stackTrace()
			render status: 400, 'The provided agreement state ${params.state} is invalid'
			return
		}

		render status: 200
	}

	def saveState = {
		// TODO find out the real difference between PUT and POST on an agreement
		if(!params.id) {
			render status: 400 , text: "No id provided for required state"
			return
		}

		def sla = SLA.get(params.id)

		if(!sla) {
			render status: 404, text: "SLA with id ${params.id} not found"
			return
		}

		if(!params.state) {
			render status: 400, text: "Please provide a state string"
			return
		}

		try {
			sla.state = Enum.valueOf(SLAState, params.state)
		}
		catch(IllegalArgumentException iae) {
			println iae.stackTrace()
			render status: 400, 'The provided agreement state ${params.state} is invalid'
			return
		}

		render status: 200
	}

	def deleteState = {
		// TODO find out the real meaning of "deleting a resource"
		if(!params.id) {
			render status: 400 , text: "No id provided for required state"
			return
		}

		def sla = SLA.get(params.id)

		if(!sla) {
			render status: 404, text: "SLA with id ${params.id} not found"
			return
		}

		// TODO catch the exception
		sla.state = Enum.valueOf(SLAState, stateString)

		render status: 200
	}
}

