package repository;

import model.Contato;

import java.util.List;

public interface ContatoRepository {

    void salvar(List<Contato> contatos);
    List<Contato> carregar();
}
