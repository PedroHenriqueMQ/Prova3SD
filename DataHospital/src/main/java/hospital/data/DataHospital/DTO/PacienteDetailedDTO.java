package hospital.data.DataHospital.DTO;

import hospital.data.DataHospital.model.Paciente;
import hospital.data.DataHospital.model.Prioridade;

public class PacienteDetailedDTO {
    private String cpf;
    private String nome;
    private String condicaoDescricao;
    private Prioridade prioridade;

    public PacienteDetailedDTO(Paciente paciente) {
        paciente.setCpf(paciente.getCpf());
        this.cpf = paciente.getCpf();
        this.nome = paciente.getNome();
        this.condicaoDescricao = paciente.getCondicaoDescricao();
        this.prioridade = paciente.getPrioridade();
    }

    public PacienteDetailedDTO() {
    }

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
