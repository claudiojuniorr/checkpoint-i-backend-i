package dao.impl;

import dao.IDao;
import dao.H2Database;
import model.Filial;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilialDao implements IDao<Filial> {
    private static final Logger logger = Logger.getLogger(FilialDao.class);
    private static final String SQL_INSERIR = "INSERT INTO filial (id, nomeFilial, rua, numero, cidade, estado, eCincoEstrelas) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_EXCLUIR = "DELETE FROM filial WHERE id = ?";
    private static final String SQL_ATUALIZAR = "UPDATE FILIAL SET nomeFilial = ?, rua = ?, numero = ?, cidade = ?, estado = ?, eCincoEstrelas = ? WHERE id = ?";
    private static final String SQL_BUSCAR_POR_ID = "SELECT * FROM filial WHERE id = ?";
    private static final String SQL_BUSCAR_TODOS = "SELECT * FROM filial";

    @Override
    public Filial criar(Filial entidade) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERIR)) {
            statement.setString(1, String.valueOf(entidade.getId()));
            statement.setString(2, entidade.getNomeFilial());
            statement.setString(3, entidade.getRua());
            statement.setInt(4, entidade.getNumero());
            statement.setString(5, entidade.getCidade());
            statement.setString(6, entidade.getEstado());
            statement.setBoolean(7, entidade.getECincoEstrelas());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                logger.info("------------------------------------------------");
                logger.error("A inserção falhou, nenhuma linha afetada.");
                throw new RuntimeException("A inserção falhou, nenhuma linha afetada.");
            }
            logger.info("------------------------------------------------");
            logger.info("Filial criada: " + entidade);
            return entidade;
        } catch (SQLException e) {
            logger.info("------------------------------------------------");
            logger.error("Erro ao criar filial", e);
            throw new RuntimeException("Erro ao criar filial no banco de dados", e);
        }
    }

    @Override
    public Filial atualizar(Filial filial) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ATUALIZAR)) {
            statement.setString(1, filial.getNomeFilial());
            statement.setString(2, filial.getRua());
            statement.setInt(3, filial.getNumero());
            statement.setString(4, filial.getCidade());
            statement.setString(5, filial.getEstado());
            statement.setBoolean(6, filial.getECincoEstrelas());
            statement.setInt(7, filial.getId());

            statement.executeUpdate();
            logger.info("------------------------------------------------");
            logger.info("Filial atualizada: " + filial);
            return filial;
        } catch (SQLException e) {
            logger.info("------------------------------------------------");
            logger.error("Erro ao atualizar filial", e);
            return null;
        }
    }

    @Override
    public void excluir(int id) {
        Filial filialExcluida = buscarPorId(id);

        if (filialExcluida == null) {
            logger.info("------------------------------------------------");
            logger.warn("Nenhuma filial foi excluída para o ID: " + id);
            return;
        }

        try (Connection connection = H2Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EXCLUIR)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("------------------------------------------------");
                logger.info("Filial excluída: " + filialExcluida.getNomeFilial());
            } else {
                logger.info("------------------------------------------------");
                logger.warn("Nenhuma filial foi excluída para o ID: " + id);
            }
        } catch (SQLException e) {
            logger.info("------------------------------------------------");
            logger.error("Erro ao excluir filial", e);
        }
    }

    @Override
    public Filial buscarPorId(Integer id) {
        try (Connection connection = H2Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_BUSCAR_POR_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int filialId = resultSet.getInt("id");
                String nomeFilial = resultSet.getString("nomeFilial");
                String rua = resultSet.getString("rua");
                int numero = resultSet.getInt("numero");
                String cidade = resultSet.getString("cidade");
                String estado = resultSet.getString("estado");
                boolean eCincoEstrelas = resultSet.getBoolean("eCincoEstrelas");

                Filial filial = new Filial(filialId, nomeFilial, rua, numero, cidade, estado, eCincoEstrelas);
                logger.info("------------------------------------------------");
                logger.info("Filial encontrada por ID: " + filial);
                return filial;
            }
            return null;
        } catch (SQLException e) {
            logger.info("------------------------------------------------");
            logger.error("Erro ao buscar filial por ID", e);
            return null;
        }
    }

    @Override
    public List<Filial> buscarTodos() {
        List<Filial> filiais = new ArrayList<>();
        try (Connection connection = H2Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_BUSCAR_TODOS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int filialId = resultSet.getInt("id");
                String nomeFilial = resultSet.getString("nomeFilial");
                String rua = resultSet.getString("rua");
                int numero = resultSet.getInt("numero");
                String cidade = resultSet.getString("cidade");
                String estado = resultSet.getString("estado");
                boolean eCincoEstrelas = resultSet.getBoolean("eCincoEstrelas");

                Filial filial = new Filial(filialId, nomeFilial, rua, numero, cidade, estado, eCincoEstrelas);
                filiais.add(filial);
            }
            logger.info("------------------------------------------------");
            logger.info("Lista de filiais encontrada: " + filiais);
        } catch (SQLException e) {
            logger.info("------------------------------------------------");
            logger.error("Erro ao buscar todas as filiais", e);
        }
        return filiais;
    }
}