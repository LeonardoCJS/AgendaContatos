package ui;

import exception.AgendaException;
import exception.ContatoNaoEncontradoException;
import model.Contato;
import model.enums.Categoria;
import repository.ContatoRepositoryJson;
import service.AgendaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final AgendaService agendaService;

    public Menu(AgendaService agendaService){
        this.agendaService = agendaService;
    }

    public void rodar(){
        boolean sair = false;
        do {
            mostrarMenu();
            String opcao = sc.nextLine().trim();
            switch (opcao) {
                case "1" -> addContato();
                case "2" -> removeContato();
                case "3" -> atualizarContato();
                case "4" -> buscarPorNome();
                case "5" -> buscarPorTelefone();
                case "6" -> listarTodos();
                case "7" -> listarOrdenadoNome();
                case "0" -> sair = true;
                default -> System.out.println("Opção invalida!");
            }
        }while(!sair);
    }

    private void mostrarMenu(){
        System.out.println("====== Agenda ======");
        System.out.println("1. Adicionar contato");
        System.out.println("2. Remover contato");
        System.out.println("3. Atualizar contato");
        System.out.println("4. Buscar por nome");
        System.out.println("5. Buscar por telefone");
        System.out.println("6. Listar todos");
        System.out.println("7. Listar ordenados por nome");
        System.out.println("0. Sair");
        System.out.println("Selecione um opção: ");
    }

    private void addContato(){
        try {
            System.out.println("Digite o nome do contato: ");
            String nome = sc.nextLine();
            System.out.println("Digite o telefone do contato: ");
            String telefone = sc.nextLine();
            System.out.println("Digite o email do contato: ");
            String email = sc.nextLine();
            System.out.println("Digite a categoria do contato (AMIGO, TRABALHO ou FAMILIA): ");
            String categoriaInput = sc.nextLine();
            Categoria categoria;
            try {
                categoria = Categoria.valueOf(categoriaInput.toUpperCase());
            }catch (IllegalArgumentException e){
                throw  new IllegalArgumentException("Categoria invalida!");
            }

            Contato contato = new Contato(nome, telefone, email, categoria);
            agendaService.addContato(contato);
            System.out.println("Contato adicionado com sucesso!");
        }catch (AgendaException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removeContato(){
        try {
            List<Contato> contatos = agendaService.listarContatos();
            if (contatos.isEmpty()){
                System.out.println("Nenhum contato encontrado!");
                return;
            }
            System.out.println("====== Lista de Contatos ======");
            for (int i = 0; i < contatos.size(); i++){
                System.out.println((i+1) + ". " + contatos.get(i));
                System.out.println("----------------------------");
            }
            System.out.println("Digite o numero do contato que deseja remover: ");
            int escolha = Integer.parseInt(sc.nextLine());
            escolha--;
            Contato contato = contatos.get(escolha);
            agendaService.removeContato(contato.getId());
            System.out.println("Contato removido com sucesso!");
        } catch (AgendaException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarContato(){
        try {
            List<Contato> contatos = agendaService.listarContatos();
            if (contatos.isEmpty()){
                System.out.println("Nenhum contato encontrado!");
                return;
            }
            System.out.println("====== Lista de Contatos ======");
            for (int i = 0; i < contatos.size(); i++){
                System.out.println((i+1) + ". " + contatos.get(i));
                System.out.println("----------------------------");
            }
            System.out.println("Digite o numero do contato que deseja atualizar: ");
            int escolha = Integer.parseInt(sc.nextLine());
            escolha--;
            Contato contato = contatos.get(escolha);
            try {
                System.out.println("Digite o nome do contato: ");
                String nome = sc.nextLine();
                System.out.println("Digite o telefone do contato: ");
                String telefone = sc.nextLine();
                System.out.println("Digite o email do contato: ");
                String email = sc.nextLine();
                System.out.println("Digite a categoria do contato (AMIGO, TRABALHO ou FAMILIA): ");
                String categoriaInput = sc.nextLine();
                Categoria categoria;
                try {
                    categoria = Categoria.valueOf(categoriaInput.toUpperCase());
                }catch (IllegalArgumentException e){
                    throw  new IllegalArgumentException("Categoria invalida!");
                }
                Contato contatoUpdade = new Contato(nome, telefone, email, categoria);
                agendaService.atualizarContato(contato.getId(), contatoUpdade);
                System.out.println("Contato atualizado com sucesso!");
            }catch (AgendaException e){
                System.out.println("Erro: " + e.getMessage());
            }
        }catch (AgendaException e){
            System.out.println("Erro: " + e.getMessage());
        }

    }

    private void buscarPorNome(){
        try{
            System.out.println("Digite o nome do contato: ");
            String nome = sc.nextLine();
            List<Contato> contatos = agendaService.buscarContatoPorNome(nome);
            if (contatos.isEmpty()) {
                System.out.println("Nenhum contato encontrado!");
                return;
            }
            contatos.forEach(System.out::println);
        }catch (ContatoNaoEncontradoException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarPorTelefone(){
        try{
            System.out.println("Digite o telefone do contato: ");
            String telefone = sc.nextLine();
            Contato contato = agendaService.buscarContatoPorTelefone(telefone);
            System.out.println(contato);
        }catch (ContatoNaoEncontradoException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodos(){
        List<Contato> contatos = agendaService.listarContatos();
        if (contatos.isEmpty()){
            System.out.println("Nenhum contato encontrado!");
            return;
        }
        System.out.println("====== Lista de Contatos ======");
        for (int i = 0; i < contatos.size(); i++){
            System.out.println((i+1) + ". " + contatos.get(i));
            System.out.println("----------------------------");
        }
    }

    private void listarOrdenadoNome(){
        List<Contato> contatos = agendaService.listarOrdenados();
        if (contatos.isEmpty()){
            System.out.println("Nenhum contato encontrado!");
            return;
        }
        System.out.println("====== Lista de Contatos Ordenado ======");
        for (int i = 0; i < contatos.size(); i++){
            System.out.println((i+1) + ". " + contatos.get(i));
            System.out.println("----------------------------");
        }
    }

}
