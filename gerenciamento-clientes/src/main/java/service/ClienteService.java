package service;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import model.Cliente;

@Named
@ApplicationScoped
public class ClienteService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Cliente cliente) {
        em.persist(cliente);
    }

    public List<Cliente> findAll() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public Cliente findById(Long id) {
        return em.find(Cliente.class, id);
    }

    @Transactional
    public void update(Cliente cliente) {
        em.merge(cliente);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = findById(id);
        if (cliente != null) {
            em.remove(cliente);
        }
    }

    public List<Cliente> findByNomeContainingIgnoreCase(String nome) {
        return em.createQuery(
                "SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))", Cliente.class)
                .setParameter("nome", nome)
                .getResultList();
    }
}