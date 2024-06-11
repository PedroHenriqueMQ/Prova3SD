package hospital.data.DataHospital.DTO;

import hospital.data.DataHospital.model.HistoricoChamada;
import hospital.data.DataHospital.model.Paciente;
import java.time.LocalDateTime;

public class HistoricoChamadaDTO {
    private LocalDateTime dataHorario;
    private String pacienteCpf;

    public HistoricoChamadaDTO() {
    }

    public HistoricoChamadaDTO(HistoricoChamada historicoChamada) {
        this.dataHorario = historicoChamada.getDataHorario();
        this.pacienteCpf = historicoChamada.getPaciente().getCpf();
    }

    public LocalDateTime getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(LocalDateTime dataHorario) {
        this.dataHorario = dataHorario;
    }

    public String getPacienteCpf() {
        return pacienteCpf;
    }

    public void setPacienteCpf(String pacienteCpf) {
        this.pacienteCpf = pacienteCpf;
    }
}
