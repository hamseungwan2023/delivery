package kr.sparta.deliveryapi.service;

import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    Delivery delivery(Long id);
    DeliveryStatus trackDelivery(String trackingNumber);
    List<?> getAllItem();
    <T> T findItemById(Long id, Class<T> itemType);
}