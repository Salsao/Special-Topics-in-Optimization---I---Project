
import java.io.*;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;


public class Principal {
	
	private static ArrayList<Individuo> individuos;
	private static ArrayList<Vehicle> veiculos;
	private static ArrayList<City> cities;
	
	public Principal(){
		Principal.individuos = new ArrayList<Individuo>();
		Principal.veiculos = new ArrayList<Vehicle>();
		Principal.cities = new ArrayList<City>();
	}
	
	public static ArrayList<Vehicle> getVeiculos() {
		return veiculos;
	}

	public static void setVeiculos(ArrayList<Vehicle> veiculos) {
		Principal.veiculos = veiculos;
	}

	public static ArrayList<City> getCities() {
		return cities;
	}

	public static void setCities(ArrayList<City> cities) {
		Principal.cities = cities;
	}

	public static void inserirVeiculos(Vehicle v){
		Principal.veiculos.add(v);
	}
	
	public static void inserirCidades(City c){
		Principal.cities.add(c);
	}
	
	public static void inserirRotaVeiculo(Vehicle v, City c){
		v.getRota().add(c);
		v.setarCost(c.getCusto());
	}
	
	public static void removerRotaVeiculo(Vehicle v, City c){
		v.getRota().remove(c);
		v.setarCost(-c.getCusto());
	}
	
	public static void imprimirVeiculos(int numV){
		for (int i = 0; i < numV; i++) {
			System.out.print(veiculos.get(i).getId() + " ");
			veiculos.get(i).imprimirRota(i);
		}
	}
	
	public static void imprimirCidades(int numC){
		for (int i = 0; i < numC; i++) {
			System.out.println(cities.get(i).getId() + " custo: " + cities.get(i).getCusto() + " coordenadas: " + cities.get(i).getX() + " " + cities.get(i).getY());
		}
	}
	
	public static float individuoFitness(int numVeiculos, int i){
		ArrayList<City> clone;
		float fitnessTotal = 0;
			for (int j = 0; j < numVeiculos; j++) {
				clone = individuos.get(i).getIndividuos().get(j).clonarRota(individuos.get(i).getIndividuos().get(j).getRota());
				float fitness = individuos.get(i).getIndividuos().get(j).calcularFitness(clone);
				fitnessTotal += fitness;
			}
		return fitnessTotal;
	}
	


