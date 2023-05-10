<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Home" />
<%@ include file="../common/head.jspf"%>

<div class="popup text-2xl">Popup Test</div>
<div class="layer-bg"></div>
<div class="layer">
	<div class="flex justify-between">
		<div class="text-2xl">Popup Test</div>
		<div class="close-btn">
			<div></div>
			<div></div>
		</div>
	</div>
	<div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Doloremque atque corrupti incidunt id expedita impedit alias et obcaecati illum optio cupiditate distinctio. Ipsum voluptas aspernatur alias nihil nobis molestias distinctio. </div>
</div>
<%@ include file="../common/foot.jspf"%>