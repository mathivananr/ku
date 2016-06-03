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
	href="${pageContext.request.contextPath}/assets/v/${applicationScope.assetsVersion}/ku.css" />
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/core/main.css" />
	
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/core/typeahead.css" />
<!-- Essential jQuery Plugins
================================================== -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/v/${applicationScope.assetsVersion}/ku.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/core/custom.js"></script>
	<!-- <script type='text/javascript' src="http://twitter.github.io/typeahead.js/releases/latest/typeahead.bundle.js"></script> -->
</head>

<body id="body">
	<input type="hidden" id="active-menu" value="${activeMenu}" />
	<input type="hidden" id="scrollReached" value="false" />
	<!-- preloader -->
	<!-- <div id="preloader">
		<img src="/images/core/preloader.gif" alt="Preloader">
	</div> -->
	<!-- end preloader -->

	<!--         Fixed Navigation
        ==================================== -->
    
	<header id="navigation" class="navbar">
		<!-- <div class="container"> -->
		
		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
			<div class="search-row">
				<a class="navbar-brand" href="${applicationUrl}">
					
				</a>
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-5 col-xs-8 ">
			<div class="dtable hw100">
				<div class="dtable-cell hw100">
					<div class="text-center">
						<div class="row search-row animated fadeInUp">
							<div class="col-lg-10 col-md-10 col-sm-10 col-xs-9 search-col relative">
								<i class="icon-docs icon-append"></i> <input type="text"
									name="search" id="search" class="form-control has-icon"
									placeholder="Search">
							</div>
							<div class="col-lg-2 col-md-2 col-sm-2 col-xs-3 search-col">
								<button class="btn btn-primary btn-search btn-block">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-lg-3 col-md-3 col-sm-4 col-xs-10 hidden-xs">
			<nav class="collapse navbar-collapse navbar-right search-row" role="navigation">
				<ul id="nav" class="nav navbar-nav">
					<li class="text-center">
						<a href="/chat">
							<i class="fa fa-comment" aria-hidden="true"></i>
							<p>Chat</p>
						</a>
					</li>
					<li class="text-center">
						<a href="/offers">
							<i class="fa fa-refresh" aria-hidden="true"></i>
							<p>Offers</p>
						</a>
					</li>
					<li class="text-center dropdown">
						<a href="#account" class="dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-user" aria-hidden="true"></i>
							<i class="fa fa-sort-desc" aria-hidden="true"></i>
							<p>Account</p>
						</a>
                        <ul class="dropdown-menu">
							<security:authentication var="user" property="principal" />
							<c:choose>
								<c:when test="${user != null && user != 'anonymousUser'}">
									<li>
										<div class="text-center">
											<span>Welcome <b>${user.username}</b></span>
										</div>
									</li>
									<li>
										<div class="text-center">
											<a class="btn btn-default" href="/logout">Logout</a>
										</div>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<div class="text-center">
											<a id="login-link" class="btn btn-default" data-toggle="modal"
												data-target="#myModal" href="#myModal">Login</a>
										</div>
									</li>
									<li><div class="account-signup">
											New User? <a data-toggle="modal"
												data-target="#myModal" href="#myModal">Register</a>
										</div>
								</c:otherwise>
							</c:choose>
						</ul>
                    </li>
				</ul>
			</nav>
		</div>
		
		<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <div class="modal-content">
          <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
              <h4 class="modal-title" id="myModalLabel">Login</h4>
          </div>
          <div class="modal-body">
              <div class="row">
                  <div class="col-xs-6">
                      <div class="well">
                          <form id="loginForm" novalidate="novalidate">
                              <div class="form-group">
                                  <label for="username" class="control-label">Username</label>
                                  <input type="text" class="form-control" id="j_username" name="j_username" value="" required="" title="Please enter you username" placeholder="example@gmail.com">
                                  <span class="help-block"></span>
                              </div>
                              <div class="form-group">
                                  <label for="password" class="control-label">Password</label>
                                  <input type="password" class="form-control" id="j_password" name="j_password" value="" required="" title="Please enter your password">
                                  <span class="help-block"></span>
                              </div>
                              <div id="loginErrorMsg" class="alert alert-error hide">Wrong username og password</div>
                              <button type="button" onclick="ajaxLogin();" class="btn btn-success btn-block">Login</button>
                          </form>
                      </div>
                  </div>
                  <div class="col-xs-6">
                  		<div class="well">
                          <form id="signupForm" method="post" action="signup" novalidate="novalidate">
                              <div class="form-group">
                                  <label for="username" class="control-label">Username</label>
                                  <input type="text" class="form-control" id="username" name="username" value="" required="" title="Please enter you username" placeholder="username">
                                  <span class="help-block"></span>
                              </div>
                              
                              <div class="form-group">
                                  <label for="password" class="control-label">Password</label>
                                  <input type="password" class="form-control" id="password" name="password" value="" required="" title="Please enter your password" placeholder="password">
                                  <span class="help-block"></span>
                              </div>
                              
                              <div class="form-group">
                                  <label for="email" class="control-label">Email</label>
                                  <input type="text" class="form-control" id="email" name="email" value="" required="" title="Please enter you email" placeholder="example@gmail.com">
                                  <span class="help-block"></span>
                              </div>
                              <div id="loginErrorMsg" class="alert alert-error hide">Wrong username og password</div>
                              <button type="submit" class="btn btn-danger btn-block">Signup</button>
                          </form>
                      </div>
                  </div>
              </div>
          </div>
      </div>

  </div>
