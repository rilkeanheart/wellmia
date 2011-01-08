/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 12/31/10
 * Time: 7:10 AM
 * To change this template use File | Settings | File Templates.
 */
(function () {
	var head = document.getElementsByTagName("head")[0];
	if (head) {
		var scriptStyles = document.createElement("link");
		scriptStyles.rel = "stylesheet";
		scriptStyles.type = "text/css";
		scriptStyles.href = "/css/script-styles.css";
		head.appendChild(scriptStyles);
	}
}());
