package demo;

import dao.CarDao;
import dao.ClientDao;
import dao.RentDao;
import entity.Rent;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;




public class DaoRunner {
    public static void main(String[] args) {
        List<Rent> rents = RentDao.getInstanceRentDao().findAll();
        System.out.println(rents);
        DaoRunner.updateTest();
        DaoRunner.saveTest();
        DaoRunner.deleteTest(7);
    }

        private static void updateTest() {
            RentDao rentDao = RentDao.getInstanceRentDao();
            Optional<Rent> maybeRent = rentDao.findById(3);
            System.out.println(maybeRent);
            maybeRent.ifPresent(rent -> {
                rent.setRentalPrice(Double.valueOf(1500));
                rentDao.update(rent);
            });
        }

        private static void deleteTest(Integer id) {
            RentDao rentDao = RentDao.getInstanceRentDao();
            Boolean deleteResult = rentDao.delete(id);
            System.out.println(deleteResult);
        }

        private static void saveTest() {
            RentDao rentDao = RentDao.getInstanceRentDao();
            Rent rent = new Rent();
            rent.setDateStart(LocalDate.of(2022, 10, 20));
            rent.setDuration(5);
            rent.setClient(ClientDao.getInstanceClientDao().findById(4).get());
            rent.setCar(CarDao.getInstanceCarDao().findById(10).get());
            rent.setRentalPrice(1150.0);
            rent.setAdministratorId(3);
            Rent savedRent = rentDao.save(rent);
            System.out.println(savedRent);
        }
    }


