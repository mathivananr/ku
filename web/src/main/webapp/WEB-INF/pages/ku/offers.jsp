<%@ include file="/common/taglibs.jsp"%>

<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="well">
		<div class="row row-nomargin">
			<h3><p class="theme-color">Offers</p></h3>
		</div>
	</div>
	<div class="well">
		<c:forEach var="offer" items="${offers}">
			<div class="well">
				<div class="row row-nomargin">
					<div id="offer-left" class="col-lg-2 col-md-2 col-sm-3 col-xs-12">
						<div id="offer-image" class="offer-image">
							<c:choose>
							  <c:when test="${not empty offer.imagePath}">
							    <div class="text-center"><a href="${offer.targetURL}" ><img class="col-lg-12 col-md-12 col-sm-10 col-xs-10" alt="${offer.merchantName}" src="${offer.imagePath}"></a></div>
							  </c:when>
							  <c:when test="${not empty offer.merchantLogoPath}">
							    <div class="text-center"><a href="${offer.targetURL}" ><img class="col-lg-12 col-md-12 col-sm-10 col-xs-10" alt="${offer.merchantName}" src="${offer.merchantLogoPath}"></a></div>
							  </c:when>
							  <c:otherwise>
							    <div class="text-center"><p class="col-lg-10 col-md-10 col-sm-10 col-xs-10 offer-image-merchant-name">${offer.merchantName}</p></div>
							  </c:otherwise>
						  </c:choose>
						 </div>
					</div>
					<div id="offer-center" class="col-lg-7 col-md-7 col-sm-6 col-xs-12">
						<div id="offer-title" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 margin-bottom10"><h4><strong>${offer.offerTitle}</strong></h4></div>
						<div id="offer-body" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<p>${offer.description}</p>
						</div>
					</div>
					<div id="offer-right" class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
							<div class="text-center"><p class="col-lg-10 col-md-10 col-sm-10 col-xs-10 offer-hint">${offer.offerHint}</p></div>
							<div class="text-center"><p class="col-md-10 col-sm-10 col-xs-10"><a href="${offer.targetURL}" class="btn btn-ku" target="_blank">VISIT OFFER</a></p></div>
							<c:choose> 
								 <c:when test="${not empty offer.couponCode}">
								   <div class="text-center"><p class="col-lg-10 col-md-10 col-sm-10 col-xs-10 offer-coupon-code">Code : ${offer.couponCode}</p></div>
								 </c:when>
								 <c:otherwise>
								   <div class="text-center"><p class="col-lg-10 col-md-10 col-sm-10 col-xs-10 offer-coupon-code">Deal Active</p></div>
								 </c:otherwise>
							</c:choose>
							<div class="text-center">
								<p class="col-lg-10 col-md-10 col-sm-10 col-xs-10 offer-valid">
									End : <c:choose>
											  <c:when test="${not empty offer.offerEnd}">
											    <fmt:formatDate value="${offer.offerEnd.time}" type="date"/>
											  </c:when>
											  <c:otherwise>
											    Until Stock
											  </c:otherwise>
										  </c:choose>
								</p>
							</div>
					</div>
					<hr/>
					<div id="offer-footer" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"></div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<c:forEach var="offerLabel" items="${offer.labels}">
									<span><a href="${fn:replace(offerLabel.label, ' ', '-')}" title="${offerLabel.label}" class="label label-info text-capitalize">${offerLabel.label}</a></span>
								</c:forEach>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"></div>
					</div>
				</div>
			</div>
		</c:forEach>
				
		<div class="ajax-load-more lazyLoader">
			<div class="btn-load-more btn-loadmore" data-pageno="1">
				<p>Load More Offers</p>
			</div>
		</div>
	</div>
</div>