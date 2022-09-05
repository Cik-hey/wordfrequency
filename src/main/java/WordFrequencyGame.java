import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {
    public static final int INIT_COUNT = 1;
    public String getResult(String inputString){
        if (splitInput(inputString).length== INIT_COUNT) {
            return inputString + " 1";
        } else {
            try {
                List<Input> inputList = convertToWordFrequencyList(inputString);

                //get the map for the next step of sizing the same word
                Map<String, List<Input>> map =getListMap(inputList);

                StringJoiner joiner = getStringJoiner(map);
                return joiner.toString();
            } catch (Exception e) {
                return "Calculate Error";
            }
        }
    }
    private StringJoiner getStringJoiner(Map<String, List<Input>> map) {
        List<Input> inputList;
        List<Input> list = new ArrayList<>();
        for (Map.Entry<String, List<Input>> entry : map.entrySet()){
            Input input = new Input(entry.getKey(), entry.getValue().size());
            list.add(input);
        }
        inputList = list;

        inputList.sort((firstWord, secondWord) -> secondWord.getWordCount() - firstWord.getWordCount());

        StringJoiner joiner = new StringJoiner("\n");
        for (Input word : inputList) {
            String string = word.getValue() + " " +word.getWordCount();
            joiner.add(string);
        }
        return joiner;
    }
    private List<Input> convertToWordFrequencyList(String inputStr) {
        String[] inputWords = splitInput(inputStr);

        return Arrays.asList(inputWords).stream()
                .map(word -> new Input(word, INIT_COUNT))
                .collect(Collectors.toList());
    }
    private String[] splitInput(String inputStr) {
        return inputStr.split("\\s+");
    }
    private Map<String,List<Input>> getListMap(List<Input> inputList) {
        Map<String, List<Input>> mappedList = new HashMap<>();
        inputList.forEach(eachListValue -> {
            if(!mappedList.containsKey(eachListValue.getValue())) createNewListPerWord(mappedList, eachListValue);
            else mappedList.get(eachListValue.getValue()).add(eachListValue);
        });
        return mappedList;
    }
    private void createNewListPerWord(Map<String, List<Input>> mappedList, Input eachInputList) {
        ArrayList<Input> newInputList = new ArrayList<>();
        newInputList.add(eachInputList);
        mappedList.put(eachInputList.getValue(), newInputList);
    }
}
