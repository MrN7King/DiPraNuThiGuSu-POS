package org.example.cafepos;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;


public class HelloController {

    @FXML
    private ImageView cafeLogo;

    @FXML
    private TextField fp_UserName;

    @FXML
    private ComboBox<?> fp_Question;

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private AnchorPane fp_form;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private AnchorPane logIn_Form;

    @FXML
    private Hyperlink login_ForgotPass;

    @FXML
    private PasswordField login_Password;

    @FXML
    private TextField login_UserName;

    @FXML
    private Button login_btn;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changeBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_form;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Button side_AlreadyHave;

    @FXML
    private Button side_CreateAcount;

    @FXML
    private AnchorPane side_Form;

    @FXML
    private TextField signup_Answer;

    @FXML
    private AnchorPane signup_Form;

    @FXML
    private PasswordField signup_Password;

    @FXML
    private ComboBox<?> signup_Question;

    @FXML
    private TextField signup_UserName;

    @FXML
    private Button signup_btn;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    //login code
    public void loginBtn(){

        if (login_UserName.getText().isEmpty() || login_Password.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else {
            String selectData = "SELECT username, password FROM employee WHERE username = ? AND password = ?";
            connect = Database.connectDB();

            try {

                  prepare = connect.prepareStatement(selectData);
                  prepare.setString(1, login_UserName.getText());
                  prepare.setString(2, login_Password.getText());

                  result = prepare.executeQuery();

                  if(result.next())
                  {
                   //redirects if the login is correct

                      alert = new Alert(Alert.AlertType.INFORMATION);
                      alert.setTitle("Information Message");
                      alert.setHeaderText(null);
                      alert.setContentText("Login Successful");
                      alert.showAndWait();

                  }else{
                      alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Error Message");
                      alert.setHeaderText(null);
                      alert.setContentText("Incorrect username or password");
                      alert.showAndWait();
                  }

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    //registration code
    public void regBtn() {
        if (signup_UserName.getText().isEmpty() || signup_Password.getText().isEmpty() || signup_Question.getSelectionModel().getSelectedItem() == null || signup_Answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer, date)" + "VALUES (?,?,?,?,?) ";
            connect = Database.connectDB();

            try {

                //check whether username is already taken
                String checkUserName = "SELECT username FROM employee WHERE username = '" + signup_UserName.getText()+"'";
                prepare = connect.prepareStatement(checkUserName);
                result = prepare.executeQuery();

                if (result.next())
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("User Name is already in the database");
                    alert.showAndWait();
                }else {
                    //if username not taken
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, signup_UserName.getText());
                    prepare.setString(2, signup_Password.getText());
                    prepare.setString(3, (String) signup_Question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, signup_Answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Registration Successfull");
                    alert.showAndWait();

                    signup_UserName.setText("");
                    signup_Password.setText("");
                    signup_Question.getSelectionModel().clearSelection();
                    signup_Answer.setText("");

                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_Form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_AlreadyHave.setVisible(false);
                        side_CreateAcount.setVisible(true);
                    });
                    slider.play();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


        //for Questipns
        private String[] questionList = {"Your secret that no one knows ?", "Your favourite food ?", "Your pets name ?"};

        public void reqQuestionList() {
            List<String> listQ = new ArrayList<>();

            for (String data : questionList) {
                listQ.add(data);
            }

            ObservableList listData = FXCollections.observableArrayList(listQ);
            signup_Question.setItems(listData);
            fp_Question.setItems(listData);
        }

    //gets triggered when the forgot password link clicked
    public void switchForgotPassword(){
        fp_form.setVisible(true);
        logIn_Form.setVisible(false);
        reqQuestionList();
    }

    public void proceedBtn(){
        if (fp_UserName.getText().isEmpty()||fp_Question.getSelectionModel().getSelectedItem() == null ||fp_answer == null)
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Fill All the Blanks");
            alert.showAndWait();
        }else {
            String selectData = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer= ?";
            connect = Database.connectDB();

            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_UserName.getText());
                prepare.setString(2, (String) fp_Question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();

                if(result.next())
                {
                    np_form.setVisible(true);
                    fp_form.setVisible(false);

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void changePasswordBtn() throws SQLException {
        if(np_newPassword.getText().isEmpty()|| np_confirmPassword.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Fill All Fields");
            alert.showAndWait();
        }else {

            if(np_newPassword.getText().equals(np_confirmPassword.getText())){
                String getDate = "SELECT date FROM employee WHERE username = '" +fp_UserName.getText()+ "'";

                connect = Database.connectDB();

                try{

                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date ="";
                    if(result.next()){
                        date= result.getString("date");
                    }
                    String updatePass = "UPDATE employee SET password = '"
                            +np_newPassword.getText() + "', question ='"
                            +fp_Question.getSelectionModel().getSelectedItem() + "', answer ='"
                            +fp_answer.getText() + "', date ='"
                            +date+"' WHERE username = '"
                            +fp_UserName.getText() +"'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password Updated !");
                    alert.showAndWait();

                    logIn_Form.setVisible(true);
                    np_form.setVisible(false);

                    np_newPassword.setText("");
                    np_confirmPassword.setText("");
                    fp_answer.setText("");
                    fp_UserName.setText("");
                    fp_Question.getSelectionModel().clearSelection();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Passwords does not match");
                alert.showAndWait();
            }
        }
    }

    public void backToLogin()
    {
        logIn_Form.setVisible(true);
        fp_form.setVisible(false);
    }

    public void backToForgotPass()
    {
        np_form.setVisible(false);
        fp_form.setVisible(true);
    }

    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateAcount) {
            slider.setNode(side_Form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(true);
                side_CreateAcount.setVisible(false);

                logIn_Form.setVisible(true);
                fp_form.setVisible(false);
                np_form.setVisible(false);

                reqQuestionList();
            });
            slider.play();
        } else if (event.getSource() == side_AlreadyHave) {
            slider.setNode(side_Form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(false);
                side_CreateAcount.setVisible(true);
            });
            slider.play();
        }
    }
}