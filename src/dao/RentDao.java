package dao;


import entity.Car;
import entity.Client;
import entity.Rent;
import exception.DaoException;
import util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentDao implements Dao<Integer, Rent>{


    private static final RentDao INSTANCE_RENT_DAO = new RentDao();


    private static final String DELETE = """
            DELETE FROM rent
            WHERE id = ?
            """;


    private static final String SAVE = """
            INSERT INTO rent (date_start, duration, client_id, car_id, rental_price, administrator_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;



    private static final String UPDATE = """
            UPDATE rent
            SET date_start = ?,
                duration = ?,
                client_id = ?,
                car_id = ?,
                rental_price = ?,
                administrator_id = ?
            WHERE id = ?    
            """;


    private static final String FIND_ALL = """
            SELECT rent.id,
                date_start,
                duration,
                client_id,
                car_id,
                rental_price,
                administrator_id,
                brand_name,
                model_id,
                body_type_id,
                color,
                year_issue,
                status_id,
                first_name,
                last_name,
                passport,
                phone_number,
                driving_experience                
            FROM rent
            JOIN car c
            ON rent.car_id = c.id
            JOIN client c2
            ON rent.client_id = c2.id
            """;


    private static final String FIND_BY_ID = FIND_ALL + """
            WHERE rent.id = ?
            """;

    private RentDao() {
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
             preparedStatement.setInt(1, id);
             return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Rent save(Rent rent) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
             preparedStatement.setDate(1, Date.valueOf(rent.getDateStart()));
             preparedStatement.setInt(2, rent.getDuration());
             preparedStatement.setInt(3, rent.getClient().getId());
             preparedStatement.setInt(4, rent.getCar().getId());
             preparedStatement.setDouble(5, rent.getRentalPrice());
             preparedStatement.setInt(6, rent.getAdministratorId());
             preparedStatement.executeUpdate();
             ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
             if (generatedKeys.next()) {
                 rent.setId(generatedKeys.getInt("id"));
             }
             return rent;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(Rent rent) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setDate(1, Date.valueOf(rent.getDateStart()));
            preparedStatement.setInt(2, rent.getDuration());
            preparedStatement.setInt(3, rent.getClient().getId());
            preparedStatement.setInt(4, rent.getCar().getId());
            preparedStatement.setDouble(5, rent.getRentalPrice());
            preparedStatement.setInt(6, rent.getAdministratorId());
            preparedStatement.setInt(7, rent.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }



    @Override
    public Optional<Rent> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
             preparedStatement.setInt(1, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             Rent rent = null;
             if (resultSet.next()) {
                rent = buildRent(resultSet);
            }
            return Optional.ofNullable(rent);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<Rent> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Rent> rents = new ArrayList<>();
            while (resultSet.next()) {
                rents.add(buildRent(resultSet));
            }
            return rents;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }


    private Rent buildRent(ResultSet resultSet) throws SQLException {
        Car car = new Car(
                resultSet.getInt("car_id"),
                resultSet.getString("brand_name"),
                resultSet.getInt("model_id"),
                resultSet.getInt("body_type_id"),
                resultSet.getString("color"),
                resultSet.getInt("year_issue"),
                resultSet.getInt("status_id")
        );
        Client client = new Client(
                resultSet.getInt("client_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("passport"),
                resultSet.getString("phone_number"),
                resultSet.getInt("driving_experience")
        );
        return new Rent(
                resultSet.getInt("id"),
                resultSet.getDate("date_start").toLocalDate(),
                resultSet.getInt("duration"),
                client,
                car,
                resultSet.getDouble("rental_price"),
                resultSet.getInt("administrator_id")
        );
    }

    public static RentDao getInstanceRentDao() {
        return INSTANCE_RENT_DAO;
    }

}

