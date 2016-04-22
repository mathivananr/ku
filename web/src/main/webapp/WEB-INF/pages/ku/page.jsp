<%@ include file="/common/taglibs.jsp"%>

<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="col-lg-offset-4 col-md-offset-4">
		<p>Click your favorite site and continue your successful shopping.</p>
	</div>
	<%-- <c:forEach var="merchantType" items="${merchantTypeList}">
		<div class="row">
			<div class="page-header">
				<h3>${merchantType.typeName}</h3>
			</div>
			<c:forEach var="merchant" items="${merchantType.merchants}">
				<div class="col-lg-2 col-md-2 col-sm-3 col-xs-4">
					<div class="thumbnail">
						<a title="${merchant.description}" href="${merchant.targetLink}"><img
							src="${merchant.logoPath}" alt="click ${merchant.merchantName} and start your shopping"
							width="100%" class="slider-store-image"> </a>
					</div>
				</div>
			</c:forEach>
		</div>
		<br />
	</c:forEach> --%>
	<%-- <div class="col-md-4 col-sm-5">
		<div class="tabs-left">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#all" data-toggle="tab">All</a></li>
				<c:forEach var="merchantType" items="${merchantTypeList}">
					<li><a href="#${merchantType.id}" data-toggle="tab"></a></li>
				</c:forEach>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="all"></div>
				<c:forEach var="merchantType" items="${merchantTypeList}">
					<div class="tab-pane" id="${merchantType.id}"></div>
				</c:forEach>
			</div>
			<!-- /tab-content -->
		</div>
		<!-- /tabbable -->
	</div> --%>
	<!-- /col -->




	<!-- tabs left -->
	<div class="row">
		<div class="tabbable tabs-left col-lg-3 col-md-3 col-sm-4 col-xs-4 nopadding">
			<ul class="nav nav-tabs sec-sub-title">
				<li class="active col-lg-12 col-md-12 col-sm-12 col-xs-12 nopadding"><a class="link-color"
					href="#all" data-toggle="tab"><p>All</p></a></li>
				<c:forEach var="merchantType" items="${merchantTypeList}">
					<li class="col-lg-12 col-md-12 col-sm-12 col-xs-12 nopadding"><a class="link-color"
						href="#${merchantType.typeId}" data-toggle="tab"><p>${merchantType.typeName}</p></a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="tab-content col-lg-9 col-md-9 col-sm-8 col-xs-8 nopadding">
			<div class="tab-pane active col-lg-12 col-md-12 col-sm-12 col-xs-12" id="all">
				<c:forEach var="merchantType" items="${merchantTypeList}">
					<div class="row">
						<div class="page-header">
							<h3>${merchantType.typeName}</h3>
						</div>
						<c:forEach var="merchant" items="${merchantType.merchants}">
							<div class="col-lg-2 col-md-2 col-sm-4 col-xs-6">
								<div class="thumbnail">
									<a title="click ${merchant.merchantName} and start your shopping" href="${merchant.targetLink}"><img
										src="${merchant.logoPath}" alt="${merchant.description}"
										width="100%" class="slider-store-image"> </a>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
			<c:forEach var="merchantType" items="${merchantTypeList}">
				<div class="tab-pane col-lg-12 col-md-12 col-sm-12 col-xs-12" id="${merchantType.typeId}">
					<div class="page-header">
						<h3>${merchantType.typeName}</h3>
					</div>
					<c:forEach var="merchant" items="${merchantType.merchants}">
						<div class="col-lg-2 col-md-2 col-sm-4 col-xs-6">
							<div class="thumbnail">
								<a title="click ${merchant.merchantName} and start your shopping" href="${merchant.targetLink}"><img
									src="${merchant.logoPath}" alt="${merchant.description}"
									width="100%" class="slider-store-image"> </a>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
	
			<%-- <c:forEach var="merchantType" items="${merchantTypeList}">
				<div class="tab-pane" id="${merchantType.typeId}"></div>
			</c:forEach> --%>
		</div>
	</div>
	<!-- /tabs -->


</div>