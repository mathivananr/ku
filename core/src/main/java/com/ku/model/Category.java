package com.ku.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.DocumentId;

@Entity
@Table(name = "ku_category")
public class Category extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3832626162173359411L;
	private Long categoryId;
	private String categoryName;
	private String description;
	private Category parentCategory;
	private List<Category> subCategories = new ArrayList<Category>();
	private Calendar createdOn = new GregorianCalendar();
	private Calendar updatedOn = new GregorianCalendar();
	private Calendar createdIpAddress;
	private Calendar updatedIpAddress;
	private boolean enabled;

	public Category() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	@Column(name = "offer_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "category_name")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "description", columnDefinition = "TEXT")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_category")
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@OneToMany(mappedBy = "parentCategory")
	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	@Column(name = "is_enabled", columnDefinition = "boolean default true", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public Calendar getCreatedIpAddress() {
		return createdIpAddress;
	}

	public void setCreatedIpAddress(Calendar createdIpAddress) {
		this.createdIpAddress = createdIpAddress;
	}

	public Calendar getUpdatedIpAddress() {
		return updatedIpAddress;
	}

	public void setUpdatedIpAddress(Calendar updatedIpAddress) {
		this.updatedIpAddress = updatedIpAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Category)) {
			return false;
		}

		final Category category = (Category) o;

		return !(categoryId != null ? !categoryId.equals(category.getCategoryId())
				: category.getCategoryId() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (categoryId != null ? categoryId.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("categoryId", this.categoryId);
		return sb.toString();
	}

}
