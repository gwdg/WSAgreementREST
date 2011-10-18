class UrlMappings {

	// TODO omit unsupported HTTP-method mappings
	static mappings = {
		"/template/$id"(controller: 'template') {
			action = [GET: 'show', PUT: 'update', DELETE: 'delete', POST: 'save']
		}
		"/templates"(controller: 'template') {
			action = [GET: 'list']
		}
		"/sla/$id/state/$state?"(controller: 'SLA'){
			action = [GET: 'showState', PUT: 'updateState', DELETE: 'deleteState', POST: 'saveState']
		}
		"/sla/$id"(controller: 'SLA'){
			action = [GET: 'show', PUT: 'update', DELETE: 'delete', POST: 'save']
		}
		// TODO check what happens if someone tries to PUT on the following
		"/slas"(controller: 'SLA'){
			action = [GET: 'list', POST: 'save']
		}
		"/templates/search/$query"(controller: 'template') {
			action= [GET: 'queryTemplates']
		}

		// XXX for development-purposes only; remove once done
		"/template/parse"(controller: 'template', action: 'parse')
		"/template/consume"(controller: 'template', action: 'consume')

		// "/"(view:"/index")
		"500"(view:'/error')

		// TODO add exception handling
	}
}

