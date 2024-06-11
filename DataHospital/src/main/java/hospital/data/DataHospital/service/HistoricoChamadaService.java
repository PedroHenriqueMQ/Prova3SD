package hospital.data.DataHospital.service;

import hospital.data.DataHospital.DTO.HistoricoChamadaDTO;
import hospital.data.DataHospital.model.HistoricoChamada;
import hospital.data.DataHospital.ropository.HistoricoChamadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

interface IHistoricoChamadaService {
    List<HistoricoChamadaDTO> findAllWithoutId();
    List<HistoricoChamada> findAll();
    Optional<HistoricoChamadaDTO> findById(Long id);
    List<HistoricoChamadaDTO> findAllByPacienteCpfWithoutId(String cpf);
    List<HistoricoChamada> findAllByPacienteCpfWithId(String cpf);
    boolean save(HistoricoChamada historicoChamada);
    boolean deleteById(Long id);
}

@Service
public class HistoricoChamadaService implements IHistoricoChamadaService {
    HistoricoChamadaRepository historicoChamadaRepository;

    @Autowired
    public HistoricoChamadaService(HistoricoChamadaRepository historicoChamadaRepository) {
        this.historicoChamadaRepository = historicoChamadaRepository;
    }

    @Override
    public List<HistoricoChamadaDTO> findAllWithoutId() {
        try {
            List<HistoricoChamada> todasChamadas = findAll();
            List<HistoricoChamadaDTO> todasChamadasDTO = new ArrayList<>(Collections.emptyList());

            for(HistoricoChamada chamada : todasChamadas) {
                todasChamadasDTO.add(new HistoricoChamadaDTO(chamada));
            }

            return todasChamadasDTO;
        } catch (DataAccessException e) {
            System.err.println("Erro ao recuperar todos as chamadas: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<HistoricoChamada> findAll() {
        try {
            return historicoChamadaRepository.findAll();
        } catch (DataAccessException e) {
            System.err.println("Erro ao recuperar todos os históricos de chamadas: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<HistoricoChamadaDTO> findById(Long id) {
        try {
            Optional<HistoricoChamada> historicoChamada = historicoChamadaRepository.findById(id);

            if (historicoChamada.isPresent())
                return Optional.of(new HistoricoChamadaDTO(historicoChamada.get()));
            else return Optional.empty();
        } catch (DataAccessException e) {
            System.err.println("Erro ao encontrar um histórico de chamada pelo seu ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<HistoricoChamadaDTO> findAllByPacienteCpfWithoutId(String cpf) {
        try {
            List<HistoricoChamadaDTO> todasChamadasDTO = findAllWithoutId();
            List<HistoricoChamadaDTO> todasChamadasDeUmPacienteDTO = new ArrayList<>(Collections.emptyList());

            for (HistoricoChamadaDTO chamadaDTO : todasChamadasDTO) {
                if(chamadaDTO.getPacienteCpf().equals(cpf))
                    todasChamadasDeUmPacienteDTO.add(chamadaDTO);
            }

            return todasChamadasDeUmPacienteDTO;
        } catch (DataAccessException e) {
            System.err.println("Erro ao recuperar todas as chamadas de um mesmo paciente: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<HistoricoChamada> findAllByPacienteCpfWithId(String cpf) {
        try {
            List<HistoricoChamada> todasChamadas = findAll();
            List<HistoricoChamada> todasChamadasDeUmPaciente = new ArrayList<>(Collections.emptyList());

            for (HistoricoChamada chamada : todasChamadas) {
                if(chamada.getPaciente().getCpf().equals(cpf))
                    todasChamadasDeUmPaciente.add(chamada);
            }

            return todasChamadasDeUmPaciente;
        } catch (DataAccessException e) {
            System.err.println("Erro ao recuperar todas as chamadas de um mesmo paciente: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public boolean save(HistoricoChamada historicoChamada) {
        try {
            historicoChamadaRepository.save(historicoChamada);
            return true;
        } catch (DataAccessException e) {
            System.err.println("Erro ao salvar um novo histórico de chamada: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            if(findById(id).isPresent()) {
                historicoChamadaRepository.deleteById(id);
                return true;
            } else return false;
        } catch (DataAccessException e) {
            System.err.println("Erro ao excluir um histórico de chama pelo Id: " + e.getMessage());
            return false;
        }
    }
}
