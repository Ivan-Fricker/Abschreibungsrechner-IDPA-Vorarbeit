/* Author: Jekathmenan Selvarajah
 * Project: Abschreibungsrechner To change this license header, choose License Headers in Project Properties.
 * Project: Abschreibungsrechner To change this template file, choose Tools | Templates
 * Project: Abschreibungsrechner and open the template in the editor.
 */
package ch.bbbaden.idpa.abschreibungen.model;

import ch.bbbaden.idpa.abschreibungen.MainApp;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Jekathmenan
 */
public class Model
{
    private final MainApp mainApp;
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    // List für die Abspeicherung von Abschreibungsdaten
    private List<AbschreibungDaten> daten = new ArrayList();

    // variablen zur berechnung der direkten Abschreibung
    private double totalBetrag;
    private double restWert;
    private double betragLinear;

    // variablen zur berechnung von indirekten Abschreibung
    
    private double betragWBKonto;
    private double betrag;
    private double rwNachNutzung;
    private double tempTotal;
    private double tempBetrag;
    double tempRestwert;

    /**
     * Ein Constructor um MainApp zu initialisieren
     *
     * @param mainApp
     */
    public Model(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }

    /**
     * Diese Methode berechnet die lineare Abschreibung und speichert diese in
     * Form eines AbschreibungDaten-Objekts in der Liste daten ab.
     *
     * Diese Methode berechnet den Abschreibungsbetrag und den Restwert nach der
     * Nutzungsdauer.
     *
     * Diese Methode ruft die firePropertyChange() Methode von changes auf.
     *
     * @param anzahlJahre
     * @param anschaffungwert
     */
    public void berechneLinear(int anzahlJahre, double anschaffungwert, boolean direkt)
    {
        betragLinear = anschaffungwert / anzahlJahre;
        totalBetrag = 0;
        restWert = 0;

        for (int i = 0; i < anzahlJahre; i++)
        {
            if (direkt)
            {
                totalBetrag = betragLinear * (i + 1);
                restWert = anschaffungwert - totalBetrag;
                daten.add(new AbschreibungDaten(i + 1, betragLinear));
                if (restWert >= 1)
                {
                    daten.get(i).setRwAnlageKonto(restWert);
                } else
                {
                    daten.get(i).setRwAnlageKonto(0);
                }
            } 
            else
            {
                totalBetrag = betragLinear * (i + 1);
                restWert = anschaffungwert - totalBetrag;
                daten.add(new AbschreibungDaten(i + 1, betragLinear, totalBetrag));
            }
        }
        changes.firePropertyChange("Daten", null, daten);
    }

    /**
     * Diese Methode berechnet die degressive Abschreibung und speichert diese
     * in Form eines AbschreibungDaten-Objekts in der Liste daten ab.
     *
     * Diese Methode berechnet den Abschreibungsbetrag, den Restwert, den Betrag
     * auf das WB-Konto und den Restwert nach der Nutzungsdauer.
     *
     * Diese Methode ruft die firePropertyChange() Methode von changes auf.
     *
     * @param restwert
     * @param anzahlJahre
     * @param prozentSatz
     * @param direkt
     */
    public void berechneDegressiv(double restwert, int anzahlJahre, double prozentSatz, boolean direkt)
    {
        tempRestwert = restwert;
        tempBetrag = 0;
        tempTotal = 0;
        rwNachNutzung = 0;
        betragWBKonto = 0;

        // Diese for-Schleife berechnet den Restwert nach der Nutzungsdauer
        for (int i = 1; i <= anzahlJahre; i++)
        {
            tempBetrag = tempRestwert * prozentSatz;
            tempRestwert -= tempBetrag;
            tempTotal = tempTotal += tempBetrag;

            // wenn i gleichgross wie anzahlJahre soll der 
            if ((i) == anzahlJahre)
            {
                rwNachNutzung = restwert - tempTotal;
            }
        }
        // tempRestWert wird zurückgesetzt
        tempRestwert = restwert;

        for (int i = 0; i < anzahlJahre; i++)
        {
            if (direkt)
            {
                // berechne betrag
                betrag = tempRestwert * prozentSatz;
                tempRestwert -= betrag;
                
                // speichere Jahr und Betrag in daten
                daten.add(new AbschreibungDaten(i + 1, betrag));
                
                // Wenn Restwert grösser als 1 ist: speichere ihn in daten ab
                if (rwNachNutzung >= 1)
                {
                    daten.get(i).setRwAnlageKonto(rwNachNutzung);
                } 
                else
                {
                    daten.get(i).setRwAnlageKonto(0);
                }
            } 
            else
            {
                betrag = tempRestwert * prozentSatz;
                // ziehe betrag vom restwert ab
                tempRestwert -= betrag;
                betragWBKonto += betrag;
                daten.add(new AbschreibungDaten(i + 1, betrag, betragWBKonto));

                if (rwNachNutzung >= 1)
                {
                    daten.get(i).setRwNachNutzDauer(rwNachNutzung);
                } 
                else
                {
                    daten.get(i).setRwNachNutzDauer(0);
                }
            }
        }
        changes.firePropertyChange("Daten", null, daten);
    }

    public void minimizeView(String title, String url) throws IOException
    {
        mainApp.minimizeView(title, url);
    }

    public void exit()
    {
        UIManager.put("OptionPane.background", Color.decode("#3c6562"));
        UIManager.getLookAndFeelDefaults().put("Panel.background", Color.decode("#3c6562"));
        
        int eingabe = JOptionPane.showConfirmDialog
        (
            null,
            "Wollen Sie das Programm wirklich beenden?",
            "Einverständnis",
            JOptionPane.YES_NO_OPTION
        );

        if (eingabe == 0)
        {
            mainApp.exit();
        }
    }

    public void changeView(String title, String url) throws IOException
    {
        mainApp.changeView(title, url);
    }

    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        changes.addPropertyChangeListener(l);
    }
}
