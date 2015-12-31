package com.ku.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;

import com.ku.util.StringUtil;

@Entity
@Table(name = "ku_offer")
public class Offer extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3832626162173359411L;

	private Long offerId;
	private String offerTitle;
	private String description;
	private String merchantName;
	private String couponCode;
	private String offerHint;
	private String imagePath;
	private String merchantLogoPath;
	private String targetURL;
	private String source;
	private List<OfferLabel> labels = new ArrayList<OfferLabel>();
	private String labelsString;
	private String seoKeyword;
	private int userCount;
	private Calendar offerStart;
	private Calendar offerEnd;
	private String formattedEnd;
	private Calendar createdOn;
	private Calendar updatedOn = new GregorianCalendar();
	private String createdBy;
	private String updatedBy;
	private boolean enabled;
	private boolean expired;
	
	public Offer() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	@Column(name = "offer_id")
	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	@Column(name = "title")
	public String getOfferTitle() {
		return offerTitle;
	}

	public void setOfferTitle(String offerTitle) {
		this.offerTitle = offerTitle;
	}

	@Column(name = "description", columnDefinition = "TEXT")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "merchant_name")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Column(name = "coupon_code")
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Column(name = "offer_hint")
	public String getOfferHint() {
		return offerHint;
	}

	public void setOfferHint(String offerHint) {
		this.offerHint = offerHint;
	}

	@Column(name = "image_path")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "merchant_logo_path")
	public String getMerchantLogoPath() {
		return merchantLogoPath;
	}

	public void setMerchantLogoPath(String merchantLogoPath) {
		this.merchantLogoPath = merchantLogoPath;
	}

	@Column(name = "target_url", columnDefinition = "TEXT")
	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "ku_rel_offer_label", joinColumns = {@JoinColumn(name="offer_id")},
            inverseJoinColumns = {@JoinColumn(name="label_id")} )
	public List<OfferLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<OfferLabel> labels) {
		this.labels = labels;
	}

	@Transient
	public String getLabelsString() {
		if(StringUtil.isEmptyString(labelsString)) {
			StringBuffer stringBuffer = new StringBuffer();
			if(getLabels() != null){
				for (OfferLabel label : getLabels()) {
						if(label != null){
							if (stringBuffer.length() > 0) {
								stringBuffer.append(", ");
							}
							stringBuffer.append(label.getLabel());
						}
				}
			}
			return stringBuffer.toString();
		} else {
			return labelsString;
		}
	}

	public void setLabelsString(String labelsString) {
		this.labelsString = labelsString;
	}

	@Column(name = "seo_keywords", columnDefinition = "TEXT")
	public String getSeoKeyword() {
		return seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	@Column(name = "user_count", columnDefinition = "int default 0")
	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	@Column(name = "offer_start")
	public Calendar getOfferStart() {
		return offerStart;
	}

	public void setOfferStart(Calendar offerStart) {
		this.offerStart = offerStart;
	}

	@Column(name = "offer_end")
	public Calendar getOfferEnd() {
		return offerEnd;
	}

	public void setOfferEnd(Calendar offerEnd) {
		this.offerEnd = offerEnd;
	}

	@Transient
	public String getFormattedEnd() {
		if(offerEnd != null){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
			return simpleDateFormat.format(offerEnd.getTime());
		} else {
			return "Until Stock";
		}
	}

	public void setFormattedEnd(String formattedEnd) {
		this.formattedEnd = formattedEnd;
	}

	@Column(name = "is_enabled", columnDefinition = "boolean default true", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "is_expired", columnDefinition = "boolean default false", nullable = false)
	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Column(name = "created_on")
	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "updated_on")
	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "created_by")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_by")
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Offer)) {
			return false;
		}

		final Offer offer = (Offer) o;

		return !(offerId != null ? !offerId.equals(offer.getOfferId())
				: offer.getOfferId() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (offerId != null ? offerId.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.offerId);
		return sb.toString();
	}
}
