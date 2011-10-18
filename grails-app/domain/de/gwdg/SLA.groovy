package de.gwdg

class SLA {

	enum SLAState {
		// TODO define the proper WS-Agreement states
		OFFER_RECEIVE, REJECTED, PENDING, OBSERVED, COMPLETED, PENDING_AND_TERMINATED, OBSERVED_AND_TERMINATED, TERMINATED
	}
	
	String xmlRepresentation
	SLAState state

	static belongsTo = [slaTemplate: SLATemplate]

    static constraints = {
		xmlRepresentation(nullable: false)
		state(nullable: false)
    }
}

