package ch.uzh.ciclassifier.evaluation;

import ch.uzh.ciclassifier.Extractor;
import ch.uzh.ciclassifier.exception.ConfigurationInvalidException;
import ch.uzh.ciclassifier.exception.EvaluationNotPossibleException;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.features.configuration.TemplateSimilarity;
import ch.uzh.ciclassifier.helper.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Evaluation {
    private String identifier = null;
    private String rawConfiguration = null;
    private String gitUrl = null;
    private Repository repository = null;
    private Configuration configuration = null;
    private TravisCI travisCI = null;
    private GitHub gitHub = null;
    private List<Feature> features = new ArrayList<Feature>();
    private List<FeatureType> types = new ArrayList<FeatureType>();

    public Evaluation() {
        this.identifier = this.generateRandomIdentifier();
    }

    public static Evaluation createFromFilePath(String path) throws IOException {
        String configuration = Files.readString(Path.of(path));

        return Evaluation.createFromConfiguration(configuration);
    }

    public static Evaluation createFromConfiguration(String configuration) throws IOException {
        Evaluation evaluation = new Evaluation();
        evaluation.rawConfiguration = configuration;

        try {
            evaluation.configuration = new Configuration(configuration);
            evaluation.types.add(FeatureType.CONFIGURATION);
        } catch (ConfigurationInvalidException e) {
            throw new EvaluationNotPossibleException("Configuration Invalid");
        } catch (ParseException e) {
            throw new EvaluationNotPossibleException("Parse Error");
        }

        return evaluation;
    }

    public static Evaluation createFromGitUrl(String gitUrl) {
        Extractor.LOGGER.info("Creating Evaluation from Git: " + gitUrl);
        Evaluation evaluation = new Evaluation();

        // Init Repository
        evaluation.gitUrl = gitUrl;
        try {
            evaluation.repository = new Repository(gitUrl);
            evaluation.getTypes().add(FeatureType.REPOSITORY);
        } catch (IOException | GitAPIException e) {
            throw new EvaluationNotPossibleException("Cannot clone from GitHub");
        }

        // Init Configuration
        String configuration = null;
        try {
            configuration = evaluation.repository.getConfiguration();
        } catch (Exception e) {
            throw new EvaluationNotPossibleException("Cannot read configuration");
        }
        evaluation.rawConfiguration = configuration;

        try {
            evaluation.configuration = new Configuration(configuration);
            evaluation.getTypes().add(FeatureType.CONFIGURATION);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EvaluationNotPossibleException("Cannot parse configuration");
        }

        // Init GitHub
        if (evaluation.repository != null) {
            JSONObject githubData = GitHubHelper.getRepositoryData(evaluation.getRepository().getName());
            if (null != githubData) {
                evaluation.gitHub = new GitHub(githubData);
                evaluation.getRepository().setName(evaluation.getGitHub().getName()); //override name in case it was renamed
                evaluation.getTypes().add(FeatureType.GITHUB);
            }
        }

        // Init TravisCI
        if (evaluation.getGitHub() != null) {
            try {
                String travisApi = TravisCIHelper.getApiForRepository(evaluation.getRepository().getName());
                if (null != travisApi) {
                    evaluation.travisCI = new TravisCI(travisApi, evaluation.getGitHub());
                    evaluation.types.add(FeatureType.TRAVISCI);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new EvaluationNotPossibleException("Cannot connect to Travis-CI");
            }
        }


        evaluation.identifier = evaluation.getRepository().getName().replace("/", "_");

        return evaluation;
    }

    public void evaluate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Reflections reflections = new Reflections("ch.uzh.ciclassifier.features");
        Set<Class<? extends Feature>> classes = reflections.getSubTypesOf(Feature.class);
        for (Class clazz : classes) {
            Constructor<?> constructor = clazz.getConstructor();
            Feature feature = (Feature) constructor.newInstance();
            if (this.types.contains(feature.supportedFeatureType())) {
                feature.extract(this);
                features.add(feature);
            }
        }
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("identifier", this.getIdentifier());

        JSONArray types = new JSONArray();
        for (FeatureType type : this.getTypes()) {
            types.add(type.name());
        }
        obj.put("types", types);

        JSONObject features = new JSONObject();
        for (Feature feature : this.features) {
            try {
                features.put(feature.getClass().getName(), feature.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        obj.put("features", features);

        return obj;
    }

    public String getRawConfiguration() {
        return rawConfiguration;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public Repository getRepository() {
        return repository;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public TravisCI getTravisCI() {
        return travisCI;
    }

    public GitHub getGitHub() {
        return gitHub;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public List<FeatureType> getTypes() {
        return types;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    private String generateRandomIdentifier() {
        int n = 10;
        String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
