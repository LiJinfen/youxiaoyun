$(document).ready(function() {
	
	// get schools
	$.ajax({
		url: '../sch/getAllSchool.action',
		type: 'post',
		dataType: 'json',
		
		success: function(data) {
			var schools = data.schools;
			
			$.each(schools, function(index, item) {
				//add into radio
				var option = '<option value=' + item.id + '>' + item.name + '</option>';
				$('[name=schoolId]').append(option);
				
				//add into table
				var tdata = '<tr><td>' + item.id + "</td> <td>" + item.name + '</td></tr>';
				$('.tbody').append(tdata);
			})
		}
	})
	
	// set device upload
	$('.device').on('click', function(event) {
		var device = $("input[name='device']:checked").val();
		
		if (device == 1) {
			// $('.url').css('display', 'none');
			// $('[name=file]').css('display', 'block');
		} else if (device == 2) {
		//	$('[name=file]').css('display', 'none');
			// $('.url').css('display', 'block');
		}
	});
	
	
	
	$('form').on('submit', function(event) {
		var device = $("input[name='device']:checked").val();
		
		if (!device) {
			alert('请选择设备类型');
			event.preventDefault();
		}
	});
})
	