package click.rmx.persistence.service;


import click.rmx.persistence.model.Bomb;
import click.rmx.persistence.repository.BombRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("bombService")
public class BombServiceImpl extends AbstractEntityService<Bomb> implements BombService {

	@Autowired
	private BombRepository bombRepository;
	
	@Override
	protected JpaRepository<Bomb, Long> repository() {
		return bombRepository;
	}
	
	@Override
	@Transactional
	public List<Bomb> defuse() {
		for (Bomb bomb : getEntities())
			bomb.setLive(false);
		return this.synchronize();
	}


	

	

}
