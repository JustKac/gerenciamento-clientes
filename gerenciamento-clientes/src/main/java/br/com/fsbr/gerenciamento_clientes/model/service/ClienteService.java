package br.com.fsbr.gerenciamento_clientes.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fsbr.gerenciamento_clientes.exceptions.RequiredObjectIsNullException;
import br.com.fsbr.gerenciamento_clientes.exceptions.ResourceNotFoundException;
import br.com.fsbr.gerenciamento_clientes.model.entity.Cliente;
import br.com.fsbr.gerenciamento_clientes.model.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public List<Cliente> findClienteByName(String name) {
        return clienteRepository.findByNomeContainingIgnoreCase(name);
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Cliente create(Cliente cliente) {
        if (cliente == null) {
            throw new RequiredObjectIsNullException();
        }
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente newCliente) {
        if (newCliente == null) {
            throw new RequiredObjectIsNullException();
        }

        Cliente clienteFound = findById(id);
        clienteFound.updateInfo(newCliente);
        return clienteRepository.save(clienteFound);
    }

    public void delete(Long id) {
        clienteRepository.delete(findById(id));
    }
}