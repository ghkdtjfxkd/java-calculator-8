package calculator.dto;

public record CalculationRequest(String input) {
    public static CalculationRequest of(String orderString) {
        return new CalculationRequest(orderString);
    }
}
