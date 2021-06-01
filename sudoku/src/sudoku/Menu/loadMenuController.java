package sudoku.Menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import sudoku.IO.savesArray;
import sudoku.IO.loadSudoku;
import sudoku.Board.boardController;

/**
 * Mentés betöltése
 */
public class loadMenuController {

    public Button backButton;
    public Button continueButton;
    public TableView<loaderDataType> loadTable;
    public TextArea searchBox;
    public TableColumn nameCol;
    public TableColumn sizeCol;
    public TableColumn timeCol;
    public Button searchButton;
    public Button resetButton;

    private Stage currentStage;

    private ObservableList<loaderDataType> data;

    loadMenuController(){
        currentStage = new Stage();
        makeDataStruct();
        load();
    }

    /**
     * Beolvassa a mentéseket
     */
    private void makeDataStruct() {
        try {
            if (new File("savesSample").exists()) {
                ObjectInputStream loader = new ObjectInputStream(new FileInputStream("savesSample"));
                savesArray array = (savesArray) loader.readObject();
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
     * Betölti az abalakot
     */
    private void load(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadMenu.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            Parent main = loader.load();
            Scene root = new Scene(main);
            currentStage.setTitle("Sudoku - Játékállás betöltése");
            currentStage.setScene(root);
            currentStage.setResizable(false);
            currentStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<loaderDataType, String>("name"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<loaderDataType, String>("size"));
        timeCol.setCellValueFactory(new PropertyValueFactory<loaderDataType, String>("time"));

        loadTable.setItems(data);

    }


    /**
     * Paraméter szerinti egyezést nézi egy listaelemmel
     * @param data
     * @param name
     * @return
     */
    private boolean searchData(loaderDataType data, String name){
        return data.getName().toLowerCase().equals(name.toLowerCase());
    }

    /**
     * Egy parameter szerinti lerendezett listát ad vissza
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
     * Gombok EventHandler-je
     * @param event
     */
    public void buttonAction(ActionEvent event) {
        if (event.getSource() == backButton){
            currentStage.close();
            new mainMenuController();
        } else
        if (event.getSource() == continueButton){
            loaderDataType item = loadTable.getSelectionModel().getSelectedItem();
            if (item != null) {
                System.out.println(item.getName());
                loadSudoku load = new loadSudoku(item.getName());
                currentStage.close();
                new boardController(load.getLoad());
            }
        } else
        if (event.getSource() == searchButton){
            loadTable.setItems(filterList(data, searchBox.getText()));
        } else
        if (event.getSource() == resetButton){
            loadTable.setItems(data);
            searchBox.clear();
        }
    }
}
