import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Double[][] war;
        String[] dec;
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
                    for(int y = 0; y < array.get(i)[x].length(); y++){
                        if(x != decIndex){
                            war[i][x] = Double.parseDouble(array.get(i)[x]);
                        }else{
                            dec[i] = array.get(i)[x];
                        }
                    }
                }
            }

            System.out.println(dec[0]);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
