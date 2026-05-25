package repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.ErroLeituraException;
import model.Contato;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContatoRepositoryJson implements ContatoRepository {
    private static final String ARQUIVO = "src/main/resources/contatos.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void salvar(List<Contato> contatos) {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            writer.write(mapper.writeValueAsString(contatos));
        } catch (IOException e) {
            throw new ErroLeituraException("Erro ao salvar: " + e.getMessage());
        }
    }

    @Override
    public List<Contato> carregar() {
        File file = new File(ARQUIVO);
        if (file.exists()){
            try (FileReader reader = new FileReader(ARQUIVO)) {
                return mapper.readValue(reader, new TypeReference<List<Contato>>() {});
            }catch (IOException e){
                throw new ErroLeituraException("Erro ao carregar: " + e.getMessage());
            }
        }

        return new ArrayList<>();
    }
}
