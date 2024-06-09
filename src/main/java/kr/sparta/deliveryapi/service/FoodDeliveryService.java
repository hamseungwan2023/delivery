package kr.sparta.deliveryapi.service;

import com.sun.nio.sctp.IllegalReceiveException;
import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.Food;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FoodDeliveryService extends AbstractDeliveryService {
    private final FoodRepository foodRepository;

    public FoodDeliveryService(FoodRepository foodRepository, DeliveryRepository deliveryRepository) {
        super(deliveryRepository);
        this.foodRepository = foodRepository;
    }

    @Override
    public Delivery delivery(Long foodId) {
        final Food food = foodRepository.findById(foodId)
                .orElseThrow(IllegalArgumentException::new);

        final String trackingNo = generateTrackingNo(food.getName());
        final Delivery delivery = Delivery.builder()
                .trackingNumber(trackingNo)
                .itemType(ItemType.FOOD)
                .status(DeliveryStatus.SHIPPED)
                .itemId(food.getId())
                .name(food.getName())
                .build();

        getDeliveryRepository().save(delivery);

        return delivery;
    }

    @Override
    public List<Food> getAllItem() {
        return foodRepository.findAll();
    }

    @Override
    public <Food> Food findItemById(Long foodId, Class<Food> itemType) {
        return itemType.cast(foodRepository.findById(foodId)
                .orElseThrow(()->new IllegalArgumentException("해당하는 음식이 없습니다.")));
    }
}
