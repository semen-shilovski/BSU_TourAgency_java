package com.ssv.models;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TourAgent.class)
public class TourAgent_ {
    public static volatile SingularAttribute<TourAgent, Integer> id;
    public static volatile SingularAttribute<TourAgent, String> name;
    public static volatile SingularAttribute<TourAgent, String> phoneNumber;
}
