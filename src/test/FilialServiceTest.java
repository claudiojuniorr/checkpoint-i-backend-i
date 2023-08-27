package test;

import dao.impl.FilialDao;
import model.Filial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FilialService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilialServiceTest {
    private FilialDao filialDao;
    private FilialService filialService;
    private List<Filial> listFiliais;

    @BeforeEach
    public void setup() {
        filialDao = new FilialDao();
        filialService = new FilialService(filialDao);
        limparDatabase();
    }
private void limparDatabase(){
    List<Filial> filiais = filialService.buscarTodos();
    for (Filial filial : filiais) {
        filialService.excluirFilial(filial);
    }
}

    @Test
    public void testCriarFilial() {
        Filial filial1 = new Filial(1,
                "Hotel Brasil",
                "Rua da Liberdade",
                100,
                "Sao Paulo",
                "SP",
                true);
        Filial filial2 = new Filial(2,
                "Hotel dos Manos",
                "Rua da Liberdade",
                200,
                "Sao Paulo",
                "SP",
                false);
        Filial filial3 = new Filial(3,
                "Hotel da Praia",
                "Avenida Beira-Mar",
                300,
                "Rio de Janeiro",
                "RJ",
                true);
        Filial filial4 = new Filial(4,
                "Hotel Montanha",
                "Rua da Serra",
                400,
                "Belo Horizonte",
                "MG",
                false);
        Filial filial5 = new Filial(5,
                "Hotel Flores",
                "Rua das Rosas",
                500,
                "Curitiba",
                "PR",
                true);

        filialService.criarFilial(filial1);
        filialService.criarFilial(filial2);
        filialService.criarFilial(filial3);
        filialService.criarFilial(filial4);
        filialService.criarFilial(filial5);

        List<Filial> filiais = filialService.buscarTodos();
        assertNotNull(filiais, "Falha ao buscar todas as filiais");
        assertEquals(5, filiais.size(), "Número incorreto de filiais inseridas");
        listFiliais = filiais;
    }

    @Test
    public void testBuscarFilialPorId() {
        testCriarFilial();
        Filial filialEncontrada = filialService.buscarPorId(listFiliais.get(0).getId());
        assertNotNull(filialEncontrada, "Falha ao buscar filial por ID");
        assertEquals(listFiliais.get(0).getId(), filialEncontrada.getId(), "IDs não correspondem");
    }

    @Test
    public void testExcluirFilial() {
        filialService.excluirFilial(listFiliais.get(0));
        Filial filialExcluida = filialService.buscarPorId(listFiliais.get(0).getId());
        assertNull(filialExcluida, "Falha ao excluir filial");
    }

    @Test
    public void testBuscarTodasFiliais() {
        assertFalse(listFiliais.isEmpty(), "Lista de filiais vazia");
        assertEquals(5, listFiliais.size(), "Número incorreto de filiais na lista");
    }

    @Test
    public void testAtualizarFilial() {

        List<Filial> filiais = listFiliais;

        Filial filialParaAtualizar = filiais.get(0);

        filialParaAtualizar.setNomeFilial("Hotel Estrela");
        filialParaAtualizar.setCidade("Sao Paulo");
        filialService.atualizarFilial(filialParaAtualizar);

        Filial filialAtualizada = filialService.buscarPorId(filialParaAtualizar.getId());
        assertNotNull(filialAtualizada, "Falha ao buscar filial atualizada por ID");
        assertEquals("Hotel Estrela", filialAtualizada.getNomeFilial(), "Nome da filial não foi atualizado corretamente");
        assertEquals("Sao Paulo", filialAtualizada.getCidade(), "Cidade da filial não foi atualizada corretamente");
    }
}