package click.rmx.debug.logger.repository;

import click.rmx.debug.logger.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Max on 25/10/2015.
 */
@Repository
public interface LogRepository extends JpaRepository<Log,Long> {
//
    @Query("Select l from Log l where l.channel = 'debug.log'")
    List<Log> getMessages();

    @Query("Select l from Log l where l.channel = 'debug.error'")
    List<Log> getErrors();

    @Query("Select l from Log l where l.channel = 'debug.warning'")
    List<Log> getWarnings();
}
