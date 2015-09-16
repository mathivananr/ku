<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="offerList.title"/></title>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="offerList.heading"/></h2>

    <form method="get" action="${ctx}/admin/offers" id="searchForm" class="form-inline">
    <div id="search" class="text-right">
        <span class="col-sm-9">
            <input type="text" size="20" name="q" id="query" value="${param.q}"
                   placeholder="<fmt:message key="search.enterTerms"/>" class="form-control input-sm">
        </span>
        <button id="button.search" class="btn btn-default btn-sm" type="submit">
            <i class="icon-search"></i> <fmt:message key="button.search"/>
        </button>
    </div>
    </form>

    <div id="actions" class="btn-group">
        <a class="btn btn-primary" href="<c:url value='/add-coupon?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>

    <display:table name="offerList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="offers" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="offerId" escapeXml="true" sortable="true" titleKey="offers.offerId" style="width: 25%"
                        url="/edit-coupon?from=list" paramId="offerId" paramProperty="offerId"/>
        <display:column property="offerTitle" escapeXml="true" sortable="true" titleKey="offers.title"
                        style="width: 34%"/>
        <display:column property="description" escapeXml="true" sortable="true" titleKey="offers.description"
                        style="width: 34%"/>
        <display:column property="couponCode" escapeXml="true" sortable="true" titleKey="offers.code"
                        style="width: 34%"/>
		<display:column property="offerEnd" escapeXml="true" sortable="true" titleKey="offers.offerEnd"
                        style="width: 34%"/>
        <display:column property="description" escapeXml="true" sortable="true" titleKey="offers.description"
        style="width: 34%"/>
        <display:setProperty name="paging.banner.item_name"><fmt:message key="offerList.offer"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="offerList.offers"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="Offer List.xls"/>
        <display:setProperty name="export.csv.filename" value="Offer List.csv"/>
        <display:setProperty name="export.pdf.filename" value="Offer List.pdf"/>
    </display:table>
</div>
