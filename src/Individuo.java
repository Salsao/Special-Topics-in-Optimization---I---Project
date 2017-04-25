import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Individuo implements Comparator<Individuo>{
	
	private ArrayList<Vehicle> individuos;
	private float fitness;
	
	public Individuo(){
		this.individuos = new ArrayList<Vehicle>();
		this.fitness = 0;
	}

	public ArrayList<Vehicle> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(ArrayList<Vehicle> individuos) {
		this.individuos = individuos;
	}

	public float getFitness() {
		return fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	
	public Individuo clonar (Individuo i){
		Individuo clone = new Individuo();
		for (int j = 0; j < i.getIndividuos().size(); j++) {
			//ArrayList<Vehicle> clonados = new ArrayList<Vehicle>();
			Vehicle cloneV = new Vehicle(i.getIndividuos().get(j).getId(), i.getIndividuos().get(j).getCapacity(), i.getIndividuos().get(j).getCost());
			cloneV.setRota(cloneV.clonarRota(i.getIndividuos().get(j).getRota()));
			//clonados.add(cloneV);
			clone.getIndividuos().add(cloneV);
			//clone.getIndividuos().add(i.getIndividuos().get(j));
		}
		return clone;
	}

	@Override
	public int compare(Individuo o1, Individuo o2) {
		// TODO Auto-generated method stub
		int returnVal;
		if(o1.getFitness() < o2.getFitness())
	        returnVal =  -1;
		else
	        returnVal =  1;
		return returnVal;
	}
	


}
