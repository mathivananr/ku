package com.ku.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.DocumentId;

@Entity
@Table(name = "ku_offer_label")
public class OfferLabel extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3832626162173359411L;

	private Long labelId;
	private String label;
	private boolean isMerchant;
	private boolean isCategory;
	private boolean isProduct;
	private String metaKeyword;
	private String metaDescription;
	private String createdBy;
	private String updatedBy;
	private boolean isHidden;
	private Calendar createdOn = new GregorianCalendar();
	private Calendar updatedOn = new GregorianCalendar();
	
	public OfferLabel() {
		super();
	}

	public OfferLabel(String label) {
		this.label = label;
	}
	
	@Id
	@Column(name = "label_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	@Column(name = "label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "is_merchant", columnDefinition = "boolean default false", nullable = false)
	public boolean getIsMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	@Column(name = "is_category", columnDefinition = "boolean default false", nullable = false)
	public boolean getIsCategory() {
		return isCategory;
	}

	public void setIsCategory(boolean isCategory) {
		this.isCategory = isCategory;
	}

	@Column(name = "is_product", columnDefinition = "boolean default false", nullable = false)
	public boolean getIsProduct() {
		return isProduct;
	}

	public void setIsProduct(boolean isProduct) {
		this.isProduct = isProduct;
	}

	@Column(name = "meta_keyword", columnDefinition = "TEXT")
	public String getMetaKeyword() {
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}

	@Column(name = "meta_description" , columnDefinition = "TEXT")
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
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

	@Column(name = "is_hidden", columnDefinition = "boolean default false", nullable = false)
	public boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OfferLabel)) {
			return false;
		}

		final OfferLabel label = (OfferLabel) o;

		return !(labelId != null ? !labelId.equals(label.getLabelId())
				: label.getLabelId() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (labelId != null ? labelId.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.labelId);
		return sb.toString();
	}
}
