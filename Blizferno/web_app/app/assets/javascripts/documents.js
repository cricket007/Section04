// var GroupArray;
// var deleteID;
// var Members = JSON.parse('{"members":[{"userID":"1","displayName":"Santa Claus"},{"userID":"2","displayName":"WasARebel"},{"userID":"3","displayName":"KemoSabe"},{"userID":"4","displayName":"CaptainKirk"},{"userID":"5","displayName":"Spock"},{"userID":"6","displayName":"Josh"},{"userID":"7","displayName":"Paul"},{"userID":"8","displayName":"David"},{"userID":"9","displayName":"William"},{"userID":"10","displayName":"Maxwell"},{"userID":"11","displayName":"Alpha"},{"userID":"12","displayName":"Dharmin"}]}');

// function hideGroupModal(){
// 	$('#viewGroupModal').modal('hide');
// }

// function showEditModal(){
// 	$('#editAgendaModal').modal('hide');
// 	$('#editAgendaModal').on('hidden.bs.modal', function() {
// 		$(this).removeData('bs.modal');
// 	}); 
// 	// TODO: Put this back in when it works
// 	// $.ajax({
// 	//         type: 'GET',
// 	//         url: 'http://csse371-04.csse.rose-hulman.edu/Groups/' + ID,
// 	//   success:function(data){
// 	//     if(data.groupID != null){
// 	//       GroupArray = JSON.parse(data);
// 	//     }
// 	//   }
// 	// });

// 	//document.getElementById("IDE").value = GroupArray["groupID"];
// 	document.getElementById("titleE").value = GroupArray["groupTitle"];
// 	document.getElementById("typeE").value = GroupArray["groupType"];

// 	populateSelect(Members["members"], "displayName", "userID", GroupArray["members"], "membersE")

// 	$('#editGroupModal').modal('show');
// 	$('#viewGroupModal').modal('hide');
// } 

// function showDeleteModal(ID){
// 	deleteID = ID;
// 	$('#deleteDocumentModal').modal('show');
// }

// function hideDeleteModal(){
// 	// TODO: Put this back in when it works and change it to the delete
// 	// $.ajax({
// 	//         type: 'GET',
// 	//         url: 'http://csse371-04.csse.rose-hulman.edu/Tasks/' + deletelID,
// 	//   success:function(data){
// 	//     if(data.taskID != null){
// 	//       GroupArray = JSON.parse(data);
// 	//     }
// 	//   }
// 	// });
// 	alert("The group matching id " + deleteID + " was deleted");

// 	$('#deleteGroupModal').modal('hide');
// 	// TODO: Put back in
// 	//location.reload(true);
// }

// function showGroupModalNoID(){
// 	$('#viewGroupModal').modal('hide');
// 	$('#viewGroupModal').on('hidden.bs.modal', function() {
// 		$(this).removeData('bs.modal');
// 	});

// 	// Edit info
// 	//GroupArray["groupID"] = document.getElementById("IDE").value;
// 	GroupArray["groupTitle"] = document.getElementById("titleE").value;
// 	GroupArray["groupType"] = document.getElementById("typeE").value;

// 	GroupArray["members"] = returnSelectValuesAsJSON("members", "displayName", "userID", "membersE")

// 	// TODO: Put this back in when it works and change it to update info then add a pull
// 	// $.ajax({
// 	//         type: '',
// 	//         url: 'http://csse371-04.csse.rose-hulman.edu/Tasks/' + ID,
// 	//   success:function(data){
// 	//     if(data.taskID != null){
// 	//       GroupArray = JSON.parse(data);
// 	//     }
// 	//   }
// 	// });

// 	// Update view info
// 	//document.getElementById("IDV").innerHTML = GroupArray["groupID"];
// 	document.getElementById("titleV").innerHTML = GroupArray["groupTitle"];
// 	document.getElementById("typeV").innerHTML = GroupArray["groupType"];

// 	populateTableRows(GroupArray["members"], "displayName", "TableMembersV");

// 	$('#viewGroupModal').modal('show');
// 	$('#editGroupModal').modal('hide');
// }

// function showViewDocumentModal(ID, type){
// 	$('#viewDocumentModal').modal('hide');
// 	$('#viewDocumentModal').on('hidden.bs.modal', function() {
// 		$(this).removeData('bs.modal');
// 	});

// 	// TODO: Put this back in when it works
// 	// $.ajax({
// 	//         type: 'GET',
// 	//         url: 'http://csse371-04.csse.rose-hulman.edu/Tasks/' + ID,
// 	//   success:function(data){
// 	//     if(data.taskID != null){
// 	//       GroupArray = JSON.parse(data);
// 	//     }
// 	//   }
// 	// });

