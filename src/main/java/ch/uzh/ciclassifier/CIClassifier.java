package ch.uzh.ciclassifier;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class CIClassifier {

    public final static Logger LOGGER = Logger.getLogger(CIClassifier.class.getName());

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        try (CSVReader csvReader = new CSVReader(new FileReader("data/extract4.csv"));) {
            String[] values = null;
            boolean first = true;
            while ((values = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                String shortName = values[1].replace("https://api.github.com/repos/","");
                String gitUrl = "https://github.com/" + shortName + ".git";
                String filePath = "data/results3/" + shortName.replace("/","_") + ".json";

                File file = new File(filePath);
                if (file.exists()) {
                    continue;
                }

                Evaluation evaluation = Evaluation.createFromGitUrl(gitUrl);
                evaluation.evaluate();

                //Write JSON file
                try (FileWriter fileWriter = new FileWriter(filePath)) {

                    fileWriter.write(evaluation.toJson().toJSONString());
                    fileWriter.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
