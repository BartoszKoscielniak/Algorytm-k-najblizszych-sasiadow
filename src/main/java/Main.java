import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Double[][] war = new Double[0][];
        Double[] testObject;
        Double[] distance;
        String[] dec = new String[0];
        int warCols = 0;
        int rows = 0;
        int decIndex = 0;

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
                    }
                }
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        testObject = new Double[warCols];
        for(int i = 0; i < war[0].length; i++){
            testObject[i] = war[0][i];
        }

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

        System.out.println(dec[0] + " " + distance[0]);
        System.out.println(dec[149] + " " + distance[149]);
    }
}
