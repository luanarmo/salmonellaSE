package sample;


import files.Indice;
import files.Master;
import files.Registry;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TableView<String[]> tvPremisas;
    @FXML
    Button btnAdd, btnSearch, btnDelete, btnAll, btnUpdate;
    @FXML
    TextField taAdd, taSearch, taDelete, taUpdateKey, taUpdateVal;
    ContextMenu menuOculto;
    MenuItem add = new MenuItem("anadir");
    MenuItem update = new MenuItem("actualizar");
    MenuItem delete = new MenuItem("eliminar");

    Master master = new Master();
    Indice indice = new Indice();
    List<Registry> registries = new ArrayList<>();
    List<TableColumn> columns = new ArrayList<>();
    String[][] data;
    int llave = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuOculto = new ContextMenu();
        add.setOnAction(x -> showMessage("ADD", "Add something", Alert.AlertType.INFORMATION));
        update.setOnAction(x -> showMessage("ADD", "Add something", Alert.AlertType.INFORMATION));
        delete.setOnAction(x -> eliminar());
        btnAdd.setOnAction(x -> addPremisa());
        btnDelete.setOnAction(e -> eliminar());
        btnAll.setOnAction(e -> refresh());
        btnUpdate.setOnAction(e -> actualizar());
        menuOculto.getItems().addAll(add, update, delete);
        tvPremisas.setContextMenu(menuOculto);
        btnSearch.setOnAction(e -> buscar());
        try {
            registries = master.read();
            indice.construirArbol();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addColums();
        tvPremisas.getItems().addAll(Arrays.asList(getData()));
    }

    private void addColums() {
        for (int i = 1; i <= 23; i++) {
            if (i < 23) {
                TableColumn column = new TableColumn<String[], String>("C" + i);
                tvPremisas.getColumns().add(column);
                columns.add(column);
            } else {
                TableColumn column = new TableColumn<String[], String>("P");
                tvPremisas.getColumns().add(column);
                columns.add(column);
            }
        }
        for (int u = 0; u < 23; u++) {
            int finalU = u;
            columns.get(u).setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    String[] x = p.getValue();
                    if (x != null && x.length > finalU) {
                        return new SimpleStringProperty(x[finalU]);
                    } else {
                        return new SimpleStringProperty("<no name>");
                    }
                }
            });
        }
    }

    private void showMessage(String title, String msg, Alert.AlertType type) {
        Alert message = new Alert(type);
        message.setTitle(title);
        message.setContentText(msg);
        message.showAndWait();
    }

    private String[][] getData() {
        try {
            registries = master.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] data = new String[registries.size()][23];
        for (int i = 0; i < registries.size(); i++) {
            data[i] = registries.get(i).getRecords();
        }
        return data;
    }

    private void addPremisa() {
        String aux = taAdd.getText();
        String[] premisas = aux.split(" ");
        int registros = master.getNumberOfRegistries();
        try {
            llave = indice.llaveMasGrande() + 1;
            indice.Insercion(llave, registros);
            master.save(new Registry(false, premisas));
            registries.clear();
            registries = master.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        refresh();
    }

    private void refresh() {
        tvPremisas.getItems().clear();
        columns.clear();
        tvPremisas.getItems().addAll(Arrays.asList(getData()));
        taAdd.clear();
    }

    private void buscar() {
        String val = taSearch.getText();

        try {
            int key = Integer.parseInt(val);
            int registro = indice.Recuperacion(key);
            Registry registry = master.readRegistry(registro);
            tvPremisas.getItems().clear();
            tvPremisas.getItems().addAll(registry.getRecords());
        } catch (NumberFormatException | IOException e) {
            showMessage("Error", "Tiene que ingresar un valor entero", Alert.AlertType.ERROR);
        } catch (NullPointerException e) {
            showMessage("Error", "El registro con esa llave, no existe", Alert.AlertType.ERROR);
        }

        taSearch.clear();
    }

    private void eliminar() {
        String val = taDelete.getText();

        try {
            int key = Integer.parseInt(val);
            int registro = indice.Recuperacion(key);
            Registry registry = master.readRegistry(registro);
            indice.Eliminar(key);
            indice.Mantenimiento();
            master.delete(registro);
        } catch (NumberFormatException | IOException e) {
            showMessage("Error", "Tiene que ingresar un valor entero", Alert.AlertType.ERROR);
        } catch (NullPointerException e) {
            showMessage("Error", "El registro con esa llave, no existe", Alert.AlertType.ERROR);
        }
        taDelete.clear();
        refresh();
    }

    private void actualizar() {
        String val = taUpdateVal.getText();
        String keyVal = taUpdateKey.getText();

        if (val.trim().length() == 0 || keyVal.trim().length() == 0)
            showMessage("Error", "No deje campos vacios", Alert.AlertType.ERROR);
        else {
            try {
                String[] premisas = val.split(" ");
                int key = Integer.parseInt(keyVal);
                int registro = indice.Recuperacion(key);
                master.update(registro, new Registry(false, premisas));
            } catch (NumberFormatException | IOException e) {
                showMessage("Error", "Tiene que ingresar un valor entero", Alert.AlertType.ERROR);
            } catch (NullPointerException e) {
                showMessage("Error", "El registro con esa llave, no existe", Alert.AlertType.ERROR);
            }
        }
        refresh();
        taUpdateKey.clear();
        taUpdateVal.clear();
    }
}