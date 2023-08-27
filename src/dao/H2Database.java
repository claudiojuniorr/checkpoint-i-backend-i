package dao;

import java.sql.*;
import org.apache.log4j.Logger;
public final class H2Database {
    private static final Logger logger = Logger.getLogger(H2Database.class);
    private static String driver = "org.h2.Driver";
    private static String urlBancoDeDados = "jdbc:h2:~/test;INIT=RUNSCRIPT FROM 'create.sql'";
    private static String usuario = "sa";
    private static String senha = "";
    private static Connection connection;

    public H2Database() {
        try {
            Class.forName(driver);
            criarTabelaSeNaoExistir();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar o driver do banco de dados", e);
        }
    }


    private void criarTabelaSeNaoExistir() {
        if (!tabelaExiste("FILIAL")) {
            String SQL_CRIAR = "CREATE TABLE FILIAL (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nomeFilial VARCHAR(255), " +
                    "rua VARCHAR(255), " +
                    "numero INT, " +
                    "cidade VARCHAR(255), " +
                    "estado VARCHAR(255), " +
                    "eCincoEstrelas BOOLEAN)";

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_CRIAR)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Erro ao criar tabela FILIAL", e);
            }
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(urlBancoDeDados, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean tabelaExiste(String nomeTabela) {
        try (Connection connection = getConnection()) {
            ResultSet tables = connection.getMetaData().getTables(null, null, nomeTabela, null);
            return tables.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}