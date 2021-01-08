package Player;

public class Player {
	
	private float x;
	private float y;
	private float z;
	private String name;
	private int age;
	private int health;
	
	public Player(String name, float x, float y, float z, int age, int health) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.name = name;
		this.age = age;
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

}
