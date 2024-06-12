package hospital.filas.FilasHospital.DTO;

import java.time.LocalDateTime;

public class HistoricoChamadaDTO {
    private LocalDateTime dataHorario;
    private String pacienteCpf;

    public LocalDateTime getDataHorario() {
        return dataHorario;
    }

    public String getPacienteCpf() {
        return pacienteCpf;
    }
}
