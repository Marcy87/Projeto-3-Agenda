package entities;

public class CadastroAgenda {

    private String compromisso;
    private String data;
    private String hora;

    public CadastroAgenda(String compromisso, String data, String hora){
        this.compromisso = compromisso;
        this.data = data;
        this.hora = hora;
    }

    public CadastroAgenda() {

    }

    //Método get e set

    //Compromisso
    public String getCompromisso() {
        return this.compromisso;
    }

    public void setCompromisso(String compromisso) {
        this.compromisso = compromisso;
    }

    //Data
    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    //Hora
    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}