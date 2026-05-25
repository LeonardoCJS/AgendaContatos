import repository.ContatoRepository;
import repository.ContatoRepositoryJson;
import service.AgendaService;
import ui.Menu;

public class Main {
    public static void main(String[] args) {
        ContatoRepository contatoRepository = new ContatoRepositoryJson();
        AgendaService agendaService = new AgendaService(contatoRepository);
        Menu menu = new Menu(agendaService);
        menu.rodar();
    }
}
