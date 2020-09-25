/* Author: Jekathmenan Selvarajah
 * Project: Abschreibungsrechner To change this license header, choose License Headers in Project Properties.
 * Project: Abschreibungsrechner To change this template file, choose Tools | Templates
 * Project: Abschreibungsrechner and open the template in the editor.
 */
package ch.bbbaden.idpa.abschreibungen.ViewModel;

import ch.bbbaden.idpa.abschreibungen.model.Model;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 *
 * @author Jekathmenan
 */
public class ViewModel implements PropertyChangeListener
{
    private final Model model;

    public ViewModel(Model model)
    {
        this.model = model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        
    }
    
    public void berechneLinear(int anzahlJahre, double anschaffungwert, boolean direkt) throws IOException
    {
        model.berechneLinear(anzahlJahre, anschaffungwert, direkt);
        model.changeView("output", "/fxml/OutputView.fxml");
    }
    
    public void berechneDegressiv(double restwert, int anzahlJahre, double prozentSatz, boolean direkt) throws IOException
    {
        model.berechneDegressiv(restwert, anzahlJahre, (prozentSatz /100), direkt);
        model.changeView("output", "/fxml/OutputView.fxml");
    }
    
    public void minimizeView() throws IOException
    {
        model.minimizeView("Main", "/fxml/View.fxml");
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
    public String toString()
    {
        return "das ist die to String methode des viewModels";
    }
}
