package tsp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class IntegerPermutationTest {

	@Test
	public void ensureThereAreNoSideEffects() {

		List<Integer> currentPermutation = createPermutation("16432");
		currentPermutation = Collections.unmodifiableList(currentPermutation);
		List<Integer> nextPermutation = IntegerPermutation.nextPermutation(currentPermutation);

		assertThat(getStringFromPermutation(nextPermutation), equalTo("21346"));
	}

	@Test
	public void testWhereFirstNumberIsFirstNotIncreasing() {

		List<Integer> currentPermutation = createPermutation("16432");
		List<Integer> nextPermutation = IntegerPermutation.nextPermutation(currentPermutation);

		assertThat(getStringFromPermutation(nextPermutation), equalTo("21346"));
	}

	@Test
	public void testEmptyListReturnedForFinalPerm() {

		List<Integer> currentPermutation = createPermutation("864321");
		List<Integer> nextPermutation = IntegerPermutation.nextPermutation(currentPermutation);

		assertThat(nextPermutation.size(), equalTo(0));
	}

	@Test
	public void testPenultimatePerm() {

		List<Integer> currentPermutation = createPermutation("954312");
		List<Integer> nextPermutation = IntegerPermutation.nextPermutation(currentPermutation);

		assertThat(getStringFromPermutation(nextPermutation), equalTo("954321"));
	}

	@Test
	public void testSecondPerm() {

		List<Integer> currentPermutation = createPermutation("123458");
		List<Integer> nextPermutation = IntegerPermutation.nextPermutation(currentPermutation);

		assertThat(getStringFromPermutation(nextPermutation), equalTo("123485"));
	}

	@Test
	public void testAnyOldPerm() {

		List<Integer> currentPermutation = createPermutation("68271");
		List<Integer> nextPermutation = IntegerPermutation.nextPermutation(currentPermutation);

		assertThat(getStringFromPermutation(nextPermutation), equalTo("68712"));
	}

	@Test
	public void testCorrectNumberOfItemsInSet() {
		Set<List<Integer>> actualResult = IntegerPermutation.allPermutations(2);

		assertThat(actualResult.size(), equalTo(6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExceptionOnZero() {
		Set<List<Integer>> actualResult = IntegerPermutation.allPermutations(0);
	}

	@Test
	public void testAllPermutationsReturned() {
		String[] expectedPermStrings = new String[]{"012","021","102","120","201","210"};
		Set<List<Integer>> expectedResult = createSetOfPermutations(expectedPermStrings);
		Set<List<Integer>> actualResult = IntegerPermutation.allPermutations(2);

		for(List<Integer> item:actualResult){
				assertThat("actual result not in expected result",setContainsList(expectedResult, item), equalTo(true));	
		}

		for(List<Integer> item:expectedResult){
			assertThat("expected result not in actual result",setContainsList(actualResult, item), equalTo(true));	
		}

	}

	@Test
	public void allPermutationsBackTrack_testAllReturned() {
		String[] expectedPermStrings = new String[]{"012","021","102","120","201","210"};
		Set<List<Integer>> expectedResult = createSetOfPermutations(expectedPermStrings);
		Set<List<Integer>> actualResult = IntegerPermutation.allPermutationsBackTrack(2);

		for(List<Integer> item:actualResult){
				assertThat("actual result not in expected result",setContainsList(expectedResult, item), equalTo(true));	
		}

		for(List<Integer> item:expectedResult){
			assertThat("expected result not in actual result",setContainsList(actualResult, item), equalTo(true));	
		}

	}

	private boolean setContainsList(Set<List<Integer>> set, List<Integer> list) {

		for(List<Integer> item: set){ 
			boolean sameList = item.equals(list);
			if (sameList) {
				return (true);
			}
		}
		return(false);
	}

	private List<Integer> createPermutation(String number) {

		List<Integer> permutation = new ArrayList<Integer>();
		for (int i = 0; i < number.length(); i++) {
			permutation.add(Character.getNumericValue(number.charAt(i)));
		}
		return permutation;
	}

	private Set<List<Integer>> createSetOfPermutations(String[] permStrings){
		Set<List<Integer>> setOfPermutations = new HashSet<List<Integer>>();
		for(int i = 0; i < permStrings.length; i++){
			setOfPermutations.add(createPermutation(permStrings[i]));
		}
		return setOfPermutations;
	}

	private String getStringFromPermutation(List<Integer> permutation) {

		StringBuilder permString = new StringBuilder();
		for (Integer item : permutation) {
			permString.append(item.toString());
		}
		return permString.toString();
	}

}