package classes;

import java.util.HashMap;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public abstract class State {

	private String stateName;
	private StackPane stack;
	private HashMap<String, String> map = new HashMap<>();
	
	
	
	public State(String stateName, StackPane stack) {
		this.stateName=stateName;
		this.stack=stack;
	}

	public boolean keyExists(String letter) {
		return map.containsKey(letter);
	}
	
	public String getStateName(String letter) {
		return map.get(letter);
	}

	public boolean putState(String letter, String stateName) {
		if (map.containsKey(letter))
			return false;
		map.put(letter, stateName);
		return map.containsKey(letter);
	}
	
	public String getName()
	{
		return stateName;
	}
	
	public StackPane getStack()
	{
		return stack;
	}

	// public boolean deleteState()

}