// 	/* FINISH THIS SHIT HERE */
// 	if (type == 'agenda'){
// 		if (ID == 1){
// 			agendaArray = JSON.parse('{"agendaID":"1","title": "agenda1","content": [{"topic": "@agendaTopic","time": "@duration","description": "@description","subtopic": [{"topic": "agendaTopic","time": "@duration","description": "@description"}]}]}');
// 		}else if (ID == 2){
// 			agendaArray = JSON.parse('{"agendaID":"2","title": "agenda2","content": [{"topic": "@agendaTopic","time": "@duration","description": "@description","subtopic": [{"topic": "agendaTopic","time": "@duration","description": "@description"}]}]}');
// 		}

// 		document.getElementById("titleV").innerHTML = agendaArray["title"];
// 		document.getElementById("contentV").innerHTML = agendaArray["content"][0]['topic'];

// 		// populateTableRows(GroupArray["members"], "displayName", "TableMembersV");

// 		$('#viewDocumentModal').modal('show');
// 		$('#editAgendaModal').modal('hide');
// 	}else if(type == 'note'){
// 		if (ID == 1){
// 			noteArray = JSON.parse('{"noteID":"1","title":"title1","description":"@description","content":"content1","dateCreated":"@dateCreated"}');
// 		}else if (ID == 2){
// 			noteArray = JSON.parse('{"noteID":"2","title":"title2","description":"@description","content":"content2","dateCreated":"@dateCreated"}');
// 		}else if (ID == 3){
// 			noteArray = JSON.parse('{"noteID":"3","title":"title3","description":"@description","content":"content3","dateCreated":"@dateCreated"}');
// 		}else if (ID == 4){
// 			noteArray = JSON.parse('{"noteID":"4","title":"title4","description":"@description","content":"content4","dateCreated":"@dateCreated"}');
// 		}

// 		document.getElementById("titleV").innerHTML = noteArray["title"];
// 		document.getElementById("contentV").innerHTML = noteArray["content"];

// 		// populateTableRows(GroupArray["members"], "displayName", "TableMembersV");

// 		$('#viewDocumentModal').modal('show');
// 		$('#editGroupModal').modal('hide');
// 	}
// }


// function getMembers(id){
// 	var newMembers = [];
// 	$( '#' + id + ' :selected' ).each( function( i, selected ) {
// 		newMembers[i] = $( selected ).text();
// 	});
// 	return newMembers;
// }

// function populateTableRows(JSONArray, JSONDisplayColumn, tableID){
// 	var table = document.getElementById(tableID);
// 	if(table.rows.length != 0){
// 		for(var i = table.rows.length - 1; i > -1; i--){
// 			table.deleteRow(i);
// 		}
// 	}

// 	for (var k in JSONArray){
// 		var rowCount = table.rows.length;
// 		var row = table.insertRow(rowCount);

// 		var cell = row.insertCell(0);

// 		cell.innerHTML = JSONArray[k][JSONDisplayColumn];
// 	}
// }

// function populateSelect(JSONArray, JSONDisplayColumn, JSONValueColumn, JSONSelectValues, selectID){
// 	var select = document.getElementById(selectID);

// 	if(select.options.length != 0){
// 		for(var i = select.options - 1; i > -1; i--){
// 			select.remove(i);
// 		}
// 	}

// 	for (var k in JSONArray){
// 		var el = document.createElement("option");
// 		el.textContent = JSONArray[k][JSONDisplayColumn];
// 		el.value = JSONArray[k][JSONValueColumn];
// 		select.appendChild(el);
// 	}

// 	for (var i in JSONSelectValues){
// 		for (var j in select.options){
// 			if(JSONSelectValues[i][JSONValueColumn] == select.options[j].value){
// 				select.options[j].selected = true;
// 				break;
// 			}
// 		}
// 	}
// }

// // TODO: This will probably be usless once connected to the back end
// function returnSelectValuesAsJSON(JSONtype, JSONDisplayColumn, JSONValueColumn, selectID){
// 	var select = document.getElementById(selectID);
// 	var newJSONString = "{" + "\"" + JSONtype + "\"" + ":[";

// 	for (var j in select.options){
// 		if(select.options[j].selected){
// 			newJSONString = newJSONString + "{" + "\"" + JSONValueColumn + "\":\"" + select.options[j].value + "\",\"" + JSONDisplayColumn + "\":\"" + select.options[j].textContent + "\"},";
// 		}
// 	}

// 	newJSONString = newJSONString.substring(0, newJSONString.length - 1);
// 	newJSONString = newJSONString + "]}";
// 	var newJSON = JSON.parse(newJSONString);
// 	return newJSON[JSONtype];
// }