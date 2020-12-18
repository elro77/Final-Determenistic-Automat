package boundry;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import classes.AcceptState;
import classes.DeclineState;
import classes.Language;
import classes.State;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UserWindow {
	@FXML
	private Label lblWordLbl;
	@FXML
	private Slider sldSpeedRun;

	@FXML
	private Button btnStopRun;
	@FXML
	private VBox vboxTools;
	@FXML
	private AnchorPane apPlacmentPane;
	@FXML
	private ImageView btnCancel;
	@FXML
	private AnchorPane apChooseWord;
	@FXML
	private ComboBox<String> comboWordSelection;
	@FXML
	private Button btnAccepdTheWord;

	@FXML
	private CheckBox cbConnectMode;

	@FXML
	private Label lblConnectionsLeft;

	@FXML
	private AnchorPane apLetterChose;

	@FXML
	private TextField tfOne;

	@FXML
	private TextField tfTwo;

	@FXML
	private TextField tfThree;

	@FXML
	private TextField tfFour;

	@FXML
	private Button btnAccept;

	@FXML
	private Button btnClear;

	@FXML
	private AnchorPane apBuilder;

	@FXML
	private Label lblEnteredLetters;

	@FXML
	private Circle circleAccept;

	@FXML
	private Circle circleDecline;

	@FXML
	private Button btnClearDesign;

	@FXML
	private Button btnRun;
	@FXML
	private AnchorPane apEnterWordToRunWIndow;

	@FXML
	private Button btnAccepdTheWordToRun;

	@FXML
	private ImageView btnCancelRuningWindow;

	@FXML
	private TextField tfEnteredWordToRun;

	private Language language;
	private List<StackPane> stackList = new ArrayList<>();
	private List<StackPane> connectedStackList = new ArrayList<>();
	private HashMap<String, State> map = new HashMap<>(); // map that key is the name and value is the state
	private State[] statePair = new State[2];

	private String chosenWord;
	private int connectionCounter = 0;
	private int index = 0;
	private State startState;
	private State currentState;
	private char[] arrayOfRunningWord;
	private int indexOfArrayRuning;

	private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	private Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
	private Label[] labelArray;
	Timeline timeline;

	@FXML
	void initialize() {
		apLetterChose.setVisible(true);
		lblConnectionsLeft.setText("Connections left to connect = " + connectionCounter);

	}

	public void btnAcceptEnter() {
		btnAccept.setOpacity(0.6);
	}

	public void btnAcceptExit() {
		btnAccept.setOpacity(1);
	}

	public void btnAcceptClicked() {
		if (checkIfLettersAreEntered() == false) {
			errorAlert.setContentText("Please enter at least 1 letter");
			errorAlert.show();
		} else {

			language = new Language();
			if (!tfOne.getText().trim().isEmpty())
				language.appendToList(tfOne.getText());
			if (!tfTwo.getText().trim().isEmpty())
				language.appendToList(tfTwo.getText());
			if (!tfThree.getText().trim().isEmpty())
				language.appendToList(tfThree.getText());
			if (!tfFour.getText().trim().isEmpty())
				language.appendToList(tfFour.getText());

			lblEnteredLetters.setText(language.toString());
			setComboBox();
			apLetterChose.setVisible(false);
			apBuilder.setVisible(true);
		}

	}

	public void AcceptStateCreate() {
		cbConnectMode.setSelected(false);
		createCircle(true);

	}

	public void AcceptStateEntered() {
		circleAccept.setOpacity(0.8);

	}

	public void AcceptStateExited() {
		circleAccept.setOpacity(1);
	}

	public void DeclineStateCreate() {
		cbConnectMode.setSelected(false);
		createCircle(false);

	}

	public void DeclineStateEntered() {
		this.circleDecline.setOpacity(0.8);
	}

	public void DeclineStateExited() {
		this.circleDecline.setOpacity(1);
	}

	public void CloseConnectionWindowEntered() {
		btnCancel.setOpacity(0.85);
	}

	public void CloseConnectionWindowExited() {
		btnCancel.setOpacity(1);
	}

	public void CloseConnectionWindowClicked() {
		statePair[0].getStack().setBorder(Border.EMPTY);
		statePair[1].getStack().setBorder(Border.EMPTY);
		statePair[0] = null;
		statePair[1] = null;
		cbConnectMode.setSelected(false);
		this.apBuilder.setDisable(false);
		this.apChooseWord.setVisible(false);
	}

	public void btnAccepdTheWordClicked() {
		this.chosenWord = this.comboWordSelection.getSelectionModel().getSelectedItem().toString();
		if (connectStates() == false) {
			this.errorAlert.setContentText("Already connect with: " + chosenWord);
			errorAlert.show();
		} else {
			this.apBuilder.setDisable(false);
			this.apChooseWord.setVisible(false);
			System.out.println(chosenWord);
		}
	}

	public void cbConnectModeClicked() {
		if (cbConnectMode.isSelected()) {
			for (int i = 0; i < 2; i++) {
				if (statePair[i] != null) {
					statePair[i].getStack().setBorder(Border.EMPTY);
					statePair[i] = null;
				}
			}
		}
	}

	public void btnClearDesignClicked() {
		stackList.clear();
		connectedStackList.clear();
		map.clear();
		statePair[0] = null;
		statePair[1] = null;
		chosenWord = "";
		connectionCounter = 0;
		lblConnectionsLeft.setText("Connections left to connect = " + connectionCounter);
		index = 0;
		startState = null;
		btnRun.setDisable(true);
		this.apPlacmentPane.getChildren().clear();
	}

	public void btnRunClicked() {// elro
		apEnterWordToRunWIndow.setVisible(true);

	}

	public void btnCancelRuningWindowEntered() {
		btnCancel.setOpacity(0.85);
	}

	public void btnCancelRuningWindowExited() {
		btnCancel.setOpacity(1);
	}

	public void btnCancelRuningWindowClicked() {
		vboxTools.setDisable(false);
		apEnterWordToRunWIndow.setVisible(false);
	}

	public void btnStopRunClicked() {
		timeline.stop();
		enableRequiredItems();
	}

	public void btnAccepdTheWordToRun() { // elro
		if (checkIfWordEnteredIsValid() == false) {
			errorAlert.setHeaderText("Error in the entered Word");
			errorAlert.setContentText("The word entred contains letters that are incorrect, please enter a valid word");
			errorAlert.show();
		} else {
			apEnterWordToRunWIndow.setVisible(false);
			if (currentState != null) {
				currentState.getStack().setBorder(Border.EMPTY);
			}
			this.currentState = this.startState;
			this.arrayOfRunningWord = this.tfEnteredWordToRun.getText().toCharArray();
			if(labelArray!=null && labelArray.length!=0)
			{
				for (int i = 0; i < labelArray.length; i++) {	
					this.apBuilder.getChildren().remove(labelArray[i]);	
				}
			}
			labelArray = new Label[arrayOfRunningWord.length];
			for (int i = 0; i < arrayOfRunningWord.length; i++) {	
				labelArray[i]=new Label();
				labelArray[i].setFont(new Font("Arial", 18));
				labelArray[i].setText(arrayOfRunningWord[i] + "");
				labelArray[i].setLayoutX(this.lblWordLbl.getLayoutX()+(70+i*20));
				labelArray[i].setLayoutY(this.lblWordLbl.getLayoutY());
				this.apBuilder.getChildren().add(labelArray[i]);
			}

			this.indexOfArrayRuning = 0;
			startRunning();

		}
	}

	////////////////////// private functions //////////////////

	private boolean checkIfWordEnteredIsValid() {
		char[] charArray = this.tfEnteredWordToRun.getText().toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (language.checkIfExists(charArray[i] + "") == false)
				return false;
		}
		return true;
	}

	private boolean checkIfLettersAreEntered() {
		if (tfOne.getText().trim().isEmpty() && tfTwo.getText().trim().isEmpty() && tfThree.getText().trim().isEmpty()
				&& tfFour.getText().trim().isEmpty()) {
			return false;
		}
		return true;
	}

	private void increaseConnectionCounter() {
		connectionCounter += this.language.getSize();
		this.btnRun.setDisable(true);
		lblConnectionsLeft.setText("Connections left to connect = " + connectionCounter);
	}

	private void createCircle(boolean status) {
		increaseConnectionCounter();
		Circle circle = new Circle();
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(50.0f);
		if (status == true)
			circle.setFill(Color.LIGHTGREEN);
		else
			circle.setFill(Color.LIGHTCORAL);
		Text text = new Text("q" + index);
		StackPane stack = new StackPane();
		stack.getChildren().addAll(circle, text);
		stack.setLayoutX(this.apBuilder.getLayoutX() + 200);
		stack.setLayoutY(this.apBuilder.getLayoutY() + 200);
		stackList.add(stack);
		stack.setOnMouseEntered(e -> {
			stack.setOpacity(0.8);
		});
		stack.setOnMouseExited(e -> {
			stack.setOpacity(1);
		});
		stack.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (cbConnectMode.isSelected()) {
					stack.setBorder(new Border(new BorderStroke(Color.INDIGO, BorderStrokeStyle.SOLID,
							CornerRadii.EMPTY, BorderWidths.DEFAULT)));
					if (statePair[0] == null) {
						statePair[0] = this.map.get(text.toString());
					} else {
						statePair[1] = this.map.get(text.toString());
						this.apBuilder.setDisable(true);
						this.apChooseWord.setVisible(true);
					}
				}

			} else if (e.getButton() == MouseButton.SECONDARY) {
				this.errorAlert.setContentText("right clicked");
				errorAlert.show();
				// delete here
			}
		});
		stack.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (!connectedStackList.contains(stack) && !cbConnectMode.isSelected()) {
					stack.setLayoutX(mouseEvent.getSceneX() - 50.0);
					stack.setLayoutY(mouseEvent.getSceneY() - 50.0);
				}
			}
		});
		apPlacmentPane.getChildren().add(stack);
		State state;
		if (status == true)
			state = new AcceptState(text.toString(), stack);
		else
			state = new DeclineState(text.toString(), stack);
		map.put(text.toString(), state);
		if (index == 0)
			this.startState = state;
		index++;
	}

	private boolean connectStates() {
		// do the connections here

		if (statePair[0].keyExists(chosenWord))
			return false;
		statePair[0].putState(chosenWord, statePair[1].getName());

		statePair[0].getStack().setBorder(Border.EMPTY);
		statePair[1].getStack().setBorder(Border.EMPTY);
		makeArcTo(statePair[0].getStack().getLayoutX() + 50.0, statePair[0].getStack().getLayoutY(),
				statePair[1].getStack().getLayoutX() + 50.0, statePair[1].getStack().getLayoutY());
		connectedStackList.add(statePair[0].getStack());
		connectedStackList.add(statePair[1].getStack());
		statePair[0] = null;
		statePair[1] = null;
		connectionCounter--;
		if (connectionCounter == 0)
			this.btnRun.setDisable(false);
		lblConnectionsLeft.setText("Connections left to connect = " + connectionCounter);
		return true;

	}

	private void setComboBox() {
		for (String str : this.language.getList())
			comboWordSelection.getItems().add(str);
	}

	private void makeArcTo(double startX, double startY, double endX, double endY) {
		Path path = new Path();

		// Moving to the starting point
		MoveTo moveTo = new MoveTo();
		moveTo.setX(endX);
		moveTo.setY(endY);

		// Instantiating the arcTo class
		ArcTo arcTo = new ArcTo();
		Label word = new Label(chosenWord);
		chosenWord = null;
		word.setLayoutX(startX + 0.5);
		word.setLayoutY(startY - 0.5);

		// setting properties of the path element arc
		arcTo.setX(startX);
		arcTo.setY(startY);

		arcTo.setRadiusX(-10.0);
		arcTo.setRadiusY(-10.0);

		// Adding the path elements to Observable list of the Path class

		path.getElements().add(moveTo);
		path.getElements().add(arcTo);
		this.apPlacmentPane.getChildren().add(path);
		this.apPlacmentPane.getChildren().add(word);
	}

	private void disableUnrequiredItems() {
		circleAccept.setDisable(true);
		circleDecline.setDisable(true);
		cbConnectMode.setSelected(false);
		cbConnectMode.setDisable(true);
		btnRun.setDisable(true);
		btnClearDesign.setDisable(true);
		btnStopRun.setDisable(false);
	}

	private void enableRequiredItems() {
		circleAccept.setDisable(false);
		circleDecline.setDisable(false);
		cbConnectMode.setDisable(false);
		btnRun.setDisable(false);
		btnClearDesign.setDisable(false);
		btnStopRun.setDisable(true);
	}

	private void startRunning()// function that runs on the word
	{

		this.currentState.getStack().setBorder(new Border(
				new BorderStroke(Color.INDIGO, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		disableUnrequiredItems();
		timeline = new Timeline(
				new KeyFrame(Duration.millis(2000 * (sldSpeedRun.getValue() / 100)), ae -> doSomething()));
		timeline.play();

	}

	private void doSomething() { // functions that do the tick and the running stuff
		if (indexOfArrayRuning < this.arrayOfRunningWord.length) {
			if (currentState.keyExists(arrayOfRunningWord[indexOfArrayRuning] + "")) {
				State nextState = this.map.get(currentState.getStateName(arrayOfRunningWord[indexOfArrayRuning] + ""));
				currentState.getStack().setBorder(Border.EMPTY);
				nextState.getStack().setBorder(new Border(new BorderStroke(Color.INDIGO, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				
				
				labelArray[indexOfArrayRuning].setStyle("-fx-font-weight: bold");
				labelArray[indexOfArrayRuning].setTextFill(Color.MEDIUMAQUAMARINE);
				labelArray[indexOfArrayRuning].setFont(new Font("Arial", 20));
				currentState = nextState;
				this.indexOfArrayRuning++;
				timeline = new Timeline(new KeyFrame(Duration.millis(2000 * (sldSpeedRun.getValue() / 100)),
						ae -> doSomething()));
				timeline.play();
			} else {
				timeline.stop();

				System.out.println("Error");
			}

		} else {
			if (currentState instanceof DeclineState) {
				this.confirmAlert.setHeaderText("Word Declined!");
			}
			if (currentState instanceof AcceptState) {
				this.confirmAlert.setHeaderText("Word Accepted!");
			}
			confirmAlert.show();
			timeline.stop();
			enableRequiredItems();
		}
	}
}
