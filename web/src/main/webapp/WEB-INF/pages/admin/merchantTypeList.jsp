<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="merchantTypeList.title"/></title>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="merchantTypeList.heading"/></h2>

    <form method="get" action="${ctx}/admin/merchantTypes" id="searchForm" class="form-inline">
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
        <a class="btn btn-primary" href="<c:url value='/admin/merchantType?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>

    <display:table name="merchantTypeList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="merchantTypes" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="typeCode" escapeXml="true" sortable="true" titleKey="merchantTypes.typeCode" style="width: 25%"
                        url="/admin/merchantType?from=list" paramId="id" paramProperty="typeId"/>
        <display:column property="typeName" escapeXml="true" sortable="true" titleKey="merchantTypes.typeName"
                        style="width: 34%"/>
        <display:column property="typeOrder" escapeXml="true" sortable="true" titleKey="merchantTypes.typeOrder"
                        style="width: 14%"/>
        <display:setProperty name="paging.banner.item_name"><fmt:message key="merchantTypeList.merchantType"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="merchantTypeList.merchantTypes"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="Merchant List.xls"/>
        <display:setProperty name="export.csv.filename" value="Merchant List.csv"/>
        <display:setProperty name="export.pdf.filename" value="Merchant List.pdf"/>
    </display:table>
</div>
