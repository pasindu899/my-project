$(document).ready(function() {

	// DO GET
	$.ajax({
		type: "GET",
		url: "admin-portal/view-devices",
		success: function(result) {
			$.each(result, function(i, device) {

				var deviceRow = '<tr>' +
					'<td>' + device.deviceId + '</td>' +
					'<td>' + device.serialNo + '</td>' +
					'<td>' + device.bankName + '</td>' +
					'<td>' + device.merchantName + '</td>' +
					'<td>' + device.merchantAddress + '</td>' +
					'<td>' + device.custContactNo + '</td>' +
					'<td>' + device.simNo + '</td>' +
					'<td>' + device.lastUpdate + '</td>' +
					'<td>' + device.status + '</td>' +
					'</tr>';

				$('#deviceTable tbody').append(deviceRow);

			});

			$("#deviceTable tbody tr:odd").addClass("info");
			$("#deviceTable tbody tr:even").addClass("success");
		},
		error: function(e) {			
			console.log("ERROR: ", e);
		}
	});

	// do Filter on View
	$("#inputFilter").on("keyup", function() {
		var inputValue = $(this).val().toLowerCase();
		$("#deviceTable tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(inputValue) > -1)
		});
	});
})