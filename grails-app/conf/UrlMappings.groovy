class UrlMappings {

	static mappings = {
		"/topics/$action" {
            controller = "topics"
            constraints {
                action(inList : ['topCategories','topItems','showCategories'])
            }
        }

        "/topics/$id" {
            controller = "topics"
            action = "timeLine"
        }

        "/members/$id" {
            controller = "members"
            action = "showExternal"
        }
        /*
        "/newsItem/$action" {
            controller = "newsItem"
            constraints {
                action(inList : ['addCommentAjax','create','delete','edit','index','list','save','show','update'])
            }
        }

        "/newsItem/$id" {
            controller = "newsItem"
            action = "showLink"
        }  */

        "/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		//"/"(view:"/index")
        //"/"(controller: "login")
        "/"(controller: "home", action: "index")
		"500"(view:'/error')
        //"500" (controller: "errors", action: "notFound")
        //"404" (controller: "errors", action: "notFound")
        "/login/$action?"(controller: "login")
        "/logout/$action?"(controller: "logout")
	}
}
