package org.firas.wrap.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Column;

import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "t_box_component")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class BoxComponent extends org.firas.common.model.IdModel {

    @Transient
    private static final long serialVersionUID = 1L;

    @Embeddable
    @Data
    public static class BoxComponentId implements Serializable {

        @Column(name = "box_id")
        private int boxId;

        @Column(name = "component_id")
        private int componentId;
    }

    @Id
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
