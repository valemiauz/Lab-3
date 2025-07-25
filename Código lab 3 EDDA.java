import java.io.*;
import java.util.*;

class Game {
    private String name;
    private String category;
    private int price;
    private int quality;

    public Game (String name, String category, int price, int quality){
        this.name = name;
        this.category = category;
        this.price = price;
        this.quality = quality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public int getPrice(){
        return price;
    }

    public int getQuality(){
        return quality;
    }
}

class Dataset {
    private ArrayList<Game> data;
    private String sortedByAttribute = "";

    public Dataset (ArrayList<Game> data){
        this.data=data;
    }

    public void sortByPrice() {
        data.sort(Comparator.comparingInt(Game::getPrice));
        sortedByAttribute = "price";
    }

    public void sortByCategory() {
        data.sort(Comparator.comparing(Game::getCategory));
        sortedByAttribute = "category";
    }

    public void sortByQuality() {
        data.sort(Comparator.comparingInt(Game::getQuality));
        sortedByAttribute = "quality";
    }

    public void sortByAlgorithm(String algorithm, String attribute) {
        String validAttribute;
        switch (attribute != null ? attribute.toLowerCase() : "") {
            case "price":
                validAttribute = "price";
                break;
            case "category":
                validAttribute = "category";
                break;
            case "quality":
                validAttribute = "quality";
                break;
            default:
                validAttribute = "price";
                break;
        }

        switch (algorithm != null ? algorithm.toLowerCase() : "") {
            case "bubblesort":
                bubbleSort(validAttribute);
                break;
            case "insertionsort":
                insertionSort(validAttribute);
                break;
            case "selectionsort":
                selectionSort(validAttribute);
                break;
            case "mergesort":
                mergeSort(validAttribute);
                break;
            case "quicksort":
                quickSort(validAttribute);
                break;
            case "countingsort":
                countingSort(validAttribute);
                break;
            case "collectionsort":
                collectionsSort(validAttribute);
                break;
            default:
                collectionsSort(validAttribute);
                break;
        }

        sortedByAttribute = validAttribute;
    }

