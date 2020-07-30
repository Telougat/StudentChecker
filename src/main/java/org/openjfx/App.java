package org.openjfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        var label = new Label("Accueil");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        /****************************************** Début page connexion *********************************************/
        GridPane layout_page_login = new GridPane();

        layout_page_login.setPadding(new Insets(20));
        layout_page_login.setHgap(25);
        layout_page_login.setVgap(15);

        Label label_nom_utilisateur = new Label("Nom d'utilisateur : ");
        TextField input_nom_utilisateur = new TextField();

        Label label_password = new Label("Mot de passe : ");
        TextField input_password = new TextField();

        Button bouton_connexion = new Button("Se connecter");
        GridPane.setHalignment(bouton_connexion, HPos.RIGHT);

        bouton_connexion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(input_nom_utilisateur.getText().length() > 0)
                {
                    if(input_password.getText().length() > 0)
                    {

                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText("Veuillez remplir le champs 'mot de passe'");

                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Veuillez remplir le champs 'nom utilisateur'");

                    alert.showAndWait();
                }

            }
        });

        layout_page_login.add(label_nom_utilisateur,30,25);
        layout_page_login.add(input_nom_utilisateur,31,25);
        layout_page_login.add(label_password,30,26);
        layout_page_login.add(input_password,31,26);
        layout_page_login.add(bouton_connexion,31,27);


        var page_login = new Scene(layout_page_login, stage.getWidth(), stage.getHeight());

        /******************************************** Fin page connexion *********************************************/


        /******************************************** Début page profil  *********************************************/
        /******************************************** Fin  page profil  *********************************************/

        stage.setScene(page_login);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}