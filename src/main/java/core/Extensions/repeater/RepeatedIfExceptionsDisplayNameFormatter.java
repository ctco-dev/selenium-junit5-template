package core.Extensions.repeater;

class RepeatedIfExceptionsDisplayNameFormatter {

    private final String pattern;
    private final String displayName;
    private final String tag;


    RepeatedIfExceptionsDisplayNameFormatter(String pattern, String displayName, String tag) {
        this.pattern = pattern;
        this.displayName = displayName;
        this.tag = tag;
    }

    String format(int currentRepetition, int totalRepetitions) {
        return this.pattern
                .replace(RepeatedIfExceptionsTest.DISPLAY_NAME_PLACEHOLDER, this.displayName)
                .replace(RepeatedIfExceptionsTest.TAG_PLACEHOLDER, this.tag)
                .replace(RepeatedIfExceptionsTest.CURRENT_REPETITION_PLACEHOLDER, String.valueOf(currentRepetition))
                .replace(RepeatedIfExceptionsTest.TOTAL_REPETITIONS_PLACEHOLDER, String.valueOf(totalRepetitions));
    }

}
