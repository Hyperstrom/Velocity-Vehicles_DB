import java.util.*;

public class DealerUsername {
    public static List<String> generateDealerUsernames(List<Map<String, String>> data) {
        Map<String, Integer> usernameCounts = new HashMap<>();
        List<String> usernames = new ArrayList<>();

        for (Map<String, String> row : data) {
            String name = row.get("Name").split(" ")[0].toUpperCase();
            String brandId = row.get("brand_id").substring(0, 3).toUpperCase();
            String baseUsername = "D_" + name + brandId;

            // Get occurrence count
            int count = usernameCounts.getOrDefault(baseUsername, 0);
            int num = 1 + count; // Ensure odd numbers (001, 002, 003,...)

            // Format and store
            String username = baseUsername + String.format("%03d", num);
            usernames.add(username);

            // Increment count
            usernameCounts.put(baseUsername, count + 1);
        }

        return usernames;
    }

    public static void main(String[] args) {
        List<Map<String, String>> data = Arrays.asList(
                Map.of("Name", "Arun Tat", "brand_id", "TATA"),
                Map.of("Name", "Arun Tat", "brand_id", "TATA"),
                Map.of("Name", "Balaji Kum", "brand_id", "BMW"));
        List<String> usernames = generateDealerUsernames(data);
        System.out.println(usernames);
    }
}
