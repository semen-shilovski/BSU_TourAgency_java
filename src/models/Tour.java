package models;

public class Tour {
    private String name;
    private String type;
    private double price;
    private boolean isLastMinute;
    private double discountForRegularCustomers;

    public Tour(String name, String type, double price, boolean isLastMinute, double discountForRegularCustomers) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.isLastMinute = isLastMinute;
        this.discountForRegularCustomers = discountForRegularCustomers;
    }

    private Tour() {
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

        public TourBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TourBuilder type(String type) {
            this.type = type;
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
                '}';
    }
}
