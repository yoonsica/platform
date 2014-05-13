package com.ceit.vic.platform.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Log {
	@Id
	private int id;
	
	private String content;
	
	private String ip;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RECORD_TIME")
	private Date recordTime;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.REFRESH})  
    @JoinColumn(name="PERSON_ID")  
	private Person person;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.REFRESH})  
    @JoinColumn(name="TYPE_ID")  
	private LogType logType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}
	
}
