import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MainGet {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://185.221.160.131/api/v1/transactions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String result = "";
        String line;
        while ((line = br.readLine()) != null) {
            result += line + "\n";
        }
        br.close();
        isr.close();
        inputStream.close();
       // System.out.println(result);


        List<TransactionEntity> transactions = new ArrayList<>();
        result = result.replaceAll("\\[\\{]", "");
        result = result.replaceAll("}]", "");
        result = result.replaceAll("\n", "");

        String [] str = result.split("},\\{");
        //System.out.println(Arrays.toString(str));

        for(String element : str){
            TransactionEntity transaction = new TransactionEntity();
            String [] strParts = element.split(",");

            for (String part : strParts){
                String correctedPart = part.replaceAll("\"", "");
                String[] elements = correctedPart.split(":");

                if ("id".equals(elements[0])){
                    transaction.setId(Long.parseLong(elements[1]));
                } else if ("userIdFrom".equals(elements[0])) {
                    transaction.setUserIdFrom(Long.parseLong(elements[1]));
                }else if ("userIdTo".equals(elements[0])) {
                    transaction.setUserIdTo(Long.parseLong(elements[1]));
                }else if ("amount".equals(elements[0])) {
                    transaction.setAmount(Double.parseDouble(elements[1]));
                }else if ("success".equals(elements[0])) {
                    if(elements[1].equals("true")){
                        transaction.setSuccess(true);
                    }
                    if(elements[1].equals("false")){
                        transaction.setSuccess(false);
                    }
                }else if ("denyReason".equals(elements[0])) {
                    transaction.setDenyReason(elements[1]);
                }
                transactions.add(transaction);
            }
        }

       // System.out.println(transactions);
        System.out.println(solve1(transactions));
        System.out.println(solve2(transactions));
        System.out.println(solve3(transactions));
        System.out.println("-------------------------------");
        System.out.println(solve4(transactions));
        System.out.println(solve5(transactions));
        System.out.println(solve6(transactions));
        System.out.println("-------------------------------");
        System.out.println(solve7(transactions));
        System.out.println(solve8(transactions));
        System.out.println(solve9(transactions));
        System.out.println("---------------------");
        System.out.println(solve10(transactions));
        /*System.out.println(solve11(transactions));
        System.out.println(solve12(transactions));
        System.out.println("-------------------------------");
        System.out.println(solve13(transactions));*/
        System.out.println(solve14(transactions));
        System.out.println(solve15(transactions));

    }


    public static double solve1(List<TransactionEntity> trans){
        return trans.stream()
                .mapToDouble(t -> t.getAmount())
                .max()
                .orElse(0);
    }

    public static double solve2(List<TransactionEntity> trans){
        return trans.stream()
                .filter(s -> s.isSuccess() == true)
                .mapToDouble(t -> t.getAmount())
                .min()
                .orElse(0);
    }

    public static double solve3(List<TransactionEntity> trans){
        return (double) trans.stream()
                .filter(s -> s.isSuccess() == false)
                .count()/ trans.size();
    }
    
    public static Set<Long> solve4(List<TransactionEntity> trans){
        return trans.stream()
                .map(i -> i.getUserIdFrom())
                .collect(Collectors.toSet());
    }

    public static List<String> solve5(List<TransactionEntity> trans){
        return trans.stream()
                .map(r -> r.getDenyReason())
                .filter(r -> !r.equals("null"))
                .distinct()
                .toList();
    }

    public static Map<Long, Double> solve6(List<TransactionEntity>  trans){
        return trans.stream()
                .filter(t -> t.isSuccess()==true)
                .collect(Collectors.groupingBy(
                        TransactionEntity::getUserIdFrom,
                        Collectors.summingDouble(TransactionEntity::getAmount)
                ));
    }



    public static Map<Long, Double> solve7(List<TransactionEntity> transactions) {
        return transactions.stream()
                .filter(TransactionEntity::isSuccess)
                .collect(Collectors.groupingBy(
                        TransactionEntity::getUserIdTo,
                        Collectors.averagingDouble(TransactionEntity::getAmount)
                ));
    }

    public static List<TransactionEntity> solve8(List<TransactionEntity> trans){
        return trans.stream()
                .sorted(Comparator.comparingDouble(TransactionEntity::getAmount).reversed())
                .limit(5)
                .toList();
    }

    public static List<Long> solve9(List<TransactionEntity> trans){
        return trans.stream()
                .collect(Collectors.groupingBy(
                        TransactionEntity::getUserIdFrom,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream().allMatch(t -> t.isSuccess() ==true)
                        )
                ))
                .entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static Map<Boolean, List<TransactionEntity>> solve10(List<TransactionEntity> trans){
        return trans.stream()
                .collect(Collectors.partitioningBy(TransactionEntity::isSuccess));
    }

    public static Map<Long, Map<Long, Double>> solve11(List<TransactionEntity> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionEntity::getUserIdFrom,
                        Collectors.groupingBy(
                                TransactionEntity::getUserIdTo,
                                Collectors.summingDouble(TransactionEntity::getAmount)
                        )
                ));
    }

    public static Map.Entry<AbstractMap.SimpleEntry<Long, Long>, Long> solve12(List<TransactionEntity> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> new AbstractMap.SimpleEntry<>(t.getUserIdFrom(), t.getUserIdTo()),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No transactions"));
    }


    public static List<TransactionEntity> solve13(List<TransactionEntity> transactions) {
        double average = transactions.stream()
                .mapToDouble(TransactionEntity::getAmount)
                .average()
                .orElse(0.0);
        return transactions.stream()
                .filter(t -> t.getAmount() > average)
                .collect(Collectors.toList());
    }


    public static List<String> solve14(List<TransactionEntity> transactions) {
        return transactions.stream()
                .filter(t -> !t.isSuccess())
                .map(TransactionEntity::getDenyReason)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(e -> e.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static long solve15(List<TransactionEntity> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionEntity::getUserIdTo,
                        Collectors.summingDouble(TransactionEntity::getAmount)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("No transactions"));
    }

}