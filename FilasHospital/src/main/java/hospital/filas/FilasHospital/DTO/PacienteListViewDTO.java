package hospital.filas.FilasHospital.DTO;


public class PacienteListViewDTO {
    private String cpf;
    private String nome;
    private Prioridade prioridade;

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }
}
