package ch.uzh.ciclassifier.evaluation;

import ch.uzh.ciclassifier.features.BaseFeature;
import ch.uzh.ciclassifier.features.Feature;
import ch.uzh.ciclassifier.features.FeatureType;
import ch.uzh.ciclassifier.helper.Configuration;
import ch.uzh.ciclassifier.helper.Repository;
import org.json.simple.JSONObject;
import org.reflections.Reflections;

import javax.xml.catalog.CatalogFeatures;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Evaluation {
    private String rawConfiguration = null;
    private String gitUrl = null;
    private Repository repository = null;
    private Configuration configuration = null;
    private List<Feature> features = new ArrayList<Feature>();
    private List<FeatureType> types = new ArrayList<FeatureType>();

    public static Evaluation createFromFilePath(String path) throws IOException {
        String configuration = Files.readString(Path.of(path));

        return Evaluation.createFromConfiguration(configuration);
    }

    public static Evaluation createFromConfiguration(String configuration) throws IOException {
        Evaluation evaluation = new Evaluation();
        evaluation.rawConfiguration = configuration;
        evaluation.configuration = new Configuration(configuration);
        evaluation.types.add(FeatureType.CONFIGURATION);

        return evaluation;
    }

    public static Evaluation createFromGitUrl(String gitUrl) throws IOException {
        Evaluation evaluation = new Evaluation();
        evaluation.gitUrl = gitUrl;
        evaluation.repository = new Repository(gitUrl);
        String configuration = evaluation.repository.getConfiguration();
        evaluation.rawConfiguration = configuration;
        evaluation.configuration = new Configuration(configuration);

        evaluation.types.add(FeatureType.CONFIGURATION);
        evaluation.types.add(FeatureType.REPOSITORY);
        evaluation.types.add(FeatureType.TRAVISCI);

        return evaluation;
    }

    public void evaluate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Reflections reflections = new Reflections("ch.uzh.ciclassifier.features");
        Set<Class<? extends Feature>> classes = reflections.getSubTypesOf(Feature.class);
        System.out.println(classes);
        for (Class clazz : classes) {
            Constructor<?> constructor = clazz.getConstructor();
            Feature feature = (Feature) constructor.newInstance();
            if (this.types.contains(feature.supportedFeatureType())) {
                feature.extract(this);
                features.add(feature);
            }
        }
    }

    public String toJson() {
        JSONObject obj = new JSONObject();

        for (Feature feature : this.features) {
            obj.put(feature.getClass().getName(), feature.getData());
        }

        return obj.toJSONString();
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

    public List<Feature> getFeatures() {
        return features;
    }

    public List<FeatureType> getTypes() {
        return types;
    }
}
