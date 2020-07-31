package org.openjfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.model.Login;

/**
 * JavaFX App
 */
public class App extends Application {

    Stage stageGlobal;
    @Override
    public void start(Stage stage) {
        stageGlobal = stage;

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        /******************************************** Début page profil  *********************************************/

        HBox barre_navigation = getNavigationBar();

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
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Menu menu = new Menu();
        MenuBar menuBar = new MenuBar();

        Menu menu01 = new Menu("Menu");
        Menu menu02 = new Menu("Options");

        MenuItem m1 = new MenuItem("Page de profil");
        MenuItem m2 = new MenuItem("Gestion des élèves");
        MenuItem m3 = new MenuItem("Gestion des classes");

        menu01.getItems().add(m1);
        menu01.getItems().add(m2);
        menu01.getItems().add(m3);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                HBox barre_navigation = getNavigationBar();
                MenuItem mItem = (MenuItem) e.getSource();
                String side = mItem.getText();
                switch(side)
                {
                    case "Page de profil" :
                        System.out.println("page de profil !!!");
                        stageGlobal.setTitle("Page de profil");
                        break;
                    case "Gestion des classes":
                        VBox layout_gestion_classe_general = new VBox();
                        VBox gestion_classe = getPageGestionClasse();

                        layout_gestion_classe_general.getChildren().add(barre_navigation);
                        layout_gestion_classe_general.getChildren().add(gestion_classe);

                        var page_gestion_classe = new Scene(layout_gestion_classe_general, bounds.getWidth(), bounds.getHeight());
                        stageGlobal.setScene(page_gestion_classe);
                        stageGlobal.setTitle("Gestion des classes");
                        break;
                    case "Gestion des élèves" :
                        VBox layout_gestion_eleves_general = new VBox();
                        VBox gestion_eleves = getPageGestionEleves();

                        layout_gestion_eleves_general.getChildren().add(barre_navigation);
                        layout_gestion_eleves_general.getChildren().add(gestion_eleves);

                        var page_gestion_eleves = new Scene(layout_gestion_eleves_general, bounds.getWidth(), bounds.getHeight());
                        stageGlobal.setScene(page_gestion_eleves);
                        stageGlobal.setTitle("Gestion des élèves");
                        break;
                }
            }
        };

        m1.setOnAction(event);
        m2.setOnAction(event);
        m3.setOnAction(event);

        menuBar.getMenus().add(menu);


        menuBar.getMenus().addAll(menu01, menu02);
        menuBar.setMinWidth(300); // do not shrink
        menuBar.setPrefWidth(bounds.getWidth());

        HBox layout_barre_navigation = new HBox();
        layout_barre_navigation.getChildren().add(menuBar);


        return layout_barre_navigation;
    }

    public VBox getPageGestionClasse()
    {
        VBox layoutPageGestionClasse = new VBox();

        Label labelTitre = new Label("Gestion des classes");
        labelTitre.setFont(new Font("Arial", 24));

        layoutPageGestionClasse.getChildren().add(labelTitre);

        return layoutPageGestionClasse;
    }

    public VBox getPageGestionEleves()
    {
        VBox layoutPageGestionEleves = new VBox();

        Label labelTitre = new Label("Gestion des élèves");
        labelTitre.setFont(new Font("Arial", 24));

        HBox corpsPage = new HBox();
        


        layoutPageGestionEleves.setPadding(new Insets(15));
        layoutPageGestionEleves.getChildren().add(labelTitre);

        return layoutPageGestionEleves;
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