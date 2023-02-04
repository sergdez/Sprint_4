package model;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class OrderModel {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String deliveryDate;
    private final String rentPeriod;
    private final String commentForCourier;
    private final String color;
    private final String orderMonth;
}