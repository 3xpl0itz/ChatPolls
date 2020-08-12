package com.expl0itz.chatpolls.util;

import java.util.ArrayList;

public class EachOption {

	//We'll use this class to store the stats of each poll.
	
	private String nameOfOption = "";
	private int choiceNumber = 0;
	private ArrayList<EachVote> allVotes = new ArrayList<>();
	
	public EachOption(String nameOfOption, int choiceNumber)
	{
		this.nameOfOption = nameOfOption;
		this.choiceNumber = choiceNumber;
	}
	
	public void setNameOfOption(String s)
	{
		nameOfOption = s;
	}
	
	public void setChoiceNumber(int i)
	{
		choiceNumber = i;
	}
	
	public String getOptionName()
	{
		return nameOfOption;
	}
	
	public int getChoiceNumber()
	{
		return choiceNumber;
	}
	
	public int getNumVotes()
	{
		return allVotes.size();
	}
	
	public ArrayList<EachVote> getVotes()
	{
		return allVotes;
	}
	
	public void addVote(String nameOfOption, String user)
	{
		if (nameOfOption.equals(this.nameOfOption));
		{
			EachVote eaVote = new EachVote(nameOfOption, user);
			allVotes.add(eaVote);
		}
	}
}
