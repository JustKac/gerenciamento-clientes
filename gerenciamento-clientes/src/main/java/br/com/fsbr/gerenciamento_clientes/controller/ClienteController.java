package br.com.fsbr.gerenciamento_clientes.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fsbr.gerenciamento_clientes.model.entity.Cliente;
import br.com.fsbr.gerenciamento_clientes.model.service.ClienteService;
import br.com.fsbr.gerenciamento_clientes.webservice.client.ViaCepClient;
import br.com.fsbr.gerenciamento_clientes.webservice.dto.ViaCepResponseDTO;
import jakarta.annotation.PostConstruct;

@Component
@ViewScoped
public class ClienteController implements Serializable {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ViaCepClient viaCepClient;

    private Cliente cliente = new Cliente();
    private List<Cliente> clientes;
    private String nomePesquisa;  // Variável para pesquisa por nome

    @PostConstruct
    public void init() {
        listarClientes();
    }

    // Getters and setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getNomePesquisa() {
        return nomePesquisa;
    }

    public void setNomePesquisa(String nomePesquisa) {
        this.nomePesquisa = nomePesquisa;
    }

    // Método para listar clientes (também filtra por nome)
    public void listarClientes() {
        if (nomePesquisa == null || nomePesquisa.isEmpty()) {
            clientes = clienteService.findAll();  // Busca todos se não houver filtro
        } else {
            clientes = clienteService.findClienteByName(nomePesquisa);  // Busca filtrando por nome
        }
    }

    // Método para salvar ou atualizar cliente
    public String salvar() {
        try {
            if (cliente.getId() == null) {
                clienteService.create(cliente);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Cliente cadastrado com sucesso!");
            } else {
                clienteService.update(cliente.getId(), cliente);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Cliente atualizado com sucesso!");
            }
            listarClientes(); // Atualiza a lista de clientes após a operação
            limparFormulario(); // Limpa o formulário
            return redirecionarParaIndex();

        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao salvar cliente.");
            return null;
        }
    }

    private String limparFormulario() {
        cliente = new Cliente();
        return redirecionarParaForm();
    }

    // Método para consultar o ViaCEP e preencher os campos
    public void consultarCep() {
        try {
            if (cliente.getCep() != null && !cliente.getCep().isEmpty()) {
                // Chama o ViaCepClient passando o CEP do cliente
                ViaCepResponseDTO viaCepResponse = viaCepClient.consultaCep(cliente.getCep());

                // Preenche os campos com os dados do retorno ViaCEP
                cliente.setEndereco(viaCepResponse.logradouro());
                cliente.setBairro(viaCepResponse.bairro());
                cliente.setCidade(viaCepResponse.localidade());
                cliente.setEstado(viaCepResponse.uf());
            }
        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao consultar CEP.");
        }
    }

    // Método para editar um cliente (carregar cliente no formulário)
    public String editar(Cliente cliente) {
        this.cliente = cliente;  // Preenche o cliente para edição
        return redirecionarParaForm();  // Redireciona para a página de formulário
    }

    // Método para excluir um cliente
    public String excluir(Long id) {
        try {
            clienteService.delete(id);  // Exclui o cliente pelo ID
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Cliente excluído com sucesso!");
            listarClientes();  // Atualiza a lista de clientes após a exclusão
            return redirecionarParaIndex();
        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao excluir cliente.");
        }
        return null;
    }

    // Método para adicionar mensagens de FacesContext de forma segura
    private void adicionarMensagem(FacesMessage.Severity severity, String message) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            facesContext.addMessage(null, new FacesMessage(severity, message, null));
        } else {
            // Logar erro ou fazer outra ação se o FacesContext não estiver disponível
            System.err.println("FacesContext não disponível para adicionar mensagem.");
        }
    }

    // Métodos para redirecionar para as páginas
    public String redirecionarParaIndex() {
        return "index?faces-redirect=true";
    }

    public String redirecionarParaForm() {
        return "form?faces-redirect=true";
    }
}