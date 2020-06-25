package tw.teddysoft.clean.domain.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class ValueObject implements Serializable {

    private static final long serialVersionUID = 1L;

    public ValueObject() {
        super();
    }
}
