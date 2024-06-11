package hospital.data.DataHospital.service;

import hospital.data.DataHospital.DTO.PacienteDetailedDTO;
import hospital.data.DataHospital.DTO.PacienteListViewDTO;
import hospital.data.DataHospital.model.Paciente;
import hospital.data.DataHospital.ropository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

interface IPacienteService {
    List<PacienteListViewDTO> findAllWithoutId();
    List<Paciente> findAll();
    Optional<PacienteDetailedDTO> findById(Long id);
    Optional<Long> findIdByCpf(String cpf);
    boolean save(Paciente paciente);
    boolean deleteById(Long id);
}

@Service
public class PacienteService implements IPacienteService {
    PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public List<PacienteListViewDTO> findAllWithoutId() {
        try {
            List<Paciente> pacientes = findAll();
            List<PacienteListViewDTO> pacienteListViewDTOs = new ArrayList<>(Collections.emptyList());

            for (Paciente paciente : pacientes) {
                pacienteListViewDTOs.add(new PacienteListViewDTO(paciente));
            }

            return pacienteListViewDTOs;
        } catch (DataAccessException e) {
            System.err.println("Erro ao buscar todos os pacientes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Paciente> findAll() {
        try {
            return pacienteRepository.findAll();
        } catch (DataAccessException e) {
            System.err.println("Erro ao recupurar todos os usuários: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<PacienteDetailedDTO> findById(Long id) {
        try {
            Optional<Paciente> paciente = pacienteRepository.findById(id);
            Optional<PacienteDetailedDTO> pacienteDetailedDTO = Optional.empty();

            if(paciente.isPresent())
                pacienteDetailedDTO = Optional.of(new PacienteDetailedDTO(paciente.get()));

            return pacienteDetailedDTO;
        } catch (DataAccessException e) {
            System.err.println("Erro ao buscar um paciente: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> findIdByCpf(String cpf) {
        try {
            List<Paciente> todosPacientes = findAll();

            for (Paciente pacienteAtual : todosPacientes) {
                if(pacienteAtual.getCpf().equals(cpf)) {
                    return Optional.of(pacienteAtual.getId());
                }
            }

            return Optional.empty();
        } catch (DataAccessException e) {
            System.err.println("Erro ao encontrar um paciente pelo cpf: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean save(Paciente paciente) {
        paciente.setCpf(paciente.getCpf());

        try {
            pacienteRepository.save(paciente);
            return true;
        } catch (DataAccessException e) {
            System.err.println("Erro ao salvar paciente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            if (findById(id).isPresent()) {
                pacienteRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException e) {
            System.err.println("Erro ao excluir um paciente: " + e.getMessage());
            return false;
        }
    }
}
