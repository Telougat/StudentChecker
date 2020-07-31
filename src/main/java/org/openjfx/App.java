package org.openjfx;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.model.Classe.getUtilisateursListByClasseName;
import org.model.*;

import java.sql.Timestamp;

/**
 * JavaFX App
 */
public class App extends Application {

    Stage stageGlobal;
    int idUtilisateur;

    Ecole CESI = new Ecole(1);

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
        VBox vboxBouton = new VBox();
        Label labelTitre = new Label("Liste des classes");
        labelTitre.setFont(new Font("Arial", 24));

        ObservableList<Classe> classes = FXCollections.observableArrayList(CESI.classe);

        ListView<Classe> listClasse = new ListView<Classe>(classes);
        Button addClasse = new Button("Ajouter une Classe");
        //Button modifClasse = new Button("Modifier une Classe");
        Button deleteClasse = new Button("Supprimer une Classe");
        Button boutonPresence = new Button("Ajouter présence");

        vboxBouton.getChildren().addAll(addClasse, deleteClasse, boutonPresence);

        VBox insert_presence_classe = new VBox();
        HBox hbox = new HBox();
        Label labelDateDebut = new Label("Date de debut : ");
        Label labelDateFin = new Label("Date de fin : ");
        DatePicker formDateDebut = new DatePicker();
        DatePicker formDateFin = new DatePicker();

        int result = listClasse.getSelectionModel().getSelectedIndex();
        insert_presence_classe.getChildren().addAll(labelDateDebut, labelDateFin, formDateDebut, formDateFin);
        hbox.getChildren().addAll(vboxBouton, insert_presence_classe);

        listClasse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listClasse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Classe>() {

            @Override
            public void changed(ObservableValue<? extends Classe> observable, Classe oldValue, Classe newValue) {
                //label.setText("OLD: " + oldValue + ",  NEW: " + newValue);
                System.out.println("ancienne valeur " + oldValue);
                System.out.println("nouvelle valeur " + newValue);
            }
        });



        layoutPageGestionClasse.getChildren().addAll(labelTitre, listClasse, hbox);



        listClasse.getSelectionModel().getSelectedItem();
        //System.out.println(result);
        //System.out.println(listClasse.getSelectionModel().getSelectedItem());
        //System.out.println(listClasse.getItems().get(1));
        //System.out.println(listClasse.getItems().toString());
        insert_presence_classe.setVisible(false);
        boutonPresence.setOnAction(e -> {
            insert_presence_classe.setVisible(true);
            if (formDateDebut.getValue() != null) {
                if (formDateFin.getValue() != null) {
                    if(formDateDebut.getValue().isBefore(formDateFin.getValue()))
                    {
                        LocalDate dateDebut = formDateDebut.getValue();
                        LocalDate dateFin = formDateFin.getValue();

                        LocalDateTime newDateDebut = new LocalDateTime();
                        DateTimeFormatter dateFormat = DateTimeFormatter.forPattern("yyyy-MM-dd HH:mm:ss");
                        Timestamp ts = Timestamp.valueOf(newDateDebut.toString(dateFormat));
                        //Presence presence = new Presence(1, dateDebut, dateFin);


                    }
                }
            }
        });

