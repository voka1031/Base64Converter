package com.main;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Base64Converter extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	Stage window;
	TextArea input;
	TextArea output;
	ToggleGroup toggleGroup;
	RadioButton toggleDecode;
	RadioButton toggleEnocde;
	CheckBox checkBoxOnTop;
	Button btnConvert;
	HBox menu;
	VBox content;

	private static final String TITLE = "Base64 Converter";

	private static final Integer BTN_HEIGHT = 100;

	private static final boolean IS_DECODE_MODE = true;

	private static final String INPUT_PROMPT_TEXT = "paste your text here (%s)";

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		toggleGroup = new ToggleGroup();

		toggleDecode = new RadioButton("Decode Mode");
		toggleDecode.setPrefHeight(BTN_HEIGHT);
		toggleDecode.setMaxWidth(Double.MAX_VALUE);
		toggleDecode.setToggleGroup(toggleGroup);
		toggleDecode.setOnAction(e -> toggleInfo());
		toggleDecode.setSelected(IS_DECODE_MODE);

		toggleEnocde = new RadioButton("Encode Mode");
		toggleEnocde.setPrefHeight(BTN_HEIGHT);
		toggleEnocde.setMaxWidth(Double.MAX_VALUE);
		toggleEnocde.setToggleGroup(toggleGroup);
		toggleEnocde.setOnAction(e -> toggleInfo());
		toggleEnocde.setSelected(!IS_DECODE_MODE);

		checkBoxOnTop = new CheckBox("Always On Top");
		checkBoxOnTop.setPrefHeight(BTN_HEIGHT);
		checkBoxOnTop.setMaxWidth(Double.MAX_VALUE);
		checkBoxOnTop.setOnAction(e -> window.setAlwaysOnTop(checkBoxOnTop.isSelected()));
		checkBoxOnTop.setSelected(false);

		menu = new HBox(30);
		menu.getChildren().addAll(toggleDecode, toggleEnocde, checkBoxOnTop);

		btnConvert = new Button("Convert");
		btnConvert.setPrefHeight(BTN_HEIGHT);
		btnConvert.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnConvert.setOnAction(e -> handleInput());
		btnConvert.setDefaultButton(true);

		input = new TextArea();
		input.setPromptText(String.format(INPUT_PROMPT_TEXT, IS_DECODE_MODE ? toggleDecode.getText() : toggleEnocde.getText()));

		output = new TextArea();
		output.setOnMouseClicked(e -> output.setPromptText(""));

		content = new VBox(5);
		content.getChildren().addAll(menu, btnConvert, input, output);
		content.setMaxHeight(500);

		window.setScene(new Scene(content, 400, 300));
		window.setTitle(TITLE);
		window.setMaxHeight(400);
		window.setAlwaysOnTop(checkBoxOnTop.isSelected());
		window.show();

	}

	private void toggleInfo() {
		if (toggleDecode.isSelected())
			input.setPromptText(String.format(INPUT_PROMPT_TEXT, toggleDecode.getText()));
		else if (toggleEnocde.isSelected())
			input.setPromptText(String.format(INPUT_PROMPT_TEXT, toggleEnocde.getText()));
	}

	private void handleInput() {
		if (input.getText() == null || input.getText().length() == 0) {
			output.setPromptText("input isBlank");
			return;
		}

		try {
			if (toggleDecode.isSelected())
				output.setText(new String(Base64.getDecoder().decode(input.getText()), StandardCharsets.UTF_8));
			else
				output.setText(Base64.getEncoder().encodeToString(input.getText().getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			output.setPromptText(String.format("Convert Error : %s", e.getMessage()));
		}

		input.setText("");
	}

}
