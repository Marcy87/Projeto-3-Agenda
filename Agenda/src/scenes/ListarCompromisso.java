package scenes;

import db.SQLite;
import entities.CadastroAgenda;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ListarCompromisso extends Application {
    @Override
    public void start(Stage listarCompromisso) throws Exception {

        //Declaração da TableView
        TableView tableAgenda = new TableView();

        Alert alertEliminar = new Alert(Alert.AlertType.INFORMATION);

        //Declaração das Colunas da minha TableView
        TableColumn<CadastroAgenda, String> tcCompromisso = new TableColumn<>("compromisso");
        TableColumn<CadastroAgenda, String> tcData = new TableColumn<>("data");
        TableColumn<CadastroAgenda, String> tcHora = new TableColumn<>("hora");

        //Nome das Celulas de Controle ADICIONANDO
        tcCompromisso.setCellValueFactory(new PropertyValueFactory<>("compromisso"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcHora.setCellValueFactory(new PropertyValueFactory<>("hora"));

        //Vinculo entre TableView e a TableColumn
        tableAgenda.getColumns().add(tcCompromisso);
        tableAgenda.getColumns().add(tcData);
        tableAgenda.getColumns().add(tcHora);

        SQLite dbAgenda = new SQLite();
        for (CadastroAgenda cad : dbAgenda.getAgenda()) {
            tableAgenda.getItems().add(cad);
        }


        //BOTÃO ELIMINAR
        Button btnEliminar = new Button("Eliminar");

        //BOTÃO FILTRAR
        Label lbFiltro = new Label("Pesquisa:");
        TextField tfFiltro = new TextField();
        Button btnFiltro = new Button("Filtrar");

        //BOTÃO MOSTRAR TODA A MINHA LISTA
        Button btnMostrarLista = new Button("Mostrar Lista");

        TilePane tpCadastroListar = new TilePane();

        VBox vbox = new VBox(tableAgenda);

        tpCadastroListar.getChildren().add(vbox);
        tpCadastroListar.getChildren().add(btnEliminar);

        tpCadastroListar.getChildren().add(lbFiltro);
        tpCadastroListar.getChildren().add(tfFiltro);
        tpCadastroListar.getChildren().add(btnFiltro);

        tpCadastroListar.getChildren().add(btnMostrarLista);

        //EVENTO PARA ELIMINAR UM COMPROMISSO
        EventHandler<ActionEvent> eventoEliminar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tableAgenda.getSelectionModel().getSelectedItems().size() == 0) {
                    alertEliminar.setContentText("Não foi possível eliminar, selecione um usuário");
                } else {
                    Object agendaEliminar = tableAgenda.getSelectionModel().getSelectedItems().get(0);
                    CadastroAgenda eliminarCadastro = new CadastroAgenda();
                    String agenda = ((CadastroAgenda) agendaEliminar).getData();
                    eliminarCadastro.setData(agenda);

                    try {
                        SQLite dbAgenda = new SQLite();
                        dbAgenda.eliminarAgenda(eliminarCadastro);

                        tableAgenda.getItems().clear();

                        for (CadastroAgenda cad : dbAgenda.getAgenda()) {
                            tableAgenda.getItems().add(cad);
                        }

                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    alertEliminar.setContentText("Usuário eliminado");
                }
            }
        };

        EventHandler<ActionEvent> eventoFiltrar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    SQLite dbAgenda = new SQLite();

                    tableAgenda.getItems().clear();

                    for (CadastroAgenda cad : dbAgenda.filtroAgenda(tfFiltro.getText())) {
                        tableAgenda.getItems().add(cad);
                    }

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

        EventHandler<ActionEvent> eventoMostrarLista = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    SQLite dbAgenda = new SQLite();

                    tableAgenda.getItems().clear();

                    for (CadastroAgenda cad : dbAgenda.getAgenda()) {
                        tableAgenda.getItems().add(cad);
                    }

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

        btnEliminar.setOnAction(eventoEliminar);
        btnFiltro.setOnAction(eventoFiltrar);
        btnMostrarLista.setOnAction(eventoMostrarLista);

        Scene scene = new Scene(tpCadastroListar);
        listarCompromisso.setScene(scene);
        listarCompromisso.show();
    }

    public void begin() {
        launch();
    }
}
