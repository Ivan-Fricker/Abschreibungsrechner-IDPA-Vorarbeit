/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.idpa.abschreibungen.View;

import ch.bbbaden.idpa.abschreibungen.ViewModel.OutputViewModel;
import ch.bbbaden.idpa.abschreibungen.model.AbschreibungDaten;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Jekathmenan
 */
public class OutputView implements Initializable
{
    private OutputViewModel viewModel;
    @FXML
    private TableColumn<AbschreibungDaten, String> colJahr;
    @FXML
    private TableColumn<AbschreibungDaten, String> colAbschreibung;
    @FXML
    private TableColumn<AbschreibungDaten, String> colRWAnlagekonto;
    @FXML
    private TableColumn<AbschreibungDaten, String> colBetragWBKonto;
    @FXML
    private TableColumn<AbschreibungDaten, String> colRWNachNutzDauer;
    @FXML
    private TableView<AbschreibungDaten> ausgaben;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    } 
    
    public void bind()
    {
        ausgaben.setItems(viewModel.getDaten());
        datenEinfuegen();
    }
    
    private void datenEinfuegen()
    {
        colJahr.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AbschreibungDaten, String>, ObservableValue<String>>()
        {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AbschreibungDaten, String> n)
            {
                return new ReadOnlyObjectWrapper(n.getValue().getJahr().toString());
            }
        });
        
        colAbschreibung.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AbschreibungDaten, String>, ObservableValue<String>>()
        {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AbschreibungDaten, String> n)
            {
                return new ReadOnlyObjectWrapper(n.getValue().getBetrag().toString());
            }
        });
        
        colRWAnlagekonto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AbschreibungDaten, String>, ObservableValue<String>>()
        {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AbschreibungDaten, String> n)
            {
                return new ReadOnlyObjectWrapper(n.getValue().getRwAnlageKonto().toString());
            }
        });
        
        colBetragWBKonto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AbschreibungDaten, String>, ObservableValue<String>>()
        {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AbschreibungDaten, String> n)
            {
                return new ReadOnlyObjectWrapper(n.getValue().getBetragWBKonto().toString());
            }
        });
        
        colRWNachNutzDauer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AbschreibungDaten, String>, ObservableValue<String>>()
        {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AbschreibungDaten, String> n)
            {
                return new ReadOnlyObjectWrapper(n.getValue().getRwNachNutzDauer().toString());
            }
        });      
    }

    @FXML
    private void minimize(MouseEvent event) throws IOException
    {
        viewModel.minimizeView();
    }

    @FXML
    private void exit(MouseEvent event)
    {
        viewModel.exit();
    }
    
    public void setViewModel(OutputViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    @FXML
    private void neuBerechnen(ActionEvent event) throws IOException
    {
        viewModel.changeView("View", "/fxml/View.fxml");
    }
}