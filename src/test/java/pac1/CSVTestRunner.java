package pac1;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVTestRunner {

    String projectpath = System.getProperty("user.dir");

    @Test(dataProvider = "csvData")
    public void testWithCSVData(String username, String password) {
        System.out.println("Username: " + username + " | Password: " + password);
        // Add your Selenium automation logic here
    }

    @DataProvider(name = "csvData")
    public Object[][] getCSVData() throws IOException, CsvException {
        String csvpath = projectpath + "\\data.csv";
        CSVReader reader = new CSVReader(new FileReader(csvpath));

        List<String[]> rows = reader.readAll();

        Object[][] data = new Object[rows.size()][2];
        for (int i = 0; i < rows.size(); i++) {
            data[i][0] = rows.get(i)[0];
            data[i][1] = rows.get(i)[1];
        }

        reader.close();
        return data;
    }
}