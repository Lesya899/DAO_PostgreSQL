package dao;

import entity.Car;
import exception.DaoException;
import util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements Dao<Integer, Car> {

    private static final CarDao INSTANCE_CAR_DAO = new CarDao();


    private static final String DELETE_SQL = """
            DELETE FROM car
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO car (brand_name, model_id, body_type_id, color, year_issue, status_id) 
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE car
            SET brand_name = ?,
                model_id = ?,
                body_type_id = ?,
                color = ?,
                year_issue = ?,
                status_id = ?
            WHERE id = ?    
            """;


    private static final String FIND_ALL_SQL = """
            SELECT id,
                brand_name,
                model_id,
                body_type_id,
                color,
                year_issue,
                status_id
            FROM car
            """;


    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private CarDao() {
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
             preparedStatement.setInt(1, id);
             return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
             throw new DaoException(throwables);
        }
    }

    @Override
    public Car save(Car car) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
             preparedStatement.setString(1, car.getBrandName());
             preparedStatement.setInt(2, car.getModelId());
             preparedStatement.setInt(3, car.getBodyTypeId());
             preparedStatement.setString(4, car.getColor());
             preparedStatement.setInt(5, car.getYearIssue());
             preparedStatement.setInt(6, car.getStatusId());
             preparedStatement.executeUpdate();
             ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
             if (generatedKeys.next()){
                car.setId(generatedKeys.getInt("id"));
             }
             return car;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(Car car) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
             preparedStatement.setString(1, car.getBrandName());
             preparedStatement.setInt(2, car.getModelId());
             preparedStatement.setInt(3, car.getBodyTypeId());
             preparedStatement.setString(4, car.getColor());
             preparedStatement.setInt(5, car.getYearIssue());
             preparedStatement.setInt(6, car.getStatusId());
             preparedStatement.setInt(7, car.getId());
             preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }


    @Override
    public Optional<Car> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
             preparedStatement.setInt(1, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             Car car = null;
             if (resultSet.next()) {
                car = buildCar(resultSet);
             }
             return Optional.ofNullable(car);
        } catch (SQLException throwables) {
             throw new DaoException(throwables);
        }
    }


    @Override
    public List<Car> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
             ResultSet resultSet = preparedStatement.executeQuery();
             List<Car> cars = new ArrayList<>();
             while (resultSet.next()) {
                cars.add(buildCar(resultSet));
             }
             return cars;
        } catch (SQLException throwables) {
             throw new DaoException(throwables);
        }
    }


    private Car buildCar(ResultSet resultSet) throws SQLException {
        return new Car(
                resultSet.getInt("id"),
                resultSet.getString("brand_name"),
                resultSet.getInt("model_id"),
                resultSet.getInt("body_type_id"),
                resultSet.getString("color"),
                resultSet.getInt("year_issue"),
                resultSet.getInt("status_id")
        );
    }

    public static CarDao getInstanceCarDao() {
        return INSTANCE_CAR_DAO;
    }
}
