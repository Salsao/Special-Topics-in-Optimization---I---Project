

public class City {
	
	private int id;
	private float x;
	private float y;
	private float custo;
	
	public City(int id, float custo, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.custo = custo;
	}
	
	public City clonar(City c){
		City cidade = new City(c.id, c.custo, c.x, c.y);
		return cidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getCusto() {
		return custo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}
	
	

}
