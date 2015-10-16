package click.rmx.persistence.repository;

import click.rmx.persistence.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Max on 09/10/2015.
 */
@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
}
