package quickml.supervised.tree.decisionTree.scorers;

import com.google.common.collect.Sets;
import quickml.supervised.tree.decisionTree.valueCounters.ClassificationCounter;
import quickml.scorers.Scorer;

import java.io.Serializable;

public final class SplitDiffScorer extends Scorer<ClassificationCounter> {

	/*
	 * The general idea here is that a good split is one where the proportions
	 * of classifications on each side of the split are as different as
	 * possible. eg. if 50% of the classifications in set A are "dog", then the
	 * further away from 50% the proportion of "dog" classifications in set B
	 * are, the better.
	 * 
	 * We therefore add up the differences between the proportions, however we
	 * have another goal, which is that its preferable for the sets to be of
	 * close to equal getSize. Without this requirement a split with 0 on one getSize
	 * would get a high score because all of the proportions on that side would
	 * be 0.
	 * 
	 * So, we multiply the score by the getSize of the smallest side, which
	 * experimentally seems to provide an adequate bias against one-sided
	 * splits.
	 */

	@Override
	public void setUnSplitScore(ClassificationCounter a) {
		unSplitScore = 0;
	}

	@Override
	public Scorer<ClassificationCounter> createScorer() {
	 	return new GiniImpurityScorer();
	}

	@Override
	public double scoreSplit(final ClassificationCounter a, final ClassificationCounter b) {
		double score = 0;
		for (final Serializable value : Sets.union(a.allClassifications(), b.allClassifications())) {
			final double aProp = (double) a.getCount(value) / a.getTotal();
			final double bProp = (double) b.getCount(value) / b.getTotal();

			score += Math.abs(aProp - bProp) * Math.min(a.getTotal(), b.getTotal());
		}
		return score;
	}

    public String toString() {
        return "SplitDiffScorer";
    }

}