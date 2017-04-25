import java.util.ArrayList;


public class Vehicle {
	
	private ArrayList<City> rota;
	private int id;
	private int capacity;
	private int cost;

	public Vehicle(int id, int cap, int cost) {
		this.id = id;
		this.capacity = cap;
		this.cost = cost;
		this.rota = new ArrayList<City>();
	}
	
	public float calcularFitness(ArrayList<City> lista){
		float menorDist = 1000;
		//float fitness = 0;
		int pos = 0;
		//Achar a cidade mais perto da entra/saida
		for (int i = 0; i < lista.size(); i++){
			float dist = (float)Math.sqrt((Math.pow(Principal.getCities().get(0).getX() - lista.get(i).getX(), 2)+ Math.pow(Principal.getCities().get(0).getY() - lista.get(i).getY(),2)));
			if (dist < menorDist){
				menorDist = dist;
				pos = i;
			}
		}
		//System.out.println(menorDist);
		return menorDist + calcularFitness2(lista, pos);
	}
	
	public float calcularFitness2(ArrayList<City> lista, int pos){
		float menorDist = 1000;
		float distOrigem = 0;
		int melhor = 0;
		for (int i = 0; i < lista.size(); i++) {
			if(pos != i){
				float dist2 = (float)Math.sqrt((Math.pow(lista.get(pos).getX() - lista.get(i).getX(), 2)+ Math.pow(lista.get(pos).getY() - lista.get(i).getY(),2)));
				if (dist2 < menorDist){
					menorDist = dist2;
					melhor = i;
				}
			}
		}
		if (pos < melhor)
			melhor -= 1;
		//System.out.println(menorDist);
		//System.out.println("tamanho lista antes de remover: " + lista.size());
		if (lista.size() == 0)
			return 10000;
		else 
			lista.remove(pos);
		//System.out.println("tamanho lista: " + lista.size());
		if (lista.size() > 1){
			//System.out.println(" ENTROU FIT2 tamanho lista: " + lista.size());
			menorDist += calcularFitness2(lista, melhor);
		}
		else if (lista.size() == 1){ //Só tem 1 cidade e depois dela voltar pra origem
			//System.out.println(" CABOU tamanho da lista: " + lista.size());
			distOrigem = (float)Math.sqrt((Math.pow(Principal.getCities().get(0).getX() - lista.get(0).getX(), 2)+ Math.pow(Principal.getCities().get(0).getY() - lista.get(0).getY(),2)));
			//System.out.println(distOrigem);
		}
		return menorDist + distOrigem;
	}
	
	public void imprimirRota(int v){
		System.out.print("[");
		for (int i = 0; i < Principal.getVeiculos().get(v).getRota().size(); i++)
			System.out.print(Principal.getVeiculos().get(v).getRota().get(i).getId() + " ");
		System.out.print("] " + "Peso ocupado: " + Principal.getVeiculos().get(v).getCost() + "\n");
	}
	
	public void imprimirRotaVeiculo(Vehicle v){
		System.out.print("[");
		for (int i = 0; i < v.getRota().size(); i++)
			System.out.print(v.getRota().get(i).getId() + " ");
		System.out.print("] " + "Peso ocupado: " + v.getCost() + "\n");
	}
	
	public ArrayList<City> clonarRota(ArrayList<City> cidades){
		ArrayList<City> clone = new ArrayList<City>(cidades.size());
		for (int i = 0; i < cidades.size(); i++) {
			City cloneCidade = new City(cidades.get(i).getId(), cidades.get(i).getCusto(), cidades.get(i).getX(), cidades.get(i).getY());
			clone.add(cloneCidade);
		}
		return clone;
	}
	
	public void setarCost(float f){
		this.cost += f;
	}
	
	public ArrayList<City> getRota() {
		return rota;
	}

	public void setRota(ArrayList<City> rota) {
		this.rota = rota;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
}
