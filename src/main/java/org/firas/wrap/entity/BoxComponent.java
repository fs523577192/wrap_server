package org.firas.wrap.entity;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.firas.wrap.entity.embeddable.BoxComponentId;

@Slf4j
@Entity
@Table(name = "t_box_component")
@DynamicUpdate
@NoArgsConstructor
public class BoxComponent extends org.firas.common.model.StatusModel {

    @Transient
    private static final long serialVersionUID = 1L;

    public BoxComponent(int componentId, int number) {
        boxComponentId = new BoxComponentId();
        boxComponentId.setComponentId(componentId);
        this.number = number;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>(2, 1f);
        result.put("number", number);
        result.put("component", component.toMap());
        return result;
    }

    @EmbeddedId
    @Getter @Setter private BoxComponentId boxComponentId;

    @ManyToOne
    @MapsId("boxId")
    @JoinColumn(name = "box_id")
    @Getter @Setter private Box box;

    @ManyToOne
    @MapsId("componentId")
    @JoinColumn(name = "component_id")
    @Getter @Setter private Component component;

    @Column
    @Getter @Setter private int number;
}
