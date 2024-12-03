package bean;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.primefaces.PrimeFaces;

import model.Cliente;
import service.ClienteService;
import service.ViaCepService;

@Named("clienteView")
@SessionScoped
public class ClienteView implements Serializable {

    private Cliente cliente = new Cliente();
    private List<Cliente> clientes;
    private String nomeBusca;  // Campo para busca

    @Inject
    private ClienteService clienteService;

    @Inject
    private ViaCepService viaCepService;

    @Inject
    private FacesContext facesContext;

    @PostConstruct
    public void init() {
        clientes = clienteService.findAll();
    }

    public String save() {
        try {
            if (cliente.getId() == null) {
                clienteService.save(cliente);
            } else {
                clienteService.update(cliente);
            }
            clientes = clienteService.findAll();
            cliente = new Cliente();
            addPopupMessage("Sucesso", "Operação realizada com sucesso!", FacesMessage.SEVERITY_INFO);
            return redirectTo("index");
        } catch (Exception e) {
            addPopupMessage("Erro", "Erro ao salvar cliente: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }

    public String create() {
        this.cliente = new Cliente();
        return redirectTo("form");
    }

    public String update(Cliente cliente) {
        try {
            this.cliente = clienteService.findById(cliente.getId());
            return redirectTo("form");
        } catch (Exception e) {
            addPopupMessage("Erro", "Erro ao carregar cliente: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }

    public void delete(Long id) {
        try {
            clienteService.delete(id);
            clientes = clienteService.findAll();
            addPopupMessage("Sucesso", "Cliente removido com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addPopupMessage("Erro", "Erro ao remover cliente: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void buscarPorNome() {
        try {
            // Realiza a busca no banco de dados
            if (nomeBusca != null && !nomeBusca.trim().isEmpty()) {
                clientes = clienteService.findByNomeContainingIgnoreCase(nomeBusca);
            } else {
                // Se o campo de busca estiver vazio, exibe todos os clientes
                clientes = clienteService.findAll();
            }
        } catch (Exception e) {
            addPopupMessage("Erro", "Erro ao buscar clientes: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void buscarEndereco(AjaxBehaviorEvent event) {
        if (cliente.getCep() != null && !cliente.getCep().isEmpty()) {
            try {
                Cliente endereco = viaCepService.buscarEnderecoPorCep(cliente.getCep());
                if (endereco != null) {
                    atualizarEndereco(endereco);
                } else {
                    addPopupMessage("Erro", "CEP não encontrado", FacesMessage.SEVERITY_ERROR);
                }
            } catch (Exception e) {
                addPopupMessage("Erro", "Erro ao buscar CEP: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    private void atualizarEndereco(Cliente endereco) {
        cliente.setEndereco(endereco.getEndereco());
        cliente.setBairro(endereco.getBairro());
        cliente.setCidade(endereco.getCidade());
        cliente.setEstado(endereco.getEstado());
    }

    private void addPopupMessage(String summary, String detail, FacesMessage.Severity severity) {
        facesContext.addMessage(null, new FacesMessage(severity, summary, detail));
        PrimeFaces.current().executeScript("PF('popupDialog').show();");
    }

    private String redirectTo(String page) {
        return page + "?faces-redirect=true";
    }

    // Getters e Setters
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

    public String getNomeBusca() {
        return nomeBusca;
    }

    public void setNomeBusca(String nomeBusca) {
        this.nomeBusca = nomeBusca;
    }
}