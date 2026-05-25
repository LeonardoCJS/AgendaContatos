package service;

import exception.AgendaException;
import exception.ContatoNaoEncontradoException;
import model.Contato;
import repository.ContatoRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaService {
    private final ContatoRepository contatoRepository;
    private List<Contato> contatos;
    private long proximoId;

    public AgendaService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
        this.contatos = contatoRepository.carregar();
        this.proximoId = contatos.stream()
                .mapToLong(c -> c.getId())
                .max()
                .orElse(0L) + 1L;
    }

    public void addContato(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("Contato não pode ser nulo.");
        }
        if (contatos.stream().anyMatch(c -> c.getTelefone().equals(contato.getTelefone()))) {
            throw new AgendaException("Contato ja existente.");
        }
        Contato contatoAdd = new Contato(proximoId, contato.getNome(), contato.getTelefone(), contato.getEmail(), contato.getCategoria());
        contatos.add(contatoAdd);
        proximoId++;
        contatoRepository.salvar(contatos);
    }

    public void removeContato(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id não pode ser nulo.");
        }

        boolean contatoRemovido = contatos.removeIf(c -> c.getId().equals(id));

        if (!contatoRemovido) {
            throw new ContatoNaoEncontradoException("Contato não encontrado. Id: " + id);
        }
        contatoRepository.salvar(contatos);
    }


    public List<Contato> buscarContatoPorNome (String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }

        return contatos.stream()
                .filter(c -> c.getNome().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());

    }

    public Contato buscarContatoPorTelefone(String telefone) {
        if (telefone == null) {
            throw new IllegalArgumentException("Telefone não pode ser nulo.");
        }

        return contatos.stream()
                .filter(c -> c.getTelefone().equals(telefone))
                .findFirst()
                .orElseThrow(() -> new ContatoNaoEncontradoException("Contato não encontrado. Telefone: " + telefone));
    }


}
