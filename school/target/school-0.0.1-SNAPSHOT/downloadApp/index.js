$(document).ready(function () {
	
	if (isWechat()) {
		$('#layer').css('display', 'block');
		$('html').css('overflow', 'hidden');
		$('body').css('overflow', 'hidden');
	} else if(navigator.userAgent.indexOf('QQ') != -1 && navigator.platform.indexOf('iPhone') != -1) {
		$('#labelmain').html('由于QQ不允许直接下载，请点击右上角按钮，选择在浏览器中打开，再按步骤操作即可。');
		$('#layer').css('display', 'block');
		$('html').css('overflow', 'hidden');
		$('body').css('overflow', 'hidden');		
	}
	
	var downloadUrl,
			url = location.search.slice(1),
			schoolName = decodeURI(url.split('=')[1]);

	$('#download').click(function () {
		var device = $("input[name='device']:checked").val();

		if (!device) {
			alert('请选择您手机的操作系统');
    } else {
			$.ajax({
				url: '../version/findLikeSchoolName.action',
				type: 'post',
				dataType: 'json',
        async: false,
				data: {
					"device": device,
					"schoolName": schoolName
				},
				success: function (data) {
					var version = data.version[0];
          saveURL(version.url, version.schoolName);
          device === 2 ? iosDownloadURL() : downloadURL();
        },
				error: function () {
					alert('服务器故障，请稍后再试！')
				}
			});
    }
	});
	
	$("input[name='device']").on('change', function () {
		saveURL(version.url, schoolName);
		window.scrollTo(0, 1000);
	});
	
});

function isWechat () {
	var ua = navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == "micromessenger") {
		return true;
	} else {
		return false;
	}
}

// li 点击后操作
function saveURL (url, schoolName) {
	
	downloadUrl = url;
	console.log(downloadUrl);
	var device = $("input[name='device']:checked").val(),
		version = '*您选择了';
	device == 1 ? version += "安卓版" : version += "IOS版";
	$('#version').html(version + schoolName);
}

//　点击下载
function downloadURL () {
	try {
		location.href = downloadUrl;
	} catch (e) {
		alert('请按步骤操作！')
	}
	
}

function iosDownloadURL () {
	
	try {
	//	window.open('ios.html?' + downloadUrl);
		location.href = downloadUrl;
	} catch (e) {
		alert('请按步骤操作！')
	}
	
}