</div>
  
		
		<div class="col-lg-1 col-md-1 col-sm-1 col-xs-2">
			<nav class='sidebar sidebar-menu-collapsed'> 
				<a href='javascript void(0);' id='justify-icon'>
        			<span class='fa fa-bars pull-right'></span>
     			</a>
				<div id="side-menu" class="side-menu hide">
			        <ul class='level1'>
			            <li class='parent-menu'> 
			            	<a class='' href='#' title='Dashboard'>
					            <span class='glyphicon glyphicon-home collapsed-element'></span>
					            <span class='expanded-element'>Dashboard</span>
					        </a>
			
			                <ul class='sub-menu' style="display:none;">
			                    <li> <a href='#' title='Traffic'>Traffic</a>
			
			                    </li>
			                    <li> <a href='#' title='Conversion rate'>Conversion rate</a>
			
			                    </li>
			                    <li> <a href='#' title='Purchases'>Purchases</a>
			
			                    </li>
			                </ul>
			            </li>
			            <li> <a class='expandable' href='#' title='APIs'>
			            <span class='glyphicon glyphicon-wrench collapsed-element'></span>
			            <span class='expanded-element'>APIs</span>
			          </a>
			
			            </li>
			            <li> <a class='expandable' href='#' title='Settings'>
			            <span class='glyphicon glyphicon-cog collapsed-element'></span>
			            <span class='expanded-element'>Settings</span>
			          </a>
			
			            </li>
			            <li> <a class='expandable' href='#' title='Account'>
			            <span class='glyphicon glyphicon-user collapsed-element'></span>
			            <span class='expanded-element'>Account</span>
			          </a>
			            </li>
			        </ul> 
			        <a href='#' id='logout-icon' title='Logout'>
	        			<span class='glyphicon glyphicon-off'></span>
	      			</a>
      			</div>
    		</nav>
			<%-- <a class="navbar-brand" href="${applicationUrl}">
					<h1 id="logo">
						<img src="images/core/logo.png" alt="${applicationName}">
						LOGO
					</h1>
				</a> --%>
			<%-- <div class="navbar-header">
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
			</div> --%>
			<!-- main nav -->
			<!-- <nav class="collapse navbar-collapse navbar-right" role="navigation">
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
			</nav> -->
			<!-- main nav -->

		</div>
		<!-- <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div id="the-basics">
				  <input class="typeahead" id="search" type="text" placeholder="Search">
				</div>
			</div>
		</div> -->
		<!-- <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
			<div class="dtable hw100">
				<div class="dtable-cell hw100">
					<div class="row search-row">
						<a href="/user/createPage" class="btn btn-danger pull-right">Create Page</a>
					</div>
				</div>
			</div>
			<ul id="nav" class="nav navbar-nav">
					<li class="current"><a href="#body">Home</a></li>
					<li><a href="#features">Features</a></li>
					<li><a href="#works">Work</a></li>
					<li><a href="#team">Team</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
		</div> -->
	</header>

	<!--        End Fixed Navigation
        ==================================== -->
	
	<!--        Main Content
        ==================================== -->
    
	<section id="works" class="works clearfix">
		<div class="project-wrapper">
			<div class="container" >
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
<script type="text/javascript">

	// Instantiate the Bloodhound suggestion engine
	var labeldata = new Bloodhound({
	    datumTokenizer: function (datum) {
	        return Bloodhound.tokenizers.whitespace(datum.value);
	    },
	    queryTokenizer: Bloodhound.tokenizers.whitespace,
	    remote: {
	        url: '/get/searchSuggest?query=%QUERY',
	        wildcard: '%QUERY',
	        filter: function (data) {
	            // Map the remote source JSON array to a JavaScript object array
	            var labels = JSON.parse(JSON.stringify(data));
	            return $.map(labels, function (label) {
	                return {
	                    value: label
	                };
	            });
	        }
	    }
	});

	// Initialize the Bloodhound suggestion engine
	labeldata.initialize();

	// Instantiate the Typeahead UI
	$('#search').typeahead(null, {
	    displayKey: 'value',
	    source: labeldata.ttAdapter()
	}).on('typeahead:selected', function (obj, datum) {
		//console.log(datum);
		//alert(window.location.origin);
		var link = window.location.origin+"/"+datum.value+"/";
		window.location.href = link;
		/* $.ajax({
			type : "GET",
			url : "/get/offers",
			data : "label=" + datum.value + "&pageNo=" + 1,
			success : function(response) {
				if(response.length > 0){
					$("#pageNo").val(1);
					$("#offersList").html(response);
					$('#loadMore').attr('href',datum.value+'/#!/'+2);
					
				} else {
					$("#offersList").html("");
					$("#loadOffers").html('<p align="center"><button id="noOffer" class="btn btn-default">No more offers available.</button></p>');
				}
				//console.log("success");
			},
			error : function(e) {
				//console.log('Error: ' + e);
			}
		}); */
	});
	
</script>
<!-- Essential jQuery Plugins
================================================== -->
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/v/${applicationScope.assetsVersion}/ku-post.js"></script> --%>
</html>
