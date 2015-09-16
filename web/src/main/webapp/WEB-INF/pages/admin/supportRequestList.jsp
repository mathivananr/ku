	<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="supportRequestList.title"/></title>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="supportRequestList.heading"/></h2>

    <form method="get" action="${ctx}/admin/supportRequests" id="searchForm" class="form-inline">
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
        <a class="btn btn-primary" href="<c:url value='/admin/supportRequest?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>

    <display:table name="supportRequestList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="supportRequests" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="id" escapeXml="true" sortable="true" titleKey="supportRequests.supportRequestId" style="width: 10%" url="/admin/supportRequest?from=list" paramId="id" paramProperty="id"/>
        <display:column property="email" escapeXml="true" sortable="true" titleKey="supportRequests.email"
        style="width: 15%"/>
        <display:column property="rechargeId" escapeXml="true" sortable="true" titleKey="supportRequests.orderId"
        style="width: 10%"/>
        <display:column property="description" escapeXml="true" sortable="true" titleKey="supportRequests.description"
        style="width: 15%"/>
        <display:column property="status" escapeXml="true" sortable="true" titleKey="supportRequests.status"
        style="width: 10%"/>
        <display:column sortProperty="status" sortable="true" titleKey="supportRequest.closed"
                        style="width: 16%; padding-left: 15px" media="html">
            <input type="checkbox" onclick="closeSupportRequest(${supportRequests.id});" id="supportRequestDone-${supportRequests.id}" data-id="${supportRequests.id}" <c:if test="${supportRequests.status == 'close'}">disabled="disabled"</c:if>  <c:if test="${supportRequests.status == 'close'}">checked="checked"</c:if>/>
        </display:column>

        <display:setProperty name="paging.banner.item_name"><fmt:message key="supportRequestList.supportRequest"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="supportRequestList.supportRequests"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="SupportRequest List.xls"/>
        <display:setProperty name="export.csv.filename" value="SupportRequest List.csv"/>
        <display:setProperty name="export.pdf.filename" value="SupportRequest List.pdf"/>
    </display:table>
</div>
