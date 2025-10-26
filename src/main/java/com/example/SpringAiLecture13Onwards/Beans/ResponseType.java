package com.example.SpringAiLecture13Onwards.Beans;

public class ResponseType {

	
	private String programmingLanguageName;
	private String description;
	private double foundingYear;
	
	public String getLanguageName() {
		return programmingLanguageName;
	}
	public void setLanguageName(String languageName) {
		programmingLanguageName = languageName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getFoundingYear() {
		return foundingYear;
	}
	public void setFoundingYear(double foundingYear) {
		this.foundingYear = foundingYear;
	}
	@Override
	public String toString() {
		return "ResponseType ["+ ", LanguageName=" + programmingLanguageName + ", description=" + description
				+ ", foundingYear=" + foundingYear + "]";
	}
	public ResponseType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseType( String languageName, String description, double foundingYear) {
		super();
		
		programmingLanguageName = languageName;
		this.description = description;
		this.foundingYear = foundingYear;
	}
	
	
}
