package hospital.data.DataHospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false)
    private String nome;
    @Column(name = "condicao_descricao", columnDefinition = "TEXT", length = 300)
    private String condicaoDescricao;
    @Column(nullable = false)
    private Prioridade prioridade;

    public Long getId() {
        return id;
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

    public String getCpf() {
        return cpf;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCondicaoDescricao(String condicaoDescricao) {
        this.condicaoDescricao = condicaoDescricao;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf.replaceAll("[.-]", "");
    }
}
