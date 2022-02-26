package com.example.gui.controller;

import com.example.gui.domain.User;
import com.example.gui.service.Service;
import com.example.gui.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class UserAccountProfileController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();

    @FXML
    private Button conversationButton;

    @FXML
    private Button confirmationButton;

    @FXML
    private Button eventsButton;

    @FXML
    private Button exploreButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button postsButton;

    @FXML
    private Button editPasswordButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button fileButton;

    @FXML
    private Button setAboutButton;

    @FXML
    private TextArea aboutTextArea;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            service.logoutUser();
        } else if (event.getSource() == confirmationButton) {
            try {
                sceneUtils.switchToDeleteConfirmationScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == logoutButton) {
            try {
                sceneUtils.switchToLoginScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == conversationButton) {
            try {
                sceneUtils.switchToUserConversationScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == eventsButton) {
            try {
                sceneUtils.switchToUserEventScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == exploreButton) {
            try {
                sceneUtils.switchToUserExploreScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == profileButton) {
            try {
                sceneUtils.switchToUserProfileScene(event, this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == postsButton) {
            try {
                sceneUtils.switchToShowPostsScene(this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == editPasswordButton) {
            try {
                sceneUtils.switchToEditPasswordScene(this.service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == setAboutButton) {
            service.updateUser("", aboutTextArea.getText(), "");
            setLabelText(service.getCurrentUser());
        } else if (event.getSource() == fileButton) {
            imageChooser();
        }
    }

    public void imageChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\zimbr\\OneDrive\\Desktop\\Proiecte JAVA\\GUI\\src\\main\\resources\\com\\example\\gui\\images"));

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

        fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterpng, extFilterJPG, extFilterjpg);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            service.savePath(file.getAbsolutePath());
        }
        setLabelText(service.getCurrentUser());
    }

    public void setLabelText(User user) {
        welcomeLabel.setText(user.getLastName() + " " + user.getFirstName() + "\nFriends:" + service.getNrOfFriends(user.getId()));
        aboutTextArea.setText(user.getAbout());
        if (service.getCurrentUser().getPath() != null) {
            String[] paths = service.getCurrentUser().getPath().split(",");
            imageView2.setImage(new Image(paths[0]));
            if (paths.length > 1) {
                imageView1.setImage(new Image(paths[1]));
            }
        }
    }

}
