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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.DocumentId;

@Entity
@Table(name = "ku_support_request")
public class SupportRequest extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3832626162173359411L;

	private Long id;
	private String rechargeId;
	private String requestId;
	private String email;
	private String title;
	private String description;
	private String status;
	private Calendar createdOn = new GregorianCalendar();
	private Calendar updatedOn = new GregorianCalendar();
	private String createdIpAddress;
	private String createdMacAddress;
	private String updatedIpAddress;
	private String updatedMacAddress;
	private String secretKey;

	public SupportRequest() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "recharge_id")
	public String getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}

	@Transient
	public String getRequestId() {
		return "MURQ" + getId();
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "secret_key")
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Column(name = "created_ip_address")
	public String getCreatedIpAddress() {
		return createdIpAddress;
	}

	public void setCreatedIpAddress(String createdIpAddress) {
		this.createdIpAddress = createdIpAddress;
	}

	@Column(name = "created_mac_address")
	public String getCreatedMacAddress() {
		return createdMacAddress;
	}

	public void setCreatedMacAddress(String createdMacAddress) {
		this.createdMacAddress = createdMacAddress;
	}

	@Column(name = "updated_ip_address")
	public String getUpdatedIpAddress() {
		return updatedIpAddress;
	}

	public void setUpdatedIpAddress(String updatedIpAddress) {
		this.updatedIpAddress = updatedIpAddress;
	}

	@Column(name = "updated_mac_address")
	public String getUpdatedMacAddress() {
		return updatedMacAddress;
	}

	public void setUpdatedMacAddress(String updatedMacAddress) {
		this.updatedMacAddress = updatedMacAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SupportRequest)) {
			return false;
		}
		final SupportRequest request = (SupportRequest) o;
		return !(id != null ? !id.equals(request.getId())
				: request.getId() != null);
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id);
		return sb.toString();
	}
}
