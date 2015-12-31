<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="coupon.title" /></title>
</head>

<div class="col-sm-2">
	<h2>
		<fmt:message key="coupon.heading" />
	</h2>
</div>
<div class="col-sm-7">
	<spring:bind path="offer.*">
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

	<form:form commandName="offer" method="post" enctype="multipart/form-data"
		action="/user/add-coupon" id="offerForm"
		autocomplete="off" cssClass="well">
		<form:hidden path="offerId" />
		<form:hidden path="offerHint"/>
		<form:hidden path="imagePath"/>
		<form:hidden path="merchantLogoPath"/>
		<form:hidden path="source"/>
		<form:hidden path="seoKeyword"/>
		<form:hidden path="userCount"/>
		<form:hidden path="createdOn"/>
		<form:hidden path="createdBy"/>
		<form:hidden path="enabled"/>
		<form:hidden path="expired"/>
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="offerTitle"
					id="offerTitle" placeholder="Title"/>
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
				<form:input cssClass="form-control" path="merchantName"
					id="merchantName" placeholder="Merchant"/>
			</div>
		</div>
		
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="couponCode"
					id="copounCode" placeholder="Coupon Code"/>
			</div>
		</div>
		
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="offerStart"
					id="offerStart" placeholder="Start"/>
			</div>
		</div>

		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="offerEnd"
					id="offerEnd" placeholder="End"/>
			</div>
		</div>

		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="targetURL"
					id="targetURL" placeholder="URL"/>
			</div>
		</div>
				
		<div class="row">
			<div class="form-group">
				<form:input cssClass="form-control" path="labelsString"
					id="labelsString" placeholder="Labels"/>
			</div>
		</div>
		
		<div class="row">
			<div class="form-group">
				<button type="submit" class="btn btn-primary" name="save"
					onclick="bCancel=false">
					<i class="icon-ok icon-white"></i>
					<fmt:message key="button.save" />
				</button>
				<button type="button" class="btn btn-default" name="cancel"
					onclick="bCancel=true">
					<i class="icon-remove"></i>
					<fmt:message key="button.cancel" />
				</button>
			</div>
		</div>
	</form:form>
</div>

<script type="text/javascript">
    $(function () {
        $('#offerEnd').datetimepicker({
        	format: 'MM/DD/YYYY'
        });
        $('#offerStart').datetimepicker({
        	format: 'MM/DD/YYYY'
        });
    });
</script>
