package org.spine.iquestionapi.util;

import com.opencsv.CSVWriter;
import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Question;
import org.spine.iquestionapi.model.Segment;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The utility class for CSV files
 */
public class CsvUtil {
    /**
     * Write a list of entries to a csv file
     * @param entries the entries
     * @param id the id of the question
     * @return the file
     * @throws Exception if the file cannot be written
     */
    public String entryToCsv(ArrayList<Entry> entries, UUID id) throws Exception {

        try{
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);
            List<String[]> data = new ArrayList<String[]>();

            ArrayList<String> header = new ArrayList<>(
                    Arrays.asList("id", "caregiver", "timestamp"));;

            for (Segment segment:  entries.get(0).getQuestionnaire().getSegments()){
                List<Question> vragen = segment.getQuestions();
                for (Question question: vragen){
                    UUID vraagId = question.getId();
                    header.add(vraagId.toString());
                }
            }
            String[] headerArray = header.toArray(new String[0]);
            data.add(headerArray);


            for (Entry entry: entries){
                ArrayList<String> entryData = new ArrayList<>();
                UUID entryId = entry.getId();
                entryData.add(entryId.toString());
                entryData.add(entry.getCaregiver().getName());
                Long entryTimeStamp = entry.getTimestamp();
                entryData.add(entryTimeStamp.toString());
                for (Segment segment: entry.getQuestionnaire().getSegments()){
                    for(Question question: segment.getQuestions()){
                        entryData.add(question.getLabel());
                    }
                }
                data.add(entryData.toArray(new String[0]));
            }

            csvWriter.writeAll(data);
            csvWriter.close();
            return stringWriter.toString();
        }
        catch (IOException e){
            throw new Exception(e.getMessage());
        }
    }
}