$(document).ready(function(){
	$('#province').on('change', function(){
		var provinceId = $(this).val();
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/contacts/location/'+provinceId,
			success: function(result) {
				var result = JSON.parse(result);
				var s = '';
				for(var i = 0; i < result.length; i++) {
					s += '<option value="' + result[i].nombre + '">' + result[i].nombre + '</option>';
				}
				$('#location').html(s);
			}
		});
	});
});