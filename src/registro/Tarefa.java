package registro;

public class Tarefa {

    private int id;
    private String done;
    private String data;
    private String prioridade;
    private String tarefa;
    private String detalhes;
    private String usuario;

    public Tarefa(
            int id,
            String done,
            String data,
            String prioridade,
            String tarefa,
            String detalhes,
            String usuario) {

        this.id = id;
        this.done = done;
        this.data = data;
        this.prioridade = prioridade;
        this.tarefa = tarefa;
        this.detalhes = detalhes;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public String getDone() {
        return done;
    }

    public String getData() {
        return data;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public String getTarefa() {
        return tarefa;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public String getUsuario() {
        return usuario;
    }
}