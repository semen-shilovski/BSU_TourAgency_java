package com.ssv.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tours")
@NamedQueries({
        @NamedQuery(name = "Tour.selectById", query = "select tr from Tour tr where tr.id = :id"),
        @NamedQuery(name = "Tour.selectAll", query = "select tr from Tour tr"),
        @NamedQuery(name = "Tour.deleteTourByName", query = "delete Tour tr where tr.name = :name"),
        @NamedQuery(name = "Tour.updateTourByName", query = "update Tour tr SET tr.name = :name, tr.type = :type, tr.price = :price, tr.isLastMinute = :isLastMinute, tr.discountForRegularCustomers = :discount, tr.client.id = :clientId, tr.tourAgent.id = :tourAgentId WHERE name = :nameEq")
})
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "price")
    private double price;
    @Column(name = "is_last_minute")
    private boolean isLastMinute;
    @Column(name = "discount")
    private double discountForRegularCustomers;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne
    @JoinColumn(name = "tour_agent_id")
    private TourAgent tourAgent;

    public Tour(String name, String type, double price, boolean isLastMinute, double discountForRegularCustomers) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.isLastMinute = isLastMinute;
        this.discountForRegularCustomers = discountForRegularCustomers;
    }

    public Tour() {

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public Client getClient() {
        return client;
    }

    public TourAgent getTourAgent() {
        return tourAgent;
    }

    public boolean isLastMinute() {
        return isLastMinute;
    }

    public double getDiscountForRegularCustomers() {
        return discountForRegularCustomers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLastMinute(boolean lastMinute) {
        isLastMinute = lastMinute;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTourAgent(TourAgent tourAgent) {
        this.tourAgent = tourAgent;
    }

    public void setDiscountForRegularCustomers(double discountForRegularCustomers) {
        this.discountForRegularCustomers = discountForRegularCustomers;
    }

    public static TourBuilder builder() {
        return new TourBuilder();
    }

    public static class TourBuilder {
        private String name;
        private String type;
        private double price;
        private boolean isLastMinute;
        private double discountForRegularCustomers;
        private Client client;
        private TourAgent tourAgent;

        public TourBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TourBuilder type(String type) {
            this.type = type;
            return this;
        }

        public TourBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public TourBuilder tourAgent(TourAgent tourAgent) {
            this.tourAgent = tourAgent;
            return this;
        }

        public TourBuilder price(double price) {
            this.price = price;
            return this;
        }

        public TourBuilder lastMinute(boolean isLastMinute) {
            this.isLastMinute = isLastMinute;
            return this;
        }

        public TourBuilder discountForRegularCustomers(double discountForRegularCustomers) {
            this.discountForRegularCustomers = discountForRegularCustomers;
            return this;
        }

        public Tour build() {
            Tour tour = new Tour();
            tour.name = this.name;
            tour.type = this.type;
            tour.price = this.price;
            tour.isLastMinute = this.isLastMinute;
            tour.discountForRegularCustomers = this.discountForRegularCustomers;
            tour.client = this.client;
            tour.tourAgent = this.tourAgent;
            return tour;
        }
    }

    @Override
    public String toString() {
        return "Tour{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", isLastMinute=" + isLastMinute +
                ", discountForRegularCustomers=" + discountForRegularCustomers +
                ", client=" + client +
                ", tourAgent=" + tourAgent +
                '}';
    }
}
