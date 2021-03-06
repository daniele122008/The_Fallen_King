package core.entities;

/**
 * Entita' che puo' subire danni
 * 
 * @author Willi Menapace
 *
 */
public interface DamageableEntity extends Entity {
    /**
     * Il numero di Hit Points rimanenti. Hp <= 0 implica la distruzione dell'elemento
     * 
     * @return Il numero di Hit Points rimanenti all'elemento
     */
	public int getHp();
	/**
	 * Imposta gli Hit Points correnti
	 * @param hp Il nuovo valore di Hit Points. Deve essere <= getMaxHP();
	 */
	public void setHp(int hp);
	/**
	 * 
	 * @return Il numero di Hit Points massimi consentiti all'elemento
	 */
    public int getMaxHp();
    /**
     * Infligge un danno all'entita'
     * 
     * @param damage Il danno da infliggere all'elemento
     */
    public void inflictDamage(int damage);
    /**
     * 
     * @return true se l'elemento e' da considerarsi morto
     */
    public boolean isDead();
    /**
     * 
     * @return Il numero di punti difesa dell'elemento
     */
    public int getDefense();
    /**
     * 
     * @return Il numero di soldi guadagnati all'uccisione dell'elemento
     */
    public int getBaseMoneyOnKill();
}
