package sudoku.Menu;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sudoku.IO.savesArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Dicsőséglista menü
 */
public class scoreMenuController {

    public Button backButton;
    public TableView<loaderDataType> loadTable;
    public TextArea searchBox;
    public TableColumn nameCol;
    public TableColumn sizeCol;
    public TableColumn timeCol;
    public Button searchButton;
    public Button resetButton;
    public RadioButton r9;
    public RadioButton r6;
    public RadioButton r4;

    private Stage currentStage;
    private ToggleGroup radioGroup;
    private ObservableList<loaderDataType> filteredList;

    private ObservableList<loaderDataType> data;

    scoreMenuController(){
        currentStage = new Stage();
        makeDataStruct();
        load();
    }

    /**
     * Beolvassa a már meglévő elmentett dicsőségtáblát
     */
    private void makeDataStruct() {
        try {
            if (new File("highScore").exists()) {
                ObjectInputStream loader = new ObjectInputStream(new FileInputStream("highScore"));
                savesArray array = (savesArray) loader.readObject();
                System.out.println(array.getArraySize());
                data = FXCollections.observableArrayList();
                for (int i=0; i < array.getArraySize();i++){
                    data.add( new loaderDataType(array.getName(i), array.getSize(i), array.getTime(i)));
                }
                loader.close();
            } else {
                data = FXCollections.observableArrayList();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ablak betöltő függvény
     */
    public void load(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scoreMenu.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            Parent main = loader.load();
            Scene root = new Scene(main);
            currentStage.setTitle("Sudoku - Dicsőségtábla");
            currentStage.setResizable(false);
            currentStage.setScene(root);
            currentStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<loaderDataType, String>("name"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<loaderDataType, String>("size"));
        timeCol.setCellValueFactory(new PropertyValueFactory<loaderDataType, String>("time"));

        loadTable.setItems(data);
        filteredList = data;

        radioGroup = new ToggleGroup();
        r4.setToggleGroup(radioGroup);
        r6.setToggleGroup(radioGroup);
        r9.setToggleGroup(radioGroup);
        radioGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> changeRadioButton(t1));
    }

    /**
     * A rádiógombok vezérléséért felelős föggvény
     * @param t1
     */
    private void changeRadioButton(Toggle t1) {
        if (t1 == r9){
            filteredList = filterList(data,"9x9");
            loadTable.setItems(filteredList);
            searchBox.clear();
        } else
        if (t1 == r6){
            filteredList = filterList(data,"6x6");
            loadTable.setItems(filteredList);
            searchBox.clear();
        } else
        if (t1 == r4){
            filteredList = filterList(data,"4x4");
            loadTable.setItems(filteredList);
            searchBox.clear();
        }
    }

    /**
     * A keresésnél megnézi hogz a beirt név egyzik-e az adott listaelemmel
     * @param data
     * @param name
     * @return
     */
    private boolean searchData(loaderDataType data, String name){
        return data.getName().toLowerCase().equals(name.toLowerCase()) ||
                data.getSize().toLowerCase().equals(name.toLowerCase());
    }

    /**
     * Egy adott paraméter szerinti lerendezett listát ad vissza
     * @param list
     * @param name
     * @return
     */
    private ObservableList<loaderDataType> filterList(List<loaderDataType> list, String name){
        List<loaderDataType> filteredList = new ArrayList<>();
        for (loaderDataType data : list){
            if (searchData(data, name)) filteredList.add(data);
        }
        return FXCollections.observableList(filteredList);
    }

    /**
     * Gombok EventHandlerje
     * @param event
     */
    public void buttonAction(ActionEvent event) {
        if (event.getSource() == backButton){
            currentStage.close();
            new mainMenuController();
        } else
        if (event.getSource() == searchButton){
            loadTable.setItems(filterList(filteredList, searchBox.getText()));
        } else
        if (event.getSource() == resetButton){
            loadTable.setItems(data);
            searchBox.clear();
            filteredList = data;
        }
    }
}
