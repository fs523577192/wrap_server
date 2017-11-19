package org.firas.wrap.entity.embeddable;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxComponentId implements Serializable {

    @Column(name = "box_id")
    private int boxId;

    @Column(name = "component_id")
    private int componentId;
}
