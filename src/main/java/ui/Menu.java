package ui;

import exception.AgendaException;
import model.Contato;
import model.enums.Categoria;
import service.AgendaService;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final AgendaService agendaService;

    public Menu(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    public void rodar() {
        boolean sair = false;
        do {
            mostrarMenu();
            String opcao = sc.nextLine().trim();
            switch (opcao) {
                case "1" -> adicionarContato();
                case "2" -> removerContato();
                case "3" -> atualizarContato();
                case "4" -> buscarPorNome();
                case "5" -> buscarPorTelefone();
                case "6" -> listarTodos();
                case "7" -> listarOrdenadosPorNome();
                case "0" -> sair = true;
                default -> System.out.println("Opção inválida!");
            }
        } while (!sair);
    }

    private void mostrarMenu() {
        System.out.println("====== Agenda ======");
        System.out.println("1. Adicionar contato");
        System.out.println("2. Remover contato");
        System.out.println("3. Atualizar contato");
        System.out.println("4. Buscar por nome");
        System.out.println("5. Buscar por telefone");
        System.out.println("6. Listar todos");
        System.out.println("7. Listar ordenados por nome");
        System.out.println("0. Sair");
        System.out.print("Selecione uma opção: ");
    }

    private void adicionarContato() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            Categoria categoria = lerCategoria();
            agendaService.adicionarContato(new Contato(nome, telefone, email, categoria));
            System.out.println("Contato adicionado com sucesso!");
        } catch (AgendaException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerContato() {
        try {
            List<Contato> contatos = agendaService.listarContatos();
            if (contatos.isEmpty()) {
                System.out.println("Nenhum contato encontrado!");
                return;
            }
            System.out.println("====== Lista de Contatos ======");
            exibirContatos(contatos);
            System.out.print("Digite o número do contato que deseja remover: ");
            int escolha = Integer.parseInt(sc.nextLine()) - 1;
            agendaService.removerContato(contatos.get(escolha).getId());
            System.out.println("Contato removido com sucesso!");
        } catch (AgendaException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida, digite um número.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: Número fora do intervalo.");
        }
    }

    private void atualizarContato() {
        try {
            List<Contato> contatos = agendaService.listarContatos();
            if (contatos.isEmpty()) {
                System.out.println("Nenhum contato encontrado!");
                return;
            }
            System.out.println("====== Lista de Contatos ======");
            exibirContatos(contatos);
            System.out.print("Digite o número do contato que deseja atualizar: ");
            int escolha = Integer.parseInt(sc.nextLine()) - 1;
            Contato contato = contatos.get(escolha);

            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            Categoria categoria = lerCategoria();

            agendaService.atualizarContato(contato.getId(), new Contato(nome, telefone, email, categoria));
            System.out.println("Contato atualizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida, digite um número.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: Número fora do intervalo.");
        } catch (AgendaException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarPorNome() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            List<Contato> contatos = agendaService.buscarContatoPorNome(nome);
            if (contatos.isEmpty()) {
                System.out.println("Nenhum contato encontrado!");
                return;
            }
            System.out.println("====== Resultado da Busca ======");
            exibirContatos(contatos);
        } catch (AgendaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarPorTelefone() {
        try {
            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            Contato contato = agendaService.buscarContatoPorTelefone(telefone);
            System.out.println(contato);
        } catch (AgendaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodos() {
        List<Contato> contatos = agendaService.listarContatos();
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato encontrado!");
            return;
        }
        System.out.println("====== Lista de Contatos ======");
        exibirContatos(contatos);
    }

    private void listarOrdenadosPorNome() {
        List<Contato> contatos = agendaService.listarOrdenados();
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato encontrado!");
            return;
        }
        System.out.println("====== Lista de Contatos Ordenados por Nome ======");
        exibirContatos(contatos);
    }

    private void exibirContatos(List<Contato> contatos) {
        for (int i = 0; i < contatos.size(); i++) {
            System.out.println((i + 1) + ". " + contatos.get(i));
            System.out.println("----------------------------");
        }
    }

    private Categoria lerCategoria() {
        System.out.print("Categoria (AMIGO, TRABALHO ou FAMILIA): ");
        String input = sc.nextLine();
        try {
            return Categoria.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria inválida: " + input);
        }
    }
}
