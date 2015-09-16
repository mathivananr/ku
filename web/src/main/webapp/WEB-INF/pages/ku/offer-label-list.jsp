<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>List Offer Labels</title>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2>Offer Labels</h2>

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
        <a class="btn btn-primary" href="<c:url value='/add-label?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>

    <display:table name="offerLabelList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="labels" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="labelId" escapeXml="true" sortable="true" titleKey="Label Id" style="width: 25%"
                        url="/edit-label?from=list" paramId="labelId" paramProperty="labelId"/>
        <display:column property="label" escapeXml="true" sortable="true" titleKey="label"
                        style="width: 34%"/>
        <display:column property="metaDescription" escapeXml="true" sortable="true" titleKey="Meta Description"
                        style="width: 34%"/>
        <display:column property="metaKeyword" escapeXml="true" sortable="true" titleKey="Meta Keywords"
                        style="width: 34%"/>
        <display:column property="isMerchant" titleKey="Is Merchant" sortable= "true"/>
		<display:setProperty name="paging.banner.item_name"><fmt:message key="offerLabelList.labels"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="offerLabelList.labels"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="Offer List.xls"/>
        <display:setProperty name="export.csv.filename" value="Offer List.csv"/>
        <display:setProperty name="export.pdf.filename" value="Offer List.pdf"/>
    </display:table>
</div>
