package com.main;

import static com.main.CommonUtil.base64Decode;
import static com.main.CommonUtil.base64Encode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Base64Converter extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private static final String TITLE = "Base64 Converter";
	private static final String TEXT_DECODE_MODE = "Decode Mode";
	private static final String TEXT_ENCODE_MODE = "Encode Mode";
	private static final boolean IS_ENCODE_MODE = true;

	private TextArea input;
	private TextArea output;

	@Override
	public void start(Stage primaryStage) {

		ToggleGroup toggleGroup = new ToggleGroup();

		RadioButton toggleEnocde = new RadioButton(TEXT_ENCODE_MODE);
		toggleEnocde.setToggleGroup(toggleGroup);
		toggleEnocde.setSelected(IS_ENCODE_MODE);
		toggleEnocde.setOnAction(e -> setToggleInfo(toggleEnocde.isSelected()));

		RadioButton toggleDecode = new RadioButton(TEXT_DECODE_MODE);
		toggleDecode.setToggleGroup(toggleGroup);
		toggleDecode.setSelected(!IS_ENCODE_MODE);
		toggleDecode.setOnAction(e -> setToggleInfo(toggleEnocde.isSelected()));

		Pane blankPane = new Pane();

		CheckBox checkBoxOnTop = new CheckBox("Always On Top");
		checkBoxOnTop.setOnAction(e -> primaryStage.setAlwaysOnTop(checkBoxOnTop.isSelected()));
		checkBoxOnTop.setSelected(false);

		ToolBar toolBar = new ToolBar(toggleEnocde, toggleDecode, blankPane, checkBoxOnTop);

		HBox.setHgrow(blankPane, Priority.ALWAYS);

		Button btnConvert = createButton(new Button("Convert"));
		btnConvert.setOnAction(e -> convert(toggleEnocde.isSelected()));
		btnConvert.setDefaultButton(true);

		Button btnClear = createButton(new Button("Clear"));
		btnClear.setOnAction(e -> clear());

		HBox panelBtn = createPanel(new HBox(5));
		panelBtn.getChildren().addAll(btnConvert, btnClear);

		HBox.setHgrow(btnConvert, Priority.ALWAYS);
		HBox.setHgrow(btnClear, Priority.ALWAYS);

		input = createTextArea();

		output = createTextArea();
		output.setOnMouseClicked(e -> output.setPromptText(""));

		setToggleInfo(toggleEnocde.isSelected());

		VBox rootLayout = new VBox(5);
		rootLayout.getChildren().addAll(toolBar, panelBtn, input, output);

		VBox.setVgrow(input, Priority.ALWAYS);
		VBox.setVgrow(output, Priority.ALWAYS);

		primaryStage.setScene(new Scene(rootLayout, 500, 400));
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(400);
		primaryStage.setTitle(TITLE);
		primaryStage.setAlwaysOnTop(checkBoxOnTop.isSelected());
		primaryStage.show();
	}

	private static final String INPUT_PROMPT_TEXT = "paste your text here (%s)";

	private void setToggleInfo(boolean isEncode) {
		String promptText = isEncode ? TEXT_ENCODE_MODE : TEXT_DECODE_MODE;
		input.setPromptText(String.format(INPUT_PROMPT_TEXT, promptText));
	}

	private void clear() {
		input.setText("");
		output.setText("");
		output.setPromptText("");
	}

	private void convert(boolean isEncode) {
		String inputText = input.getText();

		clear();

		if (inputText == null || inputText.length() == 0) {
			output.setPromptText("input not found");
			return;
		}

		try {
			output.setText(isEncode ? base64Encode(inputText) : base64Decode(inputText));
		} catch (Exception e) {
			output.setPromptText(String.format("Convert Error : %s", e.getMessage()));
		}
	}

	private <T extends Region> T createPanel(T panel) {
		panel.setPadding(new Insets(2));
		return panel;
	}

	private TextArea createTextArea() {
		TextArea textArea = createPanel(new TextArea());
		textArea.setWrapText(true);
		return textArea;
	}

	private <T extends Region> T createButton(T button) {
		button.setPrefHeight(50);
		button.setMaxHeight(100);
		button.setMaxWidth(1000);
		button.setStyle("-fx-font-size:16");
		return button;
	}

}
