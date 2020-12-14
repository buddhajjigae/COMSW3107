public enum Outcomes {
	WIN("BEATS"), LOSS("LOSES TO"), TIE("TIES WITH");
	
	private final String outcome;
	
	Outcomes(String outcome) {
		this.outcome = outcome;
	}
	
	public String getOutcome() {
		return outcome;
	}
}
