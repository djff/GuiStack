/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guistack;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import stack.Stack;

/**
 *
 * @author Python Hacker
 */
public class StackFXMLController implements Initializable, Observer {
    
    private Label label;
    @FXML
    private TextField elementInput;
    @FXML
    private ListView<Integer> stackElements;
    @FXML
    private Button pushButton;
    @FXML
    private Button popButton;
    @FXML
    private Button topButton;
    @FXML
    private Button isFullButton;
    @FXML
    private Button isEmptyButton;
    @FXML
    private Button createButton;
    @FXML
    private Button emptyButton;
    
    Stack stack;
    Alert alert;
    ObservableList<Integer> items;
    StackTest  stacktest;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       this.pushButton.setDisable(true);
       this.popButton.setDisable(true);
       this.topButton.setDisable(true);
       this.emptyButton.setDisable(true);
       this.isEmptyButton.setDisable(true);
       this.isFullButton.setDisable(true);
       this.stackElements.setVisible(false);
       items = stackElements.getItems();
//       stacktest = new TestSTackClass(stack);
    }    

    @FXML
    private void handlePushAction(ActionEvent event) {
        int elt = Integer.valueOf(elementInput.getText());
        elementInput.setText(null);
        try {
            stack.push(elt);
        } catch (Exception ex) {
            ErrorDialog(String.valueOf(ex), "An Error occured");
        }
    }

    @FXML
    private void handlePopAction(ActionEvent event) {
        try {
            stack.pop();
        } catch (Exception ex) {
            ErrorDialog(String.valueOf(ex), "An Error occured");
        }
    }

    @FXML
    private void handleTopAction(ActionEvent event) {
        try {
            int value = stack.top();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert_dialog("Information", "Value at the Top is ::: " + value, alert);
        } catch (Exception ex) {
            ErrorDialog(String.valueOf(ex), "An Error Occured");
        }
    }

    @FXML
    private void handleIsFullAction(ActionEvent event) {
        String message;
        Boolean isFull = stack.isFull();
        if (isFull) message = "TRUE ::: Stack is Full";
        else message = "FALSE ::: Stack is not Full";
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert_dialog("Information", message, alert);
    }

    @FXML
    private void handleIsEmptyAction(ActionEvent event) {
        String message;
        Boolean isEmpty = stack.isEmpty();
        if (isEmpty) message = "TRUE ::: Stack is Empty";
        else message = "FALSE ::: Stack is not Empty";
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert_dialog("Information", message, alert);
    }

    @FXML
    private void handleCreateAction(ActionEvent event) throws Exception {
       this.pushButton.setDisable(false);
       this.popButton.setDisable(false);
       this.topButton.setDisable(false);
       this.emptyButton.setDisable(false);
       this.isEmptyButton.setDisable(false);
       this.isFullButton.setDisable(false);
       this.stackElements.setVisible(true);
       
       // Creating the new stack
       stack = Stack.Create();
       items.clear();
       stacktest = new StackTest(stack);
       stack.addObserver(this);
       stack.addObserver(stacktest);
       stacktest.start();
       
    }

    @FXML
    private void handleEmptyAction(ActionEvent event) {
        stack.empty();
    }
    
    private void alert_dialog(String error, String message, Alert alert) {
        /*
          Method used to handle Alert boxes.
        */
            alert.setTitle(error);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();    
    }
    
    private void ErrorDialog(String exceptionText, String errorType) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Look, An Error Occured, Look at the details");
        alert.setContentText(errorType);
        
        Label label = new Label("::: The exception stacktrace was :::");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            System.out.println("Something happened in the Stack class " + arg);
            if(Integer.valueOf(arg.toString()) == 0){
                try {
                    items.add(0, stack.top());
                } catch (Exception ex) {
                    Logger.getLogger(StackFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (Integer.valueOf(arg.toString()) == 1){
                items.remove(0);
            }
            else items.clear();
        });
        
  }
}
