package classes;

import java.util.ArrayList;
import java.util.List;

public class Language {
	private List<String> listOfLetters = new ArrayList<>();
	
	public void appendToList(String str)
	{
		listOfLetters.add(str);
	}
	
	public boolean checkIfExists(String str)
	{
		return listOfLetters.contains(str);
	}
	
	public int getSize()
	{
		return listOfLetters.size();
	}
	
	public List<String> getList()
	{
		return listOfLetters;
	}

	
	@Override
	public String toString() {
		return "Sigma= " + listOfLetters;
	}	
}
