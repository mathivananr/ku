<%@ include file="/common/taglibs.jsp"%>

<head>
<title>Create label for offer</title>
</head>

<div class="col-sm-2">
	<h2>
		Label
	</h2>
</div>
<div class="col-sm-7">
	<spring:bind path="offerLabel.*">
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

	<form:form commandName="offerLabel" method="post" enctype="multipart/form-data"
		action="/add-label" id="offerLabelForm"
		autocomplete="off" cssClass="well">
		<form:hidden path="labelId" />
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="label"
					id="label" placeholder="Label"/>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<form:textarea cssClass="form-control" path="metaKeyword"
					id="metaKeyword" placeholder="Meta Keywords"/>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<form:textarea cssClass="form-control" path="metaDescription"
					id="metaDescription" placeholder="Meta Description"/>
			</div>
		</div>
		<div class="form-group">
			<label class="checkbox-inline">
	             <form:checkbox path="isMerchant" id="isMerchant"/>
	             Is Merchant
	        </label>
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