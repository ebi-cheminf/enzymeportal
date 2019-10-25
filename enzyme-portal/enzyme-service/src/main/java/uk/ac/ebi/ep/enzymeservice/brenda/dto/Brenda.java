package uk.ac.ebi.ep.enzymeservice.brenda.dto;

import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author joseph
 */
@Data
@Builder
@ToString
public class Brenda {

    private String ecNumber;
    private String organism;
    private String kcatKmValue;
    private String kmValue;
    private String substrate;

    private String comment;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.organism);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Brenda other = (Brenda) obj;
        return Objects.equals(this.organism, other.organism);
    }
    
}
