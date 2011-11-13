package com.uiproject.meetingplanner;

public class UserInstance {
	private int userID;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private String userPhone;
	private int userLocationLon;
	private int userLocationLat;
	private String userEta;
	private String userAttendingStatus;

	
	public UserInstance(int userID){
		this.userID = userID;
		this.userFirstName = "invalid";
		this.userLastName = "invalid";
		this.userEmail = "invalid";
		this.userPhone = "invalid";
		this.userLocationLon = 0;
		this.userLocationLat = 0;
		this.userEta = "0";
		this.userAttendingStatus = "pending";
	}
	
	public UserInstance(int userID, String userAttendingStatus) {
		super();
		this.userID = userID;
		this.userAttendingStatus = userAttendingStatus;
	}

	public UserInstance(int userID, String userFirstName, String userLastName, String userEmail,
				String userPhone, int userLocationLon, int userLocationLat){
		
		this.userID = userID;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userLocationLon = userLocationLon;
		this.userLocationLat = userLocationLat;
		this.userEta = "0";
		this.userAttendingStatus = "pending";
	}

	public UserInstance(int userID, String userFirstName, String userLastName, String userEmail,
			String userPhone, int userLocationLon, int userLocationLat, String userEta, String userAttendingStatus){
	
	this.userID = userID;
	this.userFirstName = userFirstName;
	this.userLastName = userLastName;
	this.userEmail = userEmail;
	this.userPhone = userPhone;
	this.userLocationLon = userLocationLon;
	this.userLocationLat = userLocationLat;
	this.userEta = userEta;
	this.userAttendingStatus = userAttendingStatus;
}
	
	public UserInstance(int userID, int userLocationLon, int userLocationLat, String userEta) {
		super();
		this.userID = userID;
		this.userLocationLon = userLocationLon;
		this.userLocationLat = userLocationLat;
		this.userEta = userEta;
	}

	/************
	 * Getters
	 ***********/
	
	public int getUserID(){
		return this.userID;
	}
	
	public String getUserFirstName(){
		return this.userFirstName;
	}
	
	public String getUserLastName(){
		return this.userLastName;
	}
	
	public String getUserEmail(){
		return this.userEmail;
	}
	
	public String getUserPhone(){
		return this.userPhone;
	}
	
	public int getUserLocationLat(){
		return this.userLocationLat;
	}
	
	public int getUserLocationLon(){
		return this.userLocationLon;
	}
	
	public String getUserEta(){
		return this.userEta;
	}
	
	public String getUserAttendingStatus(){
		return this.userAttendingStatus;
	}
	
	/************
	 * Setters
	 ***********/
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	public void setUserFirstName(String userFirstName){
		this.userFirstName = userFirstName;
	}
	
	public void setUserLastName(String userLastName){
		this.userLastName = userLastName;
	}
	
	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}
	
	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}
	
	public void setUserLocationLat(int userLocationLat){
		this.userLocationLat = userLocationLat;
	}
	
	public void setUserLocationLon(int userLocationLon){
		this.userLocationLon = userLocationLat;
	}
	
	public void setUserEta(String userEta){
		this.userEta = userEta;
	}
	
	public void setUserAttendingStatus(String userAttendingStatus){
		this.userAttendingStatus = userAttendingStatus;
	}
	
	
}
