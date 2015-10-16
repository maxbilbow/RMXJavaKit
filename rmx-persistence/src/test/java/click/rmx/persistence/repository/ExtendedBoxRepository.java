package click.rmx.persistence.repository;

import click.rmx.persistence.model.ExtendedBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Max on 12/10/2015.
 */
@Repository
public interface ExtendedBoxRepository extends JpaRepository<ExtendedBox, Long> {
}
