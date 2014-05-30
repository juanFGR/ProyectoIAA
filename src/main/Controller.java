package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loader.Loader;

import java.io.IOException;

public class Controller   {
@FXML Label jj;
    @FXML
    Parent root;

    @FXML
    protected void ee(ActionEvent event) throws IOException {


        new Loader();
    }
}
