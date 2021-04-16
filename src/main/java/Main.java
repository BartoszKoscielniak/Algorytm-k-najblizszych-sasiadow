import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Double[][] war = new Double[0][];
        Double[] testObject;
        Double[] distance;
        String[] dec = new String[0];
        String[] strk = new String[0];
        int decisionCounter = 0;
        int[] intk;
        int warCols = 0;
        int rows = 0;
        int decIndex = 0;
        int k = 25;


        try(CSVReader csvReader = new CSVReader(new FileReader("src\\main\\resources\\iris.csv"))) {
            List<String[]> array = csvReader.readAll();
            //r.forEach(x -> System.out.println(Arrays.toString(x)));

            while(rows < array.get(0).length){
                for(int i = 0; i < array.get(0)[rows].length(); i++){
                    if(array.get(0)[rows].charAt(i) >= '0' && array.get(0)[rows].charAt(i) <= '9'){
                        break;
                    }else {
                        decIndex = rows;
                    }
                }
                rows++;
            }

            rows = array.size();
            warCols = array.get(0).length - 1;
            war = new Double[rows][warCols];
            dec = new String[rows];

            for(int i = 0; i < array.size(); i++){
                for(int x = 0; x < array.get(i).length ; x++){
                    if(x != decIndex){
                        war[i][x] = Double.parseDouble(array.get(i)[x]);
                    }else{
                        dec[i] = array.get(i)[x];
                        if(i >= 1 && !(dec[i - 1].equals(dec[i]))){
                            decisionCounter++;
                        }
                    }
                }
            }
            decisionCounter++;

            //dodanie elementow do tablicy strk
            strk = new String[decisionCounter];
            strk[0] = dec[0];
            int temp = 1;
            for(int i = 0; i < dec.length; i++){
                if(i >= 1 && !(strk[temp - 1].equals(dec[i]))) {
                    strk[temp] = dec[i];
                    temp++;
                }
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        //wprowadzenie obiektu testowego
        System.out.print("Obiekt testowy: ");
        testObject = new Double[warCols];
        Random random = new Random();
        for(int i = 0; i < war[0].length; i++){
            testObject[i] = 1.0 + (7.5 - 1.0) * random.nextDouble();
            System.out.print(testObject[i] + ", ");
        }
        System.out.print("Nalezy do centrum: ");

        distance = new Double[rows];
        for(int i = 0; i < war.length; i++){
            distance[i] = Math.sqrt(Math.pow((war[i][0] - testObject[0]),2) + Math.pow((war[i][1] - testObject[1]),2) + Math.pow((war[i][2] - testObject[2]),2) + Math.pow((war[i][3] - testObject[3]),2));
        }

        //sortowanie
        int min;
        double tempD;
        String tempS;
        for(int j = 0; j < distance.length - 1; j++) {
            min = j;
            for(int i = j + 1; i < distance.length; i++) {
                if (distance[i] < distance[min]) {
                    min = i;
                    tempD = distance[min];
                    distance[min] = distance[j];
                    distance[j] = tempD;
                    tempS = dec[min];
                    dec[min] = dec[j];
                    dec[j] = tempS;
                }
            }
        }

        //punkt 10
        intk = new int[decisionCounter];
        for(int i = 0; i < k; i++){
            for(int j = 0; j < strk.length; j++){
                if(strk[j].equals(dec[i])) intk[j]++;
            }
        }

        //punkt 11
        int max = 0;
        int index = 0;
        for(int i = 0; i < intk.length; i++){
            if(intk[i] > max) index = i;
        }

        System.out.println(strk[index]);
    }
}
