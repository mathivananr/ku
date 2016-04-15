package com.ku.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.DocumentId;

@Entity
@Table(name = "ku_page")
public class Page extends BaseObject implements Serializable {
	private static final long serialVersionUID = 3832626162173359411L;

	private Long pageId;
	private String pageName;
	private String title;
	private String description;
	private User owner;
	private List<MerchantType> merchantTypes;
	private List<Merchant> merchants;
	private List<Category> categories;
	private Calendar createdOn;
	private Calendar updatedOn;
	
	public Page() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	@Column(name = "page_id")
	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	@Column(name = "page_name")
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<MerchantType> getMerchantTypes() {
		return merchantTypes;
	}

	public void setMerchantTypes(List<MerchantType> merchantTypes) {
		this.merchantTypes = merchantTypes;
	}

	public List<Merchant> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<Merchant> merchants) {
		this.merchants = merchants;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Page o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Page)) {
			return false;
		}

		final Page page = (Page) o;

		return !(pageId != null ? !pageId.equals(page.getPageId())
				: page.getPageId() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (pageId != null ? pageId.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("pageId", this.pageId);
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

}
