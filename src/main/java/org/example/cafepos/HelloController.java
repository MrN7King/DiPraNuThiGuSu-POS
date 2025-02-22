package org.example.cafepos;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class HelloController {

    @FXML
    private ImageView cafeLogo;

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

    @FXML
    private Button side_AlreadyHave;

    @FXML
    private Button side_CreateAcount;


    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateAcount) {
            slider.setNode(side_Form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(true);
                side_CreateAcount.setVisible(false);
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