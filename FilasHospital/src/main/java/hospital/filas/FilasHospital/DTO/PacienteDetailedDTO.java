package hospital.filas.FilasHospital.DTO;


public class PacienteDetailedDTO {
    private String cpf;
    private String nome;
    private String condicaoDescricao;
    private Prioridade prioridade;

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCondicaoDescricao() {
        return condicaoDescricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }
}
