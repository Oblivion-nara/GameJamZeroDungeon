package game;

public abstract class Entity {

	private int health, maxHealth, attack, defence;
	private String name;

	public Entity(int maxHealth, int attack, int defence) {
		this.maxHealth = this.health = maxHealth;
		this.attack = attack;
		this.defence = defence;
	}
	
	

}
