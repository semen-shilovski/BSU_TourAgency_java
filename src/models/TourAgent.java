package models;

public class TourAgent {
    Integer id;
    String name;
    String phoneNumber;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static TourAgentBuilder builder() {
        return new TourAgentBuilder();
    }

    public static class TourAgentBuilder {
        Integer id;
        String name;
        String phoneNumber;

        public TourAgentBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public TourAgentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TourAgentBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public TourAgent build() {
            TourAgent tourAgent = new TourAgent();
            tourAgent.id = this.id;
            tourAgent.name = this.name;
            tourAgent.phoneNumber = this.phoneNumber;
            return tourAgent;
        }
    }

    @Override
    public String toString() {
        return "TourAgent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
