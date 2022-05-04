
function updateForm() {
	// check radio value
	let selected = document.querySelector("input[type=radio]:checked").value;
	// show / hide members and groups selects
	if (selected === "PERSON") {
		document.querySelectorAll("label[for=members], select#members")
			.forEach(e => e.style.display = "none");
		document.querySelectorAll("label[for=groups], select#groups")
			.forEach(e => e.style.display = "inline");
	} else {
		document.querySelectorAll("label[for=members], select#members")
			.forEach(e => e.style.display = "inline");
		document.querySelectorAll("label[for=groups], select#groups")
			.forEach(e => e.style.display = "none");
	}
}



window.addEventListener("load", function () {

	updateForm();
	
	document.querySelectorAll("input[name=type]")
		.forEach(r => r.addEventListener("change", updateForm));

	
});