package com.ssv.models;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
@NamedQueries({
        @NamedQuery(name = "Client.selectById", query = "select cl from Client cl where cl.id = :id"),
        @NamedQuery(name = "Client.selectAll", query = "select cl from Client cl"),
        @NamedQuery(name = "Client.deleteClientByName", query = "delete Client cl where cl.name = :name"),
        @NamedQuery(name = "Client.updateClientByName", query = "update Client cl SET cl.name = :name, cl.phoneNumber = :phonenumber, cl.address = :address WHERE name = :nameEq")
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "phoneNumber")
    String phoneNumber;
    @Column(name = "address")
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
