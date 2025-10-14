package calculator.dto;

public record CalculationResponse(String output) {
    public static CalculationResponse from(String output) {
        return new CalculationResponse(output);
    }
}
