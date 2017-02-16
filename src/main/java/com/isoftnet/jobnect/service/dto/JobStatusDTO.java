package com.isoftnet.jobnect.service.dto;

import java.util.List;

public class JobStatusDTO
{
	 private List<Long> 	jobIds;
	 private String 		comment;
	 
	public List<Long> getJobIds()
	{
		return this.jobIds;
	}
	public void setJobIds(List<Long> jobIds)
	{
		this.jobIds = jobIds;
	}
	public String getComment()
	{
		return this.comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}	
}
