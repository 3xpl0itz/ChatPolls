package com.expl0itz.chatpolls.util;

import java.util.ArrayList;

public class EachPoll {

	//Multiple Poll Support :)
	private String title, description, creator, time = "";
	private int currNum = 1;
	private ArrayList<EachOption> allOpps = new ArrayList<>();
	
	public EachPoll(String t, String d, String c, String time, int curr)
	{
		title = t;
		description = d;
		creator = c;
		this.time = time;
		currNum = curr;
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
	
	public void setNumber(int i)
	{
		currNum = i;
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
	
	public int getNum()
	{
		return currNum;
	}
	
	public ArrayList<EachOption> getOptions()
	{
		return allOpps;
	}
}
