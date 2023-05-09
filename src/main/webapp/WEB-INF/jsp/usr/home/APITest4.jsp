<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="API Test4" />
<%@ include file="../common/head.jspf"%>

<div style="margin: 0 auto;">
		<div id="map" style="width: 1000px; height: 400px;"></div>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c875d31c484872a25491ce2b210f6b30"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=LIBRARY"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services,clusterer,drawing"></script>
<div id="map" style="width: 100%; height: 350px;"></div>
<p>
		<em>지도를 클릭해주세요!</em>
</p>
<div id="clickLatlng"></div>
<script>
	const API_KEY = 'yIGj7lJBJIcshuAYg2rAxIOkX81W14YthO8jdaPqMNOxbp52KQZaUPkf83%2Bs27TMVMLnNvSzzRoUmbAuju%2F30A%3D%3D';

	//await을 쓰기 위해서 async 필요
	async function getData() {
		const url = 'http://apis.data.go.kr/1790387/covid19CurrentStatusKorea/covid19CurrentStatusKoreaJason?serviceKey='
				+ API_KEY;
		const response = await
		fetch(url);
		const data = await
		response.json();
		console.log("data", data);
	}
	getData();

	// 카카오맵
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center : new kakao.maps.LatLng(36.3510064, 127.3797339), // 지도의 중심좌표
		level : 3
	// 지도의 확대 레벨
	};

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

	// 지도를 클릭한 위치에 표출할 마커입니다
	var marker = new kakao.maps.Marker({
		// 지도 중심좌표에 마커를 생성합니다 
		position : map.getCenter()
	});
	// 지도에 마커를 표시합니다
	marker.setMap(map);

	// 지도에 클릭 이벤트를 등록합니다
	// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

		// 클릭한 위도, 경도 정보를 가져옵니다 
		var latlng = mouseEvent.latLng;

		// 마커 위치를 클릭한 위치로 옮깁니다
		marker.setPosition(latlng);

		var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
		message += '경도는 ' + latlng.getLng() + ' 입니다';

		var resultDiv = document.getElementById('clickLatlng');
		resultDiv.innerHTML = message;

	});
</script>
<%@ include file="../common/foot.jspf"%>