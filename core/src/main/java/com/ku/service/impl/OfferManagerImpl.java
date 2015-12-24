package com.ku.service.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.ku.common.KUException;
import com.ku.dao.OfferDao;
import com.ku.model.Merchant;
import com.ku.model.Offer;
import com.ku.model.OfferLabel;
import com.ku.service.MerchantManager;
import com.ku.service.OfferManager;
import com.ku.util.StringUtil;

@Service("offerManager")
public class OfferManagerImpl extends GenericManagerImpl<Offer, Long> implements
		OfferManager {

	private OfferDao offerDao;
	private MerchantManager merchantManager;
	private VelocityEngine velocityEngine;
	
	@Autowired
	public OfferManagerImpl(OfferDao offerDao) {
		super(offerDao);
		this.offerDao = offerDao;
	}

	@Autowired
	public void setMerchantManager(MerchantManager merchantManager) {
		this.merchantManager = merchantManager;
	}

	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public Offer saveOffer(Offer offer) throws KUException {
		if (!StringUtil.isEmptyString(offer.getLabelsString())) {
			List<String> labelsString = new ArrayList<String>();
			String[] labelsArray = offer.getLabelsString().split(",");
			for (int i = 0; i < labelsArray.length; i++) {
				String label = labelsArray[i].trim();
				labelsString.add(label);
			}
			List<OfferLabel> offerLabels = offerDao
					.getOfferLabels(labelsString);
			for (String label : labelsString) {
				boolean isNotExists = true;
				OfferLabel newLabelObj = null;
				for (OfferLabel labelObj : offerLabels) {
					if (label.equalsIgnoreCase(labelObj.getLabel())) {
						isNotExists = false;
						newLabelObj = labelObj;
						break;
					}
				}
				if (isNotExists) {
					if (!StringUtil.isEmptyString(label.trim())) {
						OfferLabel offerLabel = new OfferLabel(label.trim());
						Merchant merchant = merchantManager
								.getMerchantByName(label.trim());
						if (merchant != null) {
							offer.setMerchantName(merchant.getMerchantName());
							offer.setMerchantLogoPath(merchant.getLogoPath());
						}
						offerLabel = saveOfferLabel(offerLabel);
						offer.getLabels().add(offerLabel);
					}
				} else {
					offer.getLabels().add(newLabelObj);
				}
			}
		}
		return offerDao.saveOffer(offer);
	}

	public OfferLabel saveOfferLabel(OfferLabel offerLabel) throws KUException {
		Merchant merchant = merchantManager.getMerchantByName(offerLabel
				.getLabel());
		if (merchant != null) {
			offerLabel.setIsMerchant(true);
		}
		return offerDao.saveOfferLabel(offerLabel);
	}

	public List<OfferLabel> saveOfferLabels(List<OfferLabel> offerLabels)
			throws KUException {
		List<OfferLabel> updatedLabels = new ArrayList<OfferLabel>();
		for (OfferLabel offerLabel : offerLabels) {
			log.info("saving label " + offerLabel.getLabel());
			OfferLabel oldLabel = offerDao.getOfferLabelByLabel(offerLabel
					.getLabel());
			if (oldLabel == null) {
				updatedLabels.add(saveOfferLabel(offerLabel));
			} else {
				if (!StringUtil.isEmptyString(offerLabel.getLabel())) {
					oldLabel.setLabel(offerLabel.getLabel());
				}
				if (!StringUtil.isEmptyString(offerLabel.getMetaDescription())) {
					oldLabel.setMetaDescription(offerLabel.getMetaDescription());
				}
				if (!StringUtil.isEmptyString(offerLabel.getMetaKeyword())) {
					oldLabel.setMetaKeyword(offerLabel.getMetaKeyword());
				}

				if (!StringUtil.isEmptyString(offerLabel.getIsMerchant())) {
					oldLabel.setIsMerchant(offerLabel.getIsMerchant());
				}

				updatedLabels.add(saveOfferLabel(oldLabel));
			}

			log.info("saved label " + offerLabel.getLabel());
		}
		return updatedLabels;
	}

	public List<Offer> saveOffers(List<Offer> offers) throws KUException {
		List<Offer> updatedOffers = new ArrayList<Offer>();
		for (Offer offer : offers) {
			Offer oldOffer = getOffer(offer.getOfferTitle(),
					offer.getMerchantName(), offer.getCouponCode(),
					offer.getOfferEnd(), offer.getTargetURL());
			if (oldOffer == null) {
				log.info("saving offer " + offer.getOfferTitle() + " for "
						+ offer.getMerchantName());
				updatedOffers.add(saveOffer(offer));
				log.info("saved offer " + offer.getOfferTitle() + " for "
						+ offer.getMerchantName());
			} else {
				log.info(offer.getOfferTitle() + " for "
						+ offer.getMerchantName() + " already exist ");
			}
		}
		return updatedOffers;
	}

	public List<OfferLabel> importOfferLabels(String filePath)
			throws KUException {
		List<OfferLabel> offerLabels = new ArrayList<OfferLabel>();
		// CSV file header
		String[] FILE_HEADER_MAPPING = { "Label", "Meta Keyword",
				"Meta Description" };
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT
				.withHeader(FILE_HEADER_MAPPING);
		try {
			// initialize FileReader object
			fileReader = new FileReader(filePath);
			// initialize CSVParser object
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			// Get a list of CSV file records
			List<CSVRecord> csvRecords = csvFileParser.getRecords();
			// Read the CSV file records starting from the second record to skip
			// the header
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				// Create a new student object and fill his data
				OfferLabel offerLabel = new OfferLabel();
				offerLabel.setLabel(record.get("Label"));
				offerLabel.setMetaKeyword(record.get("Meta Keyword"));
				offerLabel.setMetaDescription(record.get("Meta Description"));
				offerLabels.add(offerLabel);
			}
			offerLabels = saveOfferLabels(offerLabels);
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (IOException e) {
				System.out
						.println("Error while closing fileReader/csvFileParser !!!");
				e.printStackTrace();
			}
		}
		return offerLabels;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOffersContent(List<String> labels, int start, int end)
			throws KUException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("offers", getOffersByLabels(labels, start, end));
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				"offers.vm", "UTF-8", model);
	}
	
	public List<Offer> getAllOffers() throws KUException {
		return offerDao.getAllOffers();
	}

	public Offer getOfferById(Long offerId) throws KUException {
		return offerDao.getOfferById(offerId);
	}

	public List<OfferLabel> getAllOfferLabels() throws KUException {
		return offerDao.getAllOfferLabels();
	}

	public List<Offer> getOffersByLabel(String label) throws KUException {
		return offerDao.getOffersByLabel(label);
	}

	public Offer getOfferByTitle(String offerTitle) throws KUException {
		return offerDao.getOfferByTitle(offerTitle);
	}

	public Offer getOffer(String offerTitle, String merchantName,
			String couponCode, Calendar offerEnd, String targetURL) throws KUException {
		return offerDao
				.getOffer(offerTitle, merchantName, couponCode, offerEnd,targetURL);
	}

	public OfferLabel getOfferLabelById(Long labelId) throws KUException {
		return offerDao.getOfferLabelById(labelId);
	}

	public OfferLabel getOfferLabelByLabel(String label) throws KUException {
		return offerDao.getOfferLabelByLabel(label);
	}
	
	public List<Offer> getOffersByLabels(List<String> labels, int start, int end)
			throws KUException {
		return offerDao.getOffersByLabels(labels, start, end);
	}
}
