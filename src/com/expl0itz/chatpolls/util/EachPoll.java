package com.expl0itz.chatpolls.util;

import java.util.ArrayList;

public class EachPoll {

	//Multiple Polls Support :)
	private String title, description, creator, time = "";
	private int currNum = 1;
	private int delay = 0;
	private boolean clearChat = false;
	private boolean isVotable = false;
	private boolean hasRewards = false;
	private ArrayList<EachOption> allOpps = new ArrayList<>();
	private ArrayList<String> allRewards = new ArrayList<>();
	
	public EachPoll(String title, String description, String creator, String time, int delay, int currNum, 
			boolean isVotable, boolean hasRewards, boolean clearChat, ArrayList<String> allRewards)
	{
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.time = time;
		this.isVotable = isVotable;
		this.currNum = currNum;
		this.delay = delay;
		this.clearChat = clearChat;
		this.hasRewards = hasRewards;
		this.allRewards = allRewards;
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
	
	public void setDelay(int d)
	{
		delay = d;
	}
	
	public void toggleVotable(boolean isVotable)
	{
		this.isVotable = isVotable;
	}
	
	public void toggleClearChat(boolean clearChat)
	{
		this.clearChat = clearChat;
	}
	
	public void setNumber(int i)
	{
		currNum = i;
	}
	
	public void toggleRewards(boolean reward)
	{
		this.hasRewards = reward;
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
	
	public int getDelay()
	{
		return delay;
	}
	
	public boolean isClearChat()
	{
		return clearChat;
	}
	
	public boolean hasRewards()
	{
		return hasRewards;
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
