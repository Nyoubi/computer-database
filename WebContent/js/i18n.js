function changeLanguage(language) {
	if (language == "fr") {
		window.location.replace('?lang=fr');
	}
	else {
		window.location.replace('?lang=en');
	}
}