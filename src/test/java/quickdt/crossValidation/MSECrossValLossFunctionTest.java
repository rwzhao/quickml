package quickdt.crossValidation;

import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import quickdt.crossValidation.crossValLossFunctions.ClassifierMSECrossValLossFunction;
import quickdt.data.Instance;
import quickdt.data.Attributes;
import quickdt.predictiveModels.PredictiveModel;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 5/5/2014.
 */
public class MSECrossValLossFunctionTest {

    @Test
    public void testGetTotalLoss() {
        ClassifierMSECrossValLossFunction crossValLoss = new ClassifierMSECrossValLossFunction();
        PredictiveModel<Object> predictiveModel = Mockito.mock(PredictiveModel<Object>.class);
        Map<String, Serializable> test1Attributes = Mockito.mock(Map<String, Serializable>.class);
        Map<String, Serializable> test2Attributes = Mockito.mock(Map<String, Serializable>.class);
        Mockito.when(predictiveModel.getProbability(test1Attributes, "test1")).thenReturn(0.75);
        Mockito.when(predictiveModel.getProbability(test2Attributes, "test1")).thenReturn(0.5);

        Instance instance = Mockito.mock(Instance.class);
        Mockito.when(instance.getLabel()).thenReturn("test1");
        Mockito.when(instance.getWeight()).thenReturn(2.0);
        Mockito.when(instance.getRegressors()).thenReturn(test1Attributes);

        Instance instance2 = Mockito.mock(Instance.class);
        Mockito.when(instance2.getLabel()).thenReturn("test1");
        Mockito.when(instance2.getWeight()).thenReturn(1.0);
        Mockito.when(instance2.getRegressors()).thenReturn(test2Attributes);

        List<Instance> instances = new LinkedList<>();
        instances.add(instance);
        instances.add(instance2);

        Assert.assertEquals(0.125, crossValLoss.getLoss(instances, predictiveModel));
    }
}