	public static void main(String[] args) throws IOException {
		
		for (int ba = 0; ba < 5; ba++) {
			
		long lStartTime = System.currentTimeMillis();
		
		FileReader fr = new FileReader("C:/F-n135-k7.vrp");
		BufferedReader textReader = new BufferedReader(fr);
		
		Principal principal = new Principal();
		int i = 0;
		int numCity = 0;
		int numVeiculos = 0;
		int capVeiculo = 0;
		int numIndividuos = 1000;
		int numGeracoes = 1000;
		String[] texto = new String[500];
		while((texto[i] = textReader.readLine()) != null){
			if (i == 0)
				numVeiculos = Integer.parseInt(texto[i].substring(texto[i].lastIndexOf("k") + 1));
			if (i == 3)
				numCity = Integer.parseInt(texto[i].substring(12));
			if (i == 5)
				capVeiculo = Integer.parseInt(texto[i].substring(11)); 
			i++;
		}
		textReader.close();
		
		
		for (int j = 1; j <= numCity; j++) {
			String[] xy = texto[6 + j].split("\\s+");
			//System.out.println(xy[3]);
			float x = Float.parseFloat(xy[2]);
			float y = Float.parseFloat(xy[3]);
			float custo = Float.parseFloat(texto[7 + numCity + j].substring(texto[7 + numCity + j].indexOf(" ") + 1).replaceAll("\\s+",""));
			City cidade = new City(j, custo, x, y);
			inserirCidades(cidade);
		}
		
		//COMEÇAR ALGORITMO
		//System.out.println("TE");
		
		Random gerador = new Random();
		for (int k = 0; k < numIndividuos; k++) {
			//Criando Veículos
			Individuo carinha = new Individuo();
			ArrayList<Vehicle> listaVeiculos = new ArrayList<Vehicle>();
			for (int j = 0; j < numVeiculos; j++) {
				Vehicle veiculo = new Vehicle(j,capVeiculo,0);
				listaVeiculos.add(veiculo);
			}
			//System.out.println("TE2");
			for (int j = 1; j < numCity; j++) {
				int randomico = gerador.nextInt(numVeiculos);
				//for (int g = 0; g < listaVeiculos.size(); g++) {
				//	listaVeiculos.get(g).imprimirRotaVeiculo(listaVeiculos.get(g));
				//}
				//while (listaVeiculos.get(randomico).getCost() + cities.get(j).getCusto() > listaVeiculos.get(randomico).getCapacity()){
					//randomico = gerador.nextInt(numVeiculos);
					//System.out.println("fodeu: " + randomico + " olha: " + (listaVeiculos.get(randomico).getCost() + cities.get(j).getCusto()));
				//}
				inserirRotaVeiculo(listaVeiculos.get(randomico), cities.get(j));
			}
			//System.out.println("TE3");
			carinha.setIndividuos(listaVeiculos);
			individuos.add(carinha);
		}
		//System.out.println("TE4");
		//Calcular Fitness
		//float pior
		for (int z = 0; z < numGeracoes; z++) {
		for (int j = 0; j < numIndividuos; j++) {
			float fitnessCarinha = individuoFitness(numVeiculos, j);
			//System.out.println(fitnessCarinha);
			for (int k = 0; k < individuos.get(j).getIndividuos().size(); k++) {
				if (individuos.get(j).getIndividuos().get(k).getCapacity() > capVeiculo)
					individuos.get(j).setFitness(200000);
			}
			individuos.get(j).setFitness(fitnessCarinha);
		}
		//Pegar os numIndividuos/2 melhores
		Collections.sort(individuos, new Individuo());
		/*
		for (int j = 0; j < numIndividuos; j++) {
			System.out.println(individuos.get(j).getFitness());
		}
		*/
		//Remover os piores
		for (int j = numIndividuos/2; j < numIndividuos; j++) {
			individuos.remove(numIndividuos/2);
		}
		
		//crossover
		for (int j = 0; j < numIndividuos/2; j ++) {
			ArrayList<City> cidades1 = new ArrayList<City>();
			Individuo novo = new Individuo();
			novo = novo.clonar(individuos.get(j));
			
			int tam;
			//Zerar metade das rotas
			for (int k = numVeiculos/2; k < numVeiculos; k++) {
				tam = novo.getIndividuos().get(k).getRota().size();
				for (int k2 = 0; k2 < tam; k2++) {
					City clone = individuos.get(j).getIndividuos().get(k).getRota().get(k2).clonar(individuos.get(j).getIndividuos().get(k).getRota().get(k2));
					cidades1.add(clone);
					removerRotaVeiculo(novo.getIndividuos().get(k), novo.getIndividuos().get(k).getRota().get(0));
				}
			}
			
			//Filho 1
			for (int k = 0; k < cidades1.size(); k++) {
				int randomico;
				int limite = 0;
				int help = 0;
				//boolean flag = false;
				if (numVeiculos % 2 != 0)
					randomico = gerador.nextInt(numVeiculos/2 + 1);	
				else
					randomico = gerador.nextInt(numVeiculos/2);
				randomico += numVeiculos/2;
				while (novo.getIndividuos().get(randomico).getCost() + cidades1.get(k).getCusto() > novo.getIndividuos().get(randomico).getCapacity()){
					if (numVeiculos % 2 != 0)
						randomico = gerador.nextInt(numVeiculos/2 + 1);	
					else
						randomico = gerador.nextInt(numVeiculos/2);
					randomico += numVeiculos/2;
					limite++;
					if (limite > 30){
						//randomico = gerador.nextInt(numVeiculos/2);
						randomico = help;
						if (randomico >= novo.getIndividuos().size()){
							//flag = true;
							randomico--;
							break;
						}
						help++;
					}
				}
				//if (!flag)
				inserirRotaVeiculo(novo.getIndividuos().get(randomico), cidades1.get(k));
			}
			
			/*
			System.out.println("PAI");
			for (int k = 0; k < individuos.get(j).getIndividuos().size(); k++) {
				individuos.get(j).getIndividuos().get(numVeiculos - 1).imprimirRotaVeiculo(individuos.get(j).getIndividuos().get(k));
			}
			
			System.out.println("FILHO TENTA " + j);
			for (int k = 0; k < novo.getIndividuos().size(); k++) {
				novo.getIndividuos().get(k).imprimirRotaVeiculo(novo.getIndividuos().get(k));
			}
			*/
			
			individuos.add(novo);
		}
		
		

		//MUTAÇAO
		for (int j = 0; j < numIndividuos/8; j++) {
			int randIndE;
			int randVeiE;
			int randCidE;
			int randVeiS;
			int limite = 0;
			boolean flag = false;
			randIndE = gerador.nextInt(numIndividuos/2);
			randIndE += numIndividuos/2;
			randVeiE = gerador.nextInt(numVeiculos/2);
			while (individuos.get(randIndE).getIndividuos().get(randVeiE).getRota().size() == 0){
				randVeiE = gerador.nextInt(numVeiculos/2);
			}
			randCidE = gerador.nextInt(individuos.get(randIndE).getIndividuos().get(randVeiE).getRota().size());
			randVeiS = gerador.nextInt(numVeiculos);
			
			while (randVeiS == randVeiE)
				randVeiS = gerador.nextInt(numVeiculos);
			while (individuos.get(randIndE).getIndividuos().get(randVeiS).getCost() + individuos.get(randIndE).getIndividuos().get(randVeiE).getRota().get(randCidE).getCusto() > individuos.get(randIndE).getIndividuos().get(randVeiS).getCapacity()){
				randVeiS = gerador.nextInt(numVeiculos);
				limite++;
				while (randVeiS == randVeiE)
					randVeiS = gerador.nextInt(numVeiculos);
				if (limite > 30){
					flag = true;
					break;
				}
			}
			if (!flag){
				inserirRotaVeiculo(individuos.get(randIndE).getIndividuos().get(randVeiS), individuos.get(randIndE).getIndividuos().get(randVeiE).getRota().get(randCidE));
				removerRotaVeiculo(individuos.get(randIndE).getIndividuos().get(randVeiE), individuos.get(randIndE).getIndividuos().get(randVeiE).getRota().get(randCidE));
			}
			

			
		}
		
		//System.out.println("-----");
		for (int j = 0; j < numIndividuos; j++) {
			float fitnessCarinha = individuoFitness(numVeiculos, j);
			//System.out.println(fitnessCarinha + " Id: " + individuos.get(j));
			for (int k = 0; k < individuos.get(j).getIndividuos().size(); k++) {
				if (individuos.get(j).getIndividuos().get(k).getCapacity() > capVeiculo)
					individuos.get(j).setFitness(200000);
			}
			individuos.get(j).setFitness(fitnessCarinha);
		}
		//Pegar os numIndividuos/2 melhores
		//System.out.println("-- GERACAO: " + z + " -------");
		Collections.sort(individuos, new Individuo());
		//System.out.println(individuos.get(0).getFitness());
		//for (int a = 0; a < individuos.get(0).getIndividuos().size(); a++) {
		//	individuos.get(0).getIndividuos().get(a).imprimirRotaVeiculo(individuos.get(0).getIndividuos().get(a));
		//}
		
		
		
		/*
		for (int j = 0; j < numIndividuos; j++) {
			for (int j2 = 0; j2 < individuos.get(j).getIndividuos().size(); j2++) {
				//individuos.get(j).getIndividuos().get(j2).imprimirRotaVeiculo(individuos.get(j).getIndividuos().get(j2));
			}
			System.out.println(individuos.get(j).getFitness());
		}
		*/
		//System.out.println("---- FIM GERACAO-----");
		
		}
		
		long lEndTime = System.currentTimeMillis();
		long difference = lEndTime - lStartTime;
		System.out.println(individuos.get(0).getFitness()); 
		System.out.println("Elapsed seconds: " + difference/1000);
		
	}
		
	}

}
