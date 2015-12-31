<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<!-- meta charec set -->
<meta charset="utf-8">
<!-- Always force latest IE rendering engine or request Chrome Frame -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<!-- Page Title -->
<c:choose>
	<c:when test="${not empty pageTitle}">
		<title>${pageTitle}</title>	
	</c:when>
	<c:otherwise>
		<title>${appTitle}</title>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty metaKeywords}">
		<meta name="keywords" content="${metaKeywords}">
	</c:when>
	<c:otherwise>
		<meta name="keywords" content="${appMetaKeyword}">
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty metaDescription}">
		<meta name="description" content="${metaDescription}">
	</c:when>
	<c:otherwise>
		<meta name="description" content="${appMetaDescription}">
	</c:otherwise>
</c:choose>


<meta property="og:title" content="${ogTitle}" />
<meta property="og:site_name" content="${appName}" />
<meta property="og:url" content="${ogURL}" />
<meta property="og:image" content="${appUrl}/${ogImage}" />
<meta property="og:description" content="${ogDescription}" />
<meta name="author" content="${auther}">
<!-- Mobile Specific Meta -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="/images/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/v/${applicationScope.assetsVersion}/ku-pre.css" />
<!-- Essential jQuery Plugins
================================================== -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/v/${applicationScope.assetsVersion}/ku-pre.js"></script>
</head>

<body id="body">
	<input type="hidden" id="active-menu" value="${activeMenu}">
	<!-- preloader -->
	<div id="preloader">
		<img src="/images/core/preloader.gif" alt="Preloader">
	</div>
	<!-- end preloader -->

	<!--         Fixed Navigation
        ==================================== -->
	<header id="navigation" class="navbar-fixed-top navbar">
		<div class="container">
			<div class="navbar-header">
				<!--     responsive nav button -->
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <i
						class="fa fa-bars fa-2x"></i>
				</button>
				<!-- responsive nav button -->

				<!-- logo -->
				<a class="navbar-brand" href="${applicationUrl}">
					<h1 id="logo">
						<img src="images/core/logo.png" alt="${applicationName}">
					</h1>
				</a>
				<!-- logo -->
			</div>

			<!-- main nav -->
			<nav class="collapse navbar-collapse navbar-right" role="navigation">
				<ul id="nav" class="nav navbar-nav">
					<li class="current"><a href="#body">Home</a></li>
					<li><a href="#features">Features</a></li>
					<li><a href="#works">Work</a></li>
					<li><a href="#team">Team</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
				<ul class="nav pull-right">
		          <li class="dropdown" id="menuLogin">
		            <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navLogin">Login</a>
		            <div class="dropdown-menu" style="padding:17px;">
		              <form class="form" id="formLogin"> 
		                <input name="username" id="username" type="text" placeholder="Username"> 
		                <input name="password" id="password" type="password" placeholder="Password"><br>
		                <button type="button" id="btnLogin" class="btn">Login</button>
		              </form>
		            </div>
		          </li>
		        </ul>
			</nav>
			<!-- main nav -->

		</div>
	</header>
	<!--        End Fixed Navigation
        ==================================== -->
	
	<!--        Main Content
        ==================================== -->
	<section id="works" class="works clearfix">
		<div class="project-wrapper">
			<div class="container">
				<%@ include file="/common/messages.jsp"%>
				<div class="row mb50" id="main-content">
					<decorator:body />
				</div>
			</div>
		</div>
	</section>
	<!--        Main Content
        ==================================== -->

	<!--         Footer
        ==================================== -->
	<footer id="footer" class="footer">
		<div class="container">
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="row">
					<div class="footer-single">
						<!-- <img src="images/core/footer-logo.png" alt=""> -->
						<h2>
							<a title="${applicationName} home" href="${applicationUrl}">${applicationName}</a>
						</h2>
					</div>
				</div>
			</div>
			<div class="col-md-9 col-sm-6 col-xs-12">
				<div class="row">
					<div class="text-center">
						<a title="about menu" href="${applicationUrl}/site/about">About</a>
						| <a title="contact menu" href="${applicationUrl}/site/contact">Contact</a>
						| <a title="terms and conditions menu"
							href="${applicationUrl}/site/termsAndConditions">Terms and
							Conditions</a> | <a title="privacy policy menu"
							href="${applicationUrl}/site/privacyPolicy">Privacy Policy</a> |
						<a title="faq menu" href="${applicationUrl}/site/faq">FAQ</a>
					</div>
				</div>
				<div class="row">
					<p class="text-center">
						Copyright <i class="fa fa-copyright"></i> 2015 <a
							title="copy rights" href="${applicationUrl}/">${applicationName}</a>. All
						rights reserved. Designed & developed by <a title="developed by"
							href="${applicationUrl}/">${applicationName}</a>
					</p>
				</div>
			</div>
		</div>
	</footer>
	<!--         End Footer
        ==================================== -->
        
	<a href="javascript:void(0);" id="back-top"><i
		class="fa fa-angle-up fa-3x"></i></a>
</body>
<!-- Essential jQuery Plugins
================================================== -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/v/${applicationScope.assetsVersion}/ku-post.js"></script>
</html>
