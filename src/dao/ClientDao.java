package dao;


import entity.Client;
import exception.DaoException;
import util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDao implements Dao<Integer, Client>{

    private static final ClientDao INSTANCE_CLIENT_DAO = new ClientDao();

    private static final String DELETE = """
            DELETE FROM client
            WHERE id = ?
            """;


    private static final String SAVE = """
            INSERT INTO client (first_name, last_name, passport, phone_number, driving_experience)
            VALUES (?, ?, ?, ?, ?)
            """;



    private static final String UPDATE = """
            UPDATE client
            SET first_name = ?,
                last_name = ?,
                passport = ?,
                phone_number = ?,
                driving_experience = ?
            WHERE id = ?
            """;


    private static final String FIND_ALL = """
            SELECT id,
                first_name,
                last_name,
                passport,
                phone_number,
                driving_experience
            FROM client
            """;

    private static final String FIND_BY_ID = FIND_ALL + """
            WHERE id = ?
            """;


    @Override
    public boolean delete(Integer id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Client save(Client client) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
             preparedStatement.setString(1, client.getFirstName());
             preparedStatement.setString(2, client.getLastName());
             preparedStatement.setString(3, client.getPassport());
             preparedStatement.setString(4, client.getPhoneNumber());
             preparedStatement.setInt(5, client.getDrivingExperience());
             preparedStatement.executeUpdate();
             ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
             if (generatedKeys.next()){
                 client.setId(generatedKeys.getInt("id"));
             }
             return client;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }


    @Override
    public void update(Client client) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
             preparedStatement.setString(1, client.getFirstName());
             preparedStatement.setString(2, client.getLastName());
             preparedStatement.setString(3, client.getPassport());
             preparedStatement.setString(4, client.getPhoneNumber());
             preparedStatement.setInt(5, client.getDrivingExperience());
             preparedStatement.setInt(6, client.getId());
             preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<Client> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
             preparedStatement.setInt(1, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             Client client = null;
             if (resultSet.next()) {
                 client = buildClient(resultSet);
             }
             return Optional.ofNullable(client);
        } catch (SQLException throwables) {
             throw new DaoException(throwables);
        }
    }

    @Override
    public List<Client> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
             ResultSet resultSet = preparedStatement.executeQuery();
             List<Client> clients = new ArrayList<>();
             while (resultSet.next()) {
                 clients.add(buildClient(resultSet));
            }
             return clients;
        } catch (SQLException throwables) {
             throw new DaoException(throwables);
        }
    }


    private Client buildClient(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("passport"),
                resultSet.getString("phone_number"),
                resultSet.getInt("driving_experience")
        );
    }

    public static ClientDao getInstanceClientDao() {
        return INSTANCE_CLIENT_DAO;
    }
}
