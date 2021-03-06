package core.entities;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.World;

/**
 * Classe che fornisce metodi di utilita' per gestire gli attacchi
 * 
 * @author Willi Menapace
 *
 */
final class AttackHelper {
	
	//Evita che venga istanziata
	private AttackHelper() {};
	
	static enum SearchMode {
		ANY, FRIEND, ENEMY;
	}
	
	/**
	 * Determina se l'unita' attaccata e' nel range dell'unita' attaccante oppure se hanno colliso
	 * 
	 * @param searcher L'unita' attaccante
	 * @param searched L'unita' attaccata
	 * @return true se l'attaccante ha colliso con l'unita' attaccata o se e' in range
	 */
	static boolean isInRange(RangedEntity searcher, Entity searched) {
		
		Rectangle2D attackerPosition = searcher.getBox();
		Rectangle2D attackedPosition = searched.getBox();
		
		if(entitiesCollided(attackerPosition, attackedPosition)) {
			return true;
		}
		
		double xDifference = Math.abs(attackerPosition.getCenterX() - attackedPosition.getCenterX());
		double yDifference = Math.abs(attackerPosition.getCenterY() - attackedPosition.getCenterY());
		
		if(xDifference + yDifference <= searcher.getRange()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Cerca l'insieme delle entita' danneggiabili che si trovano nel range di un'altra entita'
	 * 
	 * @param searcher L'entita' di cui si vogliono trovare le altre entita' in range
	 * @param world Il contesto in cui cercare le entita'
	 * @param mode Il tipo di unita' da ricercare (amiche, nemiche, ecc)
	 * @return L'insieme delle entita' che si trovano in range di searcher
	 */
	static List<DamageableEntity> getInRangeEntities(RangedEntity searcher, World world, SearchMode mode) {
		List<DamageableEntity> inRangeEntities = new ArrayList<>();
		Iterator<Entity> iterator = world.getEntityIterator();
		while(iterator.hasNext()) {
			Entity currentEntity = iterator.next();
			if(currentEntity instanceof DamageableEntity) {
				DamageableEntity damageableEntity = (DamageableEntity) currentEntity;
				switch(mode) {
				case ANY:
					if(isInRange(searcher, damageableEntity)) {
						inRangeEntities.add(damageableEntity);
					}
					break;
				case FRIEND:
					if(searcher.getTeam() == damageableEntity.getTeam() && isInRange(searcher, damageableEntity)) {
						inRangeEntities.add(damageableEntity);
					}
					break;
				case ENEMY:
					if(searcher.getTeam() != damageableEntity.getTeam() && isInRange(searcher, damageableEntity)) {
						inRangeEntities.add(damageableEntity);
					}
					break;
				default:
					throw new UnsupportedOperationException("Unknown SearchMode");
				}
			}
			
		}
		return inRangeEntities;
	}
	
	/**
	 * Determina se due unita' hanno effettuato una collisione
	 * 
	 * @param firstPosition La posizione della prima entita' coinvolta
	 * @param secondPosition La posizione della seconda entita' coinvolta
	 * @return true se le unita' hanno colliso
	 */
	static private boolean entitiesCollided(Rectangle2D firstPosition, Rectangle2D secondPosition) {
		return firstPosition.intersects(secondPosition);
	}
	
	/**
	 * Calcola il danno normale che viene effettuato da un attaccante a un difensore
	 * Non si tiene condo di capacita' di evitare attacchi oppure di attacchi critici
	 * 
	 * @param attacker L'unita' attaccante
	 * @param attacked L'unita' attaccata
	 * @return Il danno effettuato in condizioni normali dall'unita' attaccante all'attaccata
	 */
	static int calculateNormalDamageDealt(AttackerEntity attacker, DamageableEntity attacked) {
		//La difesa e' incrementata di 1 per evitare che le entita' con difesa 0 subiscano danno infinito
		return (int) (attacker.getAttack() / (attacked.getDefense() + 1));
	}
}