package com.ku.model;

import java.io.Serializable;

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
@Table(name = "ku_config")
public class AppConfig extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3832626162173359411L;

	private Long id;

	private String dataType;
	
	private String KeyName;

	private String KeyValue;
	
	private String description;

	public AppConfig() {
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

	@Column(name = "data_type")
	public synchronized String getDataType() {
		return dataType;
	}

	public synchronized void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "key_name")
	public String getKeyName() {
		return KeyName;
	}

	public void setKeyName(String keyName) {
		KeyName = keyName;
	}

	@Column(name = "key_value")
	public String getKeyValue() {
		return KeyValue;
	}

	public void setKeyValue(String keyValue) {
		KeyValue = keyValue;
	}

	@Column(name = "description", columnDefinition = "LONGTEXT")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AppConfig)) {
			return false;
		}
		final AppConfig appConfig = (AppConfig) o;
		return !(id != null ? !id.equals(appConfig.getId())
				: appConfig.getId() != null);
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
