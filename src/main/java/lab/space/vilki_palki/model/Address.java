package lab.space.vilki_palki.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lab.space.vilki_palki.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "addresses")
@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends MappedEntity {

    @Column(length = 100)
    private String street;
    @Column(length = 10)
    private String numberHouse;
    @Column(length = 10)
    private String apartment;
    @Column(length = 20)
    private String frontDoor;
    @Column(length = 20)
    private String doorCode;
    private Integer floor;
    @Column(length = 500)
    private String notes;

}
