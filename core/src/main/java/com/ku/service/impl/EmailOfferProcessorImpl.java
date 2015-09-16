package com.ku.service.impl;

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

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SearchTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.common.KUException;
import com.ku.model.Crawl;
import com.ku.model.Node;
import com.ku.model.Offer;
import com.ku.service.EmailOfferProcessor;
import com.ku.service.OfferManager;
import com.ku.util.StringUtil;

@Service("emailOfferProcessor")
public class EmailOfferProcessorImpl implements EmailOfferProcessor {

	private OfferManager offerManager;
	
	private Node<String> nextNode;

	@Autowired
	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	@SuppressWarnings("deprecation")
	public boolean processPayoomOffers() throws KUException, ParseException {
		List<Offer> offers = new ArrayList<Offer>();
		for (Map<String, String> offerMap : getOffers()) {
			if (offerMap.get("L.P").contains("docs.google")
					|| (offerMap.get("L.P").contains("drive.google") || offerMap
							.get("L.P").contains("dropbox.com"))) {
				continue;
			}
			Offer offer = new Offer();
			offer.setMerchantName(offerMap.get("merchant"));
			if (!StringUtil.isEmptyString(offerMap.get("Offer"))) {
				offer.setOfferTitle(offerMap.get("Offer"));
			} else {
				offer.setOfferTitle(offerMap.get("offer"));
			}
			offer.setLabelsString(offerMap.get("labels"));
			if (offerMap.containsKey("T&C")) {
				offer.setDescription(offerMap.get("T&C"));
			}
			
			if(offerMap.containsKey("others")) {
				if(StringUtil.isEmptyString(offer.getDescription())){
					offer.setDescription(offerMap.get("others"));
				} else {
					offer.setDescription(offer.getDescription() + offerMap.get("others"));
				}
			}
			if(offerMap.containsKey("end")) {
				Date offerEnd = new Date();
				String end = offerMap.get("end").trim();
				String[] endDate = end.split(" ");
				System.out.println("========"+endDate[0]);
				int day = 0;
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
				} else if(endDate[0].contains("rd")){
					if(!StringUtil.isEmptyString(endDate[0].split("rd")[0])){
						day = Integer.parseInt(endDate[0].split("rd")[0]);
					}
				} else {
					offerEnd.setDate(Integer.parseInt(endDate[0]));
				}
				
				if(day > 0) {
					Calendar offerExpire = new GregorianCalendar();
					Date date = new SimpleDateFormat("MMM").parse(endDate[1]);
					offerEnd.setDate(day);
					offerEnd.setMonth(date.getMonth());
					offerExpire.setTime(offerEnd);
					offer.setOfferEnd(offerExpire);
				}
			}
			if(!StringUtil.isEmptyString(offerMap.get("L.P"))){
				offer.setTargetURL(offerMap.get("L.P"));
			}
			if (offerMap.containsKey("Coupon")) {
				offer.setLabelsString(offer.getLabelsString() + ", coupons");
				offer.setCouponCode(offerMap.get("Coupon"));
			}
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

			// creates a search criterion
			SearchTerm searchTerm = new SearchTerm() {
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
			}
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
	public Crawl<String> writePart(Part p) throws Exception {
		Crawl<String> dll = new Crawl<String>();
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
					dll.addLast(line);
				} catch (NoSuchElementException e) {
					continue;
				}
			}
			scanner.close();
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
						if (!getNextNode().getPrev().getElement().contains(":")) {
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
						if (offer.get("others") != null) {
							offer.put("others", offer.get("others")
									+ valueArray[i]);
						} else {
							offer.put("others", valueArray[i]);
						}
					}
				}

				while (node.next.getElement().trim().length() > 0
						&& !node.next.getElement().contains(":")
						&& !node.next.getElement().contains("Valid till")
						&& !node.next.getElement().contains("Valid today only")) {
					value = value + " " + node.next.getElement().trim();
					node = node.getNext();
				}
				offer.put(elementArray[0].trim(), value.trim());
			} else if (node.getElement().contains("Valid till")) {
				offer.put("end",
						node.getElement().split("Valid till")[1].trim());
			} else if (node.getElement().contains("Valid today only")) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
				Date today = new Date();
				String result = formatter.format(today);
				System.out.println("Result: " + result);
				offer.put("end", result);
			} else {
				if (offer.get("others") != null) {
					offer.put("others", offer.get("others")
							+ node.getElement().trim());
				} else {
					offer.put("others", node.getElement().trim());
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
				&& node.getNext().getElement().contains("Offer:")
				|| node.getNext().getElement().contains("Offer :")) {
			return true;
		} else {
			return false;
		}
	}

	public Node<String> getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node<String> nextNode) {
		this.nextNode = nextNode;
	}
}
