package ch.bbbaden.idpa.abschreibungen;

import ch.bbbaden.idpa.abschreibungen.View.OutputView;
import ch.bbbaden.idpa.abschreibungen.View.View;
import ch.bbbaden.idpa.abschreibungen.ViewModel.OutputViewModel;
import ch.bbbaden.idpa.abschreibungen.ViewModel.ViewModel;
import ch.bbbaden.idpa.abschreibungen.model.Model;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application 
{
    Stage secondaryStage;
    private FXMLLoader loader;
    private View view;
    OutputView outputV;
    private final Model model = new Model(this);
    
    private final ViewModel viewModel = new ViewModel(model);
    private final OutputViewModel outputVM = new OutputViewModel(model);
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        // Resource wird dem FXMLLoader zugewiesen und dem Parent geladen
        loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
        Parent root = loader.load();
        
        // view wird initialisiert
        view = loader.getController();
        
        // viewmodel wird dem view gesettet.
        view.setViewModel(viewModel);
        
        // dem model werden propertychangeListener hinzugefügt
        model.addPropertyChangeListener(viewModel);
        model.addPropertyChangeListener(outputVM);
        
        // view wird gebunden
        view.bind();
        
        //secondarystage für wird mit dem aktuellen stage initialisiert
        secondaryStage = stage;
        
        //scene wird mit dem root erstellt
        Scene scene = new Scene(root);
        
        // stage style wird festgelegt --> Kein default titlebar und keine dekoration
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        // dem stage wird eine scene übermittelt und angezeigt
        stage.setScene(scene);
        stage.show();
    }
    
    public void minimizeView(String title, String url) throws IOException
    {
        loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();
        Scene scene;

        // setting up the views
        switch (title)
        {
            case "Main":
                // 
                view = loader.getController();
                
                view.setViewModel(viewModel);
                model.addPropertyChangeListener(viewModel);
                model.addPropertyChangeListener(outputVM);
                view.bind();
                break;

            case "Output":
                // initialisiere die Objekte
                outputV = loader.getController();
                
                outputV.setViewModel(outputVM);
                model.addPropertyChangeListener(viewModel);
                model.addPropertyChangeListener(outputVM);
                outputV.bind();
                break;
        }

        // All the binding need to be done here
        scene = new Scene(root);

        // minimize window
        secondaryStage.setIconified(true);

        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

    public void exit()
    {
        System.exit(0);
    }
    
    public void changeView(String title, String url) throws IOException
    {
        loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();
        Scene scene;

        // setting up the views
        switch (title)
        {
            case "View":
                view = loader.getController();
                
                
                view.setViewModel(viewModel);
                model.addPropertyChangeListener(viewModel);
                model.addPropertyChangeListener(outputVM);
                view.bind();
                break;
                
            case "output":
                outputV = loader.getController();
                
                outputV.setViewModel(outputVM);
                model.addPropertyChangeListener(outputVM);
                model.addPropertyChangeListener(viewModel);
                outputV.bind();
                break;
        }

        // All the binding need to be done here
        scene = new Scene(root);

        // setting scene to not resizable and undecorated
        secondaryStage.setResizable(true);

        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
}
