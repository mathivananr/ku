/* ========================================================================= */
/*	Preloader
/* ========================================================================= */

jQuery(window).load(function() {
	$("#preloader").fadeOut("slow");
});

$(window).scroll(function(){
	if(!$("#navigation").hasClass("navbar-fixed-top")){
		$("#navigation").addClass("navbar-fixed-top");
	}
	
	if($(window).scrollTop() == 0) {
		$("#navigation").removeClass("navbar-fixed-top");
	}
	
	if($(window).scrollTop() >= $('#offers-container').offset().top + $('#offers-container').outerHeight() - window.innerHeight && $("#scrollReached").val() == "false") {
        $("#scrollReached").val("true");
        loadMoreOffers();
    }
	
});

$(window).on('hashchange', function() {
	var target = document.location.hash.replace("#", "");
	if(target== 'login') {
		$('#myModal').modal('show');
	}
});

(function () {
    $(function () {
    	
    	/** 
    	 * log user actions by read user link clicks 
    	 */
    	$('a').click(
    			function(e) {
    				var link = $(this).attr('href');
    				var title = $(this).attr('title');
    				if (title != undefined && title != null && title != ''
    						&& link != undefined && link != null && link != '') {
    					$.ajax({
    						type : "GET",
    						url : "/log",
    						data : "title=" + title + "&link=" + link,
    						success : function(response) {
    							//console.log("success");
    						},
    						error : function(e) {
    							//console.log('Error: ' + e);
    						}
    					});
    			}
    	});
    	
    	var target = document.location.hash.replace("#", "");
    	if(target== 'login') {
    		$('#myModal').modal('show');
    	}
        var SideBAR;
        SideBAR = (function () {
            function SideBAR() {}

            SideBAR.prototype.expandMyMenu = function () {
            	$("#side-menu").removeClass("hide");
            	$("nav.sidebar").addClass("side-menu");
                return $("nav.sidebar").removeClass("sidebar-menu-collapsed").addClass("sidebar-menu-expanded");
            };

            SideBAR.prototype.collapseMyMenu = function () {
            	$("#side-menu").addClass("hide");
            	$("nav.sidebar").removeClass("side-menu");
                return $("nav.sidebar").removeClass("sidebar-menu-expanded").addClass("sidebar-menu-collapsed");
            };

            SideBAR.prototype.showMenuTexts = function () {
                return $("nav.sidebar ul a span.expanded-element").show();
            };

            SideBAR.prototype.hideMenuTexts = function () {
                return $("nav.sidebar ul a span.expanded-element").hide();
            };

            SideBAR.prototype.showActiveSubMenu = function () {
                $("li.active ul.level2").show();
                return $("li.active a.expandable").css({
                    width: "100%"
                });
            };

            SideBAR.prototype.hideActiveSubMenu = function () {
                return $("li.active ul.level2").hide();
            };

            SideBAR.prototype.adjustPaddingOnExpand = function () {
                $("ul.level1 li a.expandable").css({
                    padding: "1px 4px 4px 0px"
                });
                return $("ul.level1 li.active a.expandable").css({
                    padding: "1px 4px 4px 4px"
                });
            };

            SideBAR.prototype.resetOriginalPaddingOnCollapse = function () {
                $("ul.nbs-level1 li a.expandable").css({
                    padding: "4px 4px 4px 0px"
                });
                return $("ul.level1 li.active a.expandable").css({
                    padding: "4px"
                });
            };

            SideBAR.prototype.ignite = function () {
                return (function (instance) {
                    return $("#justify-icon").click(function (e) {
                        if ($(this).parent("nav.sidebar").hasClass("sidebar-menu-collapsed")) {
                            instance.adjustPaddingOnExpand();
                            instance.expandMyMenu();
                            instance.showMenuTexts();
                            instance.showActiveSubMenu();
                            $(this).css({
                                color: "#FFF"
                            });
                        } else if ($(this).parent("nav.sidebar").hasClass("sidebar-menu-expanded")) {
                            instance.resetOriginalPaddingOnCollapse();
                            instance.collapseMyMenu();
                            instance.hideMenuTexts();
                            instance.hideActiveSubMenu();
                            $(this).css({
                                color: "#0EB493"
                            });
                        }
                        return false;
                    });
                })(this);
            };

            return SideBAR;

        })();
        return (new SideBAR).ignite();
    });

}).call(this);

$(window).bind("load", function() {
    $.each(document.images, function(){
               var this_image = this;
               var src = $(this_image).attr('src') || '' ;
               if(!src.length > 0){
                   //this_image.src = options.loading; // show loading
                   var lsrc = $(this_image).attr('data-src') || '' ;
                   if(lsrc.length > 0){
                       var img = new Image();
                       img.src = lsrc;
                       $(img).load(function() {
                           this_image.src = this.src;
                       });
                   }
               }
           });
  });

