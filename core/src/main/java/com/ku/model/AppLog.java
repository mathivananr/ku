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
@Table(name = "ku_log")
public class AppLog extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3832626162173359411L;

	private Long id;
	private String title;
	private String appLink;
	private Calendar createdOn = new GregorianCalendar();
	private String ipAddress;

	public AppLog() {
		super();
	}
	
	public AppLog(String title, String appLink, String ipAddress) {
		super();
		this.title = title;
		this.appLink = appLink;
		this.ipAddress = ipAddress;
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

	@Column(name = "title", columnDefinition = "TEXT")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "app_link")
	public String getAppLink() {
		return appLink;
	}

	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}

	@Column(name = "created_on")
	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "ip_address")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AppLog)) {
			return false;
		}
		final AppLog appLog = (AppLog) o;
		return !(id != null ? !id.equals(appLog.getId())
				: appLog.getId() != null);
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
