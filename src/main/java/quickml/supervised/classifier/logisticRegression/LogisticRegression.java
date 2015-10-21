package quickml.supervised.classifier.logisticRegression;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickml.MathUtils;
import quickml.data.AttributesMap;
import quickml.data.PredictionMap;
import quickml.supervised.classifier.AbstractClassifier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by alexanderhawk on 10/9/15.
 */
public class LogisticRegression extends AbstractClassifier {

    double[] weights;
    private final HashMap<String, Integer> nameToIndexMap;
    private static final Logger logger = LoggerFactory.getLogger(LogisticRegression.class);
    private  Map<Serializable, Double> classificationToClassNameMap;
    Set<Double> classifications =Sets.newHashSet();
    public LogisticRegression(double[] weights, final HashMap<String, Integer> nameToIndexMap,
                              Map<Serializable, Double> classificationToClassNameMap) {
        this.weights = weights;
        this.nameToIndexMap = nameToIndexMap;
        this.classificationToClassNameMap = classificationToClassNameMap;
        for (Double classification: classificationToClassNameMap.values()) {
            classifications.add(classification);
        }
    }

    public LogisticRegression(double[] weights, final HashMap<String, Integer> nameToIndexMap,
                              Set<Double> classifications) {
        this.weights = weights;
        this.nameToIndexMap = nameToIndexMap;
        this.classifications= classifications;
    }


    @Override
    public double getProbability(final AttributesMap attributes, final Serializable classification) {
        double dotProduct = 0;
        dotProduct += weights[0];
        for (String attribute : attributes.keySet()) {
            int index = nameToIndexMap.get(attribute);
            dotProduct += weights[index] * (Double) attributes.get(attribute);
        }
        if ((double)classification == 1.0) {
            return MathUtils.sigmoid(dotProduct);
        } else {
            return 1.0-MathUtils.sigmoid(dotProduct);

        }
    }

    @Override
    public PredictionMap predict(final AttributesMap attributes) {
        PredictionMap predictionMap = new PredictionMap(new HashMap<Serializable, Double>());
        for (Serializable classification : classifications) {
            predictionMap.put(classification, getProbability(attributes, classification));
        }
        return predictionMap;
    }

    @Override
    public PredictionMap predictWithoutAttributes(final AttributesMap attributes, final Set<String> attributesToIgnore) {
        throw new RuntimeException("not implemented");
    }


}

