package scenes;

import db.SQLite;
import entities.CadastroAgenda;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;


public class Agenda extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Descrição do compromisso
        Label lbCompromisso = new Label("Compromisso");
        TextField tfCompromisso = new TextField();

        //Data
        Label lbDataCompromisso = new Label("Data");
        DatePicker dtDataCompromisso = new DatePicker();

        //Hora
        Label lbHora = new Label("Horário");
        TextField tfHora = new TextField();
        HBox hbHora = new HBox(tfHora);

        //Botão Agendar
        Button btnAgendar = new Button("Agendar");
        HBox hbAgendar = new HBox(btnAgendar);

        //Botão Listar
        Button btnListar = new Button("Listar");
        HBox hbListar = new HBox(btnListar);

        Alert alertGeral = new Alert(Alert.AlertType.INFORMATION, "Informação");

        //Mostrar na Tela//Mostrar na Tela
        TilePane tpAgenda = new TilePane();
        tpAgenda.getChildren().add(lbCompromisso);
        tpAgenda.getChildren().add(tfCompromisso);

        tpAgenda.getChildren().add(lbDataCompromisso);
        tpAgenda.getChildren().add(dtDataCompromisso);

        tpAgenda.getChildren().add(lbHora);
        tpAgenda.getChildren().add(hbHora);

        tpAgenda.getChildren().add(hbAgendar);
        tpAgenda.getChildren().add(btnAgendar);

        tpAgenda.getChildren().add(hbListar);
        tpAgenda.getChildren().add(btnListar);

        //EVENTO APÓS TER APERTADO O BOTÃO AGENDAR
        EventHandler<ActionEvent> eventoAgendar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String sdtCompromisso = dtDataCompromisso.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                alertGeral.setContentText("Evento: " + tfCompromisso.getText() + " Data: " + sdtCompromisso);
                alertGeral.show();

                CadastroAgenda novoCadastroAgenda = new CadastroAgenda();
                novoCadastroAgenda.setCompromisso(tfCompromisso.getText());
                novoCadastroAgenda.setData(sdtCompromisso);
                novoCadastroAgenda.setHora(tfHora.getText());

                try {
                    SQLite dbAgenda = new SQLite();
                    dbAgenda.insertAgendamento(novoCadastroAgenda);

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                //Após confirmar, o TextField vai ser limpo
                tfCompromisso.clear();
                tfHora.clear();
            }
        };

        //EVENTO APÓS TER APERTADO O BOTÃO LISTAR
        EventHandler<ActionEvent> eventoListar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListarCompromisso listarCompromisso = new ListarCompromisso();
                try {
                    listarCompromisso.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        btnAgendar.setOnAction(eventoAgendar);
        btnListar.setOnAction(eventoListar);

        Scene scAgenda = new Scene(tpAgenda, 400, 400);
        primaryStage.setScene(scAgenda);
        primaryStage.setTitle("Agenda de Compromisso v1.0");
        primaryStage.show();
    }

    public void begin() {
        launch();
    }
}
