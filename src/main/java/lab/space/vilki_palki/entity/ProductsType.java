package lab.space.vilki_palki.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lab.space.vilki_palki.entity.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "products_type")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductsType  extends MappedEntity {
    @Column(length = 100)
    private String name;
}