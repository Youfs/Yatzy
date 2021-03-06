package com.kata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Yatzy {

	public static int chance(int d1, int d2, int d3, int d4, int d5) {
		return new Yatzy(d1, d2, d3, d4, d5).chance();
	}

	public static int yatzy(int... dice) {
		if (dice.length != 5)
			return 0;
		return new Yatzy(dice[0], dice[1], dice[2], dice[3], dice[4]).yatzy();

	}

	public static int ones(int d1, int d2, int d3, int d4, int d5) {
		return new Yatzy(d1, d2, d3, d4, d5).ones();
	}

	public static int twos(int d1, int d2, int d3, int d4, int d5) {
		return new Yatzy(d1, d2, d3, d4, d5).twos();
	}

	public static int threes(int d1, int d2, int d3, int d4, int d5) {
		return new Yatzy(d1, d2, d3, d4, d5).threes();

	}

	protected int[] dice;

	public Yatzy(int d1, int d2, int d3, int d4, int _5) {
		dice = new int[5];
		dice[0] = d1;
		dice[1] = d2;
		dice[2] = d3;
		dice[3] = d4;
		dice[4] = _5;
	}

	public int chance() {
		return Arrays.stream(this.dice).sum();
	}

	public int yatzy() {
		boolean isAllFieldsAreTheSame = Arrays.stream(dice).allMatch(s -> s == dice[0]);
		if (isAllFieldsAreTheSame)
			return 50;
		return 0;
	}

	public int fours() {
		return calculSumByDiceNumber(4);
	}

	public int fives() {
		return calculSumByDiceNumber(5);
	}

	public int sixes() {
		return calculSumByDiceNumber(6);
	}

	public static int score_pair(int d1, int d2, int d3, int d4, int d5) {

		return new Yatzy(d1, d2, d3, d4, d5).score_pair();

	}

	public static int two_pair(int d1, int d2, int d3, int d4, int d5) {

		return new Yatzy(d1, d2, d3, d4, d5).two_pair();

	}

	public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5) {

		return new Yatzy(d1, d2, d3, d4, d5).three_of_a_kind();

	}

	public static int four_of_a_kind(int d1, int d2, int d3, int d4, int d5) {

		return new Yatzy(d1, d2, d3, d4, d5).four_of_a_kind();

	}

	public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
		return new Yatzy(d1, d2, d3, d4, d5).smallStraight();
	}

	public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
		return new Yatzy(d1, d2, d3, d4, d5).largeStraight();
	}

	public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
		return new Yatzy(d1, d2, d3, d4, d5).fullHouse();
	}

	private int ones() {
		return calculSumByDiceNumber(1);
	}

	private int twos() {
		return calculSumByDiceNumber(2);
	}

	private int threes() {
		return calculSumByDiceNumber(3);
	}

	private int score_pair() {

		if (ifPair()) {
			return calculSumPair();
		}
		return 0;

	}

	private int two_pair() {

		if (ifTwoPair()) {
			return calculSumTwoPair();
		}
		return 0;

	}

	private int three_of_a_kind() {

		if (ifThreeOfKind()) {
			return calculSumThreeOfKind();
		}
		return 0;

	}

	private int four_of_a_kind() {

		if (ifFourOfKind()) {
			return calculSumFourOfKind();
		}
		return 0;

	}

	private int calculSumPair() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		Optional<Integer> maxDiceOccurances = groupingByCountMap.entrySet().stream()
				.filter(entry -> entry.getValue() >= 2).map(x -> x.getKey()).max(Integer::compareTo);
		return maxDiceOccurances.get() * 2;
	}

	private int calculSumTwoPair() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		int sumOfDiceNumber = groupingByCountMap.entrySet().stream().filter(entry -> entry.getValue() >= 2)
				.mapToInt(x -> x.getKey()).sum();
		return sumOfDiceNumber * 2;
	}

	private int calculSumFourOfKind() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		int theDiceNumber = groupingByCountMap.entrySet().stream().filter(x -> x.getValue() >= 4)
				.mapToInt(y -> y.getKey()).findFirst().getAsInt();

		return theDiceNumber * 4;
	}

	private int calculSumThreeOfKind() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		int theDiceNumber = groupingByCountMap.entrySet().stream().filter(x -> x.getValue() >= 3)
				.mapToInt(x -> x.getKey()).findFirst().getAsInt();
		return theDiceNumber * 3;
	}

	private boolean ifPair() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		return groupingByCountMap.values().stream().anyMatch(x -> x >= 2);
	}

	private boolean ifTwoPair() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		return groupingByCountMap.values().stream().filter(x -> x >= 2).count() == 2;
	}

	private boolean ifThreeOfKind() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		return groupingByCountMap.values().stream().anyMatch(x -> x >= 3);
	}

	private boolean ifStrictThreeOfKind() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		return groupingByCountMap.values().stream().anyMatch(x -> x == 3);
	}

	private boolean ifStrictPair() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		return groupingByCountMap.values().stream().anyMatch(x -> x == 2);
	}

	private boolean ifFourOfKind() {
		Map<Integer, Long> groupingByCountMap = groupingByCount();
		return groupingByCountMap.values().stream().anyMatch(x -> x >= 4);
	}

	private int smallStraight() {
		if (ifMatchSmallStraightRul())
			return calculSum();
		return 0;
	}

	private int largeStraight() {
		if (ifMatchLargeStraightRul())
			return calculSum();
		return 0;
	}

	private int fullHouse() {
		if (ifStrictPair() && ifStrictThreeOfKind())
			return calculSum();
		return 0;
	}

	private boolean ifMatchSmallStraightRul() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		return ifAllDistinct() && Arrays.stream(this.dice).distinct().allMatch(x -> {
			return list.contains(x);
		});
	}

	private boolean ifMatchLargeStraightRul() {
		List<Integer> list = Arrays.asList(6, 2, 3, 4, 5);
		return ifAllDistinct() && Arrays.stream(this.dice).distinct().allMatch(x -> {
			return list.contains(x);
		});
	}

	private boolean ifAllDistinct() {
		return Arrays.stream(this.dice).distinct().count() == 5;
	}

	private int calculSum() {
		return Arrays.stream(this.dice).sum();
	}

	private Map<Integer, Long> groupingByCount() {
		Map<Integer, Long> mapCount = Arrays.stream(this.dice).mapToObj(Integer::valueOf)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return mapCount;
	}

	private int calculSumByDiceNumber(final int rollToFilter) {
		return Arrays.stream(this.dice).filter(roll -> roll == rollToFilter).sum();
	}
}