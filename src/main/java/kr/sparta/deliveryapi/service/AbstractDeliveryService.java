package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.repository.DeliveryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class AbstractDeliveryService implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public AbstractDeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public abstract Delivery delivery(Long id);

    @Override
    public DeliveryStatus trackDelivery(String trackingNumber) {
        return getDeliveryRepository().findById(trackingNumber)
                .map(Delivery::getStatus)
                .orElseThrow(IllegalReceiveException::new);
    }

    @Override
    public abstract List<?> getAllItem();

    protected String generateTrackingNo(String description) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                + String.valueOf(description.hashCode()).substring(0, 4);
    }

    @Override
     public <T> T findItemById(Long id, Class<T> itemType) {
        return null;
    }

    protected DeliveryRepository getDeliveryRepository() {
        return deliveryRepository;
    }
}
