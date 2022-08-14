import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        AtomicInteger counWordsOfLength3 = new AtomicInteger();
        AtomicInteger counWordsOfLength4 = new AtomicInteger();
        AtomicInteger counWordsOfLength5 = new AtomicInteger();

        List<Thread> threads = new ArrayList<>();

        CopyOnWriteArraySet<String> allWords = new CopyOnWriteArraySet<>();

        Runnable logic1 = () -> {
            for (String text : texts) {
                StringBuilder strBuilder = new StringBuilder(text);
                strBuilder.reverse();
                String invertedText = strBuilder.toString();
                if (text.equals(invertedText) && !allWords.contains(text)) {
                    allWords.add(text);
                    switch (text.length()) {
                        case 3:
                            counWordsOfLength3.getAndIncrement();
                        case 4:
                            counWordsOfLength4.getAndIncrement();
                        case 5:
                            counWordsOfLength5.getAndIncrement();
                    }
                }
            }
            return;
        };

        Runnable logic2 = () -> {
            for (String text : texts) {
                char[] teaxtAsChar = text.toCharArray();
                int countOfSame = 0;
                for (int i = 0; i < teaxtAsChar.length - 1; i++) {
                    if (teaxtAsChar[i] == teaxtAsChar[i + 1])
                        countOfSame++;
                }
                if (countOfSame == teaxtAsChar.length - 1 && !allWords.contains(text)) {
                    allWords.add(text);
                    switch (text.length()) {
                        case 3:
                            counWordsOfLength3.getAndIncrement();
                        case 4:
                            counWordsOfLength4.getAndIncrement();
                        case 5:
                            counWordsOfLength5.getAndIncrement();
                    }
                }
            }
            return;
        };

        Runnable logic3 = () -> {
            for (String text : texts) {
                char[] teaxtAsChar = text.toCharArray();
                int countOfSame = 0;
                for (int i = 0; i < teaxtAsChar.length - 1; i++) {
                    if (teaxtAsChar[i] <= teaxtAsChar[i + 1])
                        countOfSame++;
                }
                if (countOfSame == teaxtAsChar.length - 1 && !allWords.contains(text)) {
                    allWords.add(text);
                    switch (text.length()) {
                        case 3:
                            counWordsOfLength3.getAndIncrement();
                        case 4:
                            counWordsOfLength4.getAndIncrement();
                        case 5:
                            counWordsOfLength5.getAndIncrement();
                    }
                }
            }
            return;
        };

        Thread myThread1 = new Thread(logic1);
        threads.add(myThread1);
        Thread myThread2 = new Thread(logic2);
        threads.add(myThread2);
        Thread myThread3 = new Thread(logic3);
        threads.add(myThread3);

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        System.out.println("Красивых слов с длиной 3: " + counWordsOfLength3 + " шт." + '\n' +
                "Красивых слов с длиной 4: " + counWordsOfLength4 + " шт." + '\n' +
                "Красивых слов с длиной 5: " + counWordsOfLength5 + " шт." + '\n');

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
