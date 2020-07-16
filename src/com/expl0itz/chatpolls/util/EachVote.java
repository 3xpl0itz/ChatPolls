package com.expl0itz.chatpolls.util;

public class EachVote {

	private String chosenOption, user = "";
	
	public EachVote(String chosenOption, String user)
	{
		this.chosenOption = chosenOption;
		this.user = user;
	}
	
	public String getChosenOption()
	{
		return chosenOption;
	}
	
	public String getUsername()
	{
		return user;
	}
}
