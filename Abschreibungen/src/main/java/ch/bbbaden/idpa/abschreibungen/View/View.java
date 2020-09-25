/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.idpa.abschreibungen.View;

import ch.bbbaden.idpa.abschreibungen.ViewModel.ViewModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jekathmenan
 */
public class View implements Initializable
{
    // initialisiere ViewModel
    private ViewModel viewModel;
    
    // initialisiere Radiobuttons zur Auswahl von linear, degressiv, direkt und indirekt
    @FXML
    private RadioButton linear;
    @FXML
    private RadioButton degressiv;
    @FXML
    private RadioButton direkt;
    @FXML
    private RadioButton indirekt;
    
    // initialisiere Toggle group für RadioButtons
    private ToggleGroup linDegr = new ToggleGroup();
    private ToggleGroup dirIndir = new ToggleGroup();
    
    // initialisiere Labels für Warnmitteilung
    @FXML
    private Label infoAnschaffungsWert;
    @FXML
    private Label infoNutzungsdauer;
    @FXML
    private Label infoRestwert;
    @FXML
    private Label infoAbschreibung;
    @FXML
    private Label infoDirektIndirekt;
    @FXML
    private Label infoLinearDegressiv;
    
    // initialisiere Textfelder für Dateneingabe
    @FXML
    private TextField inputAnschaffungswert;
    @FXML
    private TextField inputJahre;
    @FXML
    private TextField inputRestwert;
    @FXML
    private TextField inputAbschProzent;
    
    // initialisiere correctInput für Datenvalidierung
    private boolean correctInput = true;
    
    // initialisiere variablen zum Speichern der Eingaben
    private double anschaffungsWert;
    private int inputAnzJahre; 
    private double restwert;
    private double abschreibung;
    private boolean dir;
    private int anzahl = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        linear.setToggleGroup(linDegr);
        degressiv.setToggleGroup(linDegr);
        
