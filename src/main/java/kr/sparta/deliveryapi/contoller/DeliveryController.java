package kr.sparta.deliveryapi.contoller;

import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.Food;
import kr.sparta.deliveryapi.model.Parcel;
import kr.sparta.deliveryapi.service.FoodDeliveryService;
import kr.sparta.deliveryapi.service.ParcelDeliveryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeliveryController {
    private final FoodDeliveryService foodDeliveryService;
    private final ParcelDeliveryService parcelDeliveryService;

    public DeliveryController(FoodDeliveryService foodDeliveryService, ParcelDeliveryService parcelDeliveryService) {
        this.foodDeliveryService = foodDeliveryService;
        this.parcelDeliveryService = parcelDeliveryService;
    }

    @PostMapping("/food/{foodId}")
    public String deliverFood(@PathVariable Long foodId) {
        Delivery delivery = foodDeliveryService.delivery(foodId);
        return delivery.getName() + " 배달이 시작되었습니다. 추적 번호: " + delivery.getTrackingNumber();
    }

    @GetMapping("/food/{trackingNumber}")
    public DeliveryStatus trackFood(@PathVariable String trackingNumber) {
        return foodDeliveryService.trackDelivery(trackingNumber);
    }

    @PostMapping("/parcel/{parcelId}")
    public String deliverParcel(@PathVariable Long parcelId) {
        Delivery delivery = parcelDeliveryService.delivery(parcelId);
        return "택배 배달이 시작되었습니다. 물품명: " + delivery.getName() + ", 추적 번호: " + delivery.getTrackingNumber();
    }

    @GetMapping("/parcel/{trackingNumber}")
    public DeliveryStatus trackParcel(@PathVariable String trackingNumber) {
        return parcelDeliveryService.trackDelivery(trackingNumber);
    }

    @GetMapping("/foods")
    public List<Food> getAllFoods() {
        return foodDeliveryService.getAllItem();
    }

    @GetMapping("/parcels")
    public List<Parcel> getAllParcels() {
        return parcelDeliveryService.getAllItem();
    }
}

