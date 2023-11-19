package com.ssv.models;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Tour.class)
public class Tour_ {
    public static volatile SingularAttribute<Tour, Integer> id;
    public static volatile SingularAttribute<Tour, String> name;
    public static volatile SingularAttribute<Tour, String> type;
    public static volatile SingularAttribute<Tour, Double> price;
    public static volatile SingularAttribute<Tour, Boolean> isLastMinute;
    public static volatile SingularAttribute<Tour, Double> discountForRegularCustomers;
    public static volatile SingularAttribute<Tour, Client> client;
    public static volatile SingularAttribute<Tour, TourAgent> tourAgent;
}