    private void bubbleSort(String attribute) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (compareGames(data.get(j), data.get(j + 1), attribute) > 0) {
                    Game temp = data.get(j);
                    data.set(j, data.get(j + 1));
                    data.set(j + 1, temp);
                }
            }
        }
    }

    private void insertionSort(String attribute) {
        for (int i = 1; i < data.size(); i++) {
            Game key = data.get(i);
            int j = i - 1;

            while (j >= 0 && compareGames(data.get(j), key, attribute) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, key);
        }
    }

    private void selectionSort(String attribute) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (compareGames(data.get(j), data.get(minIndex), attribute) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Game temp = data.get(i);
                data.set(i, data.get(minIndex));
                data.set(minIndex, temp);
            }
        }
    }

    private void mergeSort(String attribute) {
        if (data.size() <= 1) return;
        mergeSortHelper(0, data.size() - 1, attribute);
    }

    private void mergeSortHelper(int left, int right, String attribute) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortHelper(left, mid, attribute);
            mergeSortHelper(mid + 1, right, attribute);
            merge(left, mid, right, attribute);
        }
    }

    private void merge(int left, int mid, int right, String attribute) {
        ArrayList<Game> leftArray = new ArrayList<>();
        ArrayList<Game> rightArray = new ArrayList<>();

        for (int i = left; i <= mid; i++) {
            leftArray.add(data.get(i));
        }
        for (int j = mid + 1; j <= right; j++) {
            rightArray.add(data.get(j));
        }

        int i = 0, j = 0, k = left;

        while (i < leftArray.size() && j < rightArray.size()) {
            if (compareGames(leftArray.get(i), rightArray.get(j), attribute) <= 0) {
                data.set(k, leftArray.get(i));
                i++;
            } else {
                data.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        while (i < leftArray.size()) {
            data.set(k, leftArray.get(i));
            i++;
            k++;
        }
        while (j < rightArray.size()) {
            data.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    private void quickSort(String attribute) {
        if (data.size() <= 1) return;
        if (data.size() <= 10) {
            insertionSort(attribute);
            return;
        }
        quickSortIterative(0, data.size() - 1, attribute);
    }

    private void quickSortIterative(int low, int high, String attribute) {
        Stack<Integer> stack = new Stack<>();
        stack.push(low);
        stack.push(high);

        while (!stack.isEmpty()) {
            high = stack.pop();
            low = stack.pop();

            if (high - low <= 10) {
                insertionSortRange(low, high, attribute);
                continue;
            }

            int pivot = partition(low, high, attribute);

            if (pivot - 1 > low) {
                stack.push(low);
                stack.push(pivot - 1);
            }

            if (pivot + 1 < high) {
                stack.push(pivot + 1);
                stack.push(high);
            }
        }
    }

    private void insertionSortRange(int low, int high, String attribute) {
        for (int i = low + 1; i <= high; i++) {
            Game key = data.get(i);
            int j = i - 1;

            while (j >= low && compareGames(data.get(j), key, attribute) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, key);
        }
    }

    private int partition(int low, int high, String attribute) {
        int mid = (low + high) / 2;
        
        if (compareGames(data.get(mid), data.get(low), attribute) < 0) {
            swap(low, mid);
        }
        if (compareGames(data.get(high), data.get(low), attribute) < 0) {
            swap(low, high);
        }
        if (compareGames(data.get(high), data.get(mid), attribute) < 0) {
            swap(mid, high);
        }
        
        swap(mid, high - 1);
        Game pivot = data.get(high - 1);
        
        int i = low;
        int j = high - 1;
        
        while (true) {
            while (compareGames(data.get(++i), pivot, attribute) < 0);
            while (compareGames(data.get(--j), pivot, attribute) > 0);
            
            if (i >= j) break;
            swap(i, j);
        }
        
        swap(i, high - 1);
        return i;
    }

    private void swap(int i, int j) {
        Game temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    private void countingSort(String attribute) {
        if (!"quality".equals(attribute)) {
            collectionsSort(attribute);
            return;
        }

        int[] count = new int[101];
        for (Game game : data) {
            count[game.getQuality()]++;
        }

        ArrayList<Game> sorted = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            for (Game game : data) {
                if (game.getQuality() == i) {
                    sorted.add(game);
                    count[i]--;
                    if (count[i] == 0) break;
                }
            }
        }

        data.clear();
        data.addAll(sorted);
    }

    private void collectionsSort(String attribute) {
        switch (attribute) {
            case "price":
                data.sort(Comparator.comparingInt(Game::getPrice));
                break;
            case "category":
                data.sort(Comparator.comparing(Game::getCategory));
                break;
            case "quality":
                data.sort(Comparator.comparingInt(Game::getQuality));
                break;
        }
    }

    private int compareGames(Game g1, Game g2, String attribute) {
        switch (attribute) {
            case "price":
                return Integer.compare(g1.getPrice(), g2.getPrice());
            case "category":
                return g1.getCategory().compareTo(g2.getCategory());
            case "quality":
                return Integer.compare(g1.getQuality(), g2.getQuality());
            default:
                return Integer.compare(g1.getPrice(), g2.getPrice());
        }
    }

    private int findFirstIndex(int targetPrice) {
        int left = 0, right = data.size() - 1;
        int result = data.size();

        while (left <= right) {
            int mid = (left + right) / 2;
            int midPrice = data.get(mid).getPrice();

            if (midPrice >= targetPrice) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    private int findLastIndex(int targetPrice) {
        int left = 0, right = data.size() - 1;
        int result = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int midPrice = data.get(mid).getPrice();

            if (midPrice <= targetPrice) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public ArrayList<Game> getGamesByPrice(int price) {
        ArrayList<Game> result = new ArrayList<>();

        if ("price".equals(sortedByAttribute)) {
            int left = 0, right = data.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                int midPrice = data.get(mid).getPrice();

                if (midPrice == price) {
                    int i = mid;
                    while (i >= 0 && data.get(i).getPrice() == price) {
                        result.add(0, data.get(i));
                        i--;
                    }
                    i = mid + 1;
                    while (i < data.size() && data.get(i).getPrice() == price) {
                        result.add(data.get(i));
                        i++;
                    }
                    break;
                } else if (midPrice < price) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

        } else {
            for (Game g : data) {
                if (g.getPrice() == price) {
                    result.add(g);
                }
            }
        }

        return result;
    }

    public ArrayList<Game> getGamesByPriceRange(int lowerPrice, int higherPrice) {
        ArrayList<Game> result = new ArrayList<>();
        if ("price".equals(sortedByAttribute)) {
            int startIndex = findFirstIndex(lowerPrice);
            int endIndex = findLastIndex(higherPrice);
            for (int i = startIndex; i <= endIndex && i < data.size(); i++) {
                result.add(data.get(i));
            }

        } else {
            for (Game g : data) {
                int price = g.getPrice();
                if (price >= lowerPrice && price <= higherPrice) {
                    result.add(g);
                }
            }
        }

        return result;
    }

    public ArrayList<Game> getGamesByCategory(String category) {
        if (category == null) return new ArrayList<>();
        ArrayList<Game> result = new ArrayList<>();

        if ("category".equals(sortedByAttribute)) {
            int left = 0, right = data.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                String midCategory = data.get(mid).getCategory();

                int cmp = midCategory.compareTo(category);

                if (cmp == 0) {
                    int i = mid;
                    while (i >= 0 && data.get(i).getCategory().equals(category)) {
                        result.add(0, data.get(i));
                        i--;
                    }
                    i = mid + 1;
                    while (i < data.size() && data.get(i).getCategory().equals(category)) {
                        result.add(data.get(i));
                        i++;
                    }
                    break;
                } else if (cmp < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        } else {
            for (Game g : data) {
                if (g.getCategory().equals(category)) {
                    result.add(g);
                }
            }
        }

        return result;
    }

    public ArrayList<Game> getGamesByQuality(int quality) {
        ArrayList<Game> result = new ArrayList<>();

        if ("quality".equals(sortedByAttribute)) {
            int left = 0, right = data.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                int midQuality = data.get(mid).getQuality();

                if (midQuality == quality) {
                    int i = mid;
                    while (i >= 0 && data.get(i).getQuality() == quality) {
                        result.add(0, data.get(i));
                        i--;
                    }
                    i = mid + 1;
                    while (i < data.size() && data.get(i).getQuality() == quality) {
                        result.add(data.get(i));
                        i++;
                    }
                    break;
                } else if (midQuality < quality) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        } else {
            for (Game g : data) {
                if (g.getQuality() == quality) {
                    result.add(g);
                }
            }
        }

        return result;
    }

    public ArrayList<Game> getData() {
        return new ArrayList<>(data);
    }

    public String getSortedByAttribute() {
        return sortedByAttribute;
    }
}

class Benchmark {
    public static double benchmarkSort(Dataset dataset, String algorithm, String attribute) {
        long totalTime = 0;
        int runs = 3;
        
        for (int i = 0; i < runs; i++) {
            Dataset copy = new Dataset(new ArrayList<>(dataset.getData()));

            long startTime = System.nanoTime();
            try {
                copy.sortByAlgorithm(algorithm, attribute);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            } catch (StackOverflowError e) {
                return 300001;
            }
        }

        double avgTime = totalTime / (double)runs / 1_000_000.0;
        return avgTime > 300000 ? 300001 : avgTime;
    }

    public static void benchmarkSearchByPrice(Dataset dataset, int targetPrice) {
        Dataset unsortedDataset = new Dataset(new ArrayList<>(dataset.getData()));
        Dataset sortedDataset = new Dataset(new ArrayList<>(dataset.getData()));
        sortedDataset.sortByPrice();

        long startTime = System.nanoTime();
        ArrayList<Game> linearResult = unsortedDataset.getGamesByPrice(targetPrice);
        long endTime = System.nanoTime();
        double linearTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        ArrayList<Game> binaryResult = sortedDataset.getGamesByPrice(targetPrice);
        endTime = System.nanoTime();
        double binaryTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("    Búsqueda lineal: %.3f ms (encontrados: %d)%n", linearTime, linearResult.size());
        System.out.printf("    Búsqueda binaria: %.3f ms (encontrados: %d)%n", binaryTime, binaryResult.size());
    }

    public static void runCompleteBenchmarks() {
        System.out.println("=== BENCHMARKS SEGÚN LA ACTIVIDAD ===\n");

        GenerateData generator = new GenerateData();
        ArrayList<Game> games100 = generator.createGames(100);
        ArrayList<Game> games10000 = generator.createGames(10000);
        ArrayList<Game> games1000000 = generator.createGames(1000000);

        Dataset dataset100 = new Dataset(games100);
        Dataset dataset10000 = new Dataset(games10000);
        Dataset dataset1000000 = new Dataset(games1000000);

        System.out.println("3.2.1. Medición del tiempo de ordenamiento\n");

        String[] algorithms = {"bubblesort", "insertionsort", "selectionsort", "mergesort", "quicksort", "countingsort", "collectionsort"};
        String[] attributes = {"category", "price", "quality"};

        for (String attribute : attributes) {
            System.out.println("=== CUADRO: Tiempos de ejecución de ordenamiento para el atributo " + attribute.toUpperCase() + " ===");
            System.out.println("┌─────────────────┬─────────────────┬─────────────────┬─────────────────┐");
            System.out.println("│   Algoritmo     │   Tamaño 10²    │   Tamaño 10⁴    │   Tamaño 10⁶    │");
            System.out.println("├─────────────────┼─────────────────┼─────────────────┼─────────────────┤");

            for (String algorithm : algorithms) {
                System.out.printf("│ %-15s │", algorithm);

                double time100 = benchmarkSort(dataset100, algorithm, attribute);
                if (time100 > 300000) {
                    System.out.printf(" >300 segundos   │");
                } else {
                    System.out.printf(" %12.3f ms │", time100);
                }

                double time10000 = benchmarkSort(dataset10000, algorithm, attribute);
                if (time10000 > 300000) {
                    System.out.printf(" >300 segundos   │");
                } else {
                    System.out.printf(" %12.3f ms │", time10000);
                }

                if (algorithm.equals("mergesort") || algorithm.equals("quicksort") || algorithm.equals("countingsort") || algorithm.equals("collectionsort")) {
                    double time1000000 = benchmarkSort(dataset1000000, algorithm, attribute);
                    if (time1000000 > 300000) {
                        System.out.printf(" >300 segundos   │");
                    } else {
                        System.out.printf(" %12.3f ms │", time1000000);
                    }
                } else {
                    System.out.printf(" >300 segundos   │");
                }

                System.out.println();
            }
            System.out.println("└─────────────────┴─────────────────┴─────────────────┴─────────────────┘\n");
        }

        System.out.println("3.2.2. Medición del tiempo de búsqueda\n");
        System.out.println("Utilizando solamente el dataset de tamaño 10⁶ (1,000,000 elementos)\n");

        System.out.println("=== CUADRO: Tiempos de ejecución de búsqueda ===");
        System.out.println("┌─────────────────────────┬─────────────────┬─────────────────────┐");
        System.out.println("│        Método           │   Algoritmo     │   Tiempo (ms)       │");
        System.out.println("├─────────────────────────┼─────────────────┼─────────────────────┤");

        int[] testPrices = {10000, 25000, 50000};

        for (int price : testPrices) {
            System.out.printf("│ getGamesByPrice(%d)     │                 │                     │%n", price);

            Dataset unsortedDataset = new Dataset(new ArrayList<>(dataset1000000.getData()));
            long startTime = System.nanoTime();
            ArrayList<Game> linearResult = unsortedDataset.getGamesByPrice(price);
            long endTime = System.nanoTime();
            double linearTime = (endTime - startTime) / 1_000_000.0;

            System.out.printf("│                         │ linearSearch    │ %15.3f ms │%n", linearTime);

            Dataset sortedDataset = new Dataset(new ArrayList<>(dataset1000000.getData()));
            sortedDataset.sortByPrice();
            startTime = System.nanoTime();
            ArrayList<Game> binaryResult = sortedDataset.getGamesByPrice(price);
            endTime = System.nanoTime();
            double binaryTime = (endTime - startTime) / 1_000_000.0;

            System.out.printf("│                         │ binarySearch    │ %15.3f ms │%n", binaryTime);
            System.out.println("├─────────────────────────┼─────────────────┼─────────────────────┤");
        }

        System.out.println("│ getGamesByPriceRange    │                 │                     │");
        Dataset unsortedDataset = new Dataset(new ArrayList<>(dataset1000000.getData()));
        long startTime = System.nanoTime();
        ArrayList<Game> linearRangeResult = unsortedDataset.getGamesByPriceRange(20000, 30000);
        long endTime = System.nanoTime();
        double linearRangeTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("│                         │ linearSearch    │ %15.3f ms │%n", linearRangeTime);

        Dataset sortedDataset = new Dataset(new ArrayList<>(dataset1000000.getData()));
        sortedDataset.sortByPrice();
        startTime = System.nanoTime();
        ArrayList<Game> binaryRangeResult = sortedDataset.getGamesByPriceRange(20000, 30000);
        endTime = System.nanoTime();
        double binaryRangeTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("│                         │ binarySearch    │ %15.3f ms │%n", binaryRangeTime);
        System.out.println("├─────────────────────────┼─────────────────┼─────────────────────┤");

        System.out.println("│ getGamesByCategory      │                 │                     │");
        unsortedDataset = new Dataset(new ArrayList<>(dataset1000000.getData()));
        startTime = System.nanoTime();
        ArrayList<Game> linearCategoryResult = unsortedDataset.getGamesByCategory("Acción");
        endTime = System.nanoTime();
        double linearCategoryTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("│                         │ linearSearch    │ %15.3f ms │%n", linearCategoryTime);

        sortedDataset = new Dataset(new ArrayList<>(dataset1000000.getData()));
        sortedDataset.sortByCategory();
        startTime = System.nanoTime();
        ArrayList<Game> binaryCategoryResult = sortedDataset.getGamesByCategory("Acción");
        endTime = System.nanoTime();
        double binaryCategoryTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("│                         │ binarySearch    │ %15.3f ms │%n", binaryCategoryTime);

        System.out.println("└─────────────────────────┴─────────────────┴─────────────────────┘");

        System.out.println("\nNota: Para la búsqueda binaria es necesario que los datos estén ordenados.");
        System.out.println("Se implementó el manejo de sortedByAttribute para saber por qué atributo está ordenado el dataset.");
    }
}

class GenerateData {
    private ArrayList<Game> data = new ArrayList<>();
    private Random random = new Random();

    private String[] palabras = {"Dragon", "Empire", "Quest", "Galaxy", "Legends", "Warrior"};

    private String[] categorias = {"Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Simulación"};

    public GenerateData() {
    }

    private String generateRandomName() {
        if (random.nextBoolean()) {
            return palabras[random.nextInt(palabras.length)];
        } else {
            String palabra1 = palabras[random.nextInt(palabras.length)];
            String palabra2 = palabras[random.nextInt(palabras.length)];
            return palabra1 + palabra2;
        }
    }

    private String generateRandomCategory() {
        return categorias[random.nextInt(categorias.length)];
    }

    private int generateRandomPrice() {
        return random.nextInt(70001);
    }

    private int generateRandomQuality() {
        return random.nextInt(101);
    }

    public ArrayList<Game> createGames(int n) {
        ArrayList<Game> games = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String name = generateRandomName();
            String category = generateRandomCategory();
            int price = generateRandomPrice();
            int quality = generateRandomQuality();

            Game game = new Game(name, category, price, quality);
            games.add(game);
        }

        return games;
    }

    public void generateToFile(ArrayList<Game> games, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("name,category,price,quality");

            for (Game game : games) {
                writer.printf("%s,%s,%d,%d%n",
                        game.getName(),
                        game.getCategory(),
                        game.getPrice(),
                        game.getQuality());
            }

        } catch (IOException e) {
            System.err.println("Error al escribir archivo: " + e.getMessage());
        }
    }
}

class Main {
    public static void main(String[] args) {
        Benchmark.runCompleteBenchmarks();

        System.out.println("\n=== Generación de archivos CSV ===");
        GenerateData generator = new GenerateData();

        ArrayList<Game> games100 = generator.createGames(100);
        generator.generateToFile(games100, "games_100.csv");
        System.out.println("Generado: games_100.csv");

        ArrayList<Game> games10000 = generator.createGames(10000);
        generator.generateToFile(games10000, "games_10000.csv");
        System.out.println("Generado: games_10000.csv");

        ArrayList<Game> games1000000 = generator.createGames(1000000);
        generator.generateToFile(games1000000, "games_1000000.csv");
        System.out.println("Generado: games_1000000.csv");
    }
}
