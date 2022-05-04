


function onItemDeleteClickEventWithXhr(e) {
	// second time clicked
	if (this.classList.contains("clicked")) {
		// update popup
		//bootstrap.Popover.getInstance(this).content = () => "<i class='fas fa-spinner fa-spin'></i>Removing ...";
		e.currentTarget.dataset.bsContent = "<i class='fas fa-spinner fa-spin'></i> Removing ...";
		bootstrap.Popover.getInstance(this).show();
		// send ajax request
		let xhr = new XMLHttpRequest();
		let b = e.currentTarget;
		xhr.onreadystatechange = () => {
			console.log("state = " + xhr.readyState + " status = " +xhr.status);
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					// update popup
					b.dataset.bsContent = "<i class='fas fa-check'></i> Removed";
					bootstrap.Popover.getInstance(b).show();
					// hide popover and remove item after delay
					window.setTimeout(
						() => {
							b.parentNode.parentNode.parentNode.removeChild(b.parentNode.parentNode);
							bootstrap.Popover.getInstance(this).hide();
						},
						2000);
				} else {
					// update popup
					b.dataset.bsContent = "<i class='fas fa-exclamation'></i> Failed";
					bootstrap.Popover.getInstance(b).show();
					window.setTimeout(
						() => {
							bootstrap.Popover.getInstance(this).hide();
						 	// add class clicked 
							this.classList.remove("clicked");
							// change icon
							this.firstElementChild.classList.add("fa-user-times");
							this.firstElementChild.classList.remove("fa-times");
							b.dataset.bsContent = "Click again to remove";
						},
						2000);
				}
			}
		};
		xhr.open("GET", e.currentTarget.href)
		xhr.send();
		// prevent sending request to backend
		e.preventDefault();
	}
	// first time clicked
	else {
	 	// add class clicked 
		this.classList.add("clicked");
		// change icon
		this.firstElementChild.classList.remove("fa-user-times");
		this.firstElementChild.classList.add("fa-times");
		// show popover
		bootstrap.Popover.getInstance(this).show();
		// prevent sending request to backend
		e.preventDefault();
	}
}



function onItemDeleteClickEventWithFetch(e) {
	// second time clicked
	if (this.classList.contains("clicked")) {
		// update popup
		//bootstrap.Popover.getInstance(this).content = () => "<i class='fas fa-spinner fa-spin'></i>Removing ...";
		e.currentTarget.dataset.bsContent = "<i class='fas fa-spinner fa-spin'></i> Removing ...";
		bootstrap.Popover.getInstance(this).show();
		// send ajax request
		window.fetch(e.currentTarget.href)
			.then(response => {
				if (response.ok) {
					// update popup
					b.dataset.bsContent = "<i class='fas fa-check'></i> Removed";
					bootstrap.Popover.getInstance(b).show();
					// hide popover and remove item after delay
					window.setTimeout(
						() => {
							b.parentNode.parentNode.parentNode.removeChild(b.parentNode.parentNode);
							bootstrap.Popover.getInstance(this).hide();
						},
						2000);
				} else {
					// update popup
					b.dataset.bsContent = "<i class='fas fa-exclamation'></i> Failed";
					bootstrap.Popover.getInstance(b).show();
					window.setTimeout(
						() => {
							bootstrap.Popover.getInstance(this).hide();
						 	// add class clicked 
							this.classList.remove("clicked");
							// change icon
							this.firstElementChild.classList.add("fa-user-times");
							this.firstElementChild.classList.remove("fa-times");
							b.dataset.bsContent = "Click again to remove";
						},
						2000);
				}
			});
		// prevent sending request to backend
		e.preventDefault();
	}
	// first time clicked
	else {
	 	// add class clicked 
		this.classList.add("clicked");
		// change icon
		this.firstElementChild.classList.remove("fa-user-times");
		this.firstElementChild.classList.add("fa-times");
		// show popover
		bootstrap.Popover.getInstance(this).show();
		// prevent sending request to backend
		e.preventDefault();
	}
}

function onItemDeleteBlurEvent(e) {
	// change back icon
	this.firstElementChild.classList.add("fa-user-times");
	this.firstElementChild.classList.remove("fa-times");
	// hide popover
	bootstrap.Popover.getInstance(this).hide();
	 // remove class clicked 
	this.classList.remove("clicked");
}



window.addEventListener("load", function () {

	Array.from(document.querySelectorAll(".item .item-actions a[href*='delete']"))
		.forEach(b => {
			b.addEventListener("click", onItemDeleteClickEventWithFetch);
			b.addEventListener("blur", onItemDeleteBlurEvent);
		});
	// activating popovers
	Array.from(document.querySelectorAll('.item .item-actions  [data-bs-toggle="popover"]'))
		.forEach(b => new bootstrap.Popover(b));
});