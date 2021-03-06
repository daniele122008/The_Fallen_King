package core.entities;

import core.Team;
import core.World;

abstract class KamikazeAttacker extends RangeFinderAttacker {
	
	KamikazeAttacker(Team team, double xSpawnPosition, double ySpawnPosition, double width, double height) {
		super(team, xSpawnPosition, ySpawnPosition, width, height);
	}
	
	@Override
	boolean attack(World world, DamageableEntity damageableEntity) {

		int damageDealt = AttackHelper.calculateNormalDamageDealt(this, damageableEntity);
		damageableEntity.inflictDamage(damageDealt);
		
		//Si attacca una sola unita', dopo l'attaccante si distrugge
		world.removeDeferredEntity(this);
		return true;
	}
}
