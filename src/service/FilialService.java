package service;

import dao.IDao;
import dao.impl.FilialDao;
import model.Filial;
import org.apache.log4j.Logger;

import java.util.List;

public class FilialService {
    private final IDao<Filial> daoFilial;

    private static final Logger logger = Logger.getLogger(FilialDao.class);

    public FilialService(IDao<Filial> daoFilial) {
        this.daoFilial = daoFilial;
    }

    public Filial criarFilial(Filial filial) {
        try {
            return daoFilial.criar(filial);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar filial: " + e.getMessage());
            return null;
        }
    }

    public Filial buscarPorId(int id) {
        return daoFilial.buscarPorId(id);
    }

    public List<Filial> buscarTodos() {
        return daoFilial.buscarTodos();
    }

    public Filial atualizarFilial(Filial filial) {
        return daoFilial.atualizar(filial);
    }

    public void excluirFilial(Filial filial) {
        daoFilial.excluir(filial.getId());
    }

}