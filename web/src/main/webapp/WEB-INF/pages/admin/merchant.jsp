<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="merchant.title" /></title>
</head>

<div class="col-sm-2">
	<h2>
		<fmt:message key="merchant.heading" />
	</h2>
</div>
<div class="col-sm-7">
	<spring:bind path="merchant.*">
		<c:if test="${not empty status.errorMessages}">
			<div class="alert alert-danger alert-dismissable">
				<a href="#" data-dismiss="alert" class="close">&times;</a>
				<c:forEach var="error" items="${status.errorMessages}">
					<c:out value="${error}" escapeXml="false" />
					<br />
				</c:forEach>
			</div>
		</c:if>
	</spring:bind>

	<form:form commandName="merchant" method="post" enctype="multipart/form-data"
		action="/admin/saveMerchantDetails" id="merchantForm"
		autocomplete="off" cssClass="well">
		<form:hidden path="id" />
		<form:hidden path="logoPath" />
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="merchantName"
					id="merchantName" placeholder="Name"/>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<form:textarea cssClass="form-control" path="description"
					id="description" placeholder="Description"/>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<select id="merchantType" name="merchantType.id" class="form-control">
					<c:forEach items="${merchantTypes}" var="type">
	                	<option value="${type.value}" ${fn:contains(merchantType.id, type.value) ? 'selected' : ''}>${type.label}</option>
	                </c:forEach>
				</select>
			</div>
		</div>

		<div class="row">
			<div class="form-group">
				<input type="file" name="file" id="file" />
			</div>
		</div>

		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="targetLink"
					id="targetLink" placeholder="Target Link"/>
			</div>
		</div>

		<div class="row">
			<div class="form-group">
				<label class="checkbox-inline"> <form:checkbox
						path="enabled" id="enabled" /> <fmt:message
						key="merchant.enabled" />
				</label> <label class="checkbox-inline"> <form:checkbox
						path="topMerchant" id="topMerchant" /> <fmt:message
						key="merchant.topMerchant" />
				</label>
			</div>
		</div>

		<div class="row">
			<div class="form-group">
				<button type="submit" class="btn btn-primary" name="save"
					onclick="bCancel=false">
					<i class="icon-ok icon-white"></i>
					<fmt:message key="button.save" />
				</button>
				<button type="submit" class="btn btn-default" name="cancel"
					onclick="bCancel=true">
					<i class="icon-remove"></i>
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</form:form>
</div>