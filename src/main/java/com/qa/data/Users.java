package com.qa.data;

public class Users {
	
	

	String userId;
	String userName;
	String rewardPoints;
	String rewardBalance;
	String createDate;
	String createdBy;

	
	public Users() {
		
	}
	
public Users(String userName, String userId, String rewardBalance, String rewardPoints) {

	this.userName=userName;
	this.userId=userId;
	this.rewardBalance=rewardBalance;
	this.rewardPoints=rewardPoints;
	}


//getters and setters

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getRewardPoints() {
	return rewardPoints;
}

public void setRewardPoints(String rewardPoints) {
	this.rewardPoints = rewardPoints;
}

public String getRewardBalance() {
	return rewardBalance;
}

public void setRewardBalance(String rewardBalance) {
	this.rewardBalance = rewardBalance;
}


}