        direkt.setToggleGroup(dirIndir);
        indirekt.setToggleGroup(dirIndir);
    }
    
    /**
     * Diese methode verbindet die Daten aus der ViewModel-Klasse mit den entsprechenden Gui-Elementen.
     * 
     * In dieser Klasse wird diese Methode nicht benötigt.
     */
    public void bind()
    {
    }

    
    /**
     * Diese Methode wird beim Klick auf das Exit-Zeichen aufgerufen.
     * 
     * Diese Methode schliesst das Fenster durch aufruf von ViewModels Methode exit().
     * 
     * @param event 
     */
    @FXML
    private void exit(MouseEvent event)
    {
        viewModel.exit();
    }

    /**
     * Diese Methode wird beim Klick auf das Minimize-Zeichen aufgerufen.
     * 
     * Diese Methode minimiert das Fenster durch aufruf von ViewModels Methode minimizeView().
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void minimize(MouseEvent event) throws IOException
    {
        viewModel.minimizeView();
    }

    /**
     * Diese Methode wird beim Klick auf berechnen aufgerufen.
     * 
     * Diese Methode überprüft, ob Fehleingaben vorhanden sind und die Radiobuttons direkt/indirekt und linear/degressiv gewählt worden sind.
     * 
     * Bei korrekten Eingaben wird die entsprechende Methode des ViewModels aufgerufen.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void berechne(ActionEvent event) throws IOException
    {
        correctInput = true;
        
        if(!direkt.isSelected() && !indirekt.isSelected())
        {
            correctInput = false;
            infoDirektIndirekt.setText("Wählen Sie eines der beiden Checkboxen!");
        }
        else if(direkt.isSelected())
        {
            dir = true;
        }
        else
        {
            dir = false;
        }
        
        if(degressiv.isSelected())
        {
            checkInputAbschreibung();
            checkInputRestwert();
            checkInputJahre();
            
            // viewmodels berechneDegressiv aufrufen
            if(correctInput)
            {
                viewModel.berechneDegressiv(restwert, inputAnzJahre, abschreibung, dir);
            }
        }
        else if(linear.isSelected())
        {
            checkInputJahre();
            checkInputAnschaffungswert();
            
            // viewmodels berechneLinear aufrufen
            if(correctInput)
            {
                viewModel.berechneLinear(inputAnzJahre, anschaffungsWert, dir);
            }
        }
        else
        {
            correctInput = false;
            infoLinearDegressiv.setText("Wählen Sie eines der beiden Checkboxen!");
        }
    }
    
    /**
     * Diese Methode überprüft die Validität der Eingabe Anschaffungswert.
     * 
     * Bei falschen eingaben wird correctInput auf false gesetzt und Warnhinweise angezeigt.
     */
    private void checkInputAnschaffungswert()
    {
        try
        {
            anschaffungsWert = Double.parseDouble(inputAnschaffungswert.getText());
            
            // Anschaffungswert darf nicht kleiner als 50 sein.
            if(anschaffungsWert < 50)
            {
                correctInput = false;
                infoAnschaffungsWert.setText("Anschaffungswert darf nicht kleiner als 50 sein.");
            }
            else
            {
                infoAnschaffungsWert.setText(""); 
            }
        }
        catch(NumberFormatException e)
        {
            String warnung;
            if(inputAnschaffungswert.getText().equals(""))
            {
                warnung = "Anschaffungswert darf nicht leer sein!";
            }
            else
            {
                warnung = "Anschaffungswert enthält falsche Werte. Bitte korrigieren!";
            }
            correctInput = false;
            infoAnschaffungsWert.setText(warnung);   
        }
    }
    
    /**
     * Diese Methode überprüft die Validität der Eingabe Nutzungsdauer.
     * 
     * Bei falschen eingaben wird correctInput auf false gesetzt und Warnhinweise angezeigt.
     */
    private void checkInputJahre()
    {
        try
        {
            inputAnzJahre = Integer.parseInt(inputJahre.getText());
            
            // Nutzungsdauer soll voraussichtlich 20 Jahre sein.
            if(inputAnzJahre < 1 || inputAnzJahre > 20)
            {
                correctInput = false;
                infoNutzungsdauer.setText("Nutzungsdauer muss zwischen 1 und 20 liegen.");
            }
            else
            {
                infoNutzungsdauer.setText(""); 
            }
        }
        catch(NumberFormatException e)
        {
            String warnung;
            if(inputJahre.getText().equals(""))
            {
                warnung = "Nutzungsdauer darf nicht leer sein!";
            }
            else
            {
                warnung = "Nutzungsdauer enthält falsche Werte. Bitte korrigieren!";
            }
            correctInput = false;
            infoNutzungsdauer.setText(warnung);
        }
    }
    
    /**
     * Diese Methode überprüft die Validität der Eingabe Restwert.
     * 
     * Bei falschen eingaben wird correctInput auf false gesetzt und Warnhinweise angezeigt.
     */
    private void checkInputRestwert()
    {
        try
        {
            restwert = Double.parseDouble(inputRestwert.getText());
            infoRestwert.setText("");
            
            // Restwert darf nicht kleiner als 50 sein.
            if(restwert < 50)
            {
                correctInput = false;
                infoNutzungsdauer.setText("Anschaffungswert darf nicht kleiner als 50 sein.");
            }
            else
            {
                infoNutzungsdauer.setText(""); 
            }
        }
        catch(NumberFormatException e)
        {
            String warnung;
            if(inputRestwert.getText().equals(""))
            {
                warnung = "Restwert darf nicht leer sein!";
            }
            else
            {
                warnung = "Restwert enthält falsche Werte. Bitte korrigieren!";
            }
            
            correctInput = false;
            infoRestwert.setText(warnung);
        }
    }
    
    /**
     * Diese Methode überprüft die Validität der Eingabe Abschreibung in Prozent.
     * 
     * Bei falschen eingaben wird correctInput auf false gesetzt und Warnhinweise angezeigt.
     */
    private void checkInputAbschreibung()
    {
        try
        {
            abschreibung = Double.parseDouble(inputAbschProzent.getText());
            infoAbschreibung.setText("");
        }
        catch(NumberFormatException e)
        {
            String warnung;
            if(inputAbschProzent.getText().equals(""))
            {
                warnung = "Abschreibung in Prozent darf nicht leer sein!";
            }
            else
            {
                warnung = "Abschreibung in Prozent stimmt nicht. Bitte nur Ganz- oder Kommazahlen eingeben.";
            }
            
            correctInput = false;
            infoAbschreibung.setText(warnung);
        }
    }

    /**
     * Diese Methode wird beim Klick auf abbrechen aufgerufen.
     * 
     * Diese Methode setzt alle Eigaben, Hinweise und Warnungen zurück.
     * 
     * @param event 
     */
    @FXML
    private void abbrechen(ActionEvent event)
    {
        inputAbschProzent.setText("");
        inputAnschaffungswert.setText("");
        inputJahre.setText("");
        inputRestwert.setText("");
        linear.setSelected(false);
        degressiv.setSelected(false);
        direkt.setSelected(false);
        indirekt.setSelected(false);
        
        infoAbschreibung.setText("");
        infoAnschaffungsWert.setText("");
        infoDirektIndirekt.setText("");
        infoLinearDegressiv.setText("");
        infoNutzungsdauer.setText("");
        infoRestwert.setText("");
    }

    /**
     * Diese Methode wird beim Klick auf das Radiobutton linear aufgerufen
     * 
     * Diese Methode setzt nicht benötigte Eingaben und Warnnachrichten zurück und wirft benötigte Hinweise auf.
     * 
     * @param event 
     */
    @FXML
    private void isLinear(ActionEvent event)
    {
        // Restwerteingabe und Eingabe Abschreibung in Prozent werden gelöscht
        inputRestwert.setText("");
        inputAbschProzent.setText("");
        
        // Warnung über Anschaffungswert und Nutzungsdauer werden zurückgesetzt
        infoAnschaffungsWert.setText("");
        
        // Gebe info für die nincht benötigten Werte --> Restwert und Abschreibung in %
        if(!inputRestwert.equals(""))
        {
            infoRestwert.setText("Der Restwert wird nicht benötigt.");
        }
        if(!inputAbschProzent.equals(""))
        {
            infoAbschreibung.setText("Die Abschreibung in % wird nicht benötigt.");
        }
        
        infoLinearDegressiv.setText("");
    }

    /**
     * Diese Methode wird beim Klick auf das Radiobutton degressiv aufgerufen
     * 
     * Diese Methode setzt nicht benötigte Warnnachrichten zurück und wirft benötigte Hinweise auf.
     * 
     * @param event 
     */
    @FXML
    private void isDegressiv(ActionEvent event)
    {
        inputAnschaffungswert.setText("");
        infoAnschaffungsWert.setText("Der Anschaffungswert wird nicht benötigt.");
        
        infoRestwert.setText("");
        infoAbschreibung.setText("");        
        infoLinearDegressiv.setText("");
    }

    /**
     * Diese Methode wird beim Klick auf das Radiobutton direkt aufgerufen
     * 
     * Diese Methode setzt nicht benötigte und Warnnachrichten zurück.
     * 
     * @param event 
     */
    @FXML
    private void isDirekt(ActionEvent event)
    {
        infoDirektIndirekt.setText("");
    }

    /**
     * Diese Methode wird beim Klick auf das Radiobutton indirekt aufgerufen
     * 
     * Diese Methode setzt nicht benötigte und Warnnachrichten zurück.
     * 
     * @param event 
     */
    @FXML
    private void isIndirekt(ActionEvent event)
    {
        infoDirektIndirekt.setText("");
    }
    
    /**
     * Ein setter für ViewModel
     * 
     * @param viewModel 
     */
    public void setViewModel(ViewModel viewModel)
    {
        this.viewModel = viewModel;
    }
}
