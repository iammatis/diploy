package sk.vilk.diploy.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class SimpleEntity {

    @Id
    private UUID id;

    private String attribute1;
    private Integer attribute2;

    // Needed
    public SimpleEntity() {
    }

    public SimpleEntity(UUID id, String attribute1, Integer attribute2) {
        this.id = id;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }

    @Override
    public boolean equals(Object obj) {
        return SimpleEntity.class == obj.getClass() &&
                this.id.equals(((SimpleEntity) obj).id) &&
                this.attribute1.equals(((SimpleEntity) obj).attribute1) &&
                this.attribute2.equals(((SimpleEntity) obj).attribute2);
    }
}
