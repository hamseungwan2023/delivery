package kr.sparta.deliveryapi.service;

import kr.sparta.deliveryapi.model.Delivery;
import kr.sparta.deliveryapi.model.Parcel;
import kr.sparta.deliveryapi.model.enumtype.DeliveryStatus;
import kr.sparta.deliveryapi.model.enumtype.ItemType;
import kr.sparta.deliveryapi.repository.DeliveryRepository;
import kr.sparta.deliveryapi.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelDeliveryService extends AbstractDeliveryService {
    private final ParcelRepository parcelRepository;

    public ParcelDeliveryService(DeliveryRepository deliveryRepository, ParcelRepository parcelRepository) {
        super(deliveryRepository);
        this.parcelRepository = parcelRepository;
    }

    @Override
    public Delivery delivery (Long id){
        final Parcel parcel = findItemById(id, Parcel.class);
        final String trackingNo = generateTrackingNo(parcel.getDescription());
        final Delivery delivery = Delivery.builder()
                .trackingNumber(trackingNo)
                .itemType(ItemType.PARCEL)
                .status(DeliveryStatus.SHIPPED)
                .itemId(parcel.getId())
                .name(parcel.getDescription())
                .build();

        getDeliveryRepository().save(delivery);
        return delivery;
    }

    @Override
    public List<Parcel> getAllItem() {
        return parcelRepository.findAll();
    }

    @Override
    public <Parcel> Parcel findItemById(Long id, Class<Parcel> itemType) {
        return itemType.cast(parcelRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당하는 택배가 없습니다.")));
    }
}