/*$(document)
		.ready(
				function() {

					 ========================================================================= 
					
					 * Menu item highlighting /*
					 * =========================================================================
					 

					jQuery('#nav').singlePageNav({
						offset : jQuery('#nav').outerHeight(),
						filter : ':not(.external)',
						speed : 1200,
						currentClass : 'current',
						easing : 'easeInOutExpo',
						updateHash : true,
						beforeStart : function() {
							console.log('begin scrolling');
						},
						onComplete : function() {
							console.log('done scrolling');
						}
					});

					
					 * $(window).scroll(function () { if ($(window).scrollTop() >
					 * 400) {
					 * $("#navigation").css("background-color","#0EB493"); }
					 * else { $("#navigation").css("background-color","rgba(16,
					 * 22, 54, 0.2)"); } });
					 

					 ========================================================================= 
					
					 * Fix Slider Height /*
					 * =========================================================================
					 

					var slideHeight = $(window).height();

					$(
							'#slider, .carousel.slide, .carousel-inner, .carousel-inner .item')
							.css('height', slideHeight);

					$(window)
							.resize(
									function() {
												'use strict',
												$(
														'#slider, .carousel.slide, .carousel-inner, .carousel-inner .item')
														.css('height',
																slideHeight);
									});

					 ========================================================================= 
					
					 * Portfolio Filtering /*
					 * =========================================================================
					 

					// portfolio filtering
					$(".project-wrapper").mixItUp();

					$(".fancybox").fancybox({
						padding : 0,

						openEffect : 'elastic',
						openSpeed : 650,

						closeEffect : 'elastic',
						closeSpeed : 550,

						closeClick : true,
					});

					 ========================================================================= 
					
					 * Parallax /*
					 * =========================================================================
					 

					$('#facts').parallax("50%", 0.3);

					 ========================================================================= 
					
					 * Timer count /*
					 * =========================================================================
					 

					"use strict";
					$(".number-counters").appear(function() {
						$(".number-counters [data-to]").each(function() {
							var e = $(this).attr("data-to");
							$(this).delay(6e3).countTo({
								from : 50,
								to : e,
								speed : 3e3,
								refreshInterval : 50
							})
						})
					});

					 ========================================================================= 
					
					 * Back to Top /*
					 * =========================================================================
					 

					$(window).scroll(function() {
						if ($(window).scrollTop() > 400) {
							$("#back-top").fadeIn(200)
						} else {
							$("#back-top").fadeOut(200)
						}
					});
					$("#back-top").click(function() {
						$("html, body").stop().animate({
							scrollTop : 0
						}, 1500, "easeInOutExpo")
					});

					$('#clock').countdown('2015/5/10', function(event) {
						// $(this).html(event.strftime('%D days left
						// %H:%M:%S'));
					});

					 ========================================================================= 
					
					 * Active Menu item highlighting /*
					 * =========================================================================
					 
					$('#' + $('#active-menu').val()).addClass('active');
					*//***********************************************************
					 * Activate Pop over
					 *//*
					$('[data-toggle="popover"]').popover({
						trigger : 'hover'
					})
				});*/


function loadMoreOffers() {
	var label = $("#label").val();
	var pageNo = $("#pageNo").val();
	var nextPageNo = 2;
	if(pageNo > 2){
		nextPageNo = parseInt(pageNo) + 1;
	}
	$.ajax({
		type : "GET",
		url : "/get/offers",
		data : "label=" + label + "&pageNo=" + nextPageNo,
		success : function(response) {
			if(response.length > 0){
				nextPageNo = parseInt(pageNo) + 1;
				$("#pageNo").val(nextPageNo);
				$("#offersList").append(response);
				$('#loadMore').attr('href','#!/'+nextPageNo);
				$("#scrollReached").val("false");
			} else {
				$("#loadOffers").html('<p align="center"><button id="noOffer" class="btn btn-default">No more offers available.</button></p>');
			}
			//console.log("success");
		},
		error : function(e) {
			//console.log('Error: ' + e);
		}
	});
}

$('#loadMore').click(
		function(e) {
			loadMoreOffers();
});

function ajaxLogin(){
    
    var user_pass = $("#j_password").val();
    var user_name = $("#j_username").val(); 
 
//Ajax login - we send credentials to j_spring_security_check (as in form based login
    $.ajax({
          url: "/ajaxLogin",    
          data: { j_username: user_name , j_password: user_pass }, 
          type: "POST",
          beforeSend: function (xhr) {
             xhr.setRequestHeader("X-Ajax-call", "true");
          },
          success: function(result) {   
        	  $('#myModal').modal('hide');
          //if login is success, hide the login modal and
          //re-execute the function which called the protected resource
          //(#7 in the diagram flow)
         /* if (result == "ok") {
  
            $("#ajax_login_error_"+ suffix).html("");            
            $('#ajaxLogin').hide();
             
            return true;
          }else {           
             
            $("#ajax_login_error_"+ suffix).html('<span  class="alert display_b clear_b centeralign">Bad user/password</span>') ;
            return false;           
        }*/
    },
    error: function(XMLHttpRequest, textStatus, errorThrown){
        $("#ajax_login_error_"+ suffix).html("Bad user/password") ;
        return false; 
    }
});
}