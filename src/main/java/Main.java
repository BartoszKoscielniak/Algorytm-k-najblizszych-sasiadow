import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Double[][] war = new Double[0][];
        Double[][] testObject = new Double[0][];
        Double[] distance;
        String[] dec = new String[0];
        String[] strk = new String[0];
        String[] tempDec = new String[0];
        ArrayList<String> uniqueDec = new ArrayList<String>();
        int decisionCounter = 0;
        int[] intk;
        int warCols = 0;
        int rows = 0;
        int decIndex = 0;
        int k = 7;

        try(CSVReader csvReader = new CSVReader(new FileReader("src\\main\\resources\\ecoli_trening.csv"))) {
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
            /*
            System.out.println(decisionCounter);
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
            */
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        try(CSVReader csvReader = new CSVReader(new FileReader("src\\main\\resources\\ecoli_test.csv"))){
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

        for (int y = 0; y < testObject.length; y++) {
            distance = new Double[war.length];
            for (int i = 0; i < war.length; i++) {
                for (int x = 0; x < war[i].length; x++) {
                    distance[i] =+ Math.pow((war[i][x] - testObject[y][x]), 2);
                }
                distance[i] = Math.sqrt(distance[i]);
            }

            tempDec = dec;
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

            for(int i = 0; i < distance.length; i++){
                //System.out.println("index:" + y + " " + "index distance:" + i + " " + distance[i]);
            }

            intk = new int[decisionCounter];
            for(int i = 0; i < k; i++){
               for(int x = 0; x < uniqueDec.size(); x++){
                   if(uniqueDec.get(x).equals(tempDec[i])) intk[x]++;
               }
            }

            //punkt 11
            int max = 0;
            int index = 0;
            for(int i = 0; i < intk.length; i++){
                if(intk[i] > max){
                    max = intk[i];
                    index = i;
                }
            }

            System.out.println("index: " + y + " " + testObject[y][0] + " " + uniqueDec.get(index));

        }

        //TODO: czas dzialania programu liczenie
    }
}
