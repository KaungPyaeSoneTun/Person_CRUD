function getPersonList() {
	loading();
	const workspace = document.getElementById("workspace");
	workspace.innerHTML = "";
	console.log("person loaded");
    $.ajax({
		type:"GET",
		url:"person/",
		headers: {"userId":"personCRUD"},
		error : error,
	}).done(response => {
		hideLoading();
		workspace.innerHTML = response; 
		let table = $('#personTable').DataTable();
		$('#dateFilterBtn').click(function(){
			table.draw();
		})
	});
	function error() {
		alert("Can't retrive person list.");
		hideLoading();
	}
}

function hideDialog() {
	$('#formtemp').html("");
    $('#formtemp').removeClass("dialog");
}

function getTodayDateStr() {
	const date = new Date();
	const month = date.getMonth() < 9 ? `0${date.getMonth() + 1}` : date.getMonth() + 1;
	const day = date.getDate() < 9 ? `0${date.getDate()}` : date.getDate();
	return `${date.getFullYear()}-${month}-${day}`;
}
class Person {
	
	static showNewForm() {
		$.get("person/form").done(
				response => {
					const formTemp = $("#formtemp");
					formTemp.addClass("dialog");
					formTemp.html(response);
					$('#formDate').attr('max', getTodayDateStr());
					var mainApp = new Vue({
				        el: '#formtemp',
				        vuetify: new Vuetify(),
				        data: {
				        	person :{
				        		firstName : "",
					        	lastName : "",
					        	email : "",
					        	dateOfBirth : "",
					        	address: {
					        		latitude : 0,
					        		longitude : 0
					        	}
				        	}
				        	
				        },methods : {
				        	save(person){
				        		
				        		Person.save(person);	
				        	},
				        	cancel() {
				        		hideDialog();
				        	}
				        }
				    })
					formTemp.show();
				});
	}
	
	static save(person) {
		loading();
		person.address.latitude = $("#latText").val();
		person.address.longitude = $("#langText").val();
		console.log(person);
		 var request = new XMLHttpRequest;
		    request.onreadystatechange = function() {
		        if (request.readyState == 4) {
		            if (request.status == 200) {
		                hideLoading();
		                getPersonList();
		                hideDialog();
		            } else {
		                alert("Can't save person.");
		            }
		        }
		    };
		    loading();
		     // save person
		     request.open("POST", "person");
		     request.setRequestHeader('userId','personCRUD');
		     request.setRequestHeader('Content-Type','application/json');
		     request.send(JSON.stringify(person));
	}
	
	static delete(id) {
		console.log(id);
		let conf = confirm("Are you sure to delete this person?");
		if(conf == true){
			var request = new XMLHttpRequest;
		    request.onreadystatechange = function() {
		        if (request.readyState == 4) {
		            if (request.status == 200) {
		                getPersonList();
		                hideDialog();
		            } else {
		                alert("Can't delete person.");
		            }
		        }
		    };
		    loading();
		     // delete person
		     request.open("DELETE", "person/"+id);
		     request.setRequestHeader('userId','personCRUD');
		     request.send();
		}

	}
	
	
	
	static edit(id) {
		let restData ={};
		$.ajax({
			type:"GET",
			url:"person/"+id,
			headers: {"userId":"personCRUD"},
			error : error,
		}).done(response => {
			restData.person = response;
			editForm(restData.person);
		});
		function error() {
			alert("Can't edit person.");
			hideLoading();
		}
		
	}
}
function editForm(restPerson) {
	$.get("person/form").done(
			response => {
				const formTemp = $("#formtemp");
				formTemp.addClass("dialog");
				formTemp.html(response);
				$('#formDate').attr('max', getTodayDateStr());
				var mainApp = new Vue({
			        el: '#formtemp',
			        vuetify: new Vuetify(),
			        data: {
			        		person : restPerson,	
			        },methods : {
			        	save(person){
			        		person.address.latitude = $("#latText").val();
			        		person.address.longitude = $("langText").val();
			        		Person.save(person);	
			        	},
			        	cancel() {
			        		hideDialog();
			        	}

			        }
			    })
				formTemp.show();
			});
}