import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main {

  public static void main(String[] args) {

    final int MAX_GEN = 1000;
    final int NUM_INDIVIDUOS = 20;
    final double X_BOUND = 10 ;// 20 e -20
    final double F = 0.5;
    final double Cr = 0.8;

    ArrayList<Individuo> individuos = new ArrayList<>(NUM_INDIVIDUOS);
    individuos.addAll(init(NUM_INDIVIDUOS,X_BOUND));
    // funcao = x1^2 + x2^2


    Individuo exp = new Individuo();
    Random random = new Random();

    avaliaIndividuos(individuos);

    int INICIAL_GEN = 1;
    ArrayList<Individuo> new_pop = null;
    while(INICIAL_GEN <= MAX_GEN){

      new_pop = new ArrayList<>(NUM_INDIVIDUOS);

      for(int i = 0; i < NUM_INDIVIDUOS; i++){

        int r1 = random.nextInt(0,NUM_INDIVIDUOS-1);
        int r2 = random.nextInt(0,NUM_INDIVIDUOS-1);
        int r3 = random.nextInt(0,NUM_INDIVIDUOS-1);

        Individuo u = new Individuo();
        u.gerarIndividuo(individuos,r1,r2,r3,F);


        exp = recombinar(individuos.get(i),u,Cr);
        exp.avaliar();

        if(validarDominancia(exp.avaliacao,individuos.get(i).avaliacao)){
          new_pop.add(i,exp);
        }else if(validarDominancia(individuos.get(i).avaliacao,exp.avaliacao)){
          new_pop.add(i,individuos.get(i));
        }else{
          int sorteio = random.nextInt(0,1);
          if (sorteio == 1){
            new_pop.add(i,exp);
          }else{
            new_pop.add(i,individuos.get(i));
          }
        }
      }
      double melhorAvaliacao = 0;
      for (int i = 0; i < new_pop.size(); i++) {
        if (i == 0){
          melhorAvaliacao = new_pop.get(i).avaliacao[0];
        }else{
          if (new_pop.get(i).avaliacao[0] < melhorAvaliacao){
            melhorAvaliacao = new_pop.get(i).avaliacao[0];
          }
        }

      }


      individuos.clear();
      individuos.addAll(new_pop);

      INICIAL_GEN++;
    }

    for (Individuo individuo :individuos) {
      System.out.println();
    }

    new_pop = new_pop;
    for (int i = 0; i < individuos.size(); i++) {
      String dadosDoIndividuo = "Individuo " + (i+1);
      for (int j = 0; j <individuos.get(i).avaliacao.length ; j++) {
        dadosDoIndividuo += "\t Avaliacao " + (j+1)+": " +  individuos.get(i).avaliacao[j];
      }
      System.out.println(dadosDoIndividuo);
    }
  }



  public static Individuo recombinar(Individuo individuo, Individuo u, double Cr){
    Individuo filho = new Individuo();
    for(int i = 0; i < individuo.genes.length ; i++){
      double r = new Random().nextDouble(0,1);
      if(r < Cr){
        filho.genes[i] = individuo.genes[i];
      }else{
        filho.genes[i] = u.genes[i];
      }
    }
    return filho;
  }

  public  static void avaliaIndividuos(ArrayList<Individuo> individuos){
    for (Individuo individuo :
            individuos) {
      individuo.avaliar();
    }
    individuos = individuos;
  }

  public static ArrayList<Individuo> init(int NUM_INDIVIDUOS,double X_BOUND){
    ArrayList<Individuo> popInicial = new ArrayList<>(NUM_INDIVIDUOS);
    for (int i = 0; i < NUM_INDIVIDUOS; i++) {
      Individuo individuo = new Individuo();
      individuo.gerarGenes(-X_BOUND,X_BOUND);
      popInicial.add(individuo);
    }
    return popInicial;
  }

  public static boolean validarDominancia(double[] avalInd,double[] avalInd2){
    boolean domina = false;
    for (int i = 0; i < avalInd.length; i++) {
      if (avalInd[i]< avalInd2[i]){
        domina = true;
      }else if((avalInd[i] > avalInd2[i]) ){
        return false;
      }
    }
    return domina;
  }
}