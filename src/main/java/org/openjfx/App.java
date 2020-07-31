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
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.model.Classe;
import org.model.Ecole;
import org.model.Login;
import org.model.Utilisateur;

/**
 * JavaFX App
 */
public class App extends Application {

    Stage stageGlobal;
    int idUtilisateur;

    //Ecole CESI = new Ecole(1);

    @Override
    public void start(Stage stage) {
        stageGlobal = stage;
        stageGlobal.setResizable(false);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getWidth()/3);
        stage.setY(bounds.getWidth()/6);
        stage.setWidth(bounds.getWidth()/3);
        stage.setHeight(bounds.getHeight()/2);

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
                        idUtilisateur = login.check(input_nom_utilisateur.getText(), input_password.getText());

                        if(idUtilisateur != -1)
                        {
                            Utilisateur utilisateur = new Utilisateur(idUtilisateur);

                            VBox layout_page_profil_general = new VBox();
                            GridPane profil = getPageProfil();
                            if(utilisateur.groupe.getLabel().equals("Admin")) // check role admin
                            {
                                HBox barre_navigation = getNavigationBar();

                                layout_page_profil_general.getChildren().add(barre_navigation);
                                layout_page_profil_general.getChildren().add(profil);
                            }
                            else {
                                layout_page_profil_general.getChildren().add(profil);
                            }
                            var page_profil = new Scene(layout_page_profil_general, bounds.getWidth(), bounds.getHeight());
                            stageGlobal.setScene(page_profil);
                            stageGlobal.setTitle("Page de profil");

                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText("Erreur de connexion");
                            alert.setContentText("Email ou mot de passe incorrect");

                            alert.showAndWait();
                        }
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

        layout_page_login.add(label_nom_utilisateur,4,2);
        layout_page_login.add(input_nom_utilisateur,5,2);
        layout_page_login.add(label_password,4,3);
        layout_page_login.add(input_password,5,3);
        layout_page_login.add(bouton_connexion,5,4);


        var page_login = new Scene(layout_page_login, stage.getWidth(), stage.getHeight());

        /******************************************** Fin page connexion *********************************************/




        stage.setScene(page_login);
        stage.show();
    }

    public HBox getNavigationBar()
    {
        double width = stageGlobal.getWidth();
        double height = stageGlobal.getHeight();

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
                        VBox layout_page_profil_general = new VBox();
                        GridPane profil = getPageProfil();

                        layout_page_profil_general.getChildren().add(barre_navigation);
                        layout_page_profil_general.getChildren().add(profil);

                        var page_profil = new Scene(layout_page_profil_general, width, height);
                        stageGlobal.setScene(page_profil);
                        stageGlobal.setTitle("Page de profil");
                        break;
                    case "Gestion des classes":
                        VBox layout_gestion_classe_general = new VBox();
                        VBox gestion_classe = getPageGestionClasse();

                        layout_gestion_classe_general.getChildren().add(barre_navigation);
                        layout_gestion_classe_general.getChildren().add(gestion_classe);

                        var page_gestion_classe = new Scene(layout_gestion_classe_general, width, height);
                        stageGlobal.setScene(page_gestion_classe);
                        stageGlobal.setTitle("Gestion des classes");
                        break;
                    case "Gestion des élèves" :
                        VBox layout_gestion_eleves_general = new VBox();
                        VBox gestion_eleves = getPageGestionEleves();

                        layout_gestion_eleves_general.getChildren().add(barre_navigation);
                        layout_gestion_eleves_general.getChildren().add(gestion_eleves);

                        var page_gestion_eleves = new Scene(layout_gestion_eleves_general, width, height);
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
        menuBar.setPrefWidth(width);

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
        VBox conteneur_elements = new VBox();

        ListView listeEleve = new ListView();
        ComboBox<String> cbbClasses = new ComboBox<String>();
        cbbClasses.getItems().addAll("RIL","RISR");
        cbbClasses.setPromptText("Choisissez une classe");
        cbbClasses.setVisibleRowCount(5); // Max 5 éléments visibles

        conteneur_elements.getChildren().add(cbbClasses);
        conteneur_elements.getChildren().add(listeEleve);

        VBox layout_boutons = new VBox();
        Button bouton_ajout_eleve = new Button("Ajouter un élève");
        Button bouton_supprimer_eleve = new Button("Supprimer un élève");
        Button bouton_modifier_eleve = new Button("Modifier un élève");

        layout_boutons.getChildren().add(bouton_ajout_eleve);
        layout_boutons.getChildren().add(bouton_modifier_eleve);
        layout_boutons.getChildren().add(bouton_supprimer_eleve);

        layout_boutons.setPadding(new Insets(15));

        corpsPage.getChildren().add(conteneur_elements);
        corpsPage.getChildren().add(layout_boutons);

        corpsPage.setPadding(new Insets(15));   

        layoutPageGestionEleves.setPadding(new Insets(15));
        layoutPageGestionEleves.getChildren().add(labelTitre);
        layoutPageGestionEleves.getChildren().add(corpsPage);

        return layoutPageGestionEleves;
    }

    public GridPane getPageProfil() {
        GridPane page_profil = new GridPane();
        page_profil.setPadding(new Insets(50, 0, 0, 200));
        page_profil.setVgap(8);
        //page_profil.setAlignment(Pos.CENTER);
        Utilisateur utilisateur = new Utilisateur(idUtilisateur);

        Classe classe = new Classe(utilisateur);

        Label labelTest = new Label("Bonjour " + utilisateur.getPrenom() + " " + utilisateur.getNom());
        Label labelNom = new Label("Votre Nom : " + utilisateur.getNom());
        Label labelPrenom = new Label("Votre Prenom : " + utilisateur.getPrenom());
        Label labelMail = new Label("Votre Mail : " + utilisateur.getMail());
        Label labelGroupe = new Label("Votre Groupe : " + utilisateur.groupe.getLabel());
        Label labelClasse = new Label("Votre Classe : " + classe.getNom());
        Button buttonPresence = new Button("Déclarer sa présence");
        page_profil.add(labelTest, 0, 0);
        page_profil.add(labelNom, 0, 4);
        page_profil.add(labelPrenom, 0, 6);
        page_profil.add(labelMail, 0, 8);
        page_profil.add(labelGroupe, 0, 10);
        page_profil.add(labelClasse, 0, 12);
        page_profil.add(buttonPresence, 0, 16);
        return page_profil;
    }

    public static void main(String[] args) {
        launch();
    }

}