import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CarIdGenerator {
    public static String generateCarCode(
            String model,
            String variant,
            String priceINR,
            String fuelType,
            String bodyType,
            String colour,
            String dealerUsername) {
        // Build the base string as in Python
        String baseStr = String.format("%s_%s_%s_%s_%s_%s_%s",
                model, variant, priceINR, fuelType, bodyType, colour, dealerUsername);

        // Short tag: first 3 letters of model, uppercased
        String shortTag = (model != null && model.length() >= 3)
                ? model.substring(0, 3).toUpperCase()
                : "XXX";

        // MD5 hash, first 6 hex characters, uppercased
        String uniqueHash = md5Hex(baseStr).substring(0, 6).toUpperCase();

        return shortTag + uniqueHash;
    }

    private static String md5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found");
        }
    }

    // Example usage
    public static void main(String[] args) {
        String carCode = generateCarCode(
                "Venue", "1.0 Turbo Gdi Mt Sx Dual Tone", "974000", "Petrol", "SUV", "Yellow", "D_KAVITHAHYU001");
        System.out.println(carCode); // Output will match Python function for same input
    }
}

// 0 Venue 1.0 Turbo Gdi Mt Sx Dual Tone 974000 Petrol SUV Yellow
// D_KAVITHAHYU001 VEND0C813