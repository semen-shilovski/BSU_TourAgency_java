package com.ssv.models;

public class Client {
    Integer id;
    String name;
    String phoneNumber;
    String address;

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Integer getId() {
        return id;
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public static class ClientBuilder {
        Integer id;
        String name;
        String phoneNumber;
        String address;

        public ClientBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ClientBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ClientBuilder address(String address) {
            this.address = address;
            return this;
        }

        public Client build() {
            Client client = new Client();
            client.id = this.id;
            client.name = this.name;
            client.phoneNumber = this.phoneNumber;
            client.address = this.address;
            return client;
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
