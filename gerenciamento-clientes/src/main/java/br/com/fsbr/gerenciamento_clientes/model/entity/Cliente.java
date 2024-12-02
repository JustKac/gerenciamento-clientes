package br.com.fsbr.gerenciamento_clientes.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "clientes", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome é obrigatório")
    private String nome;

    @NotEmpty(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @Pattern(regexp = "\\d{8}", message = "O CEP deve ter 8 caracteres")
    private String cep;

    @Pattern(regexp = "\\d{2} \\d{4,5}-\\d{4}", message = "O telefone deve estar no formato 99 99999-9999")
    private String telefone;

    @NotEmpty(message = "O endereço é obrigatório")
    private String endereco;

    @NotEmpty(message = "O bairro é obrigatório")
    private String bairro;

    @NotEmpty(message = "A cidade é obrigatória")
    private String cidade;

    @NotEmpty(message = "O estado é obrigatório")
    private String estado;

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente updateInfo(Cliente updateCliente) {
        this.nome = updateCliente.getNome();
        this.email = updateCliente.getEmail();
        this.telefone = updateCliente.getTelefone();
        this.cep = updateCliente.getCep();
        this.endereco = updateCliente.getEndereco();
        this.bairro = updateCliente.getBairro();
        this.cidade = updateCliente.getCidade();
        this.estado = updateCliente.getEstado();
        return this;
    }

}
