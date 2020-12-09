package com.fes.app.view;

import java.awt.Button;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessagesBox {
	@FXML
	Label msg;
	@FXML
	Button ok;
	
	public static void show (String msg ) throws IOException {
		
		
		FXMLLoader loder = new FXMLLoader(MessagesBox.class.getResource("MessagesBox.fxml"));
		Stage stage = new Stage();
		Parent view = loder.load();
		MessagesBox contol  = loder.getController();
		contol.msg.setText(msg);
		stage.setScene(new Scene(view));
		stage.initStyle(StageStyle.UTILITY);
		 stage.setTitle("Message Box");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
	
	public void close() {

		msg.getScene().getWindow().hide();

	}
}
