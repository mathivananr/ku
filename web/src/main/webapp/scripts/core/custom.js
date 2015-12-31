/* ========================================================================= */
/*	Preloader
/* ========================================================================= */

jQuery(window).load(function() {
	$("#preloader").fadeOut("slow");
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

$('#loadMore').click(
		function(e) {
			var label = $("#label").val();
			var pageNo = $("#pageNo").val();
			var nextPageNo = parseInt(pageNo) + 1;
			$.ajax({
				type : "GET",
				url : "/get/offers",
				data : "label=" + label + "&pageNo=" + nextPageNo,
				success : function(response) {
					if(response.length > 0){
						$("#pageNo").val(nextPageNo);
						$("#offersList").append(response);
					} else {
						$("#loadOffers").html('<p align="center"><button id="noOffer" class="btn btn-default">No more offers available.</button></p>');
					}
					//console.log("success");
				},
				error : function(e) {
					//console.log('Error: ' + e);
				}
			});
});