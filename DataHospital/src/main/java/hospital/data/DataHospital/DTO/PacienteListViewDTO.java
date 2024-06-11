package hospital.data.DataHospital.DTO;

import hospital.data.DataHospital.model.Paciente;
import hospital.data.DataHospital.model.Prioridade;

public class PacienteListViewDTO {
    private String cpf;
    private String nome;
    private Prioridade prioridade;

    public PacienteListViewDTO(Paciente paciente) {
        paciente.setCpf(paciente.getCpf());
        this.cpf = paciente.getCpf();
        this.nome = paciente.getNome();
        this.prioridade = paciente.getPrioridade();
    }

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
