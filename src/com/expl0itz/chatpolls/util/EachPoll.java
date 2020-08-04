package com.expl0itz.chatpolls.util;

import java.util.ArrayList;

public class EachPoll {

	//Multiple Polls Support :)
	private String title, description, creator, time = "";
	private int currNum = 1;
	private boolean isVotable = false;
	private ArrayList<EachOption> allOpps = new ArrayList<>();
	private ArrayList<String> allRewards = new ArrayList<>();
	
	public EachPoll(String t, String d, String c, String time, int curr, boolean isVotable, ArrayList<String> allr)
	{
		title = t;
		description = d;
		creator = c;
		this.time = time;
		this.isVotable = isVotable;
		currNum = curr;
		allRewards = allr;
	}
	
	public void addOptions(EachOption eaOpp)
	{
		allOpps.add(eaOpp);
	}
	
	public void setTitle(String t)
	{
		title = t;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	
	public void setCreator(String c)
	{
		creator = c;
	}
	
	public void setTime(String t)
	{
		time = t;
	}
	
	public void setVotableTrue()
	{
		isVotable = true;
	}
	
	public void setVotableFalse()
	{
		isVotable = false;
	}
	
	public void setNumber(int i)
	{
		currNum = i;
	}
	
	public void addReward(String in)
	{
		allRewards.add(in);
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getCreator()
	{
		return creator;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public boolean allowsVoting()
	{
		return isVotable;
	}
	
	public int getNum()
	{
		return currNum;
	}
	
	public ArrayList<EachOption> getOptions()
	{
		return allOpps;
	}
	
	public ArrayList<String> getRewards()
	{
		return allRewards;
	}
}
