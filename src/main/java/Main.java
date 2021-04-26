import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        double startTime = System.nanoTime();

        Double[][] war = new Double[0][];
        Double[][] testObject = new Double[0][];
        Double[] distance;
        String[] dec = new String[0];
        ArrayList<String> uniqueDec = new ArrayList<>();
        int decisionCounter = 0;
        int[] intk;
        int warCols = 0;
        int rows = 0;
        int decIndex = 0;

        ////////////ZMIANA K////////////
        int k = 7;
        ////////////ZMIANA OBIEKTU TRENINGOWEGO////////////
        try(CSVReader csvReader = new CSVReader(new FileReader("src\\main\\resources\\iris_trening.csv"))) {
            List<String[]> array = csvReader.readAll();

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
                        if(i >= 1 && !uniqueDec.contains(dec[i])){
                            uniqueDec.add(dec[i]);
                            decisionCounter++;
                        }
                    }
                }
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        ////////////ZMIANA OBIEKTU TESTOWEGO////////////
        try(CSVReader csvReader = new CSVReader(new FileReader("src\\main\\resources\\iris_test.csv"))){
            List<String[]> arrayS = csvReader.readAll();
            testObject = new Double[arrayS.size()][arrayS.get(0).length];

            for(int i = 0; i < arrayS.size(); i++){
                for(int x = 0; x < arrayS.get(i).length ; x++){
                    testObject[i][x] = Double.parseDouble(arrayS.get(i)[x]);
                }
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        String[] tempDec = new String[dec.length];

        for (int y = 0; y < testObject.length; y++) {
            distance = new Double[war.length];
            for (int o = 0; o < dec.length; o++){
                tempDec[o] = dec[o];
            }

            for (int i = 0; i < war.length; i++) {
                double result = 0;
                for (int x = 0; x < war[i].length; x++) {
                    result += Math.pow((war[i][x] - testObject[y][x]), 2);
                }
                distance[i] = Math.sqrt(result);
            }

            for( int i = 0; i < distance.length; i++ ) {
                for( int j = 0; j < distance.length - 1; j++ ) {
                    if( distance[ j ] > distance[ j + 1 ] ) {

                        double temp = distance[j];
                        distance[j] = distance[j + 1];
                        distance[j+1] = temp;

                        String temps = tempDec[j];
                        tempDec[j] = tempDec[j + 1];
                        tempDec[j + 1] = temps;
                    }
                }
            }

            for (int o = 0; o < dec.length; o++){
                //System.out.println();
            }

            intk = new int[uniqueDec.size()];
            for(int i = 0; i < k; i++){
               for(int x = 0; x < uniqueDec.size(); x++){
                   if(uniqueDec.get(x).equals(tempDec[i])){
                       intk[x]++;
                       break;
                   }
               }
            }

            int max = 0;
            int index = 0;
            for(int i = 0; i < intk.length; i++){
                if(intk[i] > max){
                    max = intk[i];
                    index = i;
                }
            }

            for (int q = 0; q < testObject[y].length; q++){
                System.out.print(testObject[y][q] + ", ");
            }
            System.out.println(uniqueDec.get(index));

        }

        double elapsedTime = System.nanoTime() - startTime;
        System.out.println("Wykonanie programu zajelo: " + elapsedTime/1000000000 + " sekundy");
    }

}
