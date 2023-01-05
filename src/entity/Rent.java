package entity;


import java.time.LocalDate;


public class Rent{

        private int id;
        private LocalDate dateStart;
        private int duration;
        private Client client;
        private Car car;
        private double rentalPrice;
        private int administratorId;


        public Rent(int id, LocalDate dateStart, int duration, Client client, Car car, double rentalPrice, int administratorId) {
                this.id = id;
                this.dateStart = dateStart;
                this.duration = duration;
                this.client = client;
                this.car = car;
                this.rentalPrice = rentalPrice;
                this.administratorId = administratorId;
        }

        public Rent() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public LocalDate getDateStart() {
                return dateStart;
        }

        public void setDateStart(LocalDate dateStart) {
                this.dateStart = dateStart;
        }

        public int getDuration() {
                return duration;
        }

        public void setDuration(int duration) {
                this.duration = duration;
        }

        public Client  getClient() {
                return client;
        }

        public void setClient(Client client) {
                this.client = client;
        }

        public Car getCar() {
                return car;
        }

        public void setCar(Car car) {
                this.car = car;
        }

        public double getRentalPrice() {
                return rentalPrice;
        }

        public void setRentalPrice(double rentalPrice) {
                this.rentalPrice = rentalPrice;
        }

        public int getAdministratorId() {
                return administratorId;
        }

        public void setAdministratorId(int administratorId) {
                this.administratorId = administratorId;
        }

        @Override
        public String toString() {
                return "Rent{" +
                       "id=" + id +
                       ", dateStart=" + dateStart +
                       ", duration=" + duration +
                       ", " + client +
                       "," + "\n" + car +
                       ", rentalPrice=" + rentalPrice +
                       ", administratorId=" + administratorId +
                       '}' + "\n\n";
        }
}
