package org.openjfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.model.Login;

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

        /******************************************** Début page profil  *********************************************/

        //HBox barre_navigation = getNavigationBar();
        GridPane barre_navigation = getPageProfil();

        VBox layout_general = new VBox();

        layout_general.getChildren().add(barre_navigation);

        var page_profil = new Scene(layout_general, stage.getWidth(), stage.getHeight());


        /******************************************** Fin  page profil  *********************************************/

        /****************************************** Début page connexion *********************************************/
        GridPane layout_page_login = new GridPane();

        layout_page_login.setPadding(new Insets(20));
        layout_page_login.setHgap(25);
        layout_page_login.setVgap(15);

        Label label_nom_utilisateur = new Label("Adresse mail : ");
        TextField input_nom_utilisateur = new TextField();

        Label label_password = new Label("Mot de passe : ");
        PasswordField input_password = new PasswordField();

        Button bouton_connexion = new Button("Se connecter");
        GridPane.setHalignment(bouton_connexion, HPos.RIGHT);

        bouton_connexion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(input_nom_utilisateur.getText().length() > 0)
                {
                    if(input_password.getText().length() > 0)
                    {
                        Login login = new Login();
                        int idUtilisateur = login.check(input_nom_utilisateur.getText(), input_password.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info connexion");
                        alert.setHeaderText("L'id utilisateur est " + idUtilisateur);

                        alert.showAndWait();
                        stage.setScene(page_profil);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText("Veuillez remplir le champs 'Mot de passe'");

                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Veuillez remplir le champs 'Adresse mail'");

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




        stage.setScene(page_login);
        stage.show();
    }

    public HBox getNavigationBar()
    {
        HBox layout_barre_navigation = new HBox();
        MenuBar menuBar = new MenuBar();
        Menu eleveMenu = new Menu("Gestion des élèves");
        Menu classeMenu = new Menu("Gestion des classes");

        MenuItem newEleve = new MenuItem("Ajouter un élève");
        MenuItem modifEleve = new MenuItem("Modifier un élève");
        MenuItem deleteEleve = new MenuItem("Supprimer un élève");
        MenuItem newClasse = new MenuItem("Ajouter une classe");
        MenuItem modifClasse = new MenuItem("Modifier une classe");
        MenuItem deleteClasse = new MenuItem("Supprimer une classe");

        eleveMenu.getItems().addAll(newEleve, modifEleve, deleteEleve);
        classeMenu.getItems().addAll(newClasse, modifClasse, deleteClasse);
        menuBar.getMenus().addAll(eleveMenu, classeMenu);
        EventHandler<ActionEvent> action = changeTab();
        Label labelTest = new Label("Coucou");

        layout_barre_navigation.getChildren().add(menuBar);

        return layout_barre_navigation;
    }

    public GridPane getPageProfil()
    {
        GridPane page_profil = new GridPane();
        page_profil.setPadding(new Insets(400));
        page_profil.setVgap(8);
        page_profil.setAlignment(Pos.CENTER);



        EventHandler<ActionEvent> action = changeTab();

        Label labelTest = new Label("Bonjour Michel");
        Label labelNom = new Label("Votre Nom :");
        Label labelPrenom = new Label("Votre Prenom :");
        Label labelMail = new Label("Votre Mail :");
        Label labelGroupe = new Label("Votre Groupe :");
        Label labelClasse = new Label("Votre Classe :");
        Button buttonPresence = new Button("Déclarer sa présence");

        page_profil.add(labelTest, 0, 0);
        page_profil.add(labelNom, 0, 4);
        page_profil.add(labelPrenom, 0, 6);
        page_profil.add(labelMail, 0, 8);
        page_profil.add(labelGroupe, 0, 10);
        page_profil.add(labelClasse, 0, 12);
        page_profil.add(buttonPresence, 0, 16);

        //page_profil.getChildren().addAll(labelTest, labelNom, labelPrenom, labelMail, labelGroupe, labelClasse, buttonPresence);

        return page_profil;
    }

    public EventHandler<ActionEvent> changeTab() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getText();
                System.out.println(side);

            }
        };
    };

    public static void main(String[] args) {
        launch();
    }

}