import java.util.HashMap;

public class Handle {
	public static HashMap<String, Outcomes> outcomes = new HashMap<>();

	static {
		outcomes.put(PossibleThrows.ROCKROCK.toString(), Outcomes.TIE);
		outcomes.put(PossibleThrows.ROCKPAPER.toString(), Outcomes.LOSS);
		outcomes.put(PossibleThrows.ROCKSCISSORS.toString(), Outcomes.WIN);
		outcomes.put(PossibleThrows.SCISSORSSCISSORS.toString(), Outcomes.TIE);
		outcomes.put(PossibleThrows.SCISSORSPAPER.toString(), Outcomes.WIN);
		outcomes.put(PossibleThrows.PAPERPAPER.toString(), Outcomes.TIE);
		outcomes.put(PossibleThrows.ROCKLIZARD.toString(), Outcomes.WIN);
		outcomes.put(PossibleThrows.ROCKSPOCK.toString(), Outcomes.LOSS);
		outcomes.put(PossibleThrows.SCISSORSLIZARD.toString(), Outcomes.WIN);
		outcomes.put(PossibleThrows.SCISSORSSPOCK.toString(), Outcomes.LOSS);
		outcomes.put(PossibleThrows.PAPERLIZARD.toString(), Outcomes.LOSS);
		outcomes.put(PossibleThrows.PAPERSPOCK.toString(), Outcomes.WIN);
		outcomes.put(PossibleThrows.LIZARDLIZARD.toString(), Outcomes.TIE);
		outcomes.put(PossibleThrows.LIZARDSPOCK.toString(), Outcomes.WIN);
		outcomes.put(PossibleThrows.SPOCKSPOCK.toString(), Outcomes.TIE);
	}

	public static Outcomes outcomes(MovingObject obj1, MovingObject obj2) {
		String throwNames = obj1.getName().toString() + obj2.getName().toString();
		Outcomes result = outcomes.get(throwNames);
		if( result == null ) {
			result = outcomes.get(obj2.getName().toString() + obj1.getName().toString());
			if( result == Outcomes.WIN ) {
				result = Outcomes.LOSS;
			} else if (result == Outcomes.LOSS) {
				result = Outcomes.WIN;
			}
		}
		return result;
	}
}
