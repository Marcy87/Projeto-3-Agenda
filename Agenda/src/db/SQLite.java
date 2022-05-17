package db;

import entities.CadastroAgenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLite {

    private Connection conn;
    private Statement stm;

    //Conexão ao Banco de dados
    public SQLite() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:agenda.db");
        this.stm = this.conn.createStatement();
    }


    //Método responsavél por inserir cadastro de agendamento
    public void insertAgendamento(CadastroAgenda agenda) {
        try {
            this.stm = this.conn.createStatement();
            String SQLInsertAgendamento = "insert into agenda (compromisso, data, hora) values ('" + agenda.getCompromisso() + "', '" + agenda.getData() + "', '" + agenda.getHora() + "')";
            this.stm.executeUpdate(SQLInsertAgendamento);   //executar atualização

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método para recuperar os dados da tabela Agenda
    public List<CadastroAgenda> getAgenda() throws SQLException {

        List<CadastroAgenda> listaAgenda = new ArrayList<>();
        ResultSet resultSet;

        resultSet = this.stm.executeQuery("select * from agenda");

        while (resultSet.next()) {
            listaAgenda.add(new CadastroAgenda(resultSet.getString("compromisso"), resultSet.getString("data"), resultSet.getString("hora")));
        }

        resultSet.close();
        return listaAgenda;
    }

    //Método para verificar se a data está registrado no banco de dados
    public List<CadastroAgenda> filtroAgenda(String data){
        List<CadastroAgenda> listaFiltro = new ArrayList<>();
        try{
            ResultSet resultSet;
            resultSet = this.stm.executeQuery("select Upper(compromisso) as compromisso, data, hora " +
                                                  "from agenda where data like '%"+data+"%' order by data asc ");

            while (resultSet.next()) {
                listaFiltro.add(new CadastroAgenda(resultSet.getString("compromisso"), resultSet.getString("data"), resultSet.getString("hora")));
            }

            resultSet.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return listaFiltro;
    }

    //Eliminar objeto Produto Categoria dentro do banco de dados
    public void eliminarAgenda(CadastroAgenda agenda) {
        try {
            this.stm = this.conn.createStatement();
            String cmdDelete = "delete from agenda where data = '" + agenda.getData() + "'";
            this.stm.executeUpdate(cmdDelete);  //executar o comando

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}








