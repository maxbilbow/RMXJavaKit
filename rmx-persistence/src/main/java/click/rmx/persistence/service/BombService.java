package click.rmx.persistence.service;

import click.rmx.debug.RMXException;
import click.rmx.persistence.model.Bomb;

import java.util.List;

public interface BombService extends EntityService<Bomb> {
	

	
	
	RMXException addError(RMXException e);
	
	
	List<Bomb> defuse();

}
