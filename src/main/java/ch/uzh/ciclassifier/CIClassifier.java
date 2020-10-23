package ch.uzh.ciclassifier;

import ch.uzh.ciclassifier.evaluation.Evaluation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CIClassifier {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String configuration = "language: ruby\n" +
                "rvm:\n" +
                " - 2.2\n" +
                " - jruby";

        Evaluation evaluation = Evaluation.createFromConfiguration(configuration);
        evaluation.evaluate();

        System.out.println(evaluation.toJson());

        String gitUrl = "https://github.com/tzemp/ciclassifier-sample-project.git";
        Evaluation evaluation2 = Evaluation.createFromGitUrl(gitUrl);
        evaluation2.evaluate();

        System.out.println(evaluation2.toJson());

    }
}
