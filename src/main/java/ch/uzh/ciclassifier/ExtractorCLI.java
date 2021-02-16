package ch.uzh.ciclassifier;

import ch.uzh.ciclassifier.evaluation.Evaluation;
import ch.uzh.ciclassifier.exception.EvaluationNotPossibleException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ExtractorCLI {

    public final static Logger LOGGER = Logger.getLogger(Extractor.class.getName());

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        LogManager.getRootLogger().setLevel(Level.DEBUG);
        org.apache.logging.log4j.LogManager.getRootLogger().atLevel(org.apache.logging.log4j.Level.DEBUG);

        try (CSVReader csvReader = new CSVReader(new FileReader("data/subset.csv"));) {
            String[] values = null;
            boolean first = true;
            while ((values = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                //String shortName = values[1].replace("https://api.github.com/repos/","");
                String shortName = values[1];
                String gitUrl = "https://github.com/" + shortName + ".git";
                String filePath = "data/new_extraction/" + shortName.replace("/","_") + ".json";

                Extractor.LOGGER.info("Starting evaluation for " + shortName);

                File file = new File(filePath);
                if (file.exists()) {
                    Extractor.LOGGER.info("Skipping... " + shortName);
                    continue;
                }

                try {
                    Evaluation evaluation = Evaluation.createFromGitUrl(gitUrl);
                    evaluation.evaluate();

                    //Write JSON file
                    try (FileWriter fileWriter = new FileWriter(filePath)) {

                        JSONObject res = evaluation.toJson();
                        res.put("db-identifier",values[0]);
                        fileWriter.write(res.toJSONString());
                        fileWriter.flush();

                        Extractor.LOGGER.info("Done evaluation for " + shortName);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (EvaluationNotPossibleException e) {
                    Extractor.LOGGER.severe("Evaluation not possible for project " + shortName + ", reason: " + e.getMessage());
                }

            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
