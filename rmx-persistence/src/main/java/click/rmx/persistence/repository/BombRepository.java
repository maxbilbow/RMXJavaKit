package click.rmx.persistence.repository;


import click.rmx.persistence.model.Bomb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bombRepository")
public interface BombRepository extends JpaRepository<Bomb, Long> {

}
