package dtd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by shaong on 1/2/18.
 */

public class Dictionary {
    class Letter {
        Character c;
        Map<Character, Letter> nextLetter = new TreeMap<>();
        boolean isAWord;

        public Letter(Character c, boolean isAWord) {
            this.c = c;
            this.isAWord = isAWord;
        }
    }

    private Letter root = new Letter(null, false);
    private int size = 0;

    public Dictionary() {

    }

    public void putWord(String word) {
        char[] letters = word.toCharArray();
        Letter next = root;
        for(char c : letters) {
            if(next.nextLetter.containsKey(c)) {
                // The letter already exists in the dictionary
                // Go into it
                next = next.nextLetter.get(c);
            } else {
                Letter newLetter = new Letter(c, false);
                next.nextLetter.put(c, newLetter);
                next = next.nextLetter.get(c);
            }

        }
        next.isAWord = true;
        size++;
    }

    public boolean removeWord(String word) throws IndexOutOfBoundsException{
        char[] letters = word.toCharArray();
        Stack<Letter> history = new Stack<>();
        Letter next = root;
        for(char c : letters) {
            if(next.nextLetter.containsKey(c)) {
                Letter l = next.nextLetter.get(c);
                history.push(l);
                next = l;
            } else {
                throw new IndexOutOfBoundsException("Word is not in the dictionary");
            }
        }

        Letter now = history.pop();
        // Check whether the word has more children
        if(!now.nextLetter.isEmpty()) {
            return false;
        } else {
            while(!history.empty()) {
                Letter prev = history.pop();

                if(!now.nextLetter.isEmpty()) {
                    prev.nextLetter.remove(now);
                }
                now = history.pop();
            }
            size--;
            return true;
        }
    }

    private int findDifference(char[] w1, char[] w2) {
        int minLength = (w1.length < w2.length) ? w1.length : w2.length;
        for(int i = 0; i < minLength; i++) {
            if(w1[i] != w2[i]) return i;
        }

        if(w1.length != w2.length) return minLength;

        return w1.length;
    }

    public void addAll(List<String> words) {
        Stack<Letter> history = new Stack<>();
        Letter next = root;

        String previousWord = "";
        for(String word : words) {
            char[] letters = word.toCharArray();
            char[] previousLetters = previousWord.toCharArray();

            int start;
            int trim = previousWord.length() - findDifference(letters, previousLetters);

            // Move pointer back to the similar point
            for(int i = 0; i < trim; i++) {
                next = history.pop();
            }
            start = history.size();

            // Begin from the different point
            for(int i = start; i < letters.length; i++) {

                if(!next.nextLetter.containsKey(letters[i])) {
                    Letter newLetter = new Letter(letters[i], false);
                    next.nextLetter.put(letters[i], newLetter);
                }

                history.push(next);
                next = next.nextLetter.get(letters[i]);
            }
            next.isAWord = true;
            size++;
            previousWord = word;
        }
    }

    public boolean search(String word) {
        char[] letters = word.toCharArray();
        Letter next = root;

        for(char c : letters) {
            if(next.nextLetter.containsKey(c)) {
                next = next.nextLetter.get(c);
            } else {
                return false;
            }
        }

        return next.isAWord;
    }

    public void saveToFile(String filename) {
        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            Stack<Letter> history = new Stack<>();
            Letter next = root;
            int visitedCount = 0;

            writeChar(writer, next);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeChar(BufferedWriter writer, Letter node) throws IOException{
        for(Map.Entry<Character, Letter> entry : node.nextLetter.entrySet()) {
            writer.write('>');
            writer.write(entry.getKey());
            if(entry.getValue().isAWord) {
                writer.write('#');
            }
            if(!entry.getValue().nextLetter.isEmpty()) {
                writeChar(writer, entry.getValue());
            }
            writer.write('<');
        }
    }

    public void readFromFile(String filename) {
        Path path = Paths.get(filename);

        try(BufferedReader reader = Files.newBufferedReader(path)) {
            Stack<Letter> history = new Stack<>();
            Letter next = root;
            char previousChar = ' ';
            int token = reader.read();
            while(token != -1) {
                char c = (char) token;
                if(c == '>') {
                    if(previousChar != ' ' && previousChar != '<') {
                        history.push(next.nextLetter.get(previousChar));
                        next = next.nextLetter.get(previousChar);
                    } else {
                        history.push(next);
                    }
                } else if(c == '<') {
                    next = history.pop();
                } else if(c == '#'){
                    next.nextLetter.get(previousChar).isAWord = true;
                    size++;
                } else {
                    Letter letter = new Letter(c, false);
                    next.nextLetter.put(c, letter);
                }

                if(c != '#') {
                    previousChar = c;
                }
                token = reader.read();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int size() {
        return size;
    }
}