//        addClasse.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e)
//            {
//                //HBox barre_navigation = getNavigationBar();
//                //MenuItem mItem = (MenuItem) e.getSource();
//                String side = addClasse.getText();
//                switch(side)
//                {
//                    case "Ajouter une Classe" :
//
//                        GridPane page_ajout_classe = new GridPane();
//                        page_ajout_classe.setPadding(new Insets(50, 0, 0, 200));
//                        page_ajout_classe.setVgap(8);
//                        page_ajout_classe.setAlignment(Pos.CENTER);
//
//                        Label labelNomClasse = new Label("Le nom de la classe : ");
//
//                        Button buttonRegister = new Button("Enregistrer");
//                        page_ajout_classe.add(labelNomClasse, 0, 0);
//                        page_ajout_classe.add(buttonRegister, 0, 4);
//
//                        var page_add_classe = new Scene(page_ajout_classe, width, height);
//                        stageGlobal.setScene(page_add_classe);
//                        stageGlobal.setTitle("Ajouter une classe");
//                        stageGlobal.show();
//                        break;
//                    case "Modifier une Classe":
//
//                        break;
//                    case "Supprimer une Classe" :
//
//                        break;
//                }
//            }
//        }
    return layoutPageGestionClasse;
    }

    public VBox getPageGestionEleves()
    {
        VBox layoutPageGestionEleves = new VBox();

        Label labelTitre = new Label("Gestion des élèves");
        labelTitre.setFont(new Font("Arial", 24));

        HBox corpsPage = new HBox();
        VBox conteneur_elements = new VBox();

        ListView affichageListeEleve = new ListView();
        ComboBox<String> cbbClasses = new ComboBox<String>();

        for (Classe classe: CESI.classe) {
            cbbClasses.getItems().add(classe.getNom());
        }

        cbbClasses.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                ArrayList<Utilisateur> listeEleve = getUtilisateursListByClasseName(t1);
                if(!affichageListeEleve.getItems().isEmpty())
                {
                    affichageListeEleve.getItems().clear();
                }
                for(Utilisateur user: listeEleve)
                {
                    affichageListeEleve.getItems().add(user.getNom() + " " + user.getPrenom());
                }
            }
        });



        cbbClasses.setPromptText("Choisissez une classe");
        cbbClasses.setVisibleRowCount(5); // Max 5 éléments visibles

        conteneur_elements.getChildren().add(cbbClasses);
        conteneur_elements.getChildren().add(affichageListeEleve);

        VBox layout_boutons = new VBox();
        layout_boutons.setPadding(new Insets(10,0,0,10));
        layout_boutons.setSpacing(10);

        Button bouton_ajout_eleve = new Button("Ajouter un élève");
        Button bouton_supprimer_eleve = new Button("Supprimer un élève");
        Button bouton_modifier_eleve = new Button("Modifier un élève");

        layout_boutons.getChildren().add(bouton_ajout_eleve);
        layout_boutons.getChildren().add(bouton_modifier_eleve);
        layout_boutons.getChildren().add(bouton_supprimer_eleve);

        layout_boutons.setPadding(new Insets(2));

        VBox layout_champs_insert = new VBox();
        layout_champs_insert.setPadding(new Insets(10));
        layout_champs_insert.setSpacing(10);

        TextField text_field_nom = new TextField();
        text_field_nom.setPromptText("Nom");
        layout_champs_insert.getChildren().add(text_field_nom);

        TextField text_field_prenom = new TextField();
        text_field_prenom.setPromptText("Prénom");
        layout_champs_insert.getChildren().add(text_field_prenom);

        TextField text_field_mail = new TextField();
        text_field_mail.setPromptText("Mail");
        layout_champs_insert.getChildren().add(text_field_mail);

        TextField text_field_password = new TextField();
        text_field_password.setPromptText("Mot de passe");
        layout_champs_insert.getChildren().add(text_field_password);

        ComboBox cbbClassesDispo = new ComboBox();
        for (Classe classe: CESI.classe) {
            cbbClassesDispo.getItems().add(classe.getNom());
        }
        cbbClassesDispo.setPromptText("Classe");
        layout_champs_insert.getChildren().add(cbbClassesDispo);

        ComboBox cbbRoles = new ComboBox();
        cbbRoles.getItems().addAll("Admin","Elève");
        cbbRoles.setPromptText("Groupe");
        layout_champs_insert.getChildren().add(cbbRoles);

        Button bouton_valider = new Button("Valider");
        layout_champs_insert.getChildren().add(bouton_valider);

        layout_champs_insert.setVisible(false);
        bouton_valider.setOnAction(e -> {
            if(text_field_nom.getText().length() > 0)
            {
                if(text_field_prenom.getText().length() > 0)
                {
                    if(text_field_mail.getText().length() > 0)
                    {
                        if(text_field_password.getText().length() > 0)
                        {
                            if(cbbClassesDispo.getValue() != null)
                            {
                                if(cbbRoles.getValue() != null)
                                {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Fenêtre de confirmation");
                                    alert.setHeaderText("Ajout d'un élève");
                                    alert.setContentText("Voulez-vous vraiment continuer ?");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK){
                                        String nom_eleve = text_field_nom.getText();
                                        String prenom_eleve = text_field_prenom.getText();
                                        String mail_eleve = text_field_mail.getText();
                                        String password_eleve = text_field_password.getText();
                                        String classe_eleve = cbbClassesDispo.getValue().toString();
                                        String role_eleve = cbbRoles.getValue().toString();

                                        CRUDUtilisateur crud_utilisateur = new CRUDUtilisateur();
                                        Utilisateur nouvelUtilisateur = crud_utilisateur.createUtilisateur(nom_eleve,prenom_eleve,mail_eleve,password_eleve,Groupe.getGroupByName(role_eleve),CESI);
                                        Classe obj_classe_eleve = null;
                                        for (Classe classe: CESI.classe) {
                                            if(classe.getNom().equals(classe_eleve))
                                            {
                                                obj_classe_eleve = classe;
                                            }
                                        }
                                        if(obj_classe_eleve != null)
                                        {
                                            crud_utilisateur.linkToClasse(nouvelUtilisateur,obj_classe_eleve);
                                        }
                                        else {
                                            System.out.println("Erreur sur la classe");
                                        }

                                        layout_champs_insert.setVisible(false);
                                    } else {
                                        layout_champs_insert.setVisible(true);
                                    }

                                }
                            }
                        }
                    }
                }
            }
        });

        bouton_ajout_eleve.setOnAction(e -> {
            layout_champs_insert.setVisible(true);
        });

        bouton_supprimer_eleve.setOnAction(e -> {
            if(cbbClasses.getValue() != null)
            {
                if(affichageListeEleve.getSelectionModel().getSelectedItem() != null)
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Fenêtre de confirmation");
                    alert.setHeaderText("Suppression d'un élève");
                    alert.setContentText("Voulez-vous vraiment continuer ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        String chaine_tmp = affichageListeEleve.getSelectionModel().getSelectedItem().toString();
                        String[] tab_tmp = chaine_tmp.split(" ");
                        String nom_eleve = tab_tmp[0];
                        String prenom_eleve = tab_tmp[1];
                        System.out.println("Nom : "+nom_eleve+" prenom : "+prenom_eleve);

                        ArrayList<Utilisateur> listeEleve = getUtilisateursListByClasseName(cbbClasses.getValue().toString());
                        for(Utilisateur user: listeEleve)
                        {
                            System.out.println("nom : "+user.getNom()+" prenom :"+user.getPrenom());
                            boolean retVal = false;
                            if(user.getNom().equals(nom_eleve) && user.getPrenom().equals(prenom_eleve))
                            {
                                CRUDUtilisateur crudUtilisateur = new CRUDUtilisateur();
                                crudUtilisateur.deleteUtilisateur(user);
                                retVal = true;
                            }

                            if(retVal)
                            {
                                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                                alertInfo.setTitle("Information");
                                alertInfo.setHeaderText("Opération terminé");

                                alertInfo.showAndWait();
                            }
                        }
                    } else {
                        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                        alertInfo.setTitle("Information");
                        alertInfo.setHeaderText("Opération annulé");

                        alertInfo.showAndWait();
                    }

                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Choisissez un élève à supprimer");

                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Choisissez une classe");

                alert.showAndWait();
            }
        });

        corpsPage.getChildren().add(conteneur_elements);
        corpsPage.getChildren().add(layout_boutons);
        corpsPage.getChildren().add(layout_champs_insert);

        corpsPage.setPadding(new Insets(15));   

        layoutPageGestionEleves.setPadding(new Insets(15));
        layoutPageGestionEleves.getChildren().add(labelTitre);
        layoutPageGestionEleves.getChildren().add(corpsPage);

        return layoutPageGestionEleves;
    }

    public GridPane getPageProfil() {
        GridPane page_profil = new GridPane();
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        HBox hbox = new HBox();
        vbox1.setSpacing(20);
        vbox2.setSpacing(20);
        //vbox1.setPadding(new Insets(10, 10, 10, 10));
        //vbox2.setPadding(new Insets(50, 0, 50, 50));

        Utilisateur utilisateur = new Utilisateur(idUtilisateur);

        Classe classe = new Classe(utilisateur);

        Label labelTest = new Label("Bonjour " + utilisateur.getPrenom() + " " + utilisateur.getNom());
        Label labelNom = new Label("Votre Nom : " + utilisateur.getNom());
        Label labelPrenom = new Label("Votre Prenom : " + utilisateur.getPrenom());
        Label labelMail = new Label("Votre Mail : " + utilisateur.getMail());
        Label labelGroupe = new Label("Votre Groupe : " + utilisateur.groupe.getLabel());
        Label labelClasse = new Label("Votre Classe : " + classe.getNom());

        PresenceUtilisateur presenceUtilisateur = utilisateur.getUndeclaredPresence();
        if(presenceUtilisateur != null ) {
            Label labelPresence = new Label("Presence " + utilisateur.getUndeclaredPresence());
            Button buttonPresence = new Button("Déclarer sa présence");
            buttonPresence.setOnAction(e -> {
                vbox2.getChildren().remove(labelPresence);
                vbox2.getChildren().remove(buttonPresence);
                utilisateur.declarePresence(presenceUtilisateur.presence.getId());
            });
            vbox2.getChildren().add(labelPresence);
            vbox2.getChildren().add(buttonPresence);
        }
        //ObservableList<PresenceUtilisateur> presenceUtilisateurs = FXCollections.observableArrayList(utilisateur.getUndeclaredPresences());

        //ListView<PresenceUtilisateur> presenceUser = new ListView<PresenceUtilisateur>(presenceUtilisateurs);



        vbox1.getChildren().add(labelTest);
        vbox1.getChildren().add(labelNom);
        vbox1.getChildren().add(labelPrenom);
        vbox1.getChildren().add(labelMail);
        vbox1.getChildren().add(labelGroupe);
        vbox1.getChildren().add(labelClasse);


        hbox.getChildren().addAll(vbox1, vbox2);

        page_profil.getChildren().add(hbox);

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