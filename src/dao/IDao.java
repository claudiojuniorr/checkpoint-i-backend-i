package dao;

import java.util.List;

public interface IDao<E> {
    E criar(E entidade);

    E atualizar(E entidade);

    void excluir(int id);

    E buscarPorId(Integer id);

    List<E> buscarTodos();
}
