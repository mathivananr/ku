<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="merchantType.title" /></title>
</head>

<div class="col-sm-2">
	<h2>
		<fmt:message key="merchantType.heading" />
	</h2>
</div>
<div class="col-sm-7">
	<spring:bind path="merchantType.*">
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

	<form:form commandName="merchantType" method="post" enctype="multipart/form-data"
		action="/admin/saveMerchantType" id="merchantTypeForm"
		autocomplete="off" cssClass="well">
		<form:hidden path="id" />
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="typeCode"
					id="typeCode" placeholder="Code"/>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="typeName"
					id="typeName" placeholder="Name"/>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="typeOrder"
					id="typeOrder" placeholder="Order"/>
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