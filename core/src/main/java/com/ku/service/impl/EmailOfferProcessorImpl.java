package com.ku.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.servlet.ServletContext;

import org.apache.commons.lang.math.NumberUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.model.Crawl;
import com.ku.model.Node;
import com.ku.model.Offer;
import com.ku.service.EmailOfferProcessor;
import com.ku.service.OfferManager;
import com.ku.util.ApiUtil;
import com.ku.util.StringUtil;

@Service("emailOfferProcessor")
public class EmailOfferProcessorImpl implements EmailOfferProcessor {

	private OfferManager offerManager;
	
	private Node<String> nextNode;
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	@SuppressWarnings("deprecation")
	public boolean processPayoomOffers() throws KUException, ParseException {
		List<Offer> offers = new ArrayList<Offer>();
		for (Map<String, String> offerMap : getOffers()) {
			if (offerMap.get("L.P") != null && (offerMap.get("L.P").contains("docs.google")
					|| (offerMap.get("L.P").contains("drive.google") || offerMap
							.get("L.P").contains("dropbox.com")))) {
				continue;
			}
			Offer offer = new Offer();
			
			offer.setMerchantName(offerMap.get("merchant"));
			offerMap.remove("merchant");
			//System.out.println("   Offer   "+ offerMap.get("Offer"));
			
			if (!StringUtil.isEmptyString(offerMap.get("Offer"))) {
				offer.setOfferTitle(offerMap.get("Offer"));
				offerMap.remove("Offer");
			} else if(!StringUtil.isEmptyString(offerMap.get("Payoom Exclusive Offer"))){
				offer.setOfferTitle(offerMap.get("Payoom Exclusive Offer"));
				offerMap.remove("Payoom Exclusive Offer");
			}else {
				offer.setOfferTitle(offerMap.get("offer"));
				offerMap.remove("offer");
			}
			
			offer.setLabelsString(offerMap.get("labels"));
			offerMap.remove("labels");
			
			//System.out.println("   end   "+ offerMap.get("end"));
			
			if (offerMap.containsKey("end")
					&& !offerMap.get("end").equalsIgnoreCase("Deal for the day")
					&& !offerMap.get("end").equalsIgnoreCase("Deal for the today")
					&& !offerMap.get("end").equalsIgnoreCase("Deal of the day")
					&& !offerMap.get("end").equalsIgnoreCase("Stock till last")
					&& !offerMap.get("end").equalsIgnoreCase("till stock last")
					&& !offerMap.get("end").equalsIgnoreCase("Stalk till last")
					&& !offerMap.get("end").equalsIgnoreCase("Stock Last")
					&& !offerMap.get("end").equalsIgnoreCase("Stock lasts")
					&& !offerMap.get("end").equalsIgnoreCase("Limited period")
					&& !offerMap.get("end").equalsIgnoreCase("L.P")
					&& !offerMap.get("end").contains("Every ")
					&& !offerMap.get("end").contains("Each ")
					&& !offerMap.get("end").contains("days from today")) {
				Date offerEnd = new Date();
				String end = offerMap.get("end").trim();
				end = end.replaceAll("  ", " ");
				if (end.contains(" to ") && !end.contains("AM to ")
						&& !end.contains("PM to ")) {
					end = end.split(" to ")[1];
				}
				
				if(end.contains(" and ")){
						end = end.split(" and ")[1];
				}
				 
				if(end.contains(" & ")){
					end = end.split(" & ")[1];
				}
				
				if(end.contains(" on ")){
					end = end.split(" on ")[1];
				}
				
				if(end.contains("Only applicable for ")){
					end = end.split("Only applicable for ")[1];
				}
				
				if(end.contains("Will end on ")){
					end = end.split("Will end on ")[1];
				}
				
				if(end.contains("Till ")){
					end = end.split("Till ")[1];
				}
				end = end.replaceAll(" of ", " ");
				if(end.equalsIgnoreCase("End Of the Month") || !end.contains(" ")){
					Calendar offerExpire = new GregorianCalendar();
					offerExpire
							.set(Calendar.DAY_OF_MONTH, offerExpire
									.getActualMaximum(Calendar.DAY_OF_MONTH));
					offerExpire.set(offerExpire.get(Calendar.YEAR),
							offerExpire.get(Calendar.MONTH),
							offerExpire.get(Calendar.DATE), 23, 59, 59);
					offer.setOfferEnd(offerExpire);
				} else {
					String[] endDate = end.split(" ");
					int day = 0;
					String month = endDate[1]; 
					if(endDate[0].contains("st")){
						if(!StringUtil.isEmptyString(endDate[0].split("st")[0])){
							day = Integer.parseInt(endDate[0].split("st")[0]);
						}
					} else if(endDate[0].contains("nd")){
						if(!StringUtil.isEmptyString(endDate[0].split("nd")[0])){
							day = Integer.parseInt(endDate[0].split("nd")[0]);
						}
					} else if(endDate[0].contains("th")){
						if(!StringUtil.isEmptyString(endDate[0].split("th")[0])) {
							day = Integer.parseInt(endDate[0].split("th")[0]);
						}
					} else if(endDate[0].contains("Th")){
						if(!StringUtil.isEmptyString(endDate[0].split("Th")[0])) {
							day = Integer.parseInt(endDate[0].split("Th")[0]);
						}
					} else if(endDate[0].contains("TH")){
						if(!StringUtil.isEmptyString(endDate[0].split("TH")[0])) {
							day = Integer.parseInt(endDate[0].split("TH")[0]);
						}
					} else if(endDate[0].contains("h")){
						if(!StringUtil.isEmptyString(endDate[0].split("h")[0])) {
							day = Integer.parseInt(endDate[0].split("h")[0]);
						}
					} else if(endDate[0].contains("rd")){
						if(!StringUtil.isEmptyString(endDate[0].split("rd")[0])){
							day = Integer.parseInt(endDate[0].split("rd")[0]);
						}
					} else if(endDate[0].contains("today")){
						offerEnd.setDate(offerEnd.getDate() + 1);
					} else if(endDate[0].contains("-")) {
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						try {
							Date date = formatter.parse(endDate[0]);
							offerEnd = date;
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else if(endDate[0].contains("/")){
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						try {
							Date date = formatter.parse(endDate[0]);
							offerEnd = date;
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else if(!NumberUtils.isNumber(endDate[0]) && NumberUtils.isNumber(endDate[1])){
						offerEnd.setDate(Integer.parseInt(endDate[1]));
						month = endDate[0];
					} else {
						offerEnd.setDate(Integer.parseInt(endDate[0]));
					}
					
					if(day > 0) {
						Calendar offerExpire = new GregorianCalendar();
						Date date = new SimpleDateFormat("MMM").parse(month);
						/*if(endDate[1].equalsIgnoreCase(" ") && StringUtil.isEmptyString(endDate[2])){
							date = new SimpleDateFormat("MMM").parse(endDate[2]);
						}*/
						offerEnd.setDate(day);
						offerEnd.setMonth(date.getMonth());
						offerExpire.setTime(offerEnd);
						offerExpire.set(offerExpire.get(Calendar.YEAR),
								offerExpire.get(Calendar.MONTH),
								offerExpire.get(Calendar.DATE), 23, 59, 59);
						offer.setOfferEnd(offerExpire);
					}
				}
				offerMap.remove("end");
			} 
			
			if(offerMap.containsKey("end")){
				offerMap.remove("end");
			}
			
			//System.out.println("   L.P   "+ offerMap.get("L.P"));
			
			if(!StringUtil.isEmptyString(offerMap.get("L.P"))){
				offer.setTargetURL(offerMap.get("L.P"));
				offerMap.remove("L.P");
			}
			
			if(!StringUtil.isEmptyString(offerMap.get("L.p"))){
				offer.setTargetURL(offerMap.get("L.p"));
				offerMap.remove("L.p");
			}
			
			if(!StringUtil.isEmptyString(offerMap.get("LP"))){
				offer.setTargetURL(offerMap.get("LP"));
				offerMap.remove("LP");
			}
			
			//System.out.println("   Coupon   "+ offerMap.get("Coupon"));
			if (offerMap.containsKey("Coupon")) {
				offer.setLabelsString(offer.getLabelsString() + ", coupons");
				offer.setCouponCode(offerMap.get("Coupon"));
				offerMap.remove("Coupon");
			}
			
			if (offerMap.containsKey("Coupn")) {
				offer.setLabelsString(offer.getLabelsString() + ", coupons");
				offer.setCouponCode(offerMap.get("Coupn"));
				offerMap.remove("Coupn");
			}
			
			if (offerMap.containsKey("Code")) {
				offer.setLabelsString(offer.getLabelsString() + ", coupons");
				offer.setCouponCode(offerMap.get("Code"));
				offerMap.remove("Code");
			}
			
			for( String key : offerMap.keySet() ) {
	            if(StringUtil.isEmptyString(offer.getDescription())){
	            	//System.out.println(key + " : \n " +offerMap.get(key));
	            	offer.setDescription(key + " : \n " + offerMap.get(key));
	            } else {
	            	offer.setDescription(offer.getDescription() + " \n " + key + " : \n " + offerMap.get(key));
	            }
	        }
			
			if(!StringUtil.isEmptyString(offer.getMerchantName())){
				offer.setMerchantName(offer.getMerchantName().replaceAll("\\p{Cntrl}", "").replaceAll("[^\\x00-\\x7F]", ""));
			}
			if(!StringUtil.isEmptyString(offer.getOfferTitle())){
				offer.setOfferTitle(offer.getOfferTitle().replaceAll("\\p{Cntrl}", "").replaceAll("[^\\x00-\\x7F]", ""));
			}
			if(!StringUtil.isEmptyString(offer.getDescription())){
				offer.setDescription(offer.getDescription().replaceAll("\n", "ku_newline").replaceAll("\\p{Cntrl}", "").replaceAll("[^\\x00-\\x7F]", "").replaceAll("ku_newline", "\n"));
			}
			if(!StringUtil.isEmptyString(offer.getCouponCode())){
				offer.setCouponCode(offer.getCouponCode().replaceAll("\\p{Cntrl}", "").replaceAll("[^\\x00-\\x7F]", ""));
			}
			if(!StringUtil.isEmptyString(offer.getTargetURL())){
				offer.setTargetURL(offer.getTargetURL().replaceAll("\\p{Cntrl}", "").replaceAll("[^\\x00-\\x7F]", ""));
			}
			//System.out.println("description :: " + offer.getDescription());
			offer.setSource("payoom");
			offers.add(offer);
			// offerManager.saveOffer(offer);
		}
		offerManager.saveOffers(offers);
		return true;
	}

	public List<Crawl<String>> readEmail() {
		List<Crawl<String>> crawls = new ArrayList<Crawl<String>>();
		String host = "imap.zoho.com";// change accordingly
		String storeType = "imap";
		String user = "admin@muniyamma.com";// change accordingly
		String password = "ideas2it";// change accordingly
		try {

			// create properties field
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", "993");
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("imaps");

			store.connect(host, user, password);

			// create the folder object and open it
			// Folder[] folders = store.getDefaultFolder().list();

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			
			Message[] messages=null;
			    FlagTerm flagTerm=new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			    messages=inbox.search(flagTerm);
			  System.out.println("message count ---------"+messages.length);
			  for (int i = 0; i < messages.length; i++) {
					Message message = messages[i];
					System.out.println("subject :: "+message.getSubject());
					if(message.getSubject().contains("Offer Updates") || message.getSubject().contains("Offer updates") || message.getSubject().contains("offer updates") || message.getSubject().contains("offer Updates")) {
						if (message.getContentType().contains("multipart")) {
							Multipart multiPart = (Multipart) message.getContent();
							for (int j = 0; j < multiPart.getCount(); j++) {
								try {
									MimeBodyPart part = (MimeBodyPart) multiPart
											.getBodyPart(j);
									crawls.add(writePart(part));
									inbox.setFlags(new Message[] {message}, new Flags(Flags.Flag.SEEN), true);
								} catch (KUException e) {
									continue;
								}
							}
						}
					} else {
						inbox.setFlags(new Message[] {message}, new Flags(Flags.Flag.SEEN), true);
					}
				}
			  
			// creates a search criterion
			/*SearchTerm searchTerm = new SearchTerm() {
				@Override
				public boolean match(Message message) {
					Date searchDate = new Date();
					searchDate.setHours(searchDate.getHours() - 24);
					try {
						if (message.getSubject().contains("Offer Updates")
								&& message.getSentDate().after(searchDate)) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
 					return false;
				}
			};

			Message messages[] = inbox.search(searchTerm);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				if (message.getContentType().contains("multipart")) {
					Multipart multiPart = (Multipart) message.getContent();

					for (int j = 0; j < multiPart.getCount(); j++) {
						MimeBodyPart part = (MimeBodyPart) multiPart
								.getBodyPart(j);
						crawls.add(writePart(part));
					}
				}
			}*/
			// close the store and folder objects
			inbox.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crawls;
	}

	/*
	 * This method checks for content-type based on which, it processes and
	 * fetches the content of the message
	 */
	public Crawl<String> writePart(Part p) throws KUException {
		Crawl<String> dll = new Crawl<String>();
		try {
			if (p.isMimeType("text/plain")) {
				Scanner scanner = new Scanner((String) p.getContent());
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					try {
						if (line.contains(":") && line.split(":").length < 2) {
							if (scanner.hasNextLine()) {
								String nextLine = scanner.nextLine();
								if (!nextLine.trim().isEmpty()) {
									line = line.concat(nextLine);
								}
							}
						}

						if(StringUtil.isEmptyString(line) && dll.getTail() != null &&(dll.getTail().getElement().contains("Offer*:")
								|| dll.getTail().getElement().contains("Offer* :")
								|| dll.getTail().getElement().contains("Offer:")
								|| dll.getTail().getElement().contains("Offer :"))){
							continue;
						} else {
							dll.addLast(line);
						}
					} catch (NoSuchElementException e) {
						continue;
					}
				}
				scanner.close();
			}
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new KUException("oops problem in crawl email");
		}
		return dll;
	}

	public List<Map<String, String>> getOffers() {
		List<Map<String, String>> offers = new ArrayList<Map<String, String>>();
		for (Crawl<String> crawl : readEmail()) {
			setNextNode(crawl.getHead());
			String merchant = "";
			while (getNextNode() != null) {
				if (getNextNode().getElement().trim().length() == 0) {
					if (checkIsOffers(getNextNode())) {
						Map<String, String> offer = new HashMap<String, String>();
						if (StringUtil.isEmptyString(getNextNode().getPrev().getPrev().getElement().trim()) && !getNextNode().getPrev().getElement().contains(":") && !getNextNode().getPrev().getElement().contains("-")) {
							merchant = getNextNode().getPrev().getElement();
						}
						merchant = merchant.replace("*", "");
						offer.put("merchant", merchant.trim());
						StringBuffer label = new StringBuffer();
						label.append(merchant.trim().toLowerCase());
						label.append(", ");
						label.append(merchant.trim().toLowerCase());
						label.append(" offers");
						label.append(", ");
						label.append(merchant.trim().toLowerCase());
						label.append(" coupons");
						label.append(", ");
						label.append(" offers");
						label.append(",");
						offer.put("labels", label.toString());
						offers.add(addOffer(getNextNode().getNext(), offer));
					} else {
						setNextNode(getNextNode().getNext());
					}
				} else {
					setNextNode(getNextNode().getNext());
				}
			}
		}
		return offers;
	}

	public Map<String, String> addOffer(Node<String> node,
			Map<String, String> offer) {
		if (node.getElement().trim().length() > 0) {
			if (node.getElement().contains(":")) {
				String[] elementArray = node.getElement().split(":");
				if (elementArray[0].trim().equalsIgnoreCase("Validity")
						|| elementArray[0].trim().equalsIgnoreCase("Validity Ends") 
						|| elementArray[0].trim().equalsIgnoreCase("Vailidty")
						|| elementArray[0].trim().equalsIgnoreCase("Valdity")
						|| elementArray[0].trim().equalsIgnoreCase("Valid tilll")
						|| elementArray[0].trim().equalsIgnoreCase("Valid till")
						|| elementArray[0].trim().equalsIgnoreCase("Valid")) {
					offer.put("end", elementArray[1].trim());
					//System.out.println("end 1:: "+elementArray[1].trim());
				} else {
					String value = elementArray[1];
					if (elementArray.length > 2) {
						for (int i = 2; i < elementArray.length; i++) {
							value = value + ":" + elementArray[i];
						}
					}
	
					if (elementArray[1].contains("|")) {
						String[] valueArray = elementArray[1].split("|");
						value = valueArray[0];
						for (int i = 1; i < valueArray.length; i++) {
							if (offer.get("note") != null) {
								offer.put("note", offer.get("note")
										+ valueArray[i]);
							} else {
								offer.put("note", valueArray[i]);
							}
						}
					}
	
					while (node.next.getElement().trim().length() > 0
							&& !node.next.getElement().contains(":")
							&& !node.next.getElement().contains("Valid till") 
							&& !node.next.getElement().contains("Valid tilll")
							&& !node.next.getElement().contains("Valid today only")) {
						value = value + " \n " + node.next.getElement().trim();
						node = node.getNext();
					}
					offer.put(elementArray[0].trim(), value.trim());
					
					//System.out.println("othersssss :: " + elementArray[0].trim() +" :: "+ value.trim());
				}
			}else if (node.getElement().contains("Valid tilll")) {
				offer.put("end",
						node.getElement().split("Valid tilll")[1].trim());
			} else if (node.getElement().contains("Valid till")
					&& node.getElement().split("Valid till").length > 1) {
				offer.put("end",
						node.getElement().split("Valid till")[1].trim());
			}else if (node.getElement().contains("Valid today only")) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
				Date today = new Date();
				String result = formatter.format(today);
				offer.put("end", result);
			} else {
				if (offer.get("note") != null) {
					offer.put("note", offer.get("note")
							+ node.getElement().trim());
				} else {
					offer.put("note", node.getElement().trim());
				}
			}
			setNextNode(node.getNext());
			addOffer(node.getNext(), offer);	
		}
		return offer;
	}

	public static boolean checkIsOffers(Node<String> node) {
		if (node.getNext().getElement().contains("Offer*:")
				|| node.getNext().getElement().contains("Offer* :")
				|| node.getNext().getElement().contains("Offer:")
				|| node.getNext().getElement().contains("Offer :")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean pullFlipkartOffers() throws KUException {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Fk-Affiliate-Token",
				"ef77d485ad7b418984aeea01d2d3eaa9");
		headers.put("Fk-Affiliate-Id", "adminmuni");
		params.put("headers", headers);
		try {
			String dotdResponse = ApiUtil.getFlipkartData("https://affiliate-api.flipkart.net/affiliate/offers/v1/dotd/json", params);
			offerManager.saveOffers(processFlipkartResponse(dotdResponse, "dotdList"));
			String topResponse = ApiUtil.getFlipkartData("https://affiliate-api.flipkart.net/affiliate/offers/v1/top/json", params);
			offerManager.saveOffers(processFlipkartResponse(topResponse, "topOffersList"));
			//String allResponse = ApiUtil.getFlipkartData("https://affiliate-api.flipkart.net/affiliate/offers/v1/all/json", params);
			//offerManager.saveOffers(processFlipkartResponse(allResponse, "allOffersList"));
		} catch (org.json.simple.parser.ParseException e) {
			throw new KUException(e.getMessage(), e);
		}
		return true;
	}
	
	private List<Offer> processFlipkartResponse(String response, String key) throws KUException, org.json.simple.parser.ParseException{
		JSONParser parser = new JSONParser();
		JSONObject offersJson = (JSONObject) parser.parse(response);
		//System.out.println("jsonnnnnnnnnnn ========= " + offersJson);
		JSONArray deals = (JSONArray) offersJson.get(key);
		List<Offer> offers = new ArrayList<Offer>();
		if(deals != null){
			for (int i = 0 ; i < deals.size(); i++) {
				JSONObject offerJson = (JSONObject)deals.get(i);
				Offer offer = new Offer();
				offer.setOfferTitle(offerJson.get("title").toString());
				offer.setDescription(offerJson.get("description").toString());
				offer.setTargetURL(offerJson.get("url").toString());
				
				if(!StringUtil.isEmptyString(offerJson.get("startTime"))){
					Calendar offerEnd = new GregorianCalendar();
					offerEnd.setTimeInMillis(Long.parseLong(offerJson.get("startTime").toString()));
					offer.setOfferStart(offerEnd);
				}
				
				if(!StringUtil.isEmptyString(offerJson.get("endTime"))){
					Calendar offerEnd = new GregorianCalendar();
					offerEnd.setTimeInMillis(Long.parseLong(offerJson.get("endTime").toString()));
					offer.setOfferEnd(offerEnd);
				}
				
				if(!StringUtil.isEmptyString(offerJson.get("imageUrls"))) {
					JSONArray images = (JSONArray)offerJson.get("imageUrls");
					for (int j = 0 ; j < images.size(); j++) {
						JSONObject image = (JSONObject)images.get(j);
						//System.out.println(image.get("url"));
						if(StringUtil.isEmptyString(image.get("url")) || !((String) image.get("url")).contains("200/200") ) {
							//System.out.println("next image");
							continue;
						} else {
							//System.out.println("save this image");
							String uploadDir = servletContext.getRealPath("/files");
							File f = null;
							boolean isImagesPath = false;
							if(uploadDir != null) {
								f = new File(uploadDir);
							}
							if(uploadDir == null || !f.exists()) {
								uploadDir = servletContext.getRealPath("/images");
								isImagesPath = true;
							}
							Calendar date = new GregorianCalendar();
							SimpleDateFormat sdf = new SimpleDateFormat("MMMM-yyyy");
							String dateStr = sdf.format(date.getTime());
							String path = Constants.FILE_SEP
									+ "offers"
									+ Constants.FILE_SEP
									+ dateStr
									+ Constants.FILE_SEP
									+ "flipkart"
									+ Constants.FILE_SEP;
							path = path.toLowerCase();
							f = new File(uploadDir+path);
							if(!f.exists()) {
								f.mkdirs();
							}
							if(!StringUtil.isEmptyString(offerJson.get("description"))) {
								String description = offerJson.get("description").toString()
										.replaceAll(" ", "-")
										.replaceAll("%", "-percent")
										.replaceAll("'", "")
										.replaceAll(",", "")
										.replaceAll("&", "")
										.replaceAll("#", "")
										.replaceAll("/", "");
								if(description.length() > 100){
									description = description.substring(0, 100);
								}
								String title = offer.getOfferTitle()
										.replaceAll(" ", "-")
										.replaceAll("%", "-percent")
										.replaceAll("/", ""); 
								path +=  title + description + ".jpg";
							} else {
								path += offer.getOfferTitle()
										.replaceAll(" ", "-")
										.replaceAll("%", "-percent")
										.replaceAll("/", "")
										+ ".jpg";
							}
							path = path.toLowerCase();
							ApiUtil.saveImageFromUrl(image.get("url").toString(), uploadDir+path);
							if(isImagesPath){
								offer.setImagePath("/images"+Constants.FILE_SEP+path);
							} else {
								offer.setImagePath("/files"+Constants.FILE_SEP+path);
							}
							break;
						}
					}
				}
				offer.setLabelsString("flipkart, offers, deals, coupons");
				offer.setMerchantName("flipkart");
				offer.setSource("flipkart");
				offers.add(offer);
			}
		}
		return offers;
	}
	
	public Node<String> getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node<String> nextNode) {
		this.nextNode = nextNode;
	}
	
	
}
