/* Author: Jekathmenan Selvarajah
 * Project: Programmierwochen_Casino To change this license header, choose License Headers in Project Properties.
 * Project: Programmierwochen_Casino To change this template file, choose Tools | Templates
 * Project: Programmierwochen_Casino and open the template in the editor.
 */

package ch.bbbaden.idpa.abschreibungen.ViewModel;

import ch.bbbaden.idpa.abschreibungen.model.AbschreibungDaten;
import ch.bbbaden.idpa.abschreibungen.model.Model;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jekathmenan
 */
public class OutputViewModel implements PropertyChangeListener
{
    private final Model model;
    private final ObservableList<AbschreibungDaten> daten = FXCollections.observableArrayList();

    public OutputViewModel(Model model)
    {
        this.model = model;
    }
    
    public void minimizeView() throws IOException
    {
        model.minimizeView("Output", "/fxml/OutputView.fxml");
    }
    
    public void changeView(String title, String url) throws IOException
    {
        model.changeView(title, url);
    }
    
    public void exit()
    {
        model.exit();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch(evt.getPropertyName())
        {
            case "Daten":
                daten.clear();
                try
                {
                    ArrayList<AbschreibungDaten> data = (ArrayList<AbschreibungDaten>) evt.getNewValue();
                    for (AbschreibungDaten wert : data)
                    {
                        daten.add(wert);
                    }
                }
                catch(Exception ex){
                }
                break;
        }
    }

    public ObservableList<AbschreibungDaten> getDaten()
    {
        return daten;
    }
    
    @Override
    public String toString()
    {
        return "das ist die to String methode des OUtput viewModels";
    }
}
